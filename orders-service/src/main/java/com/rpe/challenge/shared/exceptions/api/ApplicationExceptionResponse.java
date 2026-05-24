package com.rpe.challenge.shared.exceptions.api;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApplicationExceptionResponse(
	Instant timestamp,
	String requestId,
	String error,
	String module,
	String code,
	String message,
	Integer status,
	List<FieldErrorDetail> fields,
	Object details
) {
}
