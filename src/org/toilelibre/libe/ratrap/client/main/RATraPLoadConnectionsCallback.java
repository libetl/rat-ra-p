package org.toilelibre.libe.ratrap.client.main;

import org.toilelibre.libe.ratrap.client.build.SearchView;
import org.toilelibre.libe.ratrap.shared.Constants;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;

public class RATraPLoadConnectionsCallback implements RequestCallback {

	private final SearchView searchView;

	public RATraPLoadConnectionsCallback(final SearchView searchView1) {
		this.searchView = searchView1;
	}

	@Override
	public void onError(final Request request, final Throwable exception) {
		Window.alert(Constants.SERVER_ERROR + "\n" + exception);
	}

	@Override
	public void onResponseReceived(final Request request,
			final Response response) {
		if (200 == response.getStatusCode()) {
			// Window.alert(response.getText());
		} else {
			Window.alert("Received HTTP status code other than 200 : '"
					+ response.getStatusCode() + "'");
		}
		RATraP.onDataGathered(this.searchView, "connections",
				response.getText());

	}

}