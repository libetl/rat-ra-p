package org.toilelibre.libe.ratrap.shared;

public class StationPair implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1431723090035351857L;
	private Station s1;
	private Station s2;
	private Line  l;
	
	public StationPair() {
		super();
	}
	
	public StationPair(Station s1, Station s2, Line l) {
		super();
		this.s1 = s1;
		this.s2 = s2;
		this.l = l;
	}
	public Station getS1() {
		return s1;
	}
	public void setS1(Station s1) {
		this.s1 = s1;
	}
	public Station getS2() {
		return s2;
	}
	public void setS2(Station s2) {
		this.s2 = s2;
	}
	public Line getL() {
		return l;
	}
	public void setL(Line l) {
		this.l = l;
	}
	
	public String toString (){
		return s1.getName() + " " + new Connection (s2, l).toString();
	}
	
}
