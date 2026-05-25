package com.rpe.challenge.users.api.requests;

import com.rpe.challenge.users.domain.annotations.StrongPassword;
import com.rpe.challenge.users.domain.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateUserRequest(
	@NotBlank
	@NotNull
	String firstName,

	String lastName,

	@NotBlank
	@NotNull
	@Email
	String email,

	@NotBlank
	@NotNull
	@StrongPassword
	String password,

	@NotNull
	UserRole role
) {
}
