package com.rpe.challenge.orders.application.inputs;

import com.rpe.challenge.infra.api.enums.SortDirection;
import com.rpe.challenge.orders.domain.enums.OrderSortColumn;
import com.rpe.challenge.orders.domain.enums.OrderStatus;
import com.rpe.challenge.orders.domain.enums.PaymentMethod;
import com.rpe.challenge.orders.domain.valueobjects.CPF;

public record ListOrdersInput(
	CPF buyerCpf,
	OrderStatus status,
	PaymentMethod paymentMethod,
	SortDirection sortDirection,
	OrderSortColumn sortColumn
) {
	public ListOrdersInput {
		sortDirection = sortDirection != null
			? sortDirection
			: SortDirection.ASC;

		sortColumn = sortColumn != null
			? sortColumn
			: OrderSortColumn.CREATED_AT;
	}
}