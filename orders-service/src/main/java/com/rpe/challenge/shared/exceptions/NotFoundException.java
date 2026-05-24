package com.rpe.challenge.shared.exceptions;

import com.rpe.challenge.shared.exceptions.api.FieldErrorDetail;
import org.springframework.http.HttpStatus;

import java.util.List;

public class NotFoundException extends BaseException {
	public NotFoundException(String code, String message, List<FieldErrorDetail> fields) {
		super(HttpStatus.NOT_FOUND, code, message, fields, null);
	}
}
