package com.rpe.challenge.infra.api.responses;

import org.slf4j.MDC;

import java.time.Instant;

public record Response<T>(
	Instant timestamp,
	String requestId,
	T data
) {
	public Response(T data) {
		this(Instant.now(), MDC.get("requestId"), data);
	}
}
