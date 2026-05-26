package com.rpe.challenge.payments.application.services;

import com.rpe.challenge.orders.application.inputs.UpdateOrderStatusInput;
import com.rpe.challenge.orders.application.services.UpdateOrderStatusService;
import com.rpe.challenge.payments.application.messages.PixPaymentStatusMessage;
import com.rpe.challenge.payments.integration.clients.payment.PaymentProcessorClient;
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
	private final PaymentProcessorClient client;

	public void process(PixPaymentStatusMessage message) {
		log.info("[{}] Processing PIX status update ({})", message.orderId(), message.paymentStatus());
		if (message.orderId() == null || message.paymentStatus() == null) {
			log.error("[{}] • Invalid message received", message.orderId());
			return;
		}

		if (!updateOrderStatusService.execute(new UpdateOrderStatusInput(
			message.orderId(),
			message.paymentDate(),
			message.paymentStatus()
		))) {
			log.warn("[{}] Could not process PIX status update ({})", message.orderId(), message.paymentStatus());
			return;
		}

		log.info("[{}] • Order status updated to {}", message.orderId(), message.paymentStatus());
		sendPaymentUpdateStatus(message.orderId(), message.paymentDate(), message.paymentStatus());
	}

	void sendPaymentUpdateStatus(UUID orderId, Instant paymentDate, PaymentStatus paymentStatus) {
		log.info("[{}] • Sending PIX payment status update ({})", orderId, paymentStatus);

		try {
			client.updateStatus(new UpdatePaymentStatusRequest(
				orderId,
				paymentStatus,
				paymentDate
			));
		} catch (Exception ex) {
			log.warn("[{}] Payment service unavailable, keeping payment status unchanged. Reason: {}", orderId, ex.getMessage());
			return;
		}

		log.info("[{}] • Payment status updated successfully", orderId);
	}
}