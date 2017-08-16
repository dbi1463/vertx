/* VertxWebService.java created on Aug 16, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the Talkie Vert.x project.
 */
package tw.funymph.talkie.ws;

import static java.lang.String.valueOf;
import static java.lang.System.currentTimeMillis;

import io.vertx.core.Handler;
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

	/**
	 * Wrap the given handler as a new handler that provides some meta information
	 * in the response.
	 * 
	 * @param handler the given handler to wrap
	 * @return the new handler
	 */
	default Handler<RoutingContext> metaAware(Handler<RoutingContext> handler) {
		return (RoutingContext context) -> {
			long timestamp = currentTimeMillis();
			context.response().putHeader(Timestamp, valueOf(timestamp));
			handler.handle(context);
			context.response().putHeader(Elapsed, valueOf(currentTimeMillis() - timestamp));
			context.response().end();
		};
	}

	/**
	 * Initialize the request mapping for the paths that the concrete Web
	 * services can handle.
	 *  
	 * @param router the router object to register the mapping
	 */
	void route(Router router);
}
