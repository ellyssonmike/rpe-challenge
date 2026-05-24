package com.rpe.challenge.shared.exceptions.api;

import java.util.List;

public record FieldErrorDetail(
	String field,
	List<String> message
) {
}
