package org.toilelibre.libe.ratrap.client;

import org.toilelibre.libe.ratrap.shared.Path;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SearchServiceAsync {

	void search(String type, String origin, String dest,
			AsyncCallback<Path> callback);

}
