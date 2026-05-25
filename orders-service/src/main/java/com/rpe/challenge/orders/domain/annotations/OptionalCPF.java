package com.rpe.challenge.orders.domain.annotations;

import com.rpe.challenge.orders.domain.validators.OptionalCPFValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = OptionalCPFValidator.class)
public @interface OptionalCPF {
	String message() default "{validation.cpf.invalid}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
