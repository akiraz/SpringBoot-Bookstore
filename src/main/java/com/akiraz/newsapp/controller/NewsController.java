package com.akiraz.newsapp.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akiraz.newsapp.request.RequestAddNews;
import com.akiraz.newsapp.request.RequestRemoveNews;
import com.akiraz.newsapp.request.RequestUpdateNews;
import com.akiraz.newsapp.response.ResponseAddNews;
import com.akiraz.newsapp.response.ResponseAllNews;
import com.akiraz.newsapp.response.ResponseRemoveNews;
import com.akiraz.newsapp.response.ResponseUpdateNews;
import com.akiraz.newsapp.service.intf.NewsService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/news")
public class NewsController {

	private final NewsService newsService;

	@ApiOperation("addNews() - Adds news")
	@PostMapping("/")
	public ResponseEntity<ResponseAddNews> addNews(@Valid @RequestBody RequestAddNews requestAddNews) {
		return new ResponseEntity<>(newsService.addNews(requestAddNews), HttpStatus.OK);
	}

	@ApiOperation("removeNews() - Removes the given id")
	@DeleteMapping("/{newsId}")
	public ResponseEntity<ResponseRemoveNews> removeNews(@PathVariable(value = "newsId") Long newsId) {
		RequestRemoveNews requestRemoveNews = RequestRemoveNews.builder().newsId(newsId).build();
		return new ResponseEntity<>(newsService.removeNews(requestRemoveNews), HttpStatus.OK);
	}

	@ApiOperation("updateNews() - Updates the given new")
	@PutMapping("/{newsId}")
	public ResponseEntity<ResponseUpdateNews> updateNews(@PathVariable(value = "newsId") Long newsId, @Valid @RequestBody RequestUpdateNews requestUpdateNews) {
		requestUpdateNews.getNews().setId(newsId);
		return new ResponseEntity<>(newsService.updateNews(requestUpdateNews), HttpStatus.OK);
	}

	@ApiOperation("getAllNews() - lists All news")
	@GetMapping("/")
	public ResponseEntity<ResponseAllNews> getAllNews() {
		return new ResponseEntity<>(newsService.getAllNews(), HttpStatus.OK);
	}

}
