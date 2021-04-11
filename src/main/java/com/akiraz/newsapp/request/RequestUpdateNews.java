package com.akiraz.newsapp.request;

import com.akiraz.newsapp.model.entity.News;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestUpdateNews {

	private News news;
}
