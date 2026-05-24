package com.rpe.challenge.shared.exceptions;

import com.rpe.challenge.shared.exceptions.api.FieldErrorDetail;
import org.springframework.http.HttpStatus;

import java.util.List;

public class BadRequestException extends BaseException {
	public BadRequestException(String code, String message, List<FieldErrorDetail> fields) {
		super(HttpStatus.BAD_REQUEST, code, message, fields, null);
	}

	public BadRequestException(String code, String message) {
		this(code, message, null);
	}
}
