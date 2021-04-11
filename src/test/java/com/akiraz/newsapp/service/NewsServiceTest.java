package com.akiraz.newsapp.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;

import com.akiraz.newsapp.exception.NewsNotFoundException;
import com.akiraz.newsapp.model.entity.News;
import com.akiraz.newsapp.repository.NewsRepository;
import com.akiraz.newsapp.request.RequestAddNews;
import com.akiraz.newsapp.request.RequestRemoveNews;
import com.akiraz.newsapp.request.RequestUpdateNews;
import com.akiraz.newsapp.response.ResponseAddNews;
import com.akiraz.newsapp.response.ResponseAllNews;
import com.akiraz.newsapp.response.ResponseRemoveNews;
import com.akiraz.newsapp.response.ResponseUpdateNews;
import com.akiraz.newsapp.service.intf.NewsService;
import com.akiraz.newsapp.sevice.NewsServiceImpl;
import com.akiraz.newsapp.util.Constants;

@RunWith(MockitoJUnitRunner.class)
@RestClientTest(NewsService.class)
public class NewsServiceTest {

	@Mock
	private NewsRepository newsRepository;

	@InjectMocks
	private NewsServiceImpl newsService;

	@Test
	public void testAddNews() {
		// Create data
		News news = buildNews();
		RequestAddNews requestAddNews = buildRequestAddNews();

		// Create mock services behavior
		when(newsRepository.save(requestAddNews.getNews())).thenReturn(news);

		// Call test method
		ResponseAddNews responseAddNews = newsService.addNews(requestAddNews);

		// Generate expected datas
		String expectedReturnCodeSuccess = Constants.Return.SUCCESS.getCode();
		String expectedReturnMessageSuccess = Constants.Return.SUCCESS.getMessage();
		String expectedCategory = requestAddNews.getNews().getCategory();
		String expectedTitle = requestAddNews.getNews().getTitle();
		String expectedSummaryText = requestAddNews.getNews().getSummaryText();
		String expectedImageLink = requestAddNews.getNews().getImageLink();

		// Check results
		assertNotNull(responseAddNews);
		assertEquals(expectedReturnCodeSuccess, responseAddNews.getReturnCode());
		assertEquals(expectedReturnMessageSuccess, responseAddNews.getReturnMessage());
		assertEquals(expectedCategory, responseAddNews.getNews().getCategory());
		assertEquals(expectedTitle, responseAddNews.getNews().getTitle());
		assertEquals(expectedSummaryText, responseAddNews.getNews().getSummaryText());
		assertEquals(expectedImageLink, responseAddNews.getNews().getImageLink());

		// verify the mock
		Mockito.verify(newsRepository).save(requestAddNews.getNews());
	}

	@Test
	public void testRemoveNews() {
		// Create data
		RequestRemoveNews requestRemoveNews = buildRequestRemoveNews();
		News news = buildNews();

		// Create mock services behavior
		when(newsRepository.findById(requestRemoveNews.getNewsId())).thenReturn(Optional.of(news));

		// Call test method
		ResponseRemoveNews responseRemoveNews = newsService.removeNews(requestRemoveNews);

		// Generate expected datas
		String expectedReturnCodeSuccess = Constants.Return.SUCCESS.getCode();
		String expectedReturnMessageSuccess = Constants.Return.SUCCESS.getMessage();
		Long expectedReturnId = requestRemoveNews.getNewsId();

		// Check results
		assertEquals(expectedReturnCodeSuccess, responseRemoveNews.getReturnCode());
		assertEquals(expectedReturnMessageSuccess, responseRemoveNews.getReturnMessage());
		assertEquals(expectedReturnId, responseRemoveNews.getNews().getId());

		// verify the mock
		Mockito.verify(newsRepository).findById(requestRemoveNews.getNewsId());
		Mockito.verify(newsRepository).delete(news);
	}

