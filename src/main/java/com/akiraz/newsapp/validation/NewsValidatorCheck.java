package com.akiraz.newsapp.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;

import com.akiraz.newsapp.exception.NewsNotFoundException;
import com.akiraz.newsapp.model.entity.News;
import com.akiraz.newsapp.repository.NewsRepository;
import com.akiraz.newsapp.validation.annotation.NewsValidator;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class NewsValidatorCheck implements ConstraintValidator<NewsValidator, Long> {

	private final NewsRepository newsRepository;

	@Override
	public boolean isValid(Long newsId, ConstraintValidatorContext constraintValidatorContext) {
			News news = newsRepository.findById(newsId).orElseThrow(NewsNotFoundException::new);
			return null != news;
		

	}
}
