package com.rpe.challenge.shared.utils;

import java.util.Map;

public final class TimeDurationParser {
	private static final Map<String, Long> MULTIPLIERS = Map.of(
		"w", 7L * 24L * 60L * 60L * 1000L,
		"d", 24L * 60L * 60L * 1000L,
		"h", 60L * 60L * 1000L,
		"m", 60L * 1000L,
		"s", 1000L
	);

	public static long toMillis(String value) {
		String unit = value.replaceAll("\\d", "");
		String number = value.replaceAll("\\D", "");

		Long multiplier = MULTIPLIERS.get(unit);

		if (multiplier == null) {
			throw new IllegalArgumentException("Invalid duration: " + value);
		}

		return Long.parseLong(number) * multiplier;
	}
}
