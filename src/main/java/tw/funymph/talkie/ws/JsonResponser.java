/* JsonResponser.java created on Aug 17, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the Talkie Vert.x project.
 */
package tw.funymph.talkie.ws;

import static com.fasterxml.jackson.databind.PropertyNamingStrategy.SNAKE_CASE;
import static tw.funymph.talkie.ws.HttpHeaders.ContentType;
import static tw.funymph.talkie.ws.HttpHeaders.JsonContentType;
import static tw.funymph.talkie.ws.VertxWebService.WebServiceMetadata;
import static tw.funymph.talkie.ws.VertxWebService.WebServiceResult;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

/**
 * This handler writes the final result with meta data (if existing) into
 * the response in JSON format.
 * 
 * @author Spirit Tu
 * @version 1.0
 * @since 1.0
 */
public class JsonResponser implements Handler<RoutingContext> {

	private ObjectMapper mapper;

	/**
	 * Construct a <code>JsonResponser</code> with the default JSON serializer.
	 */
	public JsonResponser() {
		mapper = new ObjectMapper();
		mapper.setPropertyNamingStrategy(SNAKE_CASE);
	}

	@Override
	public void handle(RoutingContext context) {
		Map<String, Object> result = metaAwareResult(context);
		if (result.isEmpty()) {
			context.response().end();
		}
		else {
			writeJsonResponse(context, result);
		}
	}

	/**
	 * Wrap the result in the given content as a single object.
	 * 
	 * @param context the routing context
	 * @return the final result object
	 */
	private Map<String, Object> metaAwareResult(RoutingContext context) {
		Map<String, Object> object = new HashMap<String, Object>();
		if (context.get(WebServiceResult) != null) {
			object.put("data", context.get(WebServiceResult));
		}
		if (context.get(WebServiceMetadata) != null) {
			object.put("meta", context.get(WebServiceMetadata));
		}
		return object;
	}

	/**
	 * Write the result in JSON format.
	 * 
	 * @param context the routing context
	 * @param result the final result
	 */
	private void writeJsonResponse(RoutingContext context, Object result) {
		try {
			context.response().putHeader(ContentType, JsonContentType);
			context.response().end(mapper.writeValueAsString(result));
		}
		catch (JsonProcessingException e) {
			context.fail(e);
		}
	}
}
