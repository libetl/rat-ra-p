package org.toilelibre.libe.ratrap.shared.gtfs;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Trips implements Serializable {

	private long routeId;

	@ManyToOne
	//@JoinColumn(name = "routeId")
	private Routes route;

	private long serviceId;

	@OneToMany
	@JoinColumn(name = "serviceId")
	private Set<CalendarDates> calendarDates;

	@ManyToOne
	@JoinColumn(name = "serviceId")
	private Calendar calendar;

	@OneToMany
	@JoinColumn(name = "tripId")
	private Set<StopTimes> stopTimes;

	@Id
	private long tripId;
	private String tripHeadsign, tripShortName;
	private int directionId;
	private String shapeId;

	public Trips() {
		super();
	}

	public long getRouteId() {
		return routeId;
	}

	public void setRouteId(long routeId) {
		this.routeId = routeId;
	}

	public long getServiceId() {
		return serviceId;
	}

	public void setServiceId(long serviceId) {
		this.serviceId = serviceId;
	}

	public long getTripId() {
		return tripId;
	}

	public void setTripId(long tripId) {
		this.tripId = tripId;
	}

	public String getTripHeadsign() {
		return tripHeadsign;
	}

	public void setTripHeadsign(String tripHeadsign) {
		this.tripHeadsign = tripHeadsign;
	}

	public String getTripShortName() {
		return tripShortName;
	}

	public void setTripShortName(String tripShortName) {
		this.tripShortName = tripShortName;
	}

	public int getDirectionId() {
		return directionId;
	}

	public void setDirectionId(int directionId) {
		this.directionId = directionId;
	}

	public String getShapeId() {
		return shapeId;
	}

	public void setShapeId(String shapeId) {
		this.shapeId = shapeId;
	}

}
