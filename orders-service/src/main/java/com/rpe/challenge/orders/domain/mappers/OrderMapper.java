package com.rpe.challenge.orders.domain.mappers;

import com.rpe.challenge.orders.api.responses.OrderResponse;
import com.rpe.challenge.orders.domain.models.Order;
import com.rpe.challenge.orders.domain.valueobjects.BuyerName;
import com.rpe.challenge.orders.domain.valueobjects.CPF;
import com.rpe.challenge.orders.persistence.entities.OrderEntity;

public class OrderMapper {
	public static Order toDomain(OrderEntity entity) {
		return Order.builder()
			.id(entity.getId())
			.status(entity.getStatus())
			.paymentMethod(entity.getPaymentMethod())
			.itemId(entity.getItemId())
			.amount(entity.getAmount())
			.buyerName(new BuyerName(entity.getBuyerName()))
			.buyerCpf(new CPF(entity.getBuyerCpf()))
			.paymentDate(entity.getPaymentDate())
			.createdAt(entity.getCreatedAt())
			.updatedAt(entity.getUpdatedAt())
			.build();
	}

	public static OrderEntity toEntity(Order order) {
		return OrderEntity.builder()
			.id(order.getId())
			.status(order.getStatus())
			.paymentMethod(order.getPaymentMethod())
			.itemId(order.getItemId())
			.amount(order.getAmount())
			.buyerName(order.getBuyerName().value())
			.buyerCpf(order.getBuyerCpf().value())
			.paymentDate(order.getPaymentDate())
			.createdAt(order.getCreatedAt())
			.updatedAt(order.getUpdatedAt())
			.build();
	}

	public static OrderResponse toResponse(Order order) {
		return new OrderResponse(
			order.getId(),
			order.getStatus().name(),
			order.getPaymentMethod().name(),
			order.getItemId(),
			order.getAmount(),
			order.getBuyerName().value(),
			order.getBuyerCpf().value(),
			order.getPaymentDate(),
			order.getCreatedAt(),
			order.getUpdatedAt()
		);
	}
}
