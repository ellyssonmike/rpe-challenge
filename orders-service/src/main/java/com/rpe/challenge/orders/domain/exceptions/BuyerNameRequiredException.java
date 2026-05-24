package com.rpe.challenge.orders.domain.exceptions;

import com.rpe.challenge.shared.exceptions.DomainException;

public class BuyerNameRequiredException extends DomainException {
	public BuyerNameRequiredException(String code, String message) {
		super(code, message);
	}
}
