package org.toilelibre.libe.ratrap.shared;

import java.io.Serializable;

public class Position implements Serializable {

	/**
   * 
   */
	private static final long serialVersionUID = -7702343846002402301L;
	private double latitude;
	private double longitude;

	public Position() {
		super();
	}

	public Position(final double lat, final double long1) {
		this.latitude = lat;
		this.longitude = long1;
	}

	public double getLatitude() {
		return this.latitude;
	}

	public double getLongitude() {
		return this.longitude;
	}

	@Override
	public String toString() {
		return "[" + (Math.floor(this.longitude * 100) / 100.0) + ", "
				+ (Math.floor(this.latitude * 100) / 100.0) + "]";
	}

	@Override
	public boolean equals(Object p) {
		return p != null && p instanceof Position
				&& this.latitude == ((Position) p).latitude
				&& this.longitude == ((Position) p).longitude;
	}
}
