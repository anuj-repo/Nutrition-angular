package com.fertilizer.exception;

/**
 * @author Dhiraj
 *
 */
public class AlreadyLoggedInException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8671362517855261180L;

	public AlreadyLoggedInException(String message) {
		super(message);
	}

}
