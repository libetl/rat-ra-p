package org.toilelibre.libe.ratrap.shared;

import java.io.Serializable;

import org.toilelibre.libe.ratrap.client.rsrc.ColorsGet;

public enum TransportType implements Serializable {

	Bus("bus", "BUS", 10, false), Metro("metro", "M", 14, true), RER("rer",
			"RER", 10, false), Tramway("tramway", "T", 14, false), Transilien(
			"transilien", "/T/ SNCF", 8, false);

	public static TransportType getByValue(final String value) {
		for (final TransportType tp : TransportType.values()) {
			if (tp.value.toUpperCase().equals(value.toUpperCase())) {
				return tp;
			}
		}
		return null;
	}

	private String value;
	private String display;
	private int typeFontSize;
	private boolean fillCircle;

	TransportType(final String value1, final String display1,
			final int typeFontSize, final boolean fillCircle) {
		this.value = value1;
		this.display = display1;
		this.fillCircle = fillCircle;
		this.typeFontSize = typeFontSize;
	}

	public String prettyPrint(String id) {
		String s = "<div style=\"float:left;background-color:white\"><div style=\"float:left;font-size:"
				+ this.typeFontSize
				+ "px;color:#010172 ; font-weight:bold;text-align:center;background-color:transparent;width:20px;height:20px;border-radius:50%;border:0.2em solid #010172;\">";
		s += this.display;
		s += "</div>";
		String colorBg = ColorsGet.get(this, id);
		String colorText = ColorsGet.getText(this, id);
		s += "<div style=\"float:left;color:" + colorText
				+ ";background-color:";
		if (this.fillCircle) {
			s += colorBg;
		} else {
			s += "transparent";
		}
		s += ";width:20px;height:20px;text-align:center;border-radius:50%;font-size:14px;font-weight:bold;border:0.2em solid "
				+ colorBg + "\">";
		s += id;
		s += "</div></div>";
		return s;
	}

	public String toString() {
		return this.display;
	}
}
