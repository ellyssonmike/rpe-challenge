package com.rpe.challenge.shared.exceptions;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends BaseException {
	public UnauthorizedException(String code, String message) {
		super(HttpStatus.UNAUTHORIZED, code, message, null, null);
	}
}
