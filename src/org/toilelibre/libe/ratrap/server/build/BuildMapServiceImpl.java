package org.toilelibre.libe.ratrap.server.build;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import org.toilelibre.libe.ratrap.client.BuildMapService;
import org.toilelibre.libe.ratrap.shared.DataMap;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
public class BuildMapServiceImpl extends RemoteServiceServlet implements
		BuildMapService {

	/**
   * 
   */
	private static final long serialVersionUID = -7149295457401453673L;

	@Override
	public DataMap buildMap(final Map<String, String> csvs) {
		try {

			final DataMap result = CreateDataMap.create();
			for (final String csvKey : csvs.keySet()) {
				@SuppressWarnings("unchecked")
				final Class<Builder> builderC = (Class<Builder>) Class
						.forName(Builder.class.getPackage().getName()
								+ ".Build"
								+ csvKey.substring(0, 1).toUpperCase()
								+ csvKey.substring(1) + "Impl");
				final Builder builder = builderC.newInstance();
				builder.setDataMap(result);
				builder.build(csvs.get(csvKey));
			}
			LastDataMapUpdate.setData(result);
			LastDataMapUpdate.setLastUpdateTime(new Date());
			return result;
		} catch (final ClassNotFoundException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		} catch (final InstantiationException e) {
			e.printStackTrace();
		} catch (final IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public DataMap buildGTFSMap(String gtfsDIR) {
		final DataMap result = CreateDataMap.create();
		final Builder builder = new BuildGTFSModel();
		builder.setDataMap(result);
		try {
			builder.build(gtfsDIR);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

}
