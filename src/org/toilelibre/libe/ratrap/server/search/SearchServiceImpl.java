package org.toilelibre.libe.ratrap.server.search;

import org.toilelibre.libe.ratrap.client.SearchService;
import org.toilelibre.libe.ratrap.server.build.LastDataMapUpdate;
import org.toilelibre.libe.ratrap.shared.DataMap;
import org.toilelibre.libe.ratrap.shared.LocalizedPlace;
import org.toilelibre.libe.ratrap.shared.Path;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
public class SearchServiceImpl extends RemoteServiceServlet implements
		SearchService {

	/**
   * 
   */
	private static final long serialVersionUID = -7149295457401453673L;

	@Override
	public Path search(final String type, final String origin, final String dest) {
		final DataMap dataMap = LastDataMapUpdate.getData();
		LocalizedPlace originPlace;
		LocalizedPlace destPlace;
		originPlace = dataMap.getStationsByName().get(origin);
		destPlace = dataMap.getStationsByName().get(dest);
		if (originPlace == null) {
			originPlace = dataMap.getCities().get(origin);
		}
		if (destPlace == null) {
			destPlace = dataMap.getCities().get(origin);
		}
		if ((originPlace == null) || (destPlace == null)) {
			return null;
		}
		try {
			@SuppressWarnings("unchecked")
			final Class<PathSearch> pathSearch = (Class<PathSearch>) Class
					.forName(this.getClass().getPackage().getName() + "."
							+ type.substring(0, 1).toUpperCase()
							+ type.substring(1) + "PathSearchImpl");
			final PathSearch ps = pathSearch.newInstance();
			return ps.search(dataMap, originPlace, destPlace);
		} catch (final ClassNotFoundException e) {
			e.printStackTrace();
		} catch (final InstantiationException e) {
			e.printStackTrace();
		} catch (final IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

}
