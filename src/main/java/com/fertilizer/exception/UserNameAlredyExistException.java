package com.fertilizer.exception;

/**
 * @author Dhiraj
 *
 */
public class UserNameAlredyExistException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7677377211476130430L;

	public UserNameAlredyExistException() {
		super();
	}

	public UserNameAlredyExistException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UserNameAlredyExistException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserNameAlredyExistException(String message) {
		super(message);
	}

	public UserNameAlredyExistException(Throwable cause) {
		super(cause);
	}
}
