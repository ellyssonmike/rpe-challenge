package com.rpe.challenge.users.domain.exceptions;

import com.rpe.challenge.shared.exceptions.DomainException;
import com.rpe.challenge.shared.exceptions.api.FieldErrorDetail;

import java.util.List;

public class UserAlreadyExistsException extends DomainException {
	public UserAlreadyExistsException(String code, String message) {
		super(code, message);
	}

	public UserAlreadyExistsException(String code, String message, List<FieldErrorDetail> fields) {
		super(code, message, fields);
	}
}
