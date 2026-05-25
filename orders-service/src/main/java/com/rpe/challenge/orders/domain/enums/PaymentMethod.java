package com.rpe.challenge.orders.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentMethod {
	PIX("pix"),
	CREDIT_CARD("creditCard"),
	DEBIT_CARD("debitCard");

	private final String column;
}
