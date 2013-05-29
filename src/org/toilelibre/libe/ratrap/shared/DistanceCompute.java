package org.toilelibre.libe.ratrap.shared;

public class DistanceCompute {

	public static double distance(final LocalizedPlace p1,
			final LocalizedPlace p2) {
		final double v = Math.pow(
				Math.abs(p1.getPosition().getLatitude()
						- p2.getPosition().getLatitude()), 2)
				+ Math.pow(
						Math.abs(p1.getPosition().getLongitude()
								- p2.getPosition().getLongitude()), 2);
		return v;
	}
}
