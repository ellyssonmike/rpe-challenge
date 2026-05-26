package com.rpe.challenge.payments.integration.clients.payment;

import com.rpe.challenge.payments.integration.clients.payment.dtos.PaymentProcessRequest;
import com.rpe.challenge.payments.integration.clients.payment.dtos.PaymentProcessResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
	name = "payment-processor",
	url = "${clients.payment-processor.url}"
)
public interface PaymentProcessorClient {
	@PostMapping("/payments/process")
	PaymentProcessResponse process(@RequestBody PaymentProcessRequest request);
}
