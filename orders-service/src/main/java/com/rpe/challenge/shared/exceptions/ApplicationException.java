package com.rpe.challenge.shared.exceptions;

import org.springframework.http.HttpStatus;

public class ApplicationException extends BaseException {
	public ApplicationException(String code, String message) {
		super(HttpStatus.INTERNAL_SERVER_ERROR, code, message, null, null);
	}
}
