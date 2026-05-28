package com.rpe.challenge.shared.exceptions;

import com.rpe.challenge.shared.exceptions.api.FieldErrorDetail;

import java.util.List;

public class DomainException extends ApplicationException {
	public DomainException(String code, String message) {
		super(code, message);
	}

	public DomainException(String code, String message, List<FieldErrorDetail> fields) {
		super(code, message, fields);
	}
}
