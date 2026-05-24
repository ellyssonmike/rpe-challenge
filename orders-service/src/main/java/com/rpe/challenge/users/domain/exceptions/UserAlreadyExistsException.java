package com.rpe.challenge.users.domain.exceptions;

import com.rpe.challenge.shared.exceptions.DomainException;

public class UserAlreadyExistsException extends DomainException {
	public UserAlreadyExistsException(String code, String message) {
		super(code, message);
	}
}
