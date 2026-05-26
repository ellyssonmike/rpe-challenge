package com.rpe.challenge.payments.integration.clients.payment;

import com.rpe.challenge.payments.integration.clients.payment.dtos.PaymentProcessRequest;
import com.rpe.challenge.payments.integration.clients.payment.dtos.PaymentProcessResponse;
import com.rpe.challenge.payments.integration.clients.payment.dtos.UpdatePaymentStatusRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
	name = "payment-processor",
	url = "${clients.payment-processor.url}",
	configuration = PaymentProcessorConfig.class
)
public interface PaymentProcessorClient {
	@PostMapping("/payments/process")
	PaymentProcessResponse process(@RequestBody PaymentProcessRequest request);

	@PatchMapping("/payments/status")
	void updateStatus(@RequestBody UpdatePaymentStatusRequest request);
}
