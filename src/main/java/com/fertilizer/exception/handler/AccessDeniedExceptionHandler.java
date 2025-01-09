package com.fertilizer.exception.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fertilizer.exception.AccessDeniedException;
import com.fertilizer.util.Response;

/**
 * @author Dhiraj
 *
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
@ControllerAdvice
public class AccessDeniedExceptionHandler {
	private static final Logger LOGGER = LogManager.getLogger(AccessDeniedExceptionHandler.class);

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<Response<Object>> errorHAndler(AccessDeniedException ex) {
		LOGGER.catching(ex);
		return ResponseEntity.badRequest().body(new Response<>(ex.getMessage()));
	}
}