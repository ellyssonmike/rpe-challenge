package com.rpe.challenge.paymentmethods.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentMethod {
	private Integer id;
	private String description;
}
