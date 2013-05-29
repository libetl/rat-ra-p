package org.toilelibre.libe.ratrap.client;

import org.toilelibre.libe.ratrap.shared.Path;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("search")
public interface SearchService extends RemoteService {

	Path search(String type, String origin, String dest);

}
