package com.fertilizer.exception;

/**
 * @author Dhiraj
 *
 */
public class SSOException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5307764312079013051L;

	public SSOException() {
		super();
	}

	public SSOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SSOException(String message, Throwable cause) {
		super(message, cause);
	}

	public SSOException(String message) {
		super(message);
	}

	public SSOException(Throwable cause) {
		super(cause);
	}
}
