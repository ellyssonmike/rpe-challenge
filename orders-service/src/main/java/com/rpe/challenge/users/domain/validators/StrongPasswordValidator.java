package com.rpe.challenge.users.domain.validators;

import com.rpe.challenge.users.domain.annotations.StrongPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StrongPasswordValidator
	implements ConstraintValidator<StrongPassword, String> {

	private StrongPassword constraint;

	@Override
	public void initialize(StrongPassword constraintAnnotation) {
		this.constraint = constraintAnnotation;
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null || value.isBlank()) return false;
		if (value.length() < constraint.minLength()) return false;
		if (constraint.requireUppercase() && !value.matches(".*[A-Z].*")) return false;
		if (constraint.requireLowercase() && !value.matches(".*[a-z].*")) return false;
		if (constraint.requireDigit() && !value.matches(".*\\d.*")) return false;
		return !(constraint.requireSpecialChar() && !value.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*"));
	}
}