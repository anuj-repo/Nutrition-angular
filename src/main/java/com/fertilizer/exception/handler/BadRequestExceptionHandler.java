package com.fertilizer.exception.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fertilizer.exception.BadRequestException;
import com.fertilizer.util.Response;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class BadRequestExceptionHandler {

	private static final Logger LOGGER = LogManager.getLogger(BadRequestExceptionHandler.class);

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<Response<Object>> errorHAndler(BadRequestException ex) {
		LOGGER.catching(ex);
		return ResponseEntity.badRequest().body(Response.ok(ex.getMessage()));
	}

}
