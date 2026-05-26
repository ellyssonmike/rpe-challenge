package com.rpe.challenge.payments.integration.clients.payment.dtos;

import com.rpe.challenge.payments.integration.clients.payment.enums.PaymentStatus;

import java.time.Instant;

public record PaymentProcessResponse(
	PaymentStatus paymentStatus,
	Instant paymentDate,
	Instant processedAt
) {
}