	@Test(expected = NewsNotFoundException.class)
	public void testRemoveNewsNewsNotFoundException() {
		// Create data
		RequestRemoveNews requestRemoveNews = buildRequestRemoveNewsNewsNotFoundException();

		// Create mock services behavior
		when(newsRepository.findById(requestRemoveNews.getNewsId())).thenReturn(Optional.empty());

		// Call test method
		ResponseRemoveNews responseRemoveNews = newsService.removeNews(requestRemoveNews);
	}

	@Test
	public void testUpdateNews() {
		// Create data
		News news = buildNews();
		RequestUpdateNews requestUpdateNews = buildRequestUpdateNews();

		// Create mock services behavior
		when(newsRepository.findById(requestUpdateNews.getNews().getId())).thenReturn(Optional.of(news));

		// Call test method
		ResponseUpdateNews responseUpdateNews = newsService.updateNews(requestUpdateNews);

		// Generate expected datas
		String expectedReturnCodeSuccess = Constants.Return.SUCCESS.getCode();
		String expectedReturnMessageSuccess = Constants.Return.SUCCESS.getMessage();

		// Check results
		assertEquals(expectedReturnCodeSuccess, responseUpdateNews.getReturnCode());
		assertEquals(expectedReturnMessageSuccess, responseUpdateNews.getReturnMessage());

		// verify the mock
		Mockito.verify(newsRepository).findById(requestUpdateNews.getNews().getId());
	}

	@Test(expected = NewsNotFoundException.class)
	public void testUpdateNewsNewsNotFoundException() {
		// Create data
		RequestUpdateNews requestUpdateNews = buildRequestUpdateNews();

		// Create mock services behavior
		when(newsRepository.findById(requestUpdateNews.getNews().getId())).thenReturn(Optional.empty());

		// Call test method
		ResponseUpdateNews responseUpdateNews = newsService.updateNews(requestUpdateNews);

	}

	@Test
	public void testGetAllNews() {
		// Create data
		List<News> newList = buildNewList();

		// Create mock services behavior
		when(newsRepository.findAll()).thenReturn(newList);

		// Call test method
		ResponseAllNews responseAllNews = newsService.getAllNews();

		// Generate expected datas
		String expectedReturnCodeSuccess = Constants.Return.SUCCESS.getCode();
		String expectedReturnMessageSuccess = Constants.Return.SUCCESS.getMessage();

		// Check results
		assertEquals(expectedReturnCodeSuccess, responseAllNews.getReturnCode());
		assertEquals(expectedReturnMessageSuccess, responseAllNews.getReturnMessage());

		// verify the mock
		Mockito.verify(newsRepository).findAll();
	}

	private RequestUpdateNews buildRequestUpdateNews() {
		News news = buildNews();
		return RequestUpdateNews.builder().news(news).build();
	}

	private RequestAddNews buildRequestAddNews() {
		News news = buildNews();
		return RequestAddNews.builder().news(news).build();
	}

	private RequestRemoveNews buildRequestRemoveNews() {
		RequestRemoveNews requestAddNews = RequestRemoveNews.builder().newsId(1L).build();
		return requestAddNews;
	}

	private RequestRemoveNews buildRequestRemoveNewsNewsNotFoundException() {
		return RequestRemoveNews.builder().newsId(2L).build();
	}

	private News buildNews() {
		return News.builder().id(1L).category("spor").title("Türkiye maçı").summaryText("maç özeti")
				.imageLink("gol.png").website("spor.com").build();
	}

	private List<News> buildNewList() {
		List<News> newList = new ArrayList<News>();
		newList.add(News.builder().id(1L).category("spor").website("sporhaber.com").summaryText("futbol maç özeti")
				.imageLink("gol.png").build());
		newList.add(News.builder().id(2L).category("spor").website("sporhaber2.com").summaryText("basketbol maç özeti")
				.imageLink("basket.png").build());
		return newList;
	}
}
