package com.jwt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jwt_library.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(InvalidCredentialsException.class)
public ResponseEntity<ErrorResponse> handleInvalidCredentialsException(InvalidCredentialsException exception){
	ErrorResponse errorResponse=new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), exception.getMessage());
	return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
}
}
