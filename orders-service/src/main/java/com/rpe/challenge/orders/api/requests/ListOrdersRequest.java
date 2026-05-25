package com.rpe.challenge.orders.api.requests;

import com.rpe.challenge.infra.api.enums.SortDirection;
import com.rpe.challenge.orders.domain.annotations.OptionalCPF;
import com.rpe.challenge.orders.domain.enums.OrderSortColumn;
import com.rpe.challenge.orders.domain.enums.OrderStatus;
import com.rpe.challenge.orders.domain.enums.PaymentMethod;

public record ListOrdersRequest(
	OrderStatus status,
	PaymentMethod paymentMethod,

	@OptionalCPF
	String buyerCpf,

	SortDirection sortDirection,
	OrderSortColumn sortColumn
) {
}
