package com.fertilizer.exception;

import com.fertilizer.payload.response.SignupResponseDTO;

import lombok.Getter;

/**
 * @author Dhiraj
 *
 */
@Getter
public class IncompleteUserRegistrationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7010526083849089154L;
	private final SignupResponseDTO signupResponseDTO;

	public IncompleteUserRegistrationException() {
		signupResponseDTO=null;
	}

	public IncompleteUserRegistrationException(String message) {
		super(message);
		signupResponseDTO=null;
	}

	public IncompleteUserRegistrationException(Throwable cause) {
		super(cause);
		signupResponseDTO=null;
	}

	public IncompleteUserRegistrationException(String message, Throwable cause) {
		super(message, cause);
		signupResponseDTO=null;
	}

	public IncompleteUserRegistrationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		signupResponseDTO=null;
	}

	public IncompleteUserRegistrationException(String message, SignupResponseDTO signupResponseDTO) {
		super(message);
		this.signupResponseDTO=signupResponseDTO;
	}

}
