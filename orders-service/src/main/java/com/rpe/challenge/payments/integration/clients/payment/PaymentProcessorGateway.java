package com.rpe.challenge.payments.integration.clients.payment;

import com.rpe.challenge.payments.integration.clients.payment.dtos.PaymentProcessRequest;
import com.rpe.challenge.payments.integration.clients.payment.dtos.PaymentProcessResponse;
import com.rpe.challenge.payments.integration.clients.payment.dtos.UpdatePaymentStatusRequest;
import com.rpe.challenge.payments.integration.clients.payment.enums.PaymentStatus;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentProcessorGateway {
	private final PaymentProcessorClient client;

	@CircuitBreaker(name = "payment-processor")
	@Retry(name = "payment-processor", fallbackMethod = "fallbackProcess")
	public PaymentProcessResponse process(PaymentProcessRequest request) {
		return client.process(request);
	}

	@CircuitBreaker(name = "payment-processor")
	@Retry(name = "payment-processor", fallbackMethod = "fallbackUpdateStatus")
	public void updateStatus(UpdatePaymentStatusRequest request) {
		client.updateStatus(request);
	}

	public PaymentProcessResponse fallbackProcess(PaymentProcessRequest request, Throwable ex) {
		log.warn("[{}] Payment service unavailable, keeping order PENDING. Reason: {}", request.orderId(), ex.getMessage());

		return new PaymentProcessResponse(
			PaymentStatus.PENDING,
			null,
			null
		);
	}

	public void fallbackUpdateStatus(UpdatePaymentStatusRequest request, Throwable ex) {
		log.warn("[{}] Payment service unavailable, keeping payment status unchanged. Reason: {}", request.orderId(), ex.getMessage());
		
		throw new RuntimeException("Failed to update status after retries", ex);
	}
}