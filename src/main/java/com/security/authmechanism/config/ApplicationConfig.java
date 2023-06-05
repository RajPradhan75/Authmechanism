package com.security.authmechanism.config;

import java.util.logging.Logger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.security.authmechanism.dao.UserRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

	private static final Logger logger = Logger.getLogger(ApplicationConfig.class.getName());

	private final UserRepository repository;

	@Bean
	public UserDetailsService userDetailsService() {
		return username -> {
			logger.info("Fetching user details for username: " + username);
			return repository.findByEmail(username).orElseThrow(() -> {
				logger.warning("User not found: " + username);
				return new UsernameNotFoundException("User not found");
			});
		};
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		logger.info("AuthenticationProvider bean created");
		return authProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		AuthenticationManager authenticationManager = config.getAuthenticationManager();
		logger.info("AuthenticationManager bean created");
		return authenticationManager;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		logger.info("PasswordEncoder bean created");
		return passwordEncoder;
	}
}
