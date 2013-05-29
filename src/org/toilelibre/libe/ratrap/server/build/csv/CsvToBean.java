package org.toilelibre.libe.ratrap.server.build.csv;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;

public class CsvToBean {
	private Package packageName;
	private String[] headers;

	public CsvToBean() {
		this.packageName = this.getClass().getPackage();
	}

	public CsvToBean(Package packageName1) {
		this.packageName = packageName1;
	}

	public void initHeaders(String firstLine) {
		this.headers = firstLine.split(",");
		for (int i = 0; i < this.headers.length; i++) {
			this.headers[i] = this.camelcasify(this.headers[i]);
		}
	}

	private String camelcasify(String in) {
		StringBuilder sb = new StringBuilder();
		boolean capitalizeNext = false;
		for (char c : in.toCharArray()) {
			if (c == '_') {
				capitalizeNext = true;
			} else {
				if (capitalizeNext) {
					sb.append(Character.toUpperCase(c));
					capitalizeNext = false;
				} else {
					sb.append(c);
				}
			}
		}
		return sb.toString();
	}

	public List<Object> toBeans(String fileName) throws IOException,
			ClassNotFoundException, InstantiationException,
			IllegalAccessException, NoSuchFieldException, SecurityException {
		if (fileName.indexOf('/') == -1) {
			return this.toBeans("", fileName);
		}
		return this.toBeans(fileName.substring(0, fileName.lastIndexOf('/')),
				fileName.substring(fileName.lastIndexOf('/') + 1));
	}

	public void toDatabase(EntityManager session, String fileName)
			throws IOException, ClassNotFoundException, InstantiationException,
			IllegalAccessException, NoSuchFieldException, SecurityException {
		if (fileName.indexOf('/') == -1) {
			this.toDatabase(session, "", fileName);
		}
		this.toDatabase(session,
				fileName.substring(0, fileName.lastIndexOf('/')),
				fileName.substring(fileName.lastIndexOf('/') + 1));
	}

	public List<Object> toBeans(String dir, String textFileName)
			throws IOException, ClassNotFoundException, InstantiationException,
			IllegalAccessException, NoSuchFieldException, SecurityException {
		BufferedReader br = new BufferedReader(new FileReader(dir + "/"
				+ textFileName));
		List<Object> results = new ArrayList<Object>();
		this.initHeaders(br.readLine());
		String line = "";
		while ((line = br.readLine()) != null) {
			results.add(this.toBean(textFileName, line));
		}
		br.close();
		return results;
	}

	public void toDatabase(EntityManager session, String dir, String textFileName)
			throws IOException, ClassNotFoundException, InstantiationException,
			IllegalAccessException, NoSuchFieldException, SecurityException {
		BufferedReader br = new BufferedReader(new FileReader(dir + "/"
				+ textFileName));
		this.initHeaders(br.readLine());
		String line = "";
		while ((line = br.readLine()) != null) {
			session.persist(this.toBean(textFileName, line));
		}
		br.close();
	}

	public Object toBean(String textFileName, String line)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, NoSuchFieldException, SecurityException {
		Object result = null;
		if (textFileName == null || this.headers == null) {
			return result;
		}
		String objectName = textFileName.endsWith(".txt") ? textFileName
				.substring(0, textFileName.lastIndexOf(".txt")) : textFileName;
		Class<?> c = Class.forName(this.packageName.getName() + "."
				+ objectName.substring(0, 1).toUpperCase() + this.camelcasify((objectName.substring(1))));
		result = c.newInstance();
		Pattern p = Pattern.compile("([^\",]+|(\"([^\"]*)\"))(,)?");
		Matcher m = p.matcher(line);
		int i = 0;
		while (m.find()) {
			String index = this.headers[i++];
			String value = (m.group(3) != null ? m.group(3) : m.group(1));
			Field f = result.getClass().getDeclaredField(index);
			f.setAccessible(true);
			this.setValue(f, result, value);
		}
		return result;
	}

	private void setValue(Field f, Object result, String value)
			throws IllegalArgumentException, IllegalAccessException {
		if (String.class.equals(f.getType())) {
			f.set(result, value);
		} else if (boolean.class.equals(f.getType())) {
			f.set(result, "1".equals(value));
		} else if (long.class.equals(f.getType())) {
			f.set(result, Long.parseLong(value));
		} else if (int.class.equals(f.getType())) {
			f.set(result, Integer.parseInt(value));
		} else if (Date.class.equals(f.getType())) {
			DateFormat dfd = new SimpleDateFormat("yyyyMMdd");
			DateFormat dft = new SimpleDateFormat("hh:mm:ss");
			try {
				Date d = dfd.parse(value);
				f.set(result, d);
			} catch (ParseException pe) {
			}
			try {
				Date d = dft.parse(value);
				f.set(result, d);
			} catch (ParseException pe) {
			}
		}
	}
}
