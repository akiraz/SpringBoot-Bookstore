package com.akiraz.bookstore.model.entity;

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
public class CustomerOrder {

	private String name;

	private String surname;
	
	private String email;

	private String address;
	
	private Long bookId;
	
	private Integer orderCount;
}
