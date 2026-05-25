package com.rpe.challenge.orders.application.services;

import com.rpe.challenge.orders.application.inputs.ListOrdersInput;
import com.rpe.challenge.orders.domain.mappers.OrderMapper;
import com.rpe.challenge.orders.domain.models.Order;
import com.rpe.challenge.orders.persistence.entities.OrderEntity;
import com.rpe.challenge.orders.persistence.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class ListOrdersService {
	private final OrderRepository orderRepository;

	public Collection<Order> execute(ListOrdersInput filters) {
		Specification<OrderEntity> spec = Specification.allOf();

		if (filters.buyerCpf() != null) {
			spec = spec.and(((root, query, criteriaBuilder) ->
				criteriaBuilder.equal(root.get("buyerCpf"), filters.buyerCpf().value()))
			);
		}

		if (filters.status() != null) {
			spec = spec.and(((root, query, criteriaBuilder) ->
				criteriaBuilder.equal(root.get("status"), filters.status()))
			);
		}

		Sort sort = Sort.by(
			Sort.Direction.valueOf(filters.sortDirection().name()),
			filters.sortColumn().getColumn()
		);

		return orderRepository.findAll(spec, sort)
			.stream()
			.map(OrderMapper::toDomain)
			.toList();
	}
}
