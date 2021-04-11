package com.akiraz.newsapp.request;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestRemoveNews {

	@NotEmpty(message = "newsId cannot be empty!")
	private Long newsId;
}
