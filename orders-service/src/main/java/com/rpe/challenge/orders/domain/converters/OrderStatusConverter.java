package com.rpe.challenge.orders.domain.converters;

import com.rpe.challenge.orders.domain.enums.OrderStatus;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class OrderStatusConverter
	implements Converter<String, OrderStatus> {
	@Override
	public OrderStatus convert(String source) {
		return Arrays.stream(OrderStatus.values())
			.filter(v -> v.name().equalsIgnoreCase(source))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException(
				String.format("Status da ordem inválida: %s", source)
			));
	}
}
