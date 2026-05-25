package com.rpe.challenge.orders.domain.converters;

import com.rpe.challenge.orders.domain.enums.PaymentMethod;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class PaymentMethodConverter
	implements Converter<String, PaymentMethod> {
	@Override
	public PaymentMethod convert(String source) {
		return Arrays.stream(PaymentMethod.values())
			.filter(v ->
				v.name().equalsIgnoreCase(source) ||
					v.getColumn().equalsIgnoreCase(source))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException(
				String.format("Método de pagamento inválido: %s", source)
			));
	}
}
