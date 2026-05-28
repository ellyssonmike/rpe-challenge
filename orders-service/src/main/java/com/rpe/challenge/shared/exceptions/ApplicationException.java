package com.rpe.challenge.shared.exceptions;

import com.rpe.challenge.shared.exceptions.api.FieldErrorDetail;
import org.springframework.http.HttpStatus;

import java.util.List;

public class ApplicationException extends BaseException {
	public ApplicationException(String code, String message) {
		super(HttpStatus.INTERNAL_SERVER_ERROR, code, message, null, null);
	}

	public ApplicationException(String code, String message, List<FieldErrorDetail> fields) {
		super(HttpStatus.INTERNAL_SERVER_ERROR, code, message, fields, null);
	}
}
