package com.fertilizer.exception.handler;

import com.fertilizer.exception.UnauthorizedException;
import com.fertilizer.util.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
@ControllerAdvice
public class UnauthorizedExceptionHandler {

    private static final Logger LOGGER = LogManager.getLogger(UnauthorizedExceptionHandler.class);

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Response<Object>> errorHAndler(UnauthorizedException ex) {
        LOGGER.catching(ex);
        return ResponseEntity.badRequest().body(new Response<>(ex.getMessage()));
    }
}
