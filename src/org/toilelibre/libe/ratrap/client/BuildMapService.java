package org.toilelibre.libe.ratrap.client;

import java.util.Map;

import org.toilelibre.libe.ratrap.shared.DataMap;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("buildmap")
public interface BuildMapService extends RemoteService {
	DataMap buildMap(Map<String, String> dataStrings);

	DataMap buildGTFSMap(String gtfsDIR);
}
