package org.toilelibre.libe.ratrap.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.toilelibre.libe.ratrap.client.rsrc.ColorsGet;

public class Path implements Serializable, Comparable<Path> {

	/**
   * 
   */
	private static final long serialVersionUID = 2055728007310596061L;

	private List<Line> lineSteps;
	private List<Station> stationSteps;
	private String type;
	private Station start;
	private Station end;

	private int cut;

	public Path() {
		super();
		this.cut = 0;
	}

	public Path(final String t) {
		super();
		this.stationSteps = new ArrayList<Station>();
		this.lineSteps = new ArrayList<Line>();
		this.type = t;
		this.cut = 0;
	}

	public Path(final String t, Station start1, Station end1) {
		super();
		this.stationSteps = new ArrayList<Station>();
		this.lineSteps = new ArrayList<Line>();
		this.type = t;
		this.cut = 0;
		this.start = start1;
		this.end = end1;
	}

	public void append(final Station sp1, final Line lp1) {
		if (this.cut == 0) {
			this.lineSteps.add(lp1);
			this.stationSteps.add(sp1);
		} else {
			this.lineSteps.add(this.cut, lp1);
			this.stationSteps.add(this.cut, sp1);
		}
	}

	private double computeDistance() {
		double totalDistance = 0.0;
		Station s = this.start;
		if (this.getStationSteps() == null) {
			return totalDistance;
		}
		for (Station s1 : this.getStationSteps()) {
			totalDistance += DistanceCompute.distance(s, s1);
			s = s1;
		}
		totalDistance += DistanceCompute.distance(s, this.end);
		return totalDistance;
	}

	public int compareTo(final Path arg0) {
		if (arg0 == null) {
			return -1;
		}
		return new Double(this.computeDistance()).compareTo(new Double(arg0
				.computeDistance()));
	}

	public List<Line> getLineSteps() {
		return this.lineSteps;
	}

	public List<Station> getStationSteps() {
		return this.stationSteps;
	}

	public String getType() {
		return this.type;
	}

	public void insert(final Station sp1, final Line lp1) {
		this.lineSteps.add(cut, lp1);
		this.stationSteps.add(cut++, sp1);
	}
	
	public void pop (final Station s) {
		int index = this.stationSteps.indexOf (s);
		if (index != -1){
		  this.lineSteps.remove (index);
		  this.stationSteps.remove(index);
		  if (index < cut){
			  cut--;
		  }
		}
	}

	public Station getSp1 (){
		return this.getFromLastStep (true, 0).getStation ();
	}
	
	public Station getSp2 (){
		return this.getFromLastStep (false, 0).getStation ();
	}

	public Station getS1 (){
		return this.getFromLastStep (true, 0).getStation ();
	}
	
	public Station getS2 (){
		return this.getFromLastStep (false, 0).getStation ();
	}

	public Station getSM1 (){
		return (this.getFromLastStep (true, 1).getStation () == this.start ?
				new Station () : this.getFromLastStep (true, 1).getStation ());
	}
	
	public Station getSM2 (){
		return (this.getFromLastStep (false, 1).getStation () == this.start ?
				new Station () : this.getFromLastStep (false, 1).getStation ());
	}

	public Connection getFromLastStep (boolean part1, int minus){
		int index = (part1 ? this.cut - 1 - minus: this.cut + minus);
		if (index >= this.stationSteps.size () || index < 0){
			return new Connection ((part1 ? this.start : this.end), null);
		}
		return new Connection (this.stationSteps.get (index), 
				this.lineSteps.get (index));
	}
	
	public Connection getLastStep (boolean part1){
		return this.getFromLastStep (part1, 0);
	}

	public void setLineSteps(final List<Line> lineSteps) {
		this.lineSteps = lineSteps;
	}

	public void setStationSteps(final List<Station> steps) {
		this.stationSteps = steps;
	}

	public void setType(final String type) {
		this.type = type;
	}

	public Station getStart() {
		return start;
	}

	public void setStart(Station start) {
		this.start = start;
	}

	public Station getEnd() {
		return end;
	}

	public void setEnd(Station end) {
		this.end = end;
	}

	public String toString (){
		StringBuffer sb = new StringBuffer(this.type);
		Line l = null;
		if (this.start != null) {
			sb.append(" : \n       " + this.start.getName());
		}
		for (int i = 0 ; i < this.stationSteps.size() ; i++) {

			if (l != this.lineSteps.get(i)) {
				l = this.lineSteps.get(i);
				sb.append("[" + l.getType() + l.getId() + "]");
			}
			if (i == this.cut){
				sb.append (" -> \n       ------------------------------------------------------------\n       ");
			}else{
			    sb.append(" -> \n       ");
			}
			sb.append(this.stationSteps.get(i).getName());
		}
		if (this.end != null) {
			sb.append(" -> \n       " + this.end.getName());
		}
		return sb.toString();
	}
	
	public String prettyPrint() {
		StringBuffer sb = new StringBuffer("Trajet de type " + this.type
				+ " :\n");
		Line l = null;
		if (this.start != null) {
			sb.append(" v d&eacute;part : " + this.start.getName() + "\n");
		}
		for (int i = 0 ; i < this.stationSteps.size(); i++) {

			if (l != this.lineSteps.get(i)) {
				l = this.lineSteps.get(i);
				sb.append(l.prettyPrint () + "<div style=\"height:30px\">&nbsp;</div>");
			}
			sb.append(" <span style=\"font-size:18px;font-weight:bold;color:"
					+ ColorsGet.get(l.getType(), l.getId()) + "\">|</span> ");
			sb.append(" <span style=\"\">" + this.stationSteps.get(i).getName()
					+ "</span>\n");
		}
		if (this.end != null) {
			sb.append(" X arriv&eacute;e &agrave; " + this.end.getName() + "\n");
		}
		return sb.toString();
	}
}
