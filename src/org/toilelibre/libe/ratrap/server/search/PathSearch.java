package org.toilelibre.libe.ratrap.server.search;

import java.util.Arrays;
import java.util.List;

import org.toilelibre.libe.ratrap.shared.City;
import org.toilelibre.libe.ratrap.shared.DataMap;
import org.toilelibre.libe.ratrap.shared.LocalizedPlace;
import org.toilelibre.libe.ratrap.shared.Path;
import org.toilelibre.libe.ratrap.shared.Station;

public abstract class PathSearch {

	public Path search(final DataMap map, final LocalizedPlace origin,
			final LocalizedPlace dest) {
		List<Station> l1;
		List<Station> l2;
		Path smallest = null;
		if (origin instanceof City) {
			l1 = ((City) origin).getStations();
		} else {
			l1 = Arrays.asList((Station) origin);
		}
		if (dest instanceof City) {
			l2 = ((City) dest).getStations();
		} else {
			l2 = Arrays.asList((Station) dest);
		}
		for (final Station s1 : l1) {
			for (final Station s2 : l2) {
				final Path path = this.search(map, s1, s2);
				if ((path != null) && (path.compareTo(smallest) == -1)) {
					smallest = path;
				}
			}
		}
		return smallest;
	}

	protected abstract Path search(final DataMap map, final Station origin,
			final Station dest);
}
