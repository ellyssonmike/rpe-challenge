package com.rpe.challenge.orders.persistence.repositories;

import com.rpe.challenge.orders.persistence.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface OrderRepository extends
	JpaRepository<OrderEntity, UUID>,
	JpaSpecificationExecutor<OrderEntity> {
}
