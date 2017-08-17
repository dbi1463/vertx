/* StringUtils.java created on Aug 17, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the Talkie Vert.x project.
 */
package tw.funymph.talkie.utils;

/**
 * This interface provides utility methods for {@link String}.
 * 
 * @author Spirit Tu
 * @version 1.0
 * @since 1.0
 */
public interface StringUtils {

	/**
	 * Check whether the string is blank.
	 * 
	 * @param string the string to check
	 * @return true if the string is null or blank
	 */
	public static boolean isBlank(final String string) {
		return string == null || string.trim().isEmpty();
	}

	/**
	 * Check whether the string is not blank.
	 * 
	 * @param string the string to check
	 * @return true if the string is not blank
	 */
	public static boolean notBlank(final String string) {
		return string != null && !string.trim().isEmpty();
	}

	/**
	 * Assert that the given value is not blank. If the value is blank,
	 * an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param value the value to assert
	 * @param exceptionMessage the exception message
	 * @throws IllegalArgumentException if the value is null or empty
	 * @return the value to assert
	 */
	public static String assertNotBlank(final String value, final String exceptionMessage) {
		if (notBlank(value)) {
			return value;
		}
		throw new IllegalArgumentException(exceptionMessage);
	}
}
