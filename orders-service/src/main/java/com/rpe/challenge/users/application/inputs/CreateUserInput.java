package com.rpe.challenge.users.application.inputs;

import com.rpe.challenge.users.domain.enums.UserRole;

public record CreateUserInput(
	String firstName,
	String lastName,
	String email,
	String password,
	UserRole role
) {
}