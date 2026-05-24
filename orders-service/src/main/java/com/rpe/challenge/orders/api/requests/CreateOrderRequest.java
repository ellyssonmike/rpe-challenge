package com.rpe.challenge.orders.api.requests;

import com.rpe.challenge.orders.domain.enums.PaymentMethod;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.UUID;
import org.hibernate.validator.constraints.br.CPF;

public record CreateOrderRequest(
	@NotNull
	PaymentMethod paymentMethod,

	@UUID
	@NotNull
	String itemId,

	@NotNull
	@Min(1)
	long amount,

	@NotEmpty
	@NotNull
	@Size(min = 4)
	String buyerName,

	@NotEmpty
	@NotNull
	@CPF
	String buyerCpf
) {
}
