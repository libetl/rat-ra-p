package org.toilelibre.libe.ratrap.shared.gtfs;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Transfers implements Serializable {

	public class PK implements Serializable {
		private long fromStopId, toStopId;


		@ManyToOne
		@JoinColumn(name = "fromStopId",referencedColumnName="stopId")
		private Stops fromStop;
		

		@ManyToOne
		@JoinColumn(name="toStopId",referencedColumnName = "stopId")
		private Stops toStop;
		
		public long getFromStopId() {
			return fromStopId;
		}

		public void setFromStopId(long fromStopId) {
			this.fromStopId = fromStopId;
		}

		public long getToStopId() {
			return toStopId;
		}

		public void setToStopId(long toStopId) {
			this.toStopId = toStopId;
		}
	}

	@Id
	private PK id;
	private int transferType, minTransferTime;

	public int getTransferType() {
		return transferType;
	}

	public void setTransferType(int transferType) {
		this.transferType = transferType;
	}

	public int getMinTransferTime() {
		return minTransferTime;
	}

	public void setMinTransferTime(int minTransferTime) {
		this.minTransferTime = minTransferTime;
	}

}
