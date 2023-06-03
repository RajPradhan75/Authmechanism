package com.security.authmechanism.config;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	
	private final JwtService jwtService;
	

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
			@NonNull FilterChain filterChain) throws ServletException, IOException {

		final String authHeader = request.getHeader("Authorization"); // bearer token
		final String jwt;
		final String userEmail;
		// check
		if (authHeader == null || !authHeader.startsWith("Bearer")) {
			filterChain.doFilter(request, response); // pass request to next filter
			return;
		}
		jwt = authHeader.substring(7); //extract
		userEmail = jwtService.extractUsername(jwt); // extract the email from JWT Token
	}

}
