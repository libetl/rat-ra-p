package org.toilelibre.libe.ratrap.shared;

import java.io.Serializable;

public final class Connection implements Serializable {

	private Station station;
	private Line line;

	public Connection(Station arg0, Line arg1) {
		super();
		this.station = arg0;
		this.line = arg1;
	}

	/**
   * 
   */
	private static final long serialVersionUID = -6430976589873941479L;

	public Station getStation() {
		return this.station;
	}

	public Line getLine() {
		return this.line;
	}
	
	public String toString (){
		return (this.line != null ? this.line.getType ().toString () + this.line.getId () : "?") + "->" + (this.station != null ? this.station.getName () : "?");
	}
}
