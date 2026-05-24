package com.rpe.challenge.orders.domain.exceptions;

import com.rpe.challenge.shared.exceptions.DomainException;

public class CPFInvalidException extends DomainException {
	public CPFInvalidException(String code, String message) {
		super(code, message);
	}
}
