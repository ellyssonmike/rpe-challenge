package com.rpe.challenge.paymentmethods.application.services;

import com.rpe.challenge.paymentmethods.domain.mappers.PaymentMethodMapper;
import com.rpe.challenge.paymentmethods.domain.models.PaymentMethod;
import com.rpe.challenge.paymentmethods.persistence.repositories.PaymentMethodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class ListPaymentMethodsService {
	private final PaymentMethodRepository paymentMethodRepository;

	public Collection<PaymentMethod> execute() {
		return paymentMethodRepository.findAll()
			.stream()
			.map(PaymentMethodMapper::toDomain)
			.toList();
	}
}
