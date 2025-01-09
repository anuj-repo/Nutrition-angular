package com.fertilizer.exception.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fertilizer.exception.IncompleteUserRegistrationException;
import com.fertilizer.payload.response.SignupResponseDTO;
import com.fertilizer.util.Response;

/**
 * @author Dhiraj
 *
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class IncompleteRserRegistrationExceptionHandler {

	private static final Logger LOGGER = LogManager.getLogger(IncompleteRserRegistrationExceptionHandler.class);

	@ExceptionHandler(IncompleteUserRegistrationException.class)
	public ResponseEntity<Response<SignupResponseDTO>> errorHAndler(IncompleteUserRegistrationException ex) {
		LOGGER.catching(ex);
		return ResponseEntity.ok(Response.ok(ex.getSignupResponseDTO(),ex.getMessage()));
	}

}
