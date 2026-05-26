package com.rpe.challenge.payments.application.messages;

import com.rpe.challenge.payments.integration.clients.payment.enums.PaymentStatus;

import java.time.Instant;
import java.util.UUID;

public record PixPaymentStatusMessage(
	UUID orderId,
	Instant paymentDate,
	PaymentStatus paymentStatus
) {
}
