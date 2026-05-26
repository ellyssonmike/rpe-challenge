package com.rpe.challenge.payments.application.services;

import com.rpe.challenge.orders.application.inputs.UpdateOrderStatusInput;
import com.rpe.challenge.orders.application.services.UpdateOrderStatusService;
import com.rpe.challenge.payments.application.messages.PixPaymentStatusMessage;
import com.rpe.challenge.payments.integration.clients.payment.PaymentProcessorGateway;
import com.rpe.challenge.payments.integration.clients.payment.dtos.UpdatePaymentStatusRequest;
import com.rpe.challenge.payments.integration.clients.payment.enums.PaymentStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PixPaymentStatusService {
	private final UpdateOrderStatusService updateOrderStatusService;
	private final PaymentProcessorGateway paymentGateway;

	public void process(PixPaymentStatusMessage message) {
		log.info("[{}] Processing PIX status update ({})", message.orderId(), message.paymentStatus());
		if (message.orderId() == null || message.paymentStatus() == null) {
			log.error("[{}] • Invalid message received", message.orderId());
			return;
		}

		updateOrderStatusService.execute(new UpdateOrderStatusInput(
			message.orderId(),
			message.paymentDate(),
			message.paymentStatus()
		));

		log.info("[{}] • Order status updated to {}", message.orderId(), message.paymentStatus());
		sendPaymentUpdateStatus(message.orderId(), message.paymentDate(), message.paymentStatus());
	}

	void sendPaymentUpdateStatus(UUID orderId, Instant paymentDate, PaymentStatus paymentStatus) {
		log.info("[{}] • Sending PIX payment status update ({})", orderId, paymentStatus);

		paymentGateway.updateStatus(new UpdatePaymentStatusRequest(
			orderId,
			paymentStatus,
			paymentDate
		));

		log.info("[{}] • Payment status updated successfully", orderId);
	}
}