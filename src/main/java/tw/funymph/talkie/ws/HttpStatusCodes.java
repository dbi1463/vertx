/* HttpStatusCodes.java created on Aug 17, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the Talkie Vert.x project.
 */
package tw.funymph.talkie.ws;

/**
 * This interface list some of the HTTP status codes that are
 * common used in Web services.
 * 
 * @author Spirit Tu
 * @version 1.0
 * @since 1.0
 */
public interface HttpStatusCodes {

	static final int OK = 200;
	static final int Created = 201;
	static final int Accepted = 202;

	static final int MovedPermanently = 301;
	static final int NotModified = 304;

	static final int BadRequest = 400;
	static final int Unauthorized = 401;
	static final int PaymentRequired = 402;
	static final int Forbidden = 403;
	static final int NotFound = 404;
	static final int MethodNotAllowed = 405;
	static final int NotAcceptable = 406;
	static final int RequestTimeout = 408;
	static final int Conflict = 409;
	static final int UnsupportedMediaType = 415;

	static final int InternalServerError = 500;
}
