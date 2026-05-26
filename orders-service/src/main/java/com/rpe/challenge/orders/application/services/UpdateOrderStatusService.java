package com.rpe.challenge.orders.application.services;

import com.rpe.challenge.orders.application.inputs.UpdateOrderStatusInput;
import com.rpe.challenge.orders.domain.enums.OrderStatus;
import com.rpe.challenge.orders.persistence.entities.OrderEntity;
import com.rpe.challenge.orders.persistence.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateOrderStatusService {
	private final OrderRepository orderRepository;

	public boolean execute(UpdateOrderStatusInput input) {
		OrderStatus orderStatus = OrderStatus.valueOf(input.paymentStatus().name());
		Optional<OrderEntity> order = orderRepository.findById(input.orderId());
		if (order.isEmpty()) {
			log.error("[{}] • Order not found", input.orderId());
			return false;
		}

		OrderEntity entity = order.get();
		entity.setStatus(orderStatus);

		if (orderStatus.equals(OrderStatus.PAID) && input.paymentDate() != null) {
			entity.setPaymentDate(input.paymentDate());
		}

		log.info("[{}] • Updating order status ({})", input.orderId(), orderStatus);
		orderRepository.save(entity);

		return true;
	}
}
