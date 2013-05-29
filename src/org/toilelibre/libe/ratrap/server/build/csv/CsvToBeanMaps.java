package org.toilelibre.libe.ratrap.server.build.csv;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Id;

public class CsvToBeanMaps {
	private Package packageName;
	private String[] headers;
	
	private Map<String, Object> data;

	
	public CsvToBeanMaps() {
		this.packageName = this.getClass().getPackage();
		this.data = new HashMap<String, Object>();
	}

	public CsvToBeanMaps(Package packageName1) {
		this.packageName = packageName1;
		this.data = new HashMap<String, Object>();
	}

	public void initHeaders(String firstLine) {
		this.headers = firstLine.split(",");
		for (int i = 0; i < this.headers.length; i++) {
			this.headers[i] = this.camelCasify(this.headers[i]);
		}
	}

	private String camelCasify(String in) {
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

	public Map<Object, Object> toBeanMaps(String fileName) throws IOException,
			ClassNotFoundException, InstantiationException,
			IllegalAccessException, NoSuchFieldException, SecurityException {
		if (fileName.indexOf('/') == -1) {
			return this.toBeanMaps("", fileName);
		}
		return this.toBeanMaps(fileName.substring(0, fileName.lastIndexOf('/')),
				fileName.substring(fileName.lastIndexOf('/') + 1));
	}

	public Map<Object, Object> toBeanMaps(String dir, String textFileName)
			throws IOException, ClassNotFoundException, InstantiationException,
			IllegalAccessException, NoSuchFieldException, SecurityException {
		BufferedReader br = new BufferedReader(new FileReader(dir + "/"
				+ textFileName));
		Map<Object, Object> results = new HashMap<Object, Object>();
		this.initHeaders(br.readLine());
		String line = "";
		while ((line = br.readLine()) != null) {
			Object o = this.toBean(textFileName, line);
			String pk = this.getPk (o);
			results.put(pk, o);
			this.data.put(o.getClass().getSimpleName() + "#" + pk, o);
		}
		br.close();
		return results;
	}

	private String getPk(Object o) throws IllegalArgumentException, IllegalAccessException {
		Class<?> c = o.getClass();
		StringBuffer sb = new StringBuffer ();
		for (Field f : c.getDeclaredFields()){
			if (f.getAnnotation(Id.class) != null){
				if (f.getType() != null &&
						this.packageName.equals(f.getType().getPackage())){
					return this.getPk(f.get(o));
				}
				if (sb.length() > 0){
					sb.append("&");
				}
				sb.append(f.getName());
				sb.append("=");
				sb.append(f.get(o));
			}
		}
		return sb.toString();
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
				+ objectName.substring(0, 1).toUpperCase() + this.camelCasify((objectName.substring(1))));
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
		this.fillReferences (result);
		this.fillRelatedRows (result);
		return result;
	}

	private void fillReferences(Object result) {
		
	}

	private void fillRelatedRows(Object result) {
		// TODO Auto-generated method stub
		
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
