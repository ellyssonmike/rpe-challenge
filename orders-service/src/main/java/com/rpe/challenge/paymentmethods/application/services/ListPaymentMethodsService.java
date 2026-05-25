package com.rpe.challenge.paymentmethods.application.services;

import com.rpe.challenge.paymentmethods.api.responses.PaymentMethodResponse;
import com.rpe.challenge.paymentmethods.domain.mappers.PaymentMethodMapper;
import com.rpe.challenge.paymentmethods.persistence.repositories.PaymentMethodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
@Cacheable(value = "payment-methods", key = "'all'")
public class ListPaymentMethodsService {
	private final PaymentMethodRepository paymentMethodRepository;

	public Collection<PaymentMethodResponse> execute() {
		return paymentMethodRepository.findAll()
			.stream()
			.map(PaymentMethodMapper::toDomain)
			.map(PaymentMethodMapper::toResponse)
			.toList();
	}
}
