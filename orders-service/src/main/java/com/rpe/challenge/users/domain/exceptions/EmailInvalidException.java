package com.rpe.challenge.users.domain.exceptions;

import com.rpe.challenge.shared.exceptions.DomainException;

public class EmailInvalidException extends DomainException {
	public EmailInvalidException(String code, String message) {
		super(code, message);
	}
}
