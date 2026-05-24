package com.rpe.challenge.users.domain.models;

import com.rpe.challenge.users.domain.enums.UserRole;
import com.rpe.challenge.users.domain.valueobjects.Email;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Builder
public class User {
	private UUID id;
	private String firstName;
	private String lastName;
	private Email email;
	private String password;

	@Builder.Default
	private UserRole role = UserRole.USER;

	private Instant createdAt;
	private Instant updatedAt;
}
