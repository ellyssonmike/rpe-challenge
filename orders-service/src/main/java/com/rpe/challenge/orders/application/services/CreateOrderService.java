package com.rpe.challenge.orders.application.services;

import com.rpe.challenge.orders.application.inputs.CreateOrderInput;
import com.rpe.challenge.orders.domain.enums.OrderStatus;
import com.rpe.challenge.orders.domain.mappers.CreateOrderMapper;
import com.rpe.challenge.orders.domain.mappers.OrderMapper;
import com.rpe.challenge.orders.domain.models.Order;
import com.rpe.challenge.orders.persistence.entities.OrderEntity;
import com.rpe.challenge.orders.persistence.repositories.OrderRepository;
import com.rpe.challenge.payments.integration.clients.payment.PaymentProcessorGateway;
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
	private final PaymentProcessorGateway paymentGateway;

	public Order execute(CreateOrderInput input) {
		Order order = CreateOrderMapper.toDomain(input);

		OrderEntity createdOrder = orderRepository.save(
			OrderMapper.toEntity(order)
		);

		log.info("[{}] {} order created ({})", createdOrder.getId(), createdOrder.getStatus(), createdOrder.getPaymentMethod());
		log.info("[{}] • Sending {} payment request", createdOrder.getId(), createdOrder.getPaymentMethod());

		PaymentProcessResponse response = paymentGateway.process(new PaymentProcessRequest(
			order.getPaymentMethod(),
			createdOrder.getId(),
			order.getItemId(),
			order.getAmount(),
			order.getBuyerName().value(),
			order.getBuyerCpf().value()
		));

		createdOrder.setStatus(OrderStatus.valueOf(response.paymentStatus().name()));
		createdOrder.setPaymentDate(response.paymentDate());

		orderRepository.save(createdOrder);
		if (createdOrder.getStatus() != OrderStatus.PENDING) {
			log.info("[{}] • Updated order status to {}", createdOrder.getId(), response.paymentStatus());
		}

		return OrderMapper.toDomain(createdOrder);
	}
}
