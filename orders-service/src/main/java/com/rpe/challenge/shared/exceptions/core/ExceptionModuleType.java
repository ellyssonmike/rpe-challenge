package com.rpe.challenge.shared.exceptions.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionModuleType {
	AUTH("Auth"),
	ORDERS("Orders"),
	USERS("Users"),
	PAYMENT_METHODS("PaymentMethods");

	private final String column;
}