package com.fertilizer.exception;

/**
 * @author Dhiraj
 *
 */
public class InvalidUserNameOrPasswordException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1245404370285948909L;

	public InvalidUserNameOrPasswordException() {
	}

	public InvalidUserNameOrPasswordException(String message) {
		super(message);
	}

	public InvalidUserNameOrPasswordException(Throwable cause) {
		super(cause);
	}

	public InvalidUserNameOrPasswordException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidUserNameOrPasswordException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
