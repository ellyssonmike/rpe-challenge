package com.rpe.challenge.orders.domain.mappers;

import com.rpe.challenge.orders.api.requests.ListOrdersRequest;
import com.rpe.challenge.orders.application.inputs.ListOrdersInput;
import com.rpe.challenge.orders.domain.valueobjects.CPF;

public class ListOrdersMapper {
	public static ListOrdersInput toInput(ListOrdersRequest filters) {
		return new ListOrdersInput(
			(filters.buyerCpf() != null && !filters.buyerCpf().isBlank())
				? new CPF(filters.buyerCpf())
				: null,
			filters.status(),
			filters.sortDirection(),
			filters.sortColumn()
		);
	}
}
