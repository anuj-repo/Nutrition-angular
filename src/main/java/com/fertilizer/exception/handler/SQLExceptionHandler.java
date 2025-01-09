package com.fertilizer.exception.handler;


import java.sql.SQLSyntaxErrorException;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.exception.SQLGrammarException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fertilizer.util.Response;

/**
 * @author Dhiraj
 *
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class SQLExceptionHandler {
	private static final Logger LOGGER = LogManager.getLogger(SQLExceptionHandler.class);

	@ExceptionHandler(SQLSyntaxErrorException.class)
	public ResponseEntity<Response<Map<String, Object>>> errorHAndler(SQLSyntaxErrorException ex) {
		LOGGER.catching(ex);
		return new ResponseEntity<>(new Response<>(ex.getMessage()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(SQLGrammarException.class)
	public ResponseEntity<Response<Map<String, Object>>> errorHAndler(SQLGrammarException ex) {
		LOGGER.catching(ex);
		return new ResponseEntity<>(new Response<>(ex.getMessage()), HttpStatus.BAD_REQUEST);
	}

}
