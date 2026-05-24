package com.rpe.challenge.auth.api.responses;

import java.time.Instant;

public record AuthLoginResponse(
	String accessToken,
	Instant expiresIn
) {
}
