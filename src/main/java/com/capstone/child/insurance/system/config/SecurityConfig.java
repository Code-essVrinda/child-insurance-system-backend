package com.capstone.child.insurance.system.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

// Security setup for the app.
// Main reason i added this is to get a password encoder (BCrypt) so we
// stop saving passwords as plain text. It also puts all the CORS config
// in one place instead of on every controller.
@Configuration
public class SecurityConfig {

	// This is used to hash the password before saving and to check it on login.
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http
			// we use CORS config from the bean below
			.cors(cors -> cors.configurationSource(corsConfigurationSource()))
			// this is a stateless REST api, so csrf is not needed
			.csrf(AbstractHttpConfigurer::disable)
			// for now all endpoints are open. login is handled by our own code.
			// TODO: later add real auth (like JWT) and lock the endpoints.
			.authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
			// turn off the default login form and basic popup
			.formLogin(AbstractHttpConfigurer::disable)
			.httpBasic(AbstractHttpConfigurer::disable);

		return http.build();
	}

	// One CORS policy for the whole app (frontend runs on localhost:4200).
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
		config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
		config.setAllowedHeaders(Arrays.asList("*"));
		config.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}
}
