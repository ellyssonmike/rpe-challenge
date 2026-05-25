package com.rpe.challenge.orders.domain.validators;

import com.rpe.challenge.orders.domain.annotations.OptionalCPF;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class OptionalCPFValidator implements ConstraintValidator<OptionalCPF, String> {
	@Override
	public boolean isValid(
		String value,
		ConstraintValidatorContext context
	) {
		if (value == null || value.isBlank()) {
			return true;
		}

		return CPFValidator.isValid(value);
	}
}
