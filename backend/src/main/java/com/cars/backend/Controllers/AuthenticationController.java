package com.cars.backend.Controllers;

import com.cars.backend.AuthenticationClasses.AuthenticationRequest;
import com.cars.backend.AuthenticationClasses.AuthenticationResponse;
import com.cars.backend.AuthenticationClasses.RegisterRequest;
import com.cars.backend.Exceptions.ExceptionModels.EmailRegisteredException;
import com.cars.backend.Services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLIntegrityConstraintViolationException;

@RestController
@RequestMapping("/cars-advert-website/authentication")
//@CrossOrigin(origins = "http://localhost:4200/*")
public class AuthenticationController {
	private final AuthenticationService authenticationService;
	@Autowired
	public AuthenticationController(AuthenticationService authenticationService){
		this.authenticationService = authenticationService;
	}
	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> register(
			@RequestBody RegisterRequest request
			) throws EmailRegisteredException {
			return ResponseEntity.ok(authenticationService.register(request));
	}
	@PostMapping("/authenticate")
	public ResponseEntity<AuthenticationResponse> register(
			@RequestBody AuthenticationRequest request
	){
		return ResponseEntity.ok(authenticationService.authenticate(request));
	}
}
