package org.toilelibre.libe.ratrap.client.rsrc;

import com.google.gwt.i18n.client.Constants;

public interface URLs extends Constants {

	@DefaultStringValue("connectionsURL")
	String connectionsURL();

	@DefaultStringValue("pointsURL")
	String pointsURL();

	@DefaultStringValue("trafficURL")
	String trafficURL();

	@DefaultStringValue("gtfsDIR")
	String gtfsDIR();

	@DefaultStringValue("mode")
	String mode();
}
