package com.rpe.challenge.users.domain.valueobjects;

import com.rpe.challenge.shared.resolvers.MessageResolver;
import com.rpe.challenge.users.domain.exceptions.EmailInvalidException;
import com.rpe.challenge.users.domain.exceptions.EmailRequiredException;

import java.util.regex.Pattern;

public record Email(String value) {
	private static final Pattern EMAIL_REGEX =
		Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

	public Email(String value) {
		if (value == null || value.isBlank()) {
			throw new EmailRequiredException(
				"EM.VA-OB.REQ",
				MessageResolver.get("validation.email.required")
			);
		}

		if (!EMAIL_REGEX.matcher(value).matches()) {
			throw new EmailInvalidException(
				"EM.VA-OB.INVALID",
				MessageResolver.get("validation.email.invalid")
			);
		}

		this.value = value.toLowerCase().trim();
	}
}
