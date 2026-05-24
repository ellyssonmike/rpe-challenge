package com.rpe.challenge.shared.exceptions;

import com.rpe.challenge.shared.exceptions.api.ApplicationExceptionResponse;
import com.rpe.challenge.shared.exceptions.api.FieldErrorDetail;
import com.rpe.challenge.shared.exceptions.core.ExceptionContext;
import com.rpe.challenge.shared.exceptions.core.ExceptionModuleType;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.List;

@Getter
public abstract class BaseException extends RuntimeException {
	private final HttpStatus status;

	private final String module;
	private final String code;
	private final List<FieldErrorDetail> fields;
	private final Object details;
	private final Instant timestamp;

	protected BaseException(
		HttpStatus status,
		String code,
		String message,
		List<FieldErrorDetail> fields,
		Object details
	) {
		super(message);

		this.timestamp = Instant.now();
		this.module = resolveModule();
		this.status = status;
		this.code = code;
		this.fields = fields;
		this.details = details;
	}

	public ApplicationExceptionResponse toResponse() {
		return toResponse(null);
	}

	public ApplicationExceptionResponse toResponse(HttpStatus status) {
		return new ApplicationExceptionResponse(
			timestamp,
			resolveName(),
			module,
			code,
			super.getMessage(),
			status != null
				? status.value()
				: this.status.value(),
			fields,
			details
		);
	}

	private String resolveName() {
		return this.getClass()
			.getSimpleName()
			.replace("Exception", "");
	}

	private String resolveModule() {
		ExceptionModuleType module = ExceptionContext.get();
		if (module == null) {
			return "Application";
		}

		return toPascalCase(module.name());
	}

	public static String toPascalCase(String value) {
		if (value == null || value.isBlank()) {
			return value;
		}

		String lower = value.toLowerCase();
		return Character.toUpperCase(lower.charAt(0))
			+ lower.substring(1);
	}
}
