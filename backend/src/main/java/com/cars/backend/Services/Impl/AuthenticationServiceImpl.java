package com.cars.backend.Services.Impl;

import com.cars.backend.AuthenticationClasses.AuthenticationRequest;
import com.cars.backend.AuthenticationClasses.AuthenticationResponse;
import com.cars.backend.AuthenticationClasses.RegisterRequest;
import com.cars.backend.Models.Dao.Enums.Role;
import com.cars.backend.Models.Dao.UserDao;
import com.cars.backend.Repositories.UserRepository;
import com.cars.backend.Services.AuthenticationService;

import com.cars.backend.Services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	@Autowired
	public AuthenticationServiceImpl(UserRepository userRepository,
									 PasswordEncoder passwordEncoder,
									 JwtService jwtService,
									 AuthenticationManager authenticationManager){
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
		this.authenticationManager = authenticationManager;
	}
	@Override
	public AuthenticationResponse register(RegisterRequest request) {
		var user = UserDao.builder()
				.firstName(request.getFirstName())
				.secondName(request.getLastName())
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.role(Role.USER)
				.build();
		userRepository.save(user);
		var jwtToken = jwtService.generateToken(user);
		return AuthenticationResponse
				.builder()
				.token(jwtToken)
				.build();
	}

	@Override
	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getEmail(),
						request.getPassword()
				)
		);
		var user  = userRepository.findByEmail(request.getEmail())
									.orElseThrow();
		var jwtToken = jwtService.generateToken(user);
		return AuthenticationResponse
				.builder()
				.token(jwtToken)
				.build();
	}
}
