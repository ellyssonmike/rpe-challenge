package com.rpe.challenge.auth.application.dtos;

import java.time.Instant;

public record AuthLoginResult(
	String accessToken,
	Instant expiresIn
) {
}
