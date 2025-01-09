package com.fertilizer.exception;

public class InconsistentDataException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3524399816201977820L;

	public InconsistentDataException() {
		super();
	}

	public InconsistentDataException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InconsistentDataException(String message, Throwable cause) {
		super(message, cause);
	}

	public InconsistentDataException(String message) {
		super(message);
	}

	public InconsistentDataException(Throwable cause) {
		super(cause);
	}
}
