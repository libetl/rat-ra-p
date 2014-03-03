package org.toilelibre.libe.ratrap.server.search;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.toilelibre.libe.ratrap.shared.Connection;
import org.toilelibre.libe.ratrap.shared.DataMap;
import org.toilelibre.libe.ratrap.shared.DistanceCompute;
import org.toilelibre.libe.ratrap.shared.Line;
import org.toilelibre.libe.ratrap.shared.Path;
import org.toilelibre.libe.ratrap.shared.Station;

public class DistPathSearchImpl extends PathSearch {

	@Override
	public Path search(final DataMap map, final Station origin,
			final Station dest) {
		Set<Station> seen = new HashSet<Station> ();
		final Path path = new Path("dist", origin, dest);
		while (true) {
			this.getCloser (path.getS1 (), path.getS2 (), path.getSM1 (), path, seen, true);
			if (this.isNearest (path.getS1 (), path.getS2 ())){break;}
			/*this.getCloser (path.getS2 (), path.getS1 (), path.getSM2 (), path, seen, false);
			if (this.isNearest (path.getS1 (), path.getS2 ())){break;}*/
		}

		return path;
	}

	private Connection getCloser (Station s1, Station s2, Station sm1, Path path,
			Set<Station> seen, boolean insert) {
		Connection conn1;
		conn1 = this.findNextConnectionCloserTo(s1, s2, seen);
		if (conn1 != null && sm1.getId() != conn1.getStation().getId()) {
			if (insert){
			    path.insert(conn1.getStation(), conn1.getLine());
			}else{
				path.append(conn1.getStation(), conn1.getLine());				
			}
			seen.add (conn1.getStation());
		} else {
			conn1 = this.revertPathUntilConnectionNotSeen (s1, s2, sm1, path, seen, insert);
		}
	    return conn1;
    }

	private Connection revertPathUntilConnectionNotSeen (Station s1, Station s2, Station sm1,
            Path path, Set<Station> seen, boolean part1) {
		path.pop (s1);
		return path.getLastStep (part1);
    }

	private Connection findNextConnectionCloserTo(Station from, Station to, Set<Station> seen) {
		Station nextStation = null;
		Line nextLine = null;
		double distance1 = 1;
		for (final Line l : from.getConnections()) {
			if (l.getPath().get(from.getId()) != null) {
				for (final Station stmp : l.getPath().get(from.getId())) {
					if (DistanceCompute.distance(stmp, to) < distance1
						 && !seen.contains (stmp)) {
						nextLine = l;
						distance1 = DistanceCompute.distance(stmp, to);
						nextStation = stmp;
					}
				}
			}
		}
		for (final Line l : from.getConnections()) {
			if (l.getReversePath().get(from.getId()) != null) {
				for (final Station stmp : l.getReversePath().get(from.getId())) {
					if (DistanceCompute.distance(stmp, to) < distance1
						  && !seen.contains (stmp)) {
						nextLine = l;
						distance1 = DistanceCompute.distance(stmp, to);
						nextStation = stmp;
					}
				}
			}
		}
		if (nextStation == null || nextLine == null) {
			return null;
		}
		return new Connection(nextStation, nextLine);
	}
	


	private boolean isNearest (Station s1, Station s2) {
		if (s1 == s2){
			return true;
		}
		List<Station> alreadySeen = new LinkedList<Station> ();
		for (Line line : s1.getConnections ()){
			Station test = this.findNextLineStation (line, s1, alreadySeen);
			alreadySeen.add (test);
			if (test == s2){
				return true;
			}
		}
	    return false;
    }
	
	private Station findNextLineStation(final Line line, final Station s, List<Station> alreadySeen) {
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
			if ((current != s) && !alreadySeen.contains(current)
					&& (((x * x) + (y * y)) > ((newX * newX) + (newY * newY)))) {
				x = newX;
				y = newY;
				result = current;
			}
		}
		return result;
	}
}
