package com.fertilizer.exception;

/**
 * @author Imran
 *
 */
public class HubspotException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5307764312079013051L;

	public HubspotException() {
		super();
	}

	public HubspotException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public HubspotException(String message, Throwable cause) {
		super(message, cause);
	}

	public HubspotException(String message) {
		super(message);
	}

	public HubspotException(Throwable cause) {
		super(cause);
	}
}
