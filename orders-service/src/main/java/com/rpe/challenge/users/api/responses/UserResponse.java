package com.rpe.challenge.users.api.responses;

import com.rpe.challenge.users.domain.enums.UserRole;

import java.time.Instant;
import java.util.UUID;

public record UserResponse(
	UUID id,
	String firstName,
	String lastName,
	String email,
	UserRole role,
	Instant createdAt,
	Instant updatedAt
) {
}
