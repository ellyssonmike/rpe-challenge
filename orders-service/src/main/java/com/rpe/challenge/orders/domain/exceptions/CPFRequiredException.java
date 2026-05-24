package com.rpe.challenge.orders.domain.exceptions;

import com.rpe.challenge.shared.exceptions.DomainException;

public class CPFRequiredException extends DomainException {
	public CPFRequiredException(String code, String message) {
		super(code, message);
	}
}
