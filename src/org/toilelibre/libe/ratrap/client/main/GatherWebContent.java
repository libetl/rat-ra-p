package org.toilelibre.libe.ratrap.client.main;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.URL;

public class GatherWebContent {

	public static void gather(final String url,
			final RequestCallback requestCallback) {
		final RequestBuilder rb = new RequestBuilder(RequestBuilder.GET,
				URL.encode(url));

		try {
			rb.sendRequest(null, requestCallback);
		} catch (final RequestException e1) {
			e1.printStackTrace();
		}
	}

}
