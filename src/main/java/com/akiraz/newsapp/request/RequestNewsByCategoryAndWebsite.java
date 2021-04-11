package com.akiraz.newsapp.request;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestNewsByCategoryAndWebsite {

	@NotNull(message = "userId cannot be empty!")
	private Long userId;
	private String category;
	private String website;
	private boolean marked;

}
