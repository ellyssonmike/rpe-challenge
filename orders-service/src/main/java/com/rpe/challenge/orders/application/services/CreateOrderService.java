package com.rpe.challenge.orders.application.services;

import com.rpe.challenge.orders.application.inputs.CreateOrderInput;
import com.rpe.challenge.orders.domain.mappers.CreateOrderMapper;
import com.rpe.challenge.orders.domain.mappers.OrderMapper;
import com.rpe.challenge.orders.domain.models.Order;
import com.rpe.challenge.orders.persistence.entities.OrderEntity;
import com.rpe.challenge.orders.persistence.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateOrderService {
	private final OrderRepository orderRepository;

	public Order execute(CreateOrderInput input) {
		Order order = CreateOrderMapper.toDomain(input);

		OrderEntity createdOrder = orderRepository.save(
			OrderMapper.toEntity(order)
		);

		return OrderMapper.toDomain(createdOrder);
	}
}
