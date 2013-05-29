package org.toilelibre.libe.ratrap.shared;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Line implements Serializable {

	/**
   * 
   */
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private Map<Integer, List<Station>> path;
	private Map<Integer, List<Station>> reversePath;
	private List<Station> stations;
	private TransportType type;

	public Line() {
		super();
		this.stations = new LinkedList<Station>();
		this.path = new HashMap<Integer, List<Station>>();
		this.reversePath = new HashMap<Integer, List<Station>>();
	}

	public Line(final String id, final String name, final TransportType type) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.stations = new LinkedList<Station>();
		this.path = new HashMap<Integer, List<Station>>();
		this.reversePath = new HashMap<Integer, List<Station>>();
	}

	public String getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public Map<Integer, List<Station>> getPath() {
		return this.path;
	}

	public Map<Integer, List<Station>> getReversePath() {
		return this.reversePath;
	}

	public List<Station> getStations() {
		return this.stations;
	}

	public TransportType getType() {
		return this.type;
	}

	public List<Station> pathPut(final Station key, final List<Station> value) {
		return this.path.put(key.getId(), value);
	}

	public List<Station> reversePathPut(final Station key,
			final List<Station> value) {
		return this.reversePath.put(key.getId(), value);
	}

	public void setId(final String id) {
		this.id = id;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setPath(final Map<Integer, List<Station>> path) {
		this.path = path;
	}

	public void setReversePath(final Map<Integer, List<Station>> reversePath) {
		this.reversePath = reversePath;
	}

	public void setStations(final List<Station> stations) {
		this.stations = stations;
	}

	public void setType(final TransportType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		if (this.type != null) {
			return this.type.prettyPrint(this.id);
		}
		return this.type.toString() + "(" + this.id.substring(1) + ")";
	}
}
