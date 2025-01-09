package com.fertilizer.exception;

/**
 * @author Dhiraj
 *
 */
public class CCAvenueException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2066522924950355387L;

	public CCAvenueException() {
		super();
	}

	public CCAvenueException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CCAvenueException(String message, Throwable cause) {
		super(message, cause);
	}

	public CCAvenueException(String message) {
		super(message);
	}

	public CCAvenueException(Throwable cause) {
		super(cause);
	}
}
