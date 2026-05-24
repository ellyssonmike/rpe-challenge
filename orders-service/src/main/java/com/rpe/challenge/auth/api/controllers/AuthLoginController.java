package com.rpe.challenge.auth.api.controllers;

import com.rpe.challenge.auth.api.requests.AuthLoginRequest;
import com.rpe.challenge.auth.api.responses.AuthLoginResponse;
import com.rpe.challenge.auth.application.dtos.AuthLoginResult;
import com.rpe.challenge.auth.application.inputs.AuthLoginInput;
import com.rpe.challenge.auth.application.services.AuthLoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthLoginController {
	private final AuthLoginService authLoginService;

	@PostMapping("/login")
	ResponseEntity<AuthLoginResponse> login(@Valid @RequestBody AuthLoginRequest request) {
		AuthLoginResult result = authLoginService.execute(new AuthLoginInput(
			request.email(),
			request.password()
		));

		return ResponseEntity.ok(new AuthLoginResponse(
			result.accessToken(),
			result.expiresIn()
		));
	}
}
