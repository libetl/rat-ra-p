package org.toilelibre.libe.ratrap.shared;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class City implements Serializable, LocalizedPlace {

	/**
   * 
   */
	private static final long serialVersionUID = 2055728007310596061L;
	private Position center;
	private String name;
	private List<Station> stations;

	public City() {
		super();
		this.stations = new LinkedList<Station>();
	}

	public Position getCenter() {
		return this.center;
	}

	public String getName() {
		return this.name;
	}

	@Override
	public Position getPosition() {
		return this.center;
	}

	public List<Station> getStations() {
		return this.stations;
	}

	public void setCenter(final Position center) {
		this.center = center;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setStations(final List<Station> stations) {
		this.stations = stations;
	}

	@Override
	public String toString() {
		return this.name;
	}
}
