package com.akiraz.bookstore.model.entity;

import java.math.BigDecimal;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Statistics {

	private String monthName;
	private Integer totalOrderCount;
	private Integer totalBookCount;
	private BigDecimal totalAmount;
}
