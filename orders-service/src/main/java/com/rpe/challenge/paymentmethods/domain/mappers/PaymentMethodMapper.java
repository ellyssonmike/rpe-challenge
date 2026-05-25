package com.rpe.challenge.paymentmethods.domain.mappers;

import com.rpe.challenge.paymentmethods.api.responses.PaymentMethodResponse;
import com.rpe.challenge.paymentmethods.domain.models.PaymentMethod;
import com.rpe.challenge.paymentmethods.persistence.entities.PaymentMethodEntity;

public class PaymentMethodMapper {
	public static PaymentMethod toDomain(PaymentMethodEntity entity) {
		return new PaymentMethod(
			entity.getId(),
			entity.getDescription()
		);
	}

	public static PaymentMethodResponse toResponse(PaymentMethod paymentMethod) {
		return new PaymentMethodResponse(
			paymentMethod.getId(),
			paymentMethod.getDescription()
		);
	}
}
