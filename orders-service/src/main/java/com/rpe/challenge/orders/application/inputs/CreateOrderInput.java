package com.rpe.challenge.orders.application.inputs;

import com.rpe.challenge.orders.domain.enums.PaymentMethod;

import java.util.UUID;

public record CreateOrderInput(
	UUID itemId,
	PaymentMethod paymentMethod,
	long amount,
	String buyerName,
	String buyerCpf
) {
}
