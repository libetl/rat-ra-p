package org.toilelibre.libe.ratrap.shared;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Station implements Serializable, LocalizedPlace {

	/**
   * 
   */
	private static final long serialVersionUID = 2055728007310596061L;
	private City city;
	private List<Line> connections;
	private int id;
	private String name;
	private Set<String> aliases;

	private Position position;

	public Station() {
		super();
		this.connections = new LinkedList<Line>();
		this.aliases = new HashSet<String>();
	}

	public void become (Station s){
		this.aliases = s.aliases;
		this.city = s.city;
		this.connections = s.connections;
		this.id = s.id;
		this.name = s.name;
		this.position = s.position;
	}
	
	public City getCity() {
		return this.city;
	}

	public List<Line> getConnections() {
		return this.connections;
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	@Override
	public Position getPosition() {
		return this.position;
	}

	public void addAlias(String string) {
		this.aliases.add(string);
	}

	public Set<String> getAliases() {
		return this.aliases;
	}

	public void setCity(final City city) {
		this.city = city;
	}

	public void setConnections(final List<Line> connections) {
		this.connections = connections;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setPosition(final Position position) {
		this.position = position;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer (this.name + " ");
		for (Line l : this.connections){
			sb.append (l + ", ");
		}
		return sb.toString ().substring (0, sb.length () - 2);
	}
	
	public String prettyPrint() {
		return this.name + ", " + this.city + " " + this.connections + " "
				+ this.position;
	}
}
