package com.work.library.config;

import com.work.library.api.base.Response;
import com.work.library.application.exception.ApplicationException;
import com.work.library.application.exception.ErrorType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<Response> handleApplicationException(ApplicationException e) {
        log.error("ApplicationException errorType = {}, message = {}, className = {}", e.getType(), e.getMessage(), e.getClass().getName());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Response.createErrorResponse(e.getType(), e.getType().getDescription()));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Response> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error("MissingServletRequestParameterException parameterName = {}, message = {}, className = {}", e.getParameterName(), e.getMessage(), e.getClass().getName());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Response.createErrorResponse(ErrorType.INVALID_PARAMETER, ErrorType.INVALID_PARAMETER.getDescription()));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Response> handleNoResourceFoundException(NoResourceFoundException e) {
        log.error("NoResourceFoundException message = {}, className = {}", e.getMessage(), e.getClass().getName());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Response.createErrorResponse(ErrorType.RESOURCE_NOT_FOUND, ErrorType.RESOURCE_NOT_FOUND.getDescription()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Response> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("MethodArgumentTypeMismatchException parameterName = {}, message = {}, className = {}", e.getPropertyName(), e.getMessage(), e.getClass().getName());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Response.createErrorResponse(ErrorType.INVALID_PARAMETER, ErrorType.INVALID_PARAMETER.getDescription()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException message = {}, className = {}", e.getMessage(), e.getClass().getName());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Response.createErrorResponse(ErrorType.INVALID_PARAMETER, ErrorType.INVALID_PARAMETER.getDescription()));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<Response> handleBindException(BindException e) {
        log.error("Bind Exception message = {}, className = {}", e.getMessage(), e.getClass().getName());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Response.createErrorResponse(ErrorType.INVALID_PARAMETER, ErrorType.INVALID_PARAMETER.getDescription()));
    }

}
