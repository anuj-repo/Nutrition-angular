package com.fertilizer.exception.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fertilizer.exception.HubspotException;
import com.fertilizer.util.Response;

/**
 * @author Imran
 *
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class HubspotExceptionHandler {
	private static final Logger LOGGER = LogManager.getLogger(HubspotExceptionHandler.class);

	@ExceptionHandler(HubspotException.class)
	public ResponseEntity<Response<Object>> errorHAndler(HubspotException ex) {
		LOGGER.catching(ex);
		return ResponseEntity.badRequest().body(new Response<>(ex.getMessage()));
	}
}
