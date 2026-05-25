package com.rpe.challenge.paymentmethods.api.controllers;

import com.rpe.challenge.infra.api.responses.Response;
import com.rpe.challenge.paymentmethods.api.responses.PaymentMethodResponse;
import com.rpe.challenge.paymentmethods.application.services.ListPaymentMethodsService;
import com.rpe.challenge.paymentmethods.domain.mappers.PaymentMethodMapper;
import com.rpe.challenge.shared.exceptions.core.ExceptionModule;
import com.rpe.challenge.shared.exceptions.core.ExceptionModuleType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/payment-methods")
@RequiredArgsConstructor
@ExceptionModule(ExceptionModuleType.PAYMENT_METHODS)
public class ListPaymentMethodsController {
	private final ListPaymentMethodsService listPaymentMethodsService;

	@GetMapping
	ResponseEntity<Response<Collection<PaymentMethodResponse>>> list() {
		return ResponseEntity.ok(
			new Response<>(
				listPaymentMethodsService.execute()
					.stream()
					.map(PaymentMethodMapper::toResponse)
					.toList()
			)
		);
	}
}
