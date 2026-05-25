package com.rpe.challenge.infra.security.config;

import com.rpe.challenge.infra.filters.SecurityFilter;
import com.rpe.challenge.infra.security.jwt.JwtAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	private final SecurityFilter securityFilter;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity
			.csrf(AbstractHttpConfigurer::disable)
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
			.exceptionHandling(ex -> ex.authenticationEntryPoint(jwtAuthenticationEntryPoint))
			.authorizeHttpRequests(
				authorizeRequests -> authorizeRequests
					.requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
					.requestMatchers(HttpMethod.POST, "/users").permitAll()
					.requestMatchers(HttpMethod.POST, "/orders/**").hasRole("ADMIN")
					.requestMatchers(HttpMethod.GET, "/orders/**").hasRole("MANAGER")
					.anyRequest().authenticated())
			.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
}
