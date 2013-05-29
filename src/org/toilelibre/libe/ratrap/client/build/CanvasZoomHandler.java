package org.toilelibre.libe.ratrap.client.build;

import org.toilelibre.libe.ratrap.client.store.ClientDataStore;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.user.client.ui.ScrollPanel;

public class CanvasZoomHandler implements MouseWheelHandler {

	private final Canvas canvas;

	public CanvasZoomHandler(final Canvas canvas1) {
		this.canvas = canvas1;
	}

	@Override
	public void onMouseWheel(final MouseWheelEvent event) {
		final int eventX = event.getX();
		final int eventY = event.getY();
		final int limitX = 475;
		final int limitY = 600;
		if (event.isNorth()) {
			this.zoom(eventX, eventY, limitX, limitY);
		}
		if (event.isSouth()) {
			this.unzoom(eventX, eventY, limitX, limitY);
		}
	}

	private void unzoom(final double eventX, final double eventY,
			final int limitX, final int limitY) {
		final int newHeight = this.canvas.getCanvasElement().getHeight() / 2;
		final int newWidth = this.canvas.getCanvasElement().getWidth() / 2;

		final double newEventX = eventX / 2;
		final double newEventY = eventY / 2;

		int scrollX = (int) (newEventX - (limitX / 2.0));
		int scrollY = (int) (newEventY - (limitY / 2.0));
		scrollX = scrollX > 0 ? scrollX : 0;
		scrollY = scrollY > 0 ? scrollY : 0;

		this.canvas.getCanvasElement().setWidth(newWidth);
		this.canvas.getCanvasElement().setHeight(newHeight);
		this.canvas.setWidth(newWidth + "px");
		this.canvas.setHeight(newHeight + "px");

		((ScrollPanel) this.canvas.getParent())
				.setVerticalScrollPosition(scrollY);
		((ScrollPanel) this.canvas.getParent())
				.setHorizontalScrollPosition(scrollX);
		DisplayDataMap.display(ClientDataStore.map, this.canvas);
	}

	private void zoom(final double eventX, final double eventY,
			final int limitX, final int limitY) {
		final int newHeight = this.canvas.getCanvasElement().getHeight() * 2;
		final int newWidth = this.canvas.getCanvasElement().getWidth() * 2;

		final double newEventX = eventX * 2;
		final double newEventY = eventY * 2;

		int scrollX = (int) (newEventX - (limitX / 2.0));
		int scrollY = (int) (newEventY - (limitY / 2.0));
		scrollX = scrollX > 0 ? scrollX : 0;
		scrollY = scrollY > 0 ? scrollY : 0;

		this.canvas.getCanvasElement().setWidth(newWidth);
		this.canvas.getCanvasElement().setHeight(newHeight);
		this.canvas.setWidth(newWidth + "px");
		this.canvas.setHeight(newHeight + "px");

		((ScrollPanel) this.canvas.getParent())
				.setVerticalScrollPosition(scrollY);
		((ScrollPanel) this.canvas.getParent())
				.setHorizontalScrollPosition(scrollX);
		DisplayDataMap.display(ClientDataStore.map, this.canvas);
	}

}
