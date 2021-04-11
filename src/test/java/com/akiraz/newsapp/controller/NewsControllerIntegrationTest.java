package com.akiraz.newsapp.controller;

import static org.junit.Assert.assertEquals;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import com.akiraz.newsapp.NewsAppApplication;
import com.akiraz.newsapp.model.entity.News;
import com.akiraz.newsapp.request.RequestAddNews;
import com.akiraz.newsapp.request.RequestUpdateNews;
import com.akiraz.newsapp.response.ResponseAddNews;
import com.akiraz.newsapp.response.ResponseAllNews;
import com.akiraz.newsapp.response.ResponseRemoveNews;
import com.akiraz.newsapp.response.ResponseUpdateNews;
import com.akiraz.newsapp.util.Constants;

@EnableAutoConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NewsAppApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql({ "classpath:init_data.sql" })
public class NewsControllerIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate = new TestRestTemplate();

	@LocalServerPort
	private int port;

	@Test
	public void addNews() throws JSONException {
		News news = buildNews();
		RequestAddNews requestAddNews = buildRequestAddNews(news);

		ResponseAddNews responseAddNews = buildResponseAddNews();

		ResponseEntity<ResponseAddNews> responseEntity = restTemplate.postForEntity(createURLWithPort("/news/"),
				requestAddNews, ResponseAddNews.class);

		ResponseAddNews actualResponse = responseEntity.getBody();

		assertEquals(actualResponse.getReturnCode(), responseAddNews.getReturnCode());
	}

	private ResponseAddNews buildResponseAddNews() {
		return ResponseAddNews.builder().returnCode(Constants.Return.SUCCESS.getCode())
				.returnMessage(Constants.Return.SUCCESS.getMessage()).build();
	}

	private RequestAddNews buildRequestAddNews(News news) {
		return RequestAddNews.builder().news(news).build();
	}

	@Test
	public void updateNews() throws JSONException {
		Long newsId = 10L;
		News news = buildNews();

		RequestUpdateNews requestUpdateNews = buildRequestUpdateNews(news);

		ResponseUpdateNews responseUpdateNews = buildResponseUpdateNews();

		ResponseEntity<ResponseUpdateNews> responseEntity = restTemplate.exchange(createURLWithPort("/news/{newsId}"),
				HttpMethod.PUT, new HttpEntity<RequestUpdateNews>(requestUpdateNews), ResponseUpdateNews.class, newsId);

		ResponseUpdateNews actualResponse = responseEntity.getBody();

		assertEquals(actualResponse.getReturnCode(), responseUpdateNews.getReturnCode());
	}

	
	@Test
	public void updateNewsNewsNotFoundException() throws JSONException {
		Long newsId = 20L;
		News news = buildNews();

		RequestUpdateNews requestUpdateNews = buildRequestUpdateNews(news);

		ResponseUpdateNews responseUpdateNews = buildReponseUpdateNewsNotFoundException();

		ResponseEntity<ResponseUpdateNews> responseEntity = restTemplate.exchange(createURLWithPort("/news/{newsId}"),
				HttpMethod.PUT, new HttpEntity<RequestUpdateNews>(requestUpdateNews), ResponseUpdateNews.class, newsId);

		ResponseUpdateNews actualResponse = responseEntity.getBody();

		assertEquals(actualResponse.getReturnCode(), responseUpdateNews.getReturnCode());
	}

	
	@Test
	public void removeNews() throws JSONException {
		Long newsId = 11L;

		ResponseRemoveNews responseUpdateNews = buildResponseRemoveNews();

		ResponseEntity<ResponseRemoveNews> responseEntity = restTemplate.exchange(createURLWithPort("/news/{newsId}"),
				HttpMethod.DELETE, null, ResponseRemoveNews.class, newsId);

		ResponseRemoveNews actualResponse = responseEntity.getBody();

		assertEquals(actualResponse.getReturnCode(), responseUpdateNews.getReturnCode());
	}
	
	@Test
	public void removeNewsNewsNotFoundException() throws JSONException {
		Long newsId = 2L;

		ResponseRemoveNews responseUpdateNews = buildResponseRemoveNewsNotFoundException();

		ResponseEntity<ResponseRemoveNews> responseEntity = restTemplate.exchange(createURLWithPort("/news/{newsId}"),
				HttpMethod.DELETE,null, ResponseRemoveNews.class, newsId);

		ResponseRemoveNews actualResponse = responseEntity.getBody();

		assertEquals(actualResponse.getReturnCode(), responseUpdateNews.getReturnCode());
	}

	
	@Test
	public void getAllNews() throws JSONException {
		Long newsId = 1L;
		
		ResponseAllNews responseAllNews =  buildResponseAllNews();
		
		ResponseEntity<ResponseAllNews> responseEntity = restTemplate.exchange(createURLWithPort("/news/"),
				HttpMethod.GET, null, ResponseAllNews.class, newsId);

		ResponseAllNews actualResponse = responseEntity.getBody();

		assertEquals(actualResponse.getReturnCode(), responseAllNews.getReturnCode());
	}

	
	private ResponseUpdateNews buildReponseUpdateNewsNotFoundException() {
		return ResponseUpdateNews.builder()
				.returnCode(Constants.Return.NEWS_NOT_FOUND.getCode()).returnMessage(Constants.Return.NEWS_NOT_FOUND.getMessage())
				.build();
	}
	
	private ResponseRemoveNews buildResponseRemoveNewsNotFoundException() {
		return ResponseRemoveNews.builder()
				.returnCode(Constants.Return.NEWS_NOT_FOUND.getCode()).returnMessage(Constants.Return.NEWS_NOT_FOUND.getMessage())
				.build();
	}
	
	
	private ResponseRemoveNews buildResponseRemoveNews() {
		return ResponseRemoveNews.builder()
				.returnCode(Constants.Return.SUCCESS.getCode()).returnMessage(Constants.Return.SUCCESS.getMessage())
				.build();
	}
	
	private ResponseAllNews buildResponseAllNews() {
		return ResponseAllNews.builder().returnCode(Constants.Return.SUCCESS.getCode()).returnMessage(Constants.Return.SUCCESS.getMessage()).build();
	}
	
	private News buildNews() {
		return News.builder().title("integration test title").website("integration test website")
				.category("integration test category").summaryText("integration test summarytext")
				.imageLink("integration test image link").build();
	}
	
	private RequestUpdateNews buildRequestUpdateNews(News news) {
		return RequestUpdateNews.builder().news(news).build();
	}

	private ResponseUpdateNews buildResponseUpdateNews() {
		return ResponseUpdateNews.builder()
				.returnCode(Constants.Return.SUCCESS.getCode()).returnMessage(Constants.Return.SUCCESS.getMessage())
				.build();
	}

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}
}
