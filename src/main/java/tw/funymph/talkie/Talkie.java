/* Talkie.java created on Aug 16, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the Talkie Vert.x project.
 */
package tw.funymph.talkie;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import tw.funymph.talkie.ws.VertxWebService;
import tw.funymph.talkie.ws.auth.AuthWebService;

/**
 * This is the main entry of the Talkie server.
 * 
 * @author Spirit Tu
 * @version 1.0
 * @since 1.0
 */
public class Talkie {

	/**
	 * The main entry.
	 * 
	 * @param args the arguments from the command line
	 */
	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		Router root = Router.router(vertx);
		Router webServices = Router.router(vertx);

		VertxWebService authWebService = new AuthWebService();
		authWebService.route(webServices);

		root.mountSubRouter("/ws", webServices);
		HttpServer server = vertx.createHttpServer();
		server.requestHandler(root::accept).listen(8080);
	}
}
