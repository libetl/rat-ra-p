package org.toilelibre.libe.ratrap.client.main;

import org.toilelibre.libe.ratrap.client.SearchService;
import org.toilelibre.libe.ratrap.client.SearchServiceAsync;
import org.toilelibre.libe.ratrap.shared.Constants;
import org.toilelibre.libe.ratrap.shared.Path;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HTML;

public class LaunchSearchClickHandler implements ClickHandler {

	/**
	 * Create a remote service proxy to talk to the server-side search service.
	 */
	private static final SearchServiceAsync searchService = GWT
			.create(SearchService.class);
	private final HasText destination;
	private final HasText origin;

	private final String type;
	private final HTML resultLabel;

	public LaunchSearchClickHandler(final String string, final HasText ori,
			final HasText dest, HTML label) {
		this.type = string;
		this.origin = ori;
		this.destination = dest;
		this.resultLabel = label;
	}

	@Override
	public void onClick(final ClickEvent event) {
		LaunchSearchClickHandler.searchService.search(this.type,
				this.origin.getText(), this.destination.getText(),
				new AsyncCallback<Path>() {
					@Override
					public void onFailure(final Throwable caught) {
						Window.alert(Constants.SEARCH_ERROR);
					}

					@Override
					public void onSuccess(final Path result) {
						LaunchSearchClickHandler.this.resultLabel
								.setHTML(result.prettyPrint ().replace("\n",
										"<br/>"));
					}
				});
	}

}
