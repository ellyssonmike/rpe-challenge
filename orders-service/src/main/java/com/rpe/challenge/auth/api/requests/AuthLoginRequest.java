package com.rpe.challenge.auth.api.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AuthLoginRequest(
	@NotBlank
	@NotNull
	@Email
	String email,

	@NotBlank
	@NotNull
	String password
) {
}
