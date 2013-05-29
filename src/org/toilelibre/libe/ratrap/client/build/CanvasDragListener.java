package org.toilelibre.libe.ratrap.client.build;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseEvent;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.ScrollPanel;

public class CanvasDragListener implements MouseMoveHandler, MouseDownHandler,
		MouseUpHandler {

	private final Canvas canvas;
	private int lastX;
	private int lastY;
	private HandlerRegistration registration;
	private final ScrollPanel scrollPanel;

	public CanvasDragListener(final ScrollPanel mapContainer,
			final Canvas canvas1) {
		this.scrollPanel = mapContainer;
		this.canvas = canvas1;
	}

	@Override
	public void onMouseDown(final MouseDownEvent event) {
		if (this.registration == null) {
			this.saveXY(event);
			this.registration = this.canvas.addMouseMoveHandler(this);
		}
	}

	@Override
	public void onMouseMove(final MouseMoveEvent event) {
		final int deltaX = event.getClientX() - this.lastX;
		final int deltaY = event.getClientY() - this.lastY;
		final int vsp = this.scrollPanel.getVerticalScrollPosition();
		final int hsp = this.scrollPanel.getHorizontalScrollPosition();
		this.scrollPanel.setHorizontalScrollPosition(hsp - deltaX);
		this.scrollPanel.setVerticalScrollPosition(vsp - deltaY);
		this.lastX = event.getClientX();
		this.lastY = event.getClientY();
	}

	@Override
	public void onMouseUp(final MouseUpEvent event) {
		if (this.registration != null) {
			this.registration.removeHandler();
			this.registration = null;
		}
	}

	public void saveXY(final MouseEvent<?> event) {
		this.lastX = event.getClientX();
		this.lastY = event.getClientY();
	}
}
