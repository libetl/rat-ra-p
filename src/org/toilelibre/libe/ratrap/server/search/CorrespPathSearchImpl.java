package org.toilelibre.libe.ratrap.server.search;



import org.toilelibre.libe.ratrap.shared.DataMap;
import org.toilelibre.libe.ratrap.shared.DistanceCompute;
import org.toilelibre.libe.ratrap.shared.Path;
import org.toilelibre.libe.ratrap.shared.Station;
import org.toilelibre.libe.ratrap.shared.StationPair;

public class CorrespPathSearchImpl extends PathSearch {

	private double THRESHOLD = 0.00001;
	
	@Override
	public Path search(final DataMap map, final Station origin,
			final Station dest) {
		if (map.getStationPairs() == null &&
				map.getStationPairs().size() == 0){
			return null;
		}

		return this.buildPath(map, origin, dest, null);
	}
	
	public Path buildPath(
			final DataMap map, final Station origin,
			final Station dest, final StationPair lastPairFound) {
		StationPair current = null;
		Path newPath = new Path ("corresp", origin, dest);
		if (DistanceCompute.distance (origin, dest) < THRESHOLD ||
				origin == dest){
			return newPath;
		}
		current = map.getStationPairs().get (0);
		for (StationPair sp : map.getStationPairs()){
			if ((sp.getS1().getId() == origin.getId() &&
					sp.getS2().getId() == dest.getId()) ||
					(sp.getS2().getId() == origin.getId() &&
							sp.getS1().getId() == dest.getId())){
				return newPath;
			}
			double distance1 =
					DistanceCompute.distance(origin, sp.getS1());
			double distance2 =
					DistanceCompute.distance(dest, sp.getS2());
			double distanceC1 =
					DistanceCompute.distance(origin, current.getS1());
			double distanceC2 =
					DistanceCompute.distance(dest, current.getS2());
			if (distance1 * distance1 + distance2 * distance2 <
					distanceC1 * distanceC1 + distanceC2 * distanceC2){
				current = sp;
			}
		}
		if (lastPairFound != null && ((lastPairFound.getS1() == current.getS1() &&
				lastPairFound.getS2() == current.getS2()) ||
				(lastPairFound.getS1() == current.getS2() &&
				lastPairFound.getS2() == current.getS1()))){
			return newPath;
		}
		if (current.getS1() == dest){
			return newPath;
		}
		if (current.getS2() == origin){
			return newPath;
		}
		if (current.getS1() != origin){
		  newPath.insert(current.getS1(), current.getL());
		}
		if (current.getS2() != dest){
		  newPath.append(current.getS2(), current.getL());
		}
		newPath.insert (
				this.buildPath(map, origin, current.getS1(), current));
		newPath.append (
				this.buildPath(map, current.getS2(), dest, current));
		return newPath;
	}

}
