package com.rpe.challenge.orders.api.controllers;

import com.rpe.challenge.infra.api.responses.Response;
import com.rpe.challenge.orders.api.requests.ListOrdersRequest;
import com.rpe.challenge.orders.api.responses.OrderResponse;
import com.rpe.challenge.orders.application.services.ListOrdersService;
import com.rpe.challenge.orders.domain.mappers.ListOrdersMapper;
import com.rpe.challenge.orders.domain.mappers.OrderMapper;
import com.rpe.challenge.orders.domain.models.Order;
import com.rpe.challenge.shared.exceptions.core.ExceptionModule;
import com.rpe.challenge.shared.exceptions.core.ExceptionModuleType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@ExceptionModule(ExceptionModuleType.ORDERS)
public class ListOrdersController {
	private final ListOrdersService listOrdersService;

	@GetMapping
	public ResponseEntity<Response<Collection<OrderResponse>>> listOrders(@Valid ListOrdersRequest filters) {
		Collection<Order> orders = listOrdersService.execute(ListOrdersMapper.toInput(filters));

		return ResponseEntity.ok(
			new Response<>(orders
				.stream()
				.map(OrderMapper::toResponse)
				.toList()
			)
		);
	}
}
