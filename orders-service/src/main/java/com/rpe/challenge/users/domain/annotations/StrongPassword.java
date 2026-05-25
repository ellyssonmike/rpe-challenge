package com.rpe.challenge.users.domain.annotations;

import com.rpe.challenge.users.domain.validators.StrongPasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StrongPasswordValidator.class)
public @interface StrongPassword {

	String message() default "{validation.password.invalid}";

	int minLength() default 8;

	boolean requireUppercase() default true;

	boolean requireLowercase() default true;

	boolean requireDigit() default true;

	boolean requireSpecialChar() default true;

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}