package com.akiraz.newsapp.response;

import java.util.List;

import com.akiraz.newsapp.model.entity.News;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class ResponseNewsByCategoryAndNewsSite extends BaseResponse {

	private List<News> news;
}
