package com.rpe.challenge.orders.application.inputs;

import com.rpe.challenge.payments.integration.clients.payment.enums.PaymentStatus;

import java.time.Instant;
import java.util.UUID;

public record UpdateOrderStatusInput(
	UUID orderId,
	Instant paymentDate,
	PaymentStatus paymentStatus
) {
}
