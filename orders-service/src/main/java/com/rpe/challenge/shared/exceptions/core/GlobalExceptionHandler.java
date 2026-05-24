package com.rpe.challenge.shared.exceptions.core;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.rpe.challenge.shared.exceptions.*;
import com.rpe.challenge.shared.exceptions.api.ApplicationExceptionResponse;
import com.rpe.challenge.shared.exceptions.api.FieldErrorDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BaseException.class)
	public ResponseEntity<ApplicationExceptionResponse> handleBaseException(BaseException ex) {
		return ResponseEntity
			.status(ex.getStatus())
			.body(ex.toResponse());
	}

	@ExceptionHandler(DomainException.class)
	public ResponseEntity<ApplicationExceptionResponse> handleDomainException(DomainException ex) {
		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;

		return ResponseEntity
			.status(status.value())
			.body(ex.toResponse(status));
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ApplicationExceptionResponse> handleJsonParseError(
		HttpMessageNotReadableException ex) {

		Throwable cause = ex.getCause();
		if (cause instanceof InvalidFormatException invalidFormatException) {
			Class<?> targetType = invalidFormatException.getTargetType();

			if (targetType.isEnum()) {
				String fieldName = invalidFormatException.getPath()
					.stream()
					.map(JsonMappingException.Reference::getFieldName)
					.findFirst()
					.orElse("unknown");

				List<String> acceptedValues = Arrays.stream(targetType.getEnumConstants())
					.map(Object::toString)
					.toList();

				var fields = List.of(
					new FieldErrorDetail(
						fieldName,
						List.of(
							String.format(
								"Deve ser um dos seguintes valores: %s",
								String.join(", ", acceptedValues)))));

				var exception = new BadRequestException(
					"IN.REQ-ENUM.INVALID",
					"O valor informado é inválido",
					fields);

				return ResponseEntity
					.status(exception.getStatus())
					.body(exception.toResponse());
			}
		}

		var exception = new BadRequestException(
			"IN.REQ-BODY.INVALID",
			"Requisição inválida");

		return ResponseEntity
			.status(exception.getStatus())
			.body(exception.toResponse());
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ApplicationExceptionResponse> handleBadCredentials(
		BadCredentialsException ex) {
		var exception = new UnauthorizedException(
			"IN.AUTH-CRD.INVALID",
			"Usuário e/ou senha inválidos");

		return ResponseEntity
			.status(exception.getStatus())
			.body(exception.toResponse());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApplicationExceptionResponse> handleValidation(MethodArgumentNotValidException ex) {
		List<FieldErrorDetail> fields = ex.getBindingResult()
			.getFieldErrors()
			.stream()
			.collect(Collectors.groupingBy(
				FieldError::getField,
				Collectors.mapping(
					FieldError::getDefaultMessage,
					Collectors.toList())))
			.entrySet()
			.stream()
			.sorted(Map.Entry.comparingByKey())
			.map(entry -> new FieldErrorDetail(
				entry.getKey(),
				entry.getValue()))
			.toList();

		var exception = new BadRequestException(
			"IN.REQ-VAL.ERR",
			"Não foi possível validar os dados da requisição. Por favor, verifique os campos informados e tente novamente",
			fields);

		return ResponseEntity
			.status(exception.getStatus())
			.body(exception.toResponse());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApplicationExceptionResponse> handleUnexpected(Exception ex) {

		// @TODO: melhorar logging
		ex.printStackTrace();

		var exception = new ApplicationException(
			"IN.INTERNAL-ERROR",
			"Ocorreu um erro interno inesperado");

		return ResponseEntity
			.status(exception.getStatus())
			.body(exception.toResponse());
	}
}
