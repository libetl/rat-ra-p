package org.toilelibre.libe.ratrap.client.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.toilelibre.libe.ratrap.client.BuildMapService;
import org.toilelibre.libe.ratrap.client.BuildMapServiceAsync;
import org.toilelibre.libe.ratrap.client.build.DisplayDataMap;
import org.toilelibre.libe.ratrap.client.build.SearchView;
import org.toilelibre.libe.ratrap.client.rsrc.URLs;
import org.toilelibre.libe.ratrap.client.store.ClientDataStore;
import org.toilelibre.libe.ratrap.shared.Constants;
import org.toilelibre.libe.ratrap.shared.DataMap;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class RATraP implements EntryPoint {

	/**
	 * Create a remote service proxy to talk to the server-side buildMap
	 * service.
	 */
	private static final BuildMapServiceAsync buildMapService = GWT
			.create(BuildMapService.class);

	private static Map<String, String> dataStrings;

	public static void onDataGathered(final SearchView searchView,
			final String type, final String csv) {

		RATraP.dataStrings.put(type, SafeHtmlUtils.htmlEscape(csv));
		if (RATraP.dataStrings.size() == 2) {
			RATraP.buildMapService.buildMap(RATraP.dataStrings,
					new AsyncCallback<DataMap>() {
						@Override
						public void onFailure(final Throwable caught) {
							Window.alert(Constants.SERVER_ERROR);
						}

						@Override
						public void onSuccess(final DataMap result) {
							ClientDataStore.map = result;
							ClientDataStore.suggestList = new ArrayList<String>();
							ClientDataStore.suggestList.addAll(result
									.getStationsByName().keySet());
							ClientDataStore.suggestList.addAll(result
									.getCities().keySet());

							((MultiWordSuggestOracle) searchView.getOriDist()
									.getSuggestOracle())
									.addAll(ClientDataStore.suggestList);
							((MultiWordSuggestOracle) searchView.getDestDist()
									.getSuggestOracle())
									.addAll(ClientDataStore.suggestList);

							((MultiWordSuggestOracle) searchView
									.getOriCorresp().getSuggestOracle())
									.addAll(ClientDataStore.suggestList);
							((MultiWordSuggestOracle) searchView
									.getDestCorresp().getSuggestOracle())
									.addAll(ClientDataStore.suggestList);

							((MultiWordSuggestOracle) searchView.getOriLong()
									.getSuggestOracle())
									.addAll(ClientDataStore.suggestList);
							((MultiWordSuggestOracle) searchView.getDestLong()
									.getSuggestOracle())
									.addAll(ClientDataStore.suggestList);

							((MultiWordSuggestOracle) searchView.getOriLent()
									.getSuggestOracle())
									.addAll(ClientDataStore.suggestList);
							((MultiWordSuggestOracle) searchView.getDestLent()
									.getSuggestOracle())
									.addAll(ClientDataStore.suggestList);

							DisplayDataMap.display(result,
									searchView.getCanvas());
						}
					});
		}
	}

	private SearchView searchView;

	/**
	 * This is the entry point method.
	 */
	@Override
	public void onModuleLoad() {
		RATraP.dataStrings = new HashMap<String, String>();
		this.searchView = new SearchView();
		WindowRATraP.init(this.searchView);

		final URLs urls = GWT.create(URLs.class);

		if ("csv".equals(urls.mode())) {
			GatherWebContent.gather(
					GWT.getHostPageBaseURL() + urls.pointsURL(),
					new RATraPLoadPointsCallback(this.searchView));

			GatherWebContent.gather(
					GWT.getHostPageBaseURL() + urls.connectionsURL(),
					new RATraPLoadConnectionsCallback(this.searchView));
		}
		if ("gtfs".equals(urls.mode())) {
			this.loadGTFSOnServer(urls.gtfsDIR());
		}

	}

	private void loadGTFSOnServer(String gtfsDIR) {
		RATraP.buildMapService.buildGTFSMap(gtfsDIR,
				new AsyncCallback<DataMap>() {
					@Override
					public void onFailure(final Throwable caught) {
						Window.alert(Constants.SERVER_ERROR);
					}

					@Override
					public void onSuccess(final DataMap result) {
						ClientDataStore.map = result;
						ClientDataStore.suggestList = new ArrayList<String>();
						ClientDataStore.suggestList.addAll(result
								.getStationsByName().keySet());
						ClientDataStore.suggestList.addAll(result.getCities()
								.keySet());

						((MultiWordSuggestOracle) searchView.getOriDist()
								.getSuggestOracle())
								.addAll(ClientDataStore.suggestList);
						((MultiWordSuggestOracle) searchView.getDestDist()
								.getSuggestOracle())
								.addAll(ClientDataStore.suggestList);

						((MultiWordSuggestOracle) searchView.getOriCorresp()
								.getSuggestOracle())
								.addAll(ClientDataStore.suggestList);
						((MultiWordSuggestOracle) searchView.getDestCorresp()
								.getSuggestOracle())
								.addAll(ClientDataStore.suggestList);

						((MultiWordSuggestOracle) searchView.getOriLong()
								.getSuggestOracle())
								.addAll(ClientDataStore.suggestList);
						((MultiWordSuggestOracle) searchView.getDestLong()
								.getSuggestOracle())
								.addAll(ClientDataStore.suggestList);

						((MultiWordSuggestOracle) searchView.getOriLent()
								.getSuggestOracle())
								.addAll(ClientDataStore.suggestList);
						((MultiWordSuggestOracle) searchView.getDestLent()
								.getSuggestOracle())
								.addAll(ClientDataStore.suggestList);

						DisplayDataMap.display(result, searchView.getCanvas());
					}
				});
	}

}
