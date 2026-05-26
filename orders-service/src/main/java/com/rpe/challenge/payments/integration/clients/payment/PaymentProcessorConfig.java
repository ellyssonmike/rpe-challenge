package com.rpe.challenge.payments.integration.clients.payment;

import feign.Retryer;
import org.springframework.context.annotation.Bean;

public class PaymentProcessorConfig {
	@Bean
	public Retryer feignRetryer() {
		return Retryer.NEVER_RETRY;
	}
}
