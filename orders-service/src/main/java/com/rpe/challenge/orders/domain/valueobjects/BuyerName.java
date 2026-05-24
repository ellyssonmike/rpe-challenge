package com.rpe.challenge.orders.domain.valueobjects;

import com.rpe.challenge.orders.domain.exceptions.BuyerNameInvalidException;
import com.rpe.challenge.orders.domain.exceptions.BuyerNameRequiredException;
import com.rpe.challenge.shared.resolvers.MessageResolver;

public record BuyerName(String value) {
	public BuyerName(String value) {
		if (value == null || value.isEmpty()) {
			throw new BuyerNameRequiredException(
				"BN.VA-OB.REQ",
				MessageResolver.get("validation.buyer-name.required")
			);
		}

		if (value.length() <= 3) {
			throw new BuyerNameInvalidException(
				"BN.VA-OB.INVALID",
				MessageResolver.get("validation.buyer-name.min-length")
			);
		}

		this.value = value;
	}
}
