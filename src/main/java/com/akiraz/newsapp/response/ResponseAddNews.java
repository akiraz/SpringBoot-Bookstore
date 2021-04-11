package com.akiraz.newsapp.response;

import com.akiraz.newsapp.model.entity.News;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ResponseAddNews extends BaseResponse{
	
	private News news;
}
