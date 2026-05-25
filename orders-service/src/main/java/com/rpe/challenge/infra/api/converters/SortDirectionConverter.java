package com.rpe.challenge.infra.api.converters;

import com.rpe.challenge.infra.api.enums.SortDirection;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class SortDirectionConverter
	implements Converter<String, SortDirection> {
	@Override
	public SortDirection convert(String source) {
		return Arrays.stream(SortDirection.values())
			.filter(v -> v.name().equalsIgnoreCase(source))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException(
				String.format("Direção de ordenação inválida: %s", source)
			));
	}
}
