package com.rpe.challenge.shared.exceptions;

import com.rpe.challenge.shared.exceptions.api.FieldErrorDetail;
import org.springframework.http.HttpStatus;

import java.util.List;

public class ConflictException extends BaseException {
	public ConflictException(String code, String message, List<FieldErrorDetail> fields) {
		super(HttpStatus.CONFLICT, code, message, fields, null);
	}
}
