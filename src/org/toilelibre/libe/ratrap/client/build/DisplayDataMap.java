package org.toilelibre.libe.ratrap.client.build;

import java.util.HashSet;
import java.util.Set;

import org.toilelibre.libe.ratrap.client.rsrc.ColorsGet;
import org.toilelibre.libe.ratrap.shared.City;
import org.toilelibre.libe.ratrap.shared.DataMap;
import org.toilelibre.libe.ratrap.shared.Line;
import org.toilelibre.libe.ratrap.shared.Rectangle;
import org.toilelibre.libe.ratrap.shared.Station;
import org.toilelibre.libe.ratrap.shared.TransportType;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.CssColor;

public class DisplayDataMap {

	public static void display(final DataMap result, final Canvas canvas) {
		canvas.getContext2d().clearRect(0, 0,
				canvas.getCanvasElement().getWidth(),
				canvas.getCanvasElement().getHeight());
		final Rectangle r = result.getBounds();
		final double width = r.getLongMax() - r.getLongMin();
		final double height = r.getLatMax() - r.getLatMin();
		final int widthScreen = canvas.getCanvasElement().getWidth();
		final int heightScreen = canvas.getCanvasElement().getHeight();
		canvas.getContext2d().setFillStyle("#000000");
		canvas.getContext2d().setFont("10pt Calibri, Geneva, Arial");

		for (final Station station : result.getStations().values()) {
			if (((widthScreen > 1900) && (heightScreen > 2400))
					|| (station.getCity().getStations().size() < 2)) {
				final double x = ((station.getPosition().getLongitude() - r
						.getLongMin()) / width) * widthScreen;
				final double y = heightScreen
						- (((station.getPosition().getLatitude() - r
								.getLatMin()) / height) * heightScreen);
				canvas.getContext2d().arc(x, y, 1, 0, 2 * Math.PI);
				canvas.getContext2d().fillText(station.getName(), x, y);
			}
		}
		for (final City city : result.getCities().values()) {
			if (city.getStations().size() > 1) {
				canvas.getContext2d().setFillStyle("#00FF00");
				final double x = ((city.getCenter().getLongitude() - r
						.getLongMin()) / width) * widthScreen;
				final double y = heightScreen
						- (((city.getCenter().getLatitude() - r.getLatMin()) / height) * heightScreen);
				if ((x > 0) && (y > 0)) {
					canvas.getContext2d().fillText(city.getName(), x, y);
				}
			}
		}
		final Set<Double> strokes = new HashSet<Double>();
		for (final Line line : result.getLines().values()) {
			DisplayDataMap.drawALine(canvas, result, line, strokes);
		}
	}

	private static void drawALine(final Canvas canvas, final DataMap result,
			final Line line, final Set<Double> strokes) {
		for (final Integer id : line.getPath().keySet()) {
			final Station origin = result.getStations().get(id);
			for (final Station destination : line.getPath().get(id)) {
				DisplayDataMap.drawASegment(canvas, result.getBounds(),
						line.getType(), line.getId(), origin, destination,
						strokes);
			}
		}
	}

	private static void drawASegment(final Canvas canvas, final Rectangle r,
			final TransportType tp, final String id, final Station origin,
			final Station destination, final Set<Double> strokes) {
		final String color = ColorsGet.get(tp, id);
		final double width = r.getLongMax() - r.getLongMin();
		final double height = r.getLatMax() - r.getLatMin();
		final int widthScreen = canvas.getCanvasElement().getWidth();
		final int heightScreen = canvas.getCanvasElement().getHeight();

		final double x1 = ((origin.getPosition().getLongitude() - r
				.getLongMin()) / width) * widthScreen;
		double y1 = heightScreen
				- (((origin.getPosition().getLatitude() - r.getLatMin()) / height) * heightScreen);

		final double x2 = ((destination.getPosition().getLongitude() - r
				.getLongMin()) / width) * widthScreen;
		double y2 = heightScreen
				- (((destination.getPosition().getLatitude() - r.getLatMin()) / height) * heightScreen);

		Double o = new Double(((Math.floor(x1 * 100) / 100) * widthScreen)
				+ ((Math.floor(y1 * 100) / 100) * heightScreen)
				+ ((Math.floor(x2 * 100) / 100) * widthScreen)
				+ ((Math.floor(y2 * 100) / 100) * heightScreen));
		while (strokes.contains(o)) {
			y1 += 3.0;
			y2 += 3.0;
			o = new Double(((Math.floor(x1 * 100) / 100) * widthScreen)
					+ ((Math.floor(y1 * 100) / 100) * heightScreen)
					+ ((Math.floor(x2 * 100) / 100) * widthScreen)
					+ ((Math.floor(y2 * 100) / 100) * heightScreen));
		}
		strokes.add(o);
		canvas.getContext2d().setStrokeStyle(CssColor.make(color));
		canvas.getContext2d().setLineWidth(2.0);
		canvas.getContext2d().beginPath();
		canvas.getContext2d().moveTo(x1, y1);
		canvas.getContext2d().lineTo(x2, y2);
		canvas.getContext2d().stroke();
		canvas.getContext2d().closePath();
	}
}
