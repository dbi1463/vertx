/* AuthWebService.java created on Aug 16, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the Talkie Vert.x project.
 */
package tw.funymph.talkie.ws.auth;

import static tw.funymph.talkie.ws.HttpHeaders.basicAuthorization;

import java.util.UUID;

import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

import tw.funymph.talkie.ws.VertxWebService;

/**
 * This class handles the authentication related requests.
 * 
 * @author Spirit Tu
 * @version 1.0
 * @since 1.0
 */
public class AuthWebService implements VertxWebService {

	@Override
	public void route(Router router) {
		router.post("/authentications").handler(metaAware(this::login));
	}

	/**
	 * Handle the login request.
	 * 
	 * @param context the routing context
	 */
	public void login(RoutingContext context) {
		basicAuthorization(context.request().getHeader(Authorization));
		context.response().putHeader(AuthToken, UUID.randomUUID().toString());
	}
}
