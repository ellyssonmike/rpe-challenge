package com.rpe.challenge.orders.domain.models;

import com.rpe.challenge.orders.domain.enums.OrderStatus;
import com.rpe.challenge.orders.domain.enums.PaymentMethod;
import com.rpe.challenge.orders.domain.valueobjects.BuyerName;
import com.rpe.challenge.orders.domain.valueobjects.CPF;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Builder
public class Order {
	private UUID id;

	@Builder.Default
	private OrderStatus status = OrderStatus.PENDING;

	private PaymentMethod paymentMethod;
	private UUID itemId;
	private long amount;
	private BuyerName buyerName;
	private CPF buyerCpf;
	private Instant paymentDate;
	private Instant createdAt;
	private Instant updatedAt;
}
