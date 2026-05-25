package com.rpe.challenge.orders.domain.converters;

import com.rpe.challenge.orders.domain.enums.OrderSortColumn;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class OrderSortColumnConverter
	implements Converter<String, OrderSortColumn> {

	@Override
	public OrderSortColumn convert(String source) {
		return Arrays.stream(OrderSortColumn.values())
			.filter(v -> v.getColumn().equalsIgnoreCase(source))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException(
				String.format("Coluna de ordenação inválida: %s", source)
			));
	}
}
