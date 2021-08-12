package com.akiraz.bookstore.model.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tbl_order")
public class Order {

	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	private Long customerId;

	@NotNull
	private Long bookId;

	@NotNull
	private Integer orderCount;

	private final LocalDateTime createdAt = LocalDateTime.now();

}
