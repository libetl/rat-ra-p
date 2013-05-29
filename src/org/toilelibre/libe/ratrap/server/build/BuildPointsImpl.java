package org.toilelibre.libe.ratrap.server.build;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.toilelibre.libe.ratrap.shared.City;
import org.toilelibre.libe.ratrap.shared.DataMap;
import org.toilelibre.libe.ratrap.shared.Position;
import org.toilelibre.libe.ratrap.shared.Rectangle;
import org.toilelibre.libe.ratrap.shared.Station;

public class BuildPointsImpl implements Builder {
	private DataMap dataMap;

	@Override
	public void build(final String input) throws IOException {
		final Rectangle rectangle = this.dataMap.getBounds();

		final Map<Double, Station> sameStations = new HashMap<Double, Station>();

		final BufferedReader reader = new BufferedReader(
				new StringReader(input));
		String line = "";
		while ((line = reader.readLine()) != null) {
			line = line.replace("&#39;", "'");
			final String[] fields = line.split("#");
			final Float latitude = Float.valueOf(fields[2]);
			final Float longitude = Float.valueOf(fields[1]);
			final Position position = new Position(latitude, longitude);
			Station station = sameStations.get(new Double(Math.floor(latitude
					.floatValue() * 1000)
					+ Math.floor(longitude.floatValue() * 1000) / 1000));
			if (station == null) {
				station = this.dataMap.getStationsByName().get(fields[3]);
			}
			if (station == null) {
				String lettersName = fields[3].toUpperCase();
				Matcher m = Pattern.compile("^[A-Z]").matcher(lettersName);
				lettersName = m.replaceAll("");
				station = this.dataMap.getStationsByLettersName()
						.get(fields[3]);
			}
			if (station == null) {
				station = new Station();
				station.setId(Integer.valueOf(fields[0]));
				station.setPosition(position);
				station.setName(fields[3]);
				City city = this.dataMap.getCities().get(fields[4]);

				if (city == null) {
					city = new City();
					city.setName(fields[4]);
					city.setCenter(new Position());
					this.dataMap.getCities().put(fields[4], city);
				}

				final double latitudeCityCenter = (((city.getCenter()
						.getLatitude() * city.getStations().size()) + position
						.getLatitude()) / (city.getStations().size() + 1.0));
				final double longitudeCityCenter = (((city.getCenter()
						.getLongitude() * city.getStations().size()) + position
						.getLongitude()) / (city.getStations().size() + 1.0));

				city.setCenter(new Position(latitudeCityCenter,
						longitudeCityCenter));
				station.setCity(city);
				city.getStations().add(station);
			} else {
				station.addAlias(fields[3]);
			}

			if (rectangle.getLatMax() < position.getLatitude()) {
				rectangle.setLatMax(position.getLatitude());
			}
			if (rectangle.getLatMin() > position.getLatitude()) {
				rectangle.setLatMin(position.getLatitude());
			}
			if (rectangle.getLongMax() < position.getLongitude()) {
				rectangle.setLongMax(position.getLongitude());
			}
			if (rectangle.getLongMin() > position.getLongitude()) {
				rectangle.setLongMin(position.getLongitude());
			}
			this.dataMap.getStations().put(station.getId(), station);
			this.dataMap.getStationsByName().put(station.getName(), station);

			String lettersName = station.getName().toUpperCase();
			Matcher m = Pattern.compile("^[A-Z]").matcher(lettersName);
			lettersName = m.replaceAll("");
			this.dataMap.getStationsByLettersName().put(lettersName, station);

			sameStations
					.put(new Double(Math.floor(latitude.floatValue() * 1000)
							+ Math.floor(longitude.floatValue() * 1000) / 1000),
							station);

		}
	}

	@Override
	public void setDataMap(final DataMap dm) {
		this.dataMap = dm;
	}

}
