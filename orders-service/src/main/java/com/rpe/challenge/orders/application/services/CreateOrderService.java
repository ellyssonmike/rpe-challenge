package com.rpe.challenge.orders.application.services;

import com.rpe.challenge.orders.application.inputs.CreateOrderInput;
import com.rpe.challenge.orders.domain.enums.OrderStatus;
import com.rpe.challenge.orders.domain.mappers.CreateOrderMapper;
import com.rpe.challenge.orders.domain.mappers.OrderMapper;
import com.rpe.challenge.orders.domain.models.Order;
import com.rpe.challenge.orders.persistence.entities.OrderEntity;
import com.rpe.challenge.orders.persistence.repositories.OrderRepository;
import com.rpe.challenge.payments.integration.clients.payment.PaymentProcessorClient;
import com.rpe.challenge.payments.integration.clients.payment.dtos.PaymentProcessRequest;
import com.rpe.challenge.payments.integration.clients.payment.dtos.PaymentProcessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateOrderService {
	private final OrderRepository orderRepository;
	private final PaymentProcessorClient client;

	public Order execute(CreateOrderInput input) {
		Order order = CreateOrderMapper.toDomain(input);

		OrderEntity createdOrder = orderRepository.save(
			OrderMapper.toEntity(order)
		);

		try {
			PaymentProcessResponse response = client.process(new PaymentProcessRequest(
				order.getPaymentMethod(),
				createdOrder.getId(),
				order.getItemId(),
				order.getAmount(),
				order.getBuyerName().value(),
				order.getBuyerCpf().value()
			));

			createdOrder.setStatus(OrderStatus.valueOf(response.paymentStatus().name()));
			createdOrder.setPaymentDate(response.paymentDate());
		} catch (Exception ex) {
			log.warn("[{}] Payment service unavailable, keeping order PENDING. Reason: {}", createdOrder.getId(), ex.getMessage());
		}

		orderRepository.save(createdOrder);

		return OrderMapper.toDomain(createdOrder);
	}
}
