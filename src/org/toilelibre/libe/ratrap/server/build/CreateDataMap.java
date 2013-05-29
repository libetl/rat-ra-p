package org.toilelibre.libe.ratrap.server.build;

import java.util.HashMap;

import org.toilelibre.libe.ratrap.shared.City;
import org.toilelibre.libe.ratrap.shared.DataMap;
import org.toilelibre.libe.ratrap.shared.Rectangle;
import org.toilelibre.libe.ratrap.shared.Station;

public class CreateDataMap {

	public static DataMap create() {
		final DataMap result = new DataMap();
		final Rectangle rectangle = new Rectangle();
		rectangle.setLatMax(0);
		rectangle.setLatMin(90);
		rectangle.setLongMax(0);
		rectangle.setLongMin(90);
		result.setBounds(rectangle);
		result.setStations(new HashMap<Integer, Station>());
		result.setCities(new HashMap<String, City>());
		result.setStationsByName(new HashMap<String, Station>());
		result.setStationsByLettersName(new HashMap<String, Station>());
		return result;
	}

}
