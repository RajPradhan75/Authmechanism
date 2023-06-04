package com.security.authmechanism.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.authmechanism.auth.AuthenticationRequest;
import com.security.authmechanism.auth.AuthenticationResponse;
import com.security.authmechanism.auth.RegisterRequest;
import com.security.authmechanism.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
	
	private final AuthenticationService service;
	
	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
		return ResponseEntity.ok(service.register(request));
	}
	
	
	@PostMapping("/authenticate")
	public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
		return ResponseEntity.ok(service.authenticate(request));
	}
	
	
	
}
