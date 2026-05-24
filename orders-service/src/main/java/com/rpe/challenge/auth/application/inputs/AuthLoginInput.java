package com.rpe.challenge.auth.application.inputs;

public record AuthLoginInput(
	String email,
	String password
) {
}
