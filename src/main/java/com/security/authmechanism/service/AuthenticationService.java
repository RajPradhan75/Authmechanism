package com.security.authmechanism.service;

import java.util.logging.Logger;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.security.authmechanism.auth.AuthenticationRequest;
import com.security.authmechanism.auth.AuthenticationResponse;
import com.security.authmechanism.auth.RegisterRequest;
import com.security.authmechanism.config.JwtService;
import com.security.authmechanism.dao.Role;
import com.security.authmechanism.dao.UserRepository;
import com.security.authmechanism.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	private static final Logger logger = Logger.getLogger(AuthenticationService.class.getName());

	private final UserRepository repository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;

	public AuthenticationResponse register(RegisterRequest request) {
		logger.info("Registering user: " + request.getEmail());
		var user = User.builder().firstname(request.getFirstname()).lastname(request.getLastname())
				.email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
				.role(getUserRole(request)).build();

		user = repository.save(user);
		var jwtToken = jwtService.generateToken(user);
		return AuthenticationResponse.builder().token(jwtToken).build();
	}

	private Role getUserRole(RegisterRequest request) {
		if (isAdmin(request)) {
			return Role.ADMIN;
		}
		return Role.USER;
	}

	private boolean isAdmin(RegisterRequest request) {
		return request.getEmail().equals("admin@gmail.com");
	}

	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		logger.info("Authenticating user: " + request.getEmail());
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		var user = repository.findByEmail(request.getEmail()).orElseThrow();
		var jwtToken = jwtService.generateToken(user);
		return AuthenticationResponse.builder().token(jwtToken).build();
	}

}
