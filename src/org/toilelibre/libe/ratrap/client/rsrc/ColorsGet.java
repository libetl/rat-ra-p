package org.toilelibre.libe.ratrap.client.rsrc;

import java.util.HashMap;
import java.util.Map;

import org.toilelibre.libe.ratrap.shared.TransportType;

public class ColorsGet {

	public static final Map<String, String> colors = new HashMap<String, String>() {
		/**
     * 
     */
		private static final long serialVersionUID = -6186022447556938260L;

		{
			this.put("RA", "#ec232d");
			this.put("RB", "#649dd4");
			this.put("RC", "#eac823");
			this.put("RD", "#23b57d");
			this.put("RE", "#d490bf");
			this.put("M1", "#ffcd09");
			this.put("M2", "#0c6eb7");
			this.put("M3", "#9b993a");
			this.put("M3B", "#89d3de");
			this.put("M4", "#bb4b9c");
			this.put("M5", "#f68f4d");
			this.put("M6", "#78c696");
			this.put("M7", "#f59eb3");
			this.put("M7B", "#78c696");
			this.put("M8", "#c5a3cc");
			this.put("M9", "#cec82b");
			this.put("M10", "#e0b03a");
			this.put("M11", "#8d663a");
			this.put("M12", "#028d5b");
			this.put("M13", "#89d3de");
			this.put("M14", "#672f8f");
			this.put("MORV", "#7f7f7f");
			this.put("T1", "#287ebf");
			this.put("T2", "#bb4b9c");
			this.put("T3", "#f69b61");
			this.put("M1t", "#000000");
			this.put("M2t", "#ffffff");
			this.put("M3t", "#ffffff");
			this.put("M4t", "#ffffff");
			this.put("M5t", "#000000");
			this.put("M6t", "#000000");
			this.put("M7t", "#000000");
			this.put("M8t", "#000000");
			this.put("M9t", "#000000");
			this.put("M10t", "#000000");
			this.put("M11t", "#ffffff");
			this.put("M12t", "#ffffff");
			this.put("M13t", "#000000");
			this.put("M14t", "#ffffff");
			this.put("RAt", "#ec232d");
			this.put("RBt", "#649dd4");
			this.put("RCt", "#eac823");
			this.put("RDt", "#23b57d");
			this.put("REt", "#d490bf");
		}
	};

	public static String get(final TransportType tp, final String name) {
		String prefix = "";
		if (tp != null) {
			prefix = tp.name().substring(0, 1);
		}
		return ColorsGet.colors.get(prefix + name);
	}

	public static String getText(final TransportType tp, final String name) {
		String prefix = "";
		if (tp != null) {
			prefix = tp.name().substring(0, 1);
		}
		return ColorsGet.colors.get(prefix + name + "t");
	}

}
