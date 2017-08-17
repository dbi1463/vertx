/* VertxWebService.java created on Aug 16, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the Talkie Vert.x project.
 */
package tw.funymph.talkie.ws;

import java.util.HashMap;
import java.util.Map;

import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

/**
 * This interface provides the default methods for most common utilities
 * and defines the minimum methods that a concrete Web service handler
 * should implement.
 * 
 * @author Spirit Tu
 * @version 1.0
 * @since 1.0
 */
public interface VertxWebService extends HttpHeaders {

	static final String WebServiceResult = "result";
	static final String WebServiceMetadata = "meta";

	/**
	 * Mark the given object as the final result.
	 *  
	 * @param context the routing context
	 * @param object the final result
	 */
	default void resolve(RoutingContext context, Object object) {
		context.put(WebServiceResult, object);
	}

	/**
	 * Add the given key-value as a part of the metadata in the final result.
	 * 
	 * @param context the routing context
	 * @param key the metadata key
	 * @param value the metadata value
	 */
	default void addMetaData(RoutingContext context, String key, Object value) {
		Map<String, Object> meta = context.get(WebServiceMetadata);
		if (meta == null) {
			meta = new HashMap<>();
			context.put(WebServiceMetadata, meta);
		}
		meta.put(key, value);
	}

	/**
	 * Initialize the request mapping for the paths that the concrete Web
	 * services can handle.
	 *  
	 * @param router the router object to register the mapping
	 */
	void route(Router router);
}
