package org.toilelibre.libe.ratrap.shared;

import java.io.Serializable;
import java.util.Map;

import org.toilelibre.libe.ratrap.shared.gtfs.Agency;

public class DataMap implements Serializable {

	/**
   * 
   */
	private static final long serialVersionUID = -1857839877842573563L;
	private Rectangle bounds;
	private Map<String, City> cities;
	private Map<String, Line> lines;
	private Map<Integer, Station> stations;
	private Map<String, Station> stationsByName;
	private Map<String, Station> stationsByLettersName;
	private Agency agency;
	private Path trip;

	public Path getTrip() {
		return trip;
	}

	public void setTrip(Path trip) {
		this.trip = trip;
	}

	public DataMap() {
		super();
	}

	public DataMap(final Map<String, Line> lines,
			final Map<Integer, Station> stations,
			final Map<String, City> cities, final Rectangle bounds) {
		super();
		this.cities = cities;
		this.lines = lines;
		this.stations = stations;
		this.bounds = bounds;
	}

	public Rectangle getBounds() {
		return this.bounds;
	}

	public Map<String, City> getCities() {
		return this.cities;
	}

	public Map<String, Line> getLines() {
		return this.lines;
	}

	public Map<Integer, Station> getStations() {
		return this.stations;
	}

	public Map<String, Station> getStationsByName() {
		return this.stationsByName;
	}

	public Map<String, Station> getStationsByLettersName() {
		return this.stationsByLettersName;
	}

	public void setBounds(final Rectangle bounds) {
		this.bounds = bounds;
	}

	public void setCities(final Map<String, City> cities) {
		this.cities = cities;
	}

	public void setLines(final Map<String, Line> lines) {
		this.lines = lines;
	}

	public void setStations(final Map<Integer, Station> stations) {
		this.stations = stations;
	}

	public void setStationsByName(final Map<String, Station> stationsByName) {
		this.stationsByName = stationsByName;
	}

	public void setStationsByLettersName(
			final Map<String, Station> stationsByLettersName1) {
		this.stationsByLettersName = stationsByLettersName1;
	}

	public Agency getAgency() {
		return agency;
	}

	public void setAgency(Agency agency) {
		this.agency = agency;
	}

}
