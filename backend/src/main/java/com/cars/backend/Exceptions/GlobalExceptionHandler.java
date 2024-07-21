package com.cars.backend.Exceptions;

import com.cars.backend.Exceptions.ExceptionModels.EmailRegisteredException;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(EmailRegisteredException.class)
	public ResponseEntity<Map<String, String>> handleEmailAlreadyRegistered(EmailRegisteredException e){
		Map<String, String> errorResponse = new HashMap<>();
		errorResponse.put("message",e.getMessage());
		return ResponseEntity.status(403).body(errorResponse);
	}
	@ExceptionHandler(ExpiredJwtException.class)
	public ResponseEntity<Map<String,String>> handleExpiredToken(io.jsonwebtoken.ExpiredJwtException e){
		Map<String, String> errorResponse = new HashMap<>();
		errorResponse.put("message","You need to login again since your credentials have expired");
		return ResponseEntity.status(405).body(errorResponse);
	}
	@ExceptionHandler(org.springframework.security.authentication.BadCredentialsException.class)
	public ResponseEntity<Map<String,String>> handleBadCredentialsException(org.springframework.security.authentication.BadCredentialsException e){
		Map <String, String> errorResponse = new HashMap<>();
		errorResponse.put("message",e.getMessage());
		return ResponseEntity.status(401).body(errorResponse);
	}
}
