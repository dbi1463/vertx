/* HttpHeaders.java created on Aug 16, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the Talkie Vert.x project.
 */
package tw.funymph.talkie.ws;

import static java.util.Base64.getDecoder;
import static java.util.regex.Pattern.compile;
import static tw.funymph.talkie.utils.StringUtils.assertNotBlank;

import java.util.regex.Matcher;

/**
 * This interface defines the constants for HTTP headers and related
 * utility methods.
 *  
 * @author Spirit Tu
 * @version 1.0
 * @since 1.0
 */
public interface HttpHeaders {

	// Time measurement
	static final String Elapsed = "Elapsed";
	static final String Timestamp = "Timestamp";

	// Authentication
	static final String AuthToken = "Auth-Token";
	static final String Authorization = "Authorization";

	// Content negotiation
	static final String ContentMD5 = "Content-MD5";
	static final String ContentType = "Content-Type";
	static final String ContentLength = "Content-Length";
	static final String ContentDisposition = "Content-Disposition";

	static final String JsonContentType = "application/json";

	static final String BasicAuthorizationPattern = "Basic (.+)";
	static final String AttachmentFilenamePattern = "attachment[\\s;]+filename=\"(.+)\"";

	/**
	 * Get the decoded authorization information.
	 * 
	 * @param authorization the Base64 encoded information
	 * @return the decoded information
	 */
	public static String[] basicAuthorization(String authorization) {
		Matcher matcher = compile(BasicAuthorizationPattern).matcher(assertNotBlank(authorization, "the authorization is not specified"));
		if (!matcher.find()) {
			throw new IllegalArgumentException("the basic authorization is not specified");
		}
		String encodedCredential = matcher.group(1);
		String decodedCredential = new String(getDecoder().decode(encodedCredential));
		return decodedCredential.split(":");
	}
}
