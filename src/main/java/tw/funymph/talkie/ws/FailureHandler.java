/* FailureHandler.java created on Aug 17, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the Talkie Vert.x project.
 */
package tw.funymph.talkie.ws;

import static com.fasterxml.jackson.databind.PropertyNamingStrategy.SNAKE_CASE;
import static java.util.Arrays.stream;
import static tw.funymph.talkie.utils.MapUtils.asMap;
import static tw.funymph.talkie.utils.MapUtils.notEmpty;
import static tw.funymph.talkie.ws.HttpHeaders.ContentType;
import static tw.funymph.talkie.ws.HttpHeaders.JsonContentType;

import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

/**
 * This class writes the failure exception to the response in the JSON format.
 * 
 * @author Spirit Tu
 * @version 1.0
 * @since 1.0
 */
public class FailureHandler implements Handler<RoutingContext> {

	private boolean needDetail;

	private ObjectMapper mapper;

	/**
	 * Construct a <code>FailureHandler</code> with the default setting that
	 * will not write the exception traces into the response.
	 */
	public FailureHandler() {
		this(false);
	}

	/**
	 * Construct a <code>FailureHandler</code> with the setting that determine
	 * whether to write the exception traces into the response or not.
	 * 
	 * @param detailed {@code true} to write exception traces
	 */
	public FailureHandler(boolean detailed) {
		needDetail = detailed;
		mapper = new ObjectMapper();
		mapper.setPropertyNamingStrategy(SNAKE_CASE);
	}

	@Override
	public void handle(RoutingContext context) {
		Throwable e = context.failure();
		if (e instanceof WebServiceException) {
			writeJsonResponse(context, (WebServiceException)e);
		}
		else {
			writeJsonResponse(context, new WebServiceException(e));
		}
	}

	/**
	 * Write the result in JSON format.
	 * 
	 * @param context the routing context
	 * @param result the final result
	 */
	private void writeJsonResponse(RoutingContext context, WebServiceException e) {
		context.response().setStatusCode(e.getStatusCode());
		try {
			String path = context.request().path();
			String method = context.request().method().toString();
			String response = mapper.writeValueAsString(wrapException(path, method, e));
			context.response().putHeader(ContentType, JsonContentType);
			context.response().end(response);
		}
		catch (JsonProcessingException ex) {
			// Do best to write a human readable information
			context.response().end(e.getMessage());
		}
	}

	/**
	 * Wrap the basic information of the exception into a map object.
	 *  
	 * @param path the request path
	 * @param method the request method
	 * @param e the exception
	 * @return the basic information map
	 */
	private Map<String, Object> wrapException(String path, String method, WebServiceException e) {
		Map<String, Object> error = asMap("path", path);
		error.put("method", method);
		error.put("code", e.getErrorCode());
		error.put("message", e.getMessage());
		error.put("status", e.getStatusCode());
		if (notEmpty(e.getInfo())) {
			error.put("info", e.getInfo());
		}
		if (needDetail) {
			error.put("traces", stream(e.getStackTrace()).map(this::formatTrace).toArray());
		}
		return error;
	}

	/**
	 * The method reference lambda to translate stack trace element to a simple string.
	 * 
	 * @param trace the stack trace element
	 * @return the simple string
	 */
	private String formatTrace(StackTraceElement trace) {
		return String.format("at %s.%s(%s:%d)", trace.getClassName(), trace.getMethodName(), trace.getFileName(), trace.getLineNumber());
	}
}
