/* TimestampHandler.java created on Aug 17, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the Talkie Vert.x project.
 */
package tw.funymph.talkie.ws;

import static java.lang.String.format;
import static java.lang.String.valueOf;
import static java.lang.System.currentTimeMillis;
import static java.lang.System.nanoTime;
import static tw.funymph.talkie.ws.HttpHeaders.Elapsed;
import static tw.funymph.talkie.ws.HttpHeaders.Timestamp;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

/**
 * This handler records the started time and duration in the HTTP
 * response headers.
 * 
 * @author Spirit Tu
 * @version 1.0
 * @since 1.0
 */
public class TimestampHandler implements Handler<RoutingContext> {

	private static final long NanosecondsPerMilliseconds = 1000000;

	@Override
	public void handle(RoutingContext context) {
		long started = nanoTime();
		context.response().putHeader(Timestamp, valueOf(currentTimeMillis()));
		context.addHeadersEndHandler(v -> {
			double duration = (double)(nanoTime() - started) / NanosecondsPerMilliseconds;
			context.response().putHeader(Elapsed, format("%1$.3f", duration));
		});
		context.next();
	}
}
