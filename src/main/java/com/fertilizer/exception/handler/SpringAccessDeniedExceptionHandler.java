package com.fertilizer.exception.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fertilizer.util.Response;

/**
 * @author Dhiraj
 *
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class SpringAccessDeniedExceptionHandler {
	
	private static final Logger LOGGER = LogManager.getLogger(SpringAccessDeniedExceptionHandler.class);
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<Response<Object>> errorHAndler(AccessDeniedException ex) {
		LOGGER.catching(ex);
		return new ResponseEntity<>(Response.ok(ex.getMessage()), HttpStatus.UNAUTHORIZED);
	}
}
