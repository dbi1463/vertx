/* AuthWebService.java created on Aug 16, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the Talkie Vert.x project.
 */
package tw.funymph.talkie.ws.auth;

import static java.util.UUID.randomUUID;
import static tw.funymph.talkie.ws.HttpHeaders.basicAuthorization;
import static tw.funymph.talkie.ws.HttpStatusCodes.Unauthorized;

import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import tw.funymph.talkie.ws.VertxWebService;
import tw.funymph.talkie.ws.WebServiceException;

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
		router.post("/authentications").handler(this::login);
	}

	/**
	 * Handle the login request.
	 * 
	 * @param context the routing context
	 */
	public void login(RoutingContext context) {
		try {
			basicAuthorization(context.request().getHeader(Authorization));
			context.response().putHeader(AuthToken, randomUUID().toString());
			context.next();
		}
		catch (Throwable e) {
			context.fail(new WebServiceException(Unauthorized, -1, e));
		}
	}
}
