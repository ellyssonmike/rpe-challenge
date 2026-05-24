package com.rpe.challenge.orders.domain.valueobjects;

import com.rpe.challenge.orders.domain.validators.CPFValidator;
import com.rpe.challenge.shared.exceptions.BadRequestException;
import com.rpe.challenge.shared.resolvers.MessageResolver;


public record CPF(String value) {
	public CPF(String value) {
		if (value == null || value.isEmpty()) {
			throw new BadRequestException(
				"CPF.VA-OB.REQ",
				MessageResolver.get("validation.cpf.required")
			);
		}

		if (!CPFValidator.isValid(value)) {
			throw new BadRequestException(
				"CPF.VA-OB.INVALID",
				MessageResolver.get("validation.cpf.invalid")
			);
		}

		this.value = value;
	}
}
