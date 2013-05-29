package org.toilelibre.libe.ratrap.shared;

import java.io.Serializable;

public class Rectangle implements Serializable {

	/**
   * 
   */
	private static final long serialVersionUID = -4875573572279413417L;
	private Position max;
	private Position min;

	public Rectangle() {
		super();
		this.min = new Position(90, 90);
		this.max = new Position(0, 0);
	}

	public Rectangle(final float longMin, final float longMax,
			final float latMin, final float latMax) {
		super();
		this.min = new Position(latMin, longMin);
		this.max = new Position(latMax, longMax);
	}

	public double getLatMax() {
		return this.max.getLatitude();
	}

	public double getLatMin() {
		return this.min.getLatitude();
	}

	public double getLongMax() {
		return this.max.getLongitude();
	}

	public double getLongMin() {
		return this.min.getLongitude();
	}

	public void setLatMax(final double latMax) {
		this.max = new Position(latMax, this.getLongMax());
	}

	public void setLatMin(final double latMin) {
		this.min = new Position(latMin, this.getLongMin());
	}

	public void setLongMax(final double longMax) {
		this.max = new Position(this.getLatMax(), longMax);
	}

	public void setLongMin(final double longMin) {
		this.min = new Position(this.getLatMin(), longMin);
	}

}
