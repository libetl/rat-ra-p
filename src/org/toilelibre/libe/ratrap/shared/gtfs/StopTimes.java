package org.toilelibre.libe.ratrap.shared.gtfs;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class StopTimes implements Serializable {

	public class PK implements Serializable {
		@Id
		private String tripId;

		@Id
		private Date arrivalTime;
		@Id
		private Date departureTime;

		@Id
		private long stopId;

		@ManyToOne
		@JoinColumn(name = "tripId")
		private Trips trip;

		@ManyToOne
		@JoinColumn(name = "stopId")
		private Stops stop;

	}

	@Id
	private StopTimes.PK id;
	private int stopSequence;
	private String stopHeadsign, shapeDistTraveled;

	public StopTimes() {
		super();
	}

	public int getStopSequence() {
		return stopSequence;
	}

	public void setStopSequence(int stopSequence) {
		this.stopSequence = stopSequence;
	}

	public String getStopHeadsign() {
		return stopHeadsign;
	}

	public void setStopHeadsign(String stopHeadsign) {
		this.stopHeadsign = stopHeadsign;
	}

	public String getShapeDistTraveled() {
		return shapeDistTraveled;
	}

	public void setShapeDistTraveled(String shapeDistTraveled) {
		this.shapeDistTraveled = shapeDistTraveled;
	}

}
