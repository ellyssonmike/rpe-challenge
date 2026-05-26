package com.rpe.challenge.payments.integration.clients.payment.dtos;

import com.rpe.challenge.payments.integration.clients.payment.enums.PaymentStatus;

import java.time.Instant;
import java.util.UUID;

public record UpdatePaymentStatusRequest(
	UUID orderId,
	PaymentStatus paymentStatus,
	Instant paymentDate
) {
}
