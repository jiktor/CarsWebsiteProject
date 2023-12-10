package com.cars.backend.Exceptions.ExceptionModels;

public class EmailRegisteredException extends RuntimeException{
	public EmailRegisteredException(String message){
		super(message);
	}
}
