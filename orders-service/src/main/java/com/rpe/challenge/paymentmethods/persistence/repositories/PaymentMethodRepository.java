package com.rpe.challenge.paymentmethods.persistence.repositories;

import com.rpe.challenge.paymentmethods.persistence.entities.PaymentMethodEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodRepository
	extends JpaRepository<PaymentMethodEntity, Integer> {
}
