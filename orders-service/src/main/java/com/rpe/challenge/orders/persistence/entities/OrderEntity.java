package com.rpe.challenge.orders.persistence.entities;

import com.rpe.challenge.orders.domain.enums.OrderStatus;
import com.rpe.challenge.orders.domain.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class OrderEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Enumerated(EnumType.STRING)
	private PaymentMethod paymentMethod;

	@Column(nullable = false)
	private UUID itemId;

	@Column(nullable = false)
	private long amount;

	@Column(nullable = false)
	private String buyerName;

	@Column(nullable = false)
	private String buyerCpf;

	private Instant paymentDate;

	@CreationTimestamp
	private Instant createdAt;

	@UpdateTimestamp
	private Instant updatedAt;

	@Builder.Default
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@ColumnDefault("'PENDING'")
	private OrderStatus status = OrderStatus.PENDING;
}
