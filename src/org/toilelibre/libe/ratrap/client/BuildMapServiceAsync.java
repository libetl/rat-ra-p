package org.toilelibre.libe.ratrap.client;

import java.util.Map;

import org.toilelibre.libe.ratrap.shared.DataMap;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface BuildMapServiceAsync {

	void buildMap(Map<String, String> dataStrings,
			AsyncCallback<DataMap> callback);

	void buildGTFSMap(String gtfsDIR, AsyncCallback<DataMap> callback);

}
