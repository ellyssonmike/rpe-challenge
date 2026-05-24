package com.rpe.challenge.shared.exceptions;

import com.rpe.challenge.shared.exceptions.api.FieldErrorDetail;
import org.springframework.http.HttpStatus;

import java.util.List;

public class UnprocessableEntityException extends BaseException {
	public UnprocessableEntityException(String code, String message, List<FieldErrorDetail> fields) {
		super(HttpStatus.UNPROCESSABLE_ENTITY, code, message, fields, null);
	}
}
