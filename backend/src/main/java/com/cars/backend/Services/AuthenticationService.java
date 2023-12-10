package com.cars.backend.Services;

import com.cars.backend.AuthenticationClasses.AuthenticationRequest;
import com.cars.backend.AuthenticationClasses.AuthenticationResponse;
import com.cars.backend.AuthenticationClasses.RegisterRequest;
import com.cars.backend.Exceptions.ExceptionModels.EmailRegisteredException;

import java.sql.SQLIntegrityConstraintViolationException;

public interface AuthenticationService {
	public AuthenticationResponse register(RegisterRequest request) throws  EmailRegisteredException;

	public AuthenticationResponse authenticate(AuthenticationRequest request);
}
