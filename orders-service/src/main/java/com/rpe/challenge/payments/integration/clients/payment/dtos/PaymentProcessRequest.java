package com.rpe.challenge.payments.integration.clients.payment.dtos;


import com.rpe.challenge.orders.domain.enums.PaymentMethod;

import java.util.UUID;

public record PaymentProcessRequest(
	PaymentMethod paymentMethod,
	UUID orderId,
	UUID itemId,
	long amount,
	String buyerName,
	String buyerCpf
) {
}
