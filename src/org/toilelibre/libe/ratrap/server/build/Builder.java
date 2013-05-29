package org.toilelibre.libe.ratrap.server.build;

import java.io.IOException;

import org.toilelibre.libe.ratrap.shared.DataMap;

public interface Builder {

	void build(String input) throws IOException;

	void setDataMap(DataMap dm);
}
