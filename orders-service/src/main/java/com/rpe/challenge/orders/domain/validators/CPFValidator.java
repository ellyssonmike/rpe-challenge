package com.rpe.challenge.orders.domain.validators;

public final class CPFValidator {

	public static boolean isValid(String cpf) {
		if (cpf == null) {
			return false;
		}

		String normalizedCpf = cpf.replaceAll("\\D", "");

		if (normalizedCpf.length() != 11) {
			return false;
		}

		if (allDigitsAreEqual(normalizedCpf)) {
			return false;
		}

		int firstDigit = calculateDigit(normalizedCpf, 9);
		int secondDigit = calculateDigit(normalizedCpf, 10);

		return firstDigit == Character.getNumericValue(normalizedCpf.charAt(9))
			&& secondDigit == Character.getNumericValue(normalizedCpf.charAt(10));
	}

	private static boolean allDigitsAreEqual(String cpf) {
		char firstChar = cpf.charAt(0);

		for (int i = 1; i < cpf.length(); i++) {
			if (cpf.charAt(i) != firstChar) {
				return false;
			}
		}

		return true;
	}

	private static int calculateDigit(String cpf, int length) {
		int sum = 0;
		int weight = length + 1;

		for (int i = 0; i < length; i++) {
			int digit = Character.getNumericValue(cpf.charAt(i));
			sum += digit * (weight - i);
		}

		int result = (sum * 10) % 11;
		return (result == 10) ? 0 : result;
	}
}