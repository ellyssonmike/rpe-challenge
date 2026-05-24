package com.rpe.challenge.shared.exceptions;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends BaseException {
	public ForbiddenException(String code, String message) {
		super(HttpStatus.FORBIDDEN, code, message, null, null);
	}
}
