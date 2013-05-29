/**
 * 
 */
package org.toilelibre.libe.ratrap.client.build;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author lionel
 * 
 */
public class SearchView extends Composite {

	interface SearchViewUiBinder extends UiBinder<Widget, SearchView> {
	}

	private static SearchViewUiBinder uiBinder = GWT
			.create(SearchViewUiBinder.class);

	private Canvas canvas;
	@UiField
	SuggestBox destCorresp;
	@UiField
	SuggestBox destDist;
	@UiField
	SuggestBox destLent;
	@UiField
	SuggestBox destLong;
	@UiField
	ScrollPanel mapContainer;
	@UiField
	SuggestBox oriCorresp;
	@UiField
	SuggestBox oriDist;
	@UiField
	SuggestBox oriLent;
	@UiField
	SuggestBox oriLong;
	@UiField
	Button ratrapCorresp;
	@UiField
	Button ratrapDist;
	@UiField
	Button ratrapLent;
	@UiField
	Button ratrapLong;
	@UiField
	HTML resultDist;
	@UiField
	HTML resultCorresp;
	@UiField
	HTML resultLong;
	@UiField
	HTML resultLent;

	/**
	 * Because this class has a default constructor, it can be used as a binder
	 * template. In other words, it can be used in other *.ui.xml files as
	 * follows: <ui:UiBinder xmlns:ui="urn:ui:com.google.gwt.uibinder"
	 * xmlns:g="urn:import:**user's package**">
	 * <g:**UserClassName**>Hello!</g:**UserClassName> </ui:UiBinder> Note that
	 * depending on the widget that is used, it may be necessary to implement
	 * HasHTML instead of HasText.
	 */
	public SearchView() {
		this.initWidget(SearchView.uiBinder.createAndBindUi(this));
		this.init();
	}

	public SearchView(final String firstName) {
		this.initWidget(SearchView.uiBinder.createAndBindUi(this));
		this.init();

		// Can access @UiField after calling createAndBindUi
	}

	public Canvas getCanvas() {
		return this.canvas;
	}

	public SuggestBox getDestCorresp() {
		return this.destCorresp;
	}

	public SuggestBox getDestDist() {
		return this.destDist;
	}

	public SuggestBox getDestLent() {
		return this.destLent;
	}

	public SuggestBox getDestLong() {
		return this.destLong;
	}

	public ScrollPanel getMapContainer() {
		return this.mapContainer;
	}

	public SuggestBox getOriCorresp() {
		return this.oriCorresp;
	}

	public SuggestBox getOriDist() {
		return this.oriDist;
	}

	public SuggestBox getOriLent() {
		return this.oriLent;
	}

	public SuggestBox getOriLong() {
		return this.oriLong;
	}

	public Button getRatrapCorresp() {
		return this.ratrapCorresp;
	}

	public Button getRatrapDist() {
		return this.ratrapDist;
	}

	public Button getRatrapLent() {
		return this.ratrapLent;
	}

	public Button getRatrapLong() {
		return this.ratrapLong;
	}

	public HTML getResultDist() {
		return resultDist;
	}

	public HTML getResultCorresp() {
		return resultCorresp;
	}

	public HTML getResultLong() {
		return resultLong;
	}

	public HTML getResultLent() {
		return resultLent;
	}

	private void init() {
		this.canvas = Canvas.createIfSupported();
		final CanvasDragListener drag = new CanvasDragListener(
				this.mapContainer, this.canvas);
		this.mapContainer.add(this.canvas);
		this.canvas.setStyleName("mainCanvas");
		this.canvas.getCanvasElement().setWidth(475);
		this.canvas.getCanvasElement().setHeight(600);

		this.canvas.addMouseWheelHandler(new CanvasZoomHandler(this.canvas));
		this.canvas.addMouseDownHandler(drag);
		this.canvas.addMouseUpHandler(drag);
	}
}
