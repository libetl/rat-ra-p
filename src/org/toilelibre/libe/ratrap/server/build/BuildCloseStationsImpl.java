package org.toilelibre.libe.ratrap.server.build;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.toilelibre.libe.ratrap.shared.DataMap;
import org.toilelibre.libe.ratrap.shared.Line;
import org.toilelibre.libe.ratrap.shared.Station;
import org.toilelibre.libe.ratrap.shared.StationPair;

public class BuildCloseStationsImpl implements Builder {

	
	private DataMap dm;
	
	public static Station findNextLineStation(final Line line, final Station s) {
		double x = 90.0;
		double y = 90.0;
		Station result = null;
		if (s == null) {
			return null;
		}
		for (final Station current : line.getStations()) {
			final double newX = current.getPosition().getLongitude()
					- s.getPosition().getLongitude();
			final double newY = current.getPosition().getLatitude()
					- s.getPosition().getLatitude();
			if ((current != s) 
					&& (((x * x) + (y * y)) > ((newX * newX) + (newY * newY)))) {
				x = newX;
				y = newY;
				result = current;
			}
		}
		return result;
	}

	public static List<StationPair> init(DataMap dataMap) {
		List<StationPair> stationPairs = new ArrayList<StationPair> ();
		for (Station s : dataMap.getStations().values()){
			for (Line l : s.getConnections()){
			  stationPairs.add(
					  new StationPair (s, 
							  findNextLineStation (l, s), l));
			}
		}
		return stationPairs;
		
	}

	@Override
	public void build(String input) throws IOException {
		this.dm.setStationPairs(
				BuildCloseStationsImpl.init(this.dm));
	}

	@Override
	public void setDataMap(DataMap dm) {
		this.dm = dm;
	}
}
