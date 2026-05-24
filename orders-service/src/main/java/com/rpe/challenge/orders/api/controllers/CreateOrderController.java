package com.rpe.challenge.orders.api.controllers;

import com.rpe.challenge.infra.api.responses.Response;
import com.rpe.challenge.orders.api.requests.CreateOrderRequest;
import com.rpe.challenge.orders.api.responses.OrderResponse;
import com.rpe.challenge.orders.application.services.CreateOrderService;
import com.rpe.challenge.orders.domain.mappers.CreateOrderMapper;
import com.rpe.challenge.orders.domain.mappers.OrderMapper;
import com.rpe.challenge.orders.domain.models.Order;
import com.rpe.challenge.shared.exceptions.core.ExceptionModule;
import com.rpe.challenge.shared.exceptions.core.ExceptionModuleType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@ExceptionModule(ExceptionModuleType.ORDERS)
public class CreateOrderController {
	private final CreateOrderService createOrderService;

	@PostMapping
	public ResponseEntity<Response<OrderResponse>> createOrder(@Valid @RequestBody CreateOrderRequest request) {
		Order order = createOrderService.execute(CreateOrderMapper.toInput(request));

		return ResponseEntity.ok(
			new Response<>(
				OrderMapper.toResponse(order)
			)
		);
	}
}
