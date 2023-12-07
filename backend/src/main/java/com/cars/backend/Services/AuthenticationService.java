package com.cars.backend.Services;

import com.cars.backend.AuthenticationClasses.AuthenticationRequest;
import com.cars.backend.AuthenticationClasses.AuthenticationResponse;
import com.cars.backend.AuthenticationClasses.RegisterRequest;

public interface AuthenticationService {
	public AuthenticationResponse register(RegisterRequest request);

	public AuthenticationResponse authenticate(AuthenticationRequest request);
}
