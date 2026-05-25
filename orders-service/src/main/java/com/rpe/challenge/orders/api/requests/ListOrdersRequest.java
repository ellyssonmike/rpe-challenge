package com.rpe.challenge.orders.api.requests;

import com.rpe.challenge.infra.api.enums.SortDirection;
import com.rpe.challenge.orders.domain.annotations.OptionalCPF;
import com.rpe.challenge.orders.domain.enums.OrderSortColumn;
import com.rpe.challenge.orders.domain.enums.OrderStatus;

public record ListOrdersRequest(
	OrderStatus status,

	@OptionalCPF
	String buyerCpf,

	SortDirection sortDirection,
	OrderSortColumn sortColumn
) {
}
