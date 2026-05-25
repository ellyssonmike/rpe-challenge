package com.rpe.challenge.orders.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderSortColumn {
	STATUS("status"),
	AMOUNT("amount"),
	BUYER_CPF("buyerCpf"),
	BUYER_NAME("buyerName"),
	PAYMENT_DATE("paymentDate"),
	CREATED_AT("createdAt"),
	UPDATED_AT("updatedAt");

	private final String column;
}
