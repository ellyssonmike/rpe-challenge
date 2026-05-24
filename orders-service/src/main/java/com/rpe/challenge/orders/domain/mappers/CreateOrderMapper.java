package com.rpe.challenge.orders.domain.mappers;

import com.rpe.challenge.orders.api.requests.CreateOrderRequest;
import com.rpe.challenge.orders.application.inputs.CreateOrderInput;
import com.rpe.challenge.orders.domain.models.Order;
import com.rpe.challenge.orders.domain.valueobjects.BuyerName;
import com.rpe.challenge.orders.domain.valueobjects.CPF;

import java.util.UUID;

public class CreateOrderMapper {
	public static Order toDomain(CreateOrderInput input) {
		return Order.builder()
			.itemId(input.itemId())
			.paymentMethod(input.paymentMethod())
			.amount(input.amount())
			.buyerName(new BuyerName(input.buyerName()))
			.buyerCpf(new CPF(input.buyerCpf()))
			.build();
	}

	public static CreateOrderInput toInput(CreateOrderRequest request) {
		return new CreateOrderInput(
			UUID.fromString(request.itemId()),
			request.paymentMethod(),
			request.amount(),
			request.buyerName(),
			request.buyerCpf()
		);
	}
}
