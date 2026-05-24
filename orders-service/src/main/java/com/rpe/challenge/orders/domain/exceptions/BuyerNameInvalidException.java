package com.rpe.challenge.orders.domain.exceptions;

import com.rpe.challenge.shared.exceptions.DomainException;

public class BuyerNameInvalidException extends DomainException {
	public BuyerNameInvalidException(String code, String message) {
		super(code, message);
	}
}
