package com.fertilizer.exception.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fertilizer.util.Response;

/**
 * @author Dhiraj
 *
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class BindExceptionHandler {
	private static final Logger LOGGER = LogManager.getLogger(BindExceptionHandler.class);

	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler(BindException.class)
	public ResponseEntity<Response<Map<String, Object>>> errorHAndler(BindException ex) {
		LOGGER.catching(ex);
		BindingResult result = ex.getBindingResult();
		List<FieldError> errors = result.getFieldErrors();
		Map<String, Object> response = new HashMap<>();
		for (FieldError error : errors)
			response.put(error.getField(), messageSource.getMessage(error, LocaleContextHolder.getLocale()));
		return new ResponseEntity<>(new Response<>(response), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = { ConstraintViolationException.class })
	public ResponseEntity<Response<Object>> handleResourceNotFoundException(ConstraintViolationException e) {
		LOGGER.catching(e);
		Map<String, Object> response = new HashMap<>();
		String[] traces = e.getMessage().split("\\,");
		for (String string : traces) {
			String[] split = string.split("\\:");
			if (null != split[0]) {
				String[] split2 = split[0].split("\\.");
				if (null != split[1] && null != split2[split2.length - 1]) {
					response.put(split2[split2.length - 1], split[1]);
				}
			}
		}
		return new ResponseEntity<>(new Response<>(response,e.getMessage()), HttpStatus.BAD_REQUEST);
	}

}
