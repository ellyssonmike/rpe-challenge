package com.rpe.challenge.orders.api.responses;

import java.time.Instant;
import java.util.UUID;

public record OrderResponse(
	UUID id,
	String status,
	String paymentMethod,
	UUID itemId,
	long amount,
	String buyerName,
	String buyerCpf,
	Instant paymentDate,
	Instant createdAt,
	Instant updatedAt
) {
}
