package org.toilelibre.libe.ratrap.server.build;

import java.util.Date;

import org.toilelibre.libe.ratrap.shared.DataMap;

public class LastDataMapUpdate {

	private static DataMap data;
	private static Date lastUpdateTime;

	public static DataMap getData() {
		return LastDataMapUpdate.data;
	}

	public static Date getLastUpdateTime() {
		return LastDataMapUpdate.lastUpdateTime;
	}

	public static void setData(final DataMap data) {
		LastDataMapUpdate.data = data;
	}

	public static void setLastUpdateTime(final Date lastUpdateTime) {
		LastDataMapUpdate.lastUpdateTime = lastUpdateTime;
	}

}
