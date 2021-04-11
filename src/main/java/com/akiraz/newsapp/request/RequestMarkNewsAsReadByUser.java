package com.akiraz.newsapp.request;

import javax.validation.constraints.NotNull;

import com.akiraz.newsapp.validation.annotation.NewsValidator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestMarkNewsAsReadByUser {

	
	@NewsValidator()
	private Long newsId;
	@NotNull(message = "userId cannot be empty!")
	private Long userId;
	private boolean marked;
}
