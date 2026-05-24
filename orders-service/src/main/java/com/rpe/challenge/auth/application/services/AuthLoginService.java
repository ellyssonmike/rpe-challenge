package com.rpe.challenge.auth.application.services;

import com.rpe.challenge.auth.application.dtos.AuthLoginResult;
import com.rpe.challenge.auth.application.inputs.AuthLoginInput;
import com.rpe.challenge.auth.security.UserPrincipal;
import com.rpe.challenge.infra.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthLoginService {
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;

	public AuthLoginResult execute(AuthLoginInput input) {
		var credentials = new UsernamePasswordAuthenticationToken(input.email(), input.password());
		var authentication = authenticationManager.authenticate(credentials);

		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
		String accessToken = jwtService.generateToken(
			userPrincipal.getUsername(),
			Map.of("role", userPrincipal.getRole().name())
		);

		return new AuthLoginResult(
			accessToken,
			jwtService.extractExpiration(accessToken)
		);
	}
}
