package com.cars.backend.Controllers;

import com.cars.backend.AuthenticationClasses.AuthenticationRequest;
import com.cars.backend.AuthenticationClasses.AuthenticationResponse;
import com.cars.backend.AuthenticationClasses.RegisterRequest;
import com.cars.backend.Services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cars-advert-website/authentication")
public class AuthenticationController {
	private final AuthenticationService authenticationService;
	@Autowired
	public AuthenticationController(AuthenticationService authenticationService){
		this.authenticationService = authenticationService;
	}
	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> register(
			@RequestBody RegisterRequest request
	){
		return  ResponseEntity.ok(authenticationService.register(request));
	}
	@PostMapping("/authenticate")
	public ResponseEntity<AuthenticationResponse> register(
			@RequestBody AuthenticationRequest request
	){
		return ResponseEntity.ok(authenticationService.authenticate(request));
	}
}
