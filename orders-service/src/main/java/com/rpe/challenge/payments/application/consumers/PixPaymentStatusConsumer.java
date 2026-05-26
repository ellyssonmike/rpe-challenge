package com.rpe.challenge.payments.application.consumers;

import com.rpe.challenge.payments.application.messages.PixPaymentStatusMessage;
import com.rpe.challenge.payments.application.services.PixPaymentStatusService;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PixPaymentStatusConsumer {
	private final PixPaymentStatusService service;

	@SqsListener("pix-payment-status")
	public void process(PixPaymentStatusMessage message) {
		log.info("[{}] Received message", message.orderId());
		service.process(message);
	}
}
