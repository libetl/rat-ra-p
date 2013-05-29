package org.toilelibre.libe.ratrap.server.search;

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
		Station sp1 = origin;
		Station sp2 = dest;
		Station s1 = origin;
		Station s2 = dest;
		Station sm1 = new Station();
		Station sm2 = new Station();
		final Path path = new Path("dist");
		path.setStart(origin);
		path.setEnd(dest);
		Connection conn1 = new Connection(s1, null);
		Connection conn2 = new Connection(s2, null);
		while ((sp1.getId() != sp2.getId())
				&& ((conn1 != null) || (conn2 != null))) {
			conn1 = this.findNextConnectionCloserTo(s1, s2);
			if (conn1 != null && sm1.getId() != conn1.getStation().getId()) {
				path.insert(conn1.getStation(), conn1.getLine());
				sp1 = conn1.getStation();
			} else {
				conn1 = null;
			}
			conn2 = this.findNextConnectionCloserTo(s2, s1);
			if (conn2 != null && s1.getId() != s2.getId()
					&& sm2.getId() != conn2.getStation().getId()) {
				path.append(conn2.getStation(), conn2.getLine());
				sp2 = conn2.getStation();
			} else {
				conn2 = null;
			}
			sm1 = s1;
			sm2 = s2;
			s1 = sp1;
			s2 = sp2;
		}

		return path;
	}

	private Connection findNextConnectionCloserTo(Station from, Station to) {
		Station nextStation = null;
		Line nextLine = null;
		double distance1 = 1;
		for (final Line l : from.getConnections()) {
			if (l.getPath().get(from.getId()) != null) {
				for (final Station stmp : l.getPath().get(from.getId())) {
					if (DistanceCompute.distance(stmp, to) < distance1) {
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
					if (DistanceCompute.distance(stmp, to) < distance1) {
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

}
