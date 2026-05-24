package com.rpe.challenge.users.domain.exceptions;

import com.rpe.challenge.shared.exceptions.DomainException;

public class EmailRequiredException extends DomainException {
	public EmailRequiredException(String code, String message) {
		super(code, message);
	}
}
