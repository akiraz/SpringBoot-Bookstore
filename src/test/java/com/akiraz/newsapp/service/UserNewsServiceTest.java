package com.akiraz.newsapp.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;

import com.akiraz.newsapp.model.entity.UsersNews;
import com.akiraz.newsapp.repository.NewsRepository;
import com.akiraz.newsapp.repository.UserNewsRepository;
import com.akiraz.newsapp.request.RequestMarkNewsAsReadByUser;
import com.akiraz.newsapp.request.RequestNewsByCategoryAndWebsite;
import com.akiraz.newsapp.response.ResponseAllUsersNews;
import com.akiraz.newsapp.response.ResponseMarkNewsAsReadByUser;
import com.akiraz.newsapp.response.ResponseNewsByCategoryAndNewsSite;
import com.akiraz.newsapp.service.intf.UserNewsService;
import com.akiraz.newsapp.sevice.UserNewsServiceImpl;
import com.akiraz.newsapp.util.Constants;

@RunWith(MockitoJUnitRunner.class)
@RestClientTest(UserNewsService.class)
public class UserNewsServiceTest {

	@Mock
	private NewsRepository newsRepository;

	@Mock
	private UserNewsRepository userNewsRepository;

	@InjectMocks
	private UserNewsServiceImpl userNewsService;
	
	

	
	@Test
	public void testGetAllUsersNews() {
		// Create data
		List<UsersNews> usersNewsList = buildUserNewsList();
		
		// Create mock services behavior
		when(userNewsRepository.findAll()).thenReturn(usersNewsList);

		// Call test method
		ResponseAllUsersNews responseAllUsersNews = userNewsService.getAllUsersNews();

		// Generate expected datas
		String expectedReturnCodeSuccess = Constants.Return.SUCCESS.getCode();
		String expectedReturnMessageSuccess = Constants.Return.SUCCESS.getMessage();

		// Check results
		assertEquals(expectedReturnCodeSuccess, responseAllUsersNews.getReturnCode());
		assertEquals(expectedReturnMessageSuccess, responseAllUsersNews.getReturnMessage());

		// verify the mock
		Mockito.verify(userNewsRepository).findAll();
	}

	@Test	
	public void testMarkNewsAsReadByUser() {
		// Create data
		RequestMarkNewsAsReadByUser requestMarkNewsAsReadByUser = buildRequestMarkNewsAsReadByUser();
		UsersNews usersNews = buildUsersNews();
	
		// Create mock services behavior
		when(userNewsRepository.findById(requestMarkNewsAsReadByUser.getNewsId())).thenReturn(Optional.of(usersNews));
		when(userNewsRepository.save(usersNews)).thenReturn(usersNews);
		
		// Call test method
		ResponseMarkNewsAsReadByUser responseMarkNewsAsReadByUser = userNewsService.markNewsAsReadByUser(requestMarkNewsAsReadByUser);
		
		// Generate expected datas
		String expectedReturnCode = Constants.Return.SUCCESS.getCode();
		String expectedReturnMessage = Constants.Return.SUCCESS.getMessage();
		
		// Check results
		assertEquals(expectedReturnCode, responseMarkNewsAsReadByUser.getReturnCode());
		assertEquals(expectedReturnMessage, responseMarkNewsAsReadByUser.getReturnMessage());
		
		// verify the mock
		Mockito.verify(userNewsRepository).findById(requestMarkNewsAsReadByUser.getNewsId());
		Mockito.verify(userNewsRepository).save(usersNews);
		
	}
	
	@Test	
	public void testGetNewsByCategoryAndNewsSiteFromThePastHour() {
		// Create data
		RequestNewsByCategoryAndWebsite requestNewsByCategoryAndNewsite = buildRequestNewsByCategoryAndNewsite();
		List<UsersNews> usersNewsList = buildUserNewsList();
		List<Long> newsIdList = usersNewsList.stream().filter(s -> s.isMarked()).map(UsersNews::getNewsID)
				.collect(Collectors.toList());
		
		// Create mock services behavior
		when(userNewsRepository.findUserNewsByUserId(requestNewsByCategoryAndNewsite.getUserId())).thenReturn(usersNewsList);
		
		// Call test method
		ResponseNewsByCategoryAndNewsSite responseNewsByCategoryAndNewsSite = userNewsService.getNewsByCategoryAndNewsSiteFromThePastHour(requestNewsByCategoryAndNewsite);
		
		// Generate expected datas
		String expectedReturnCode = Constants.Return.SUCCESS.getCode();
		String expectedReturnMessage = Constants.Return.SUCCESS.getMessage();
		
		// Check results
		assertEquals(expectedReturnCode, responseNewsByCategoryAndNewsSite.getReturnCode());
		assertEquals(expectedReturnMessage, responseNewsByCategoryAndNewsSite.getReturnMessage());
		
		// verify the mock
		Mockito.verify(userNewsRepository).findUserNewsByUserId(requestNewsByCategoryAndNewsite.getUserId());
		Mockito.verify(newsRepository,Mockito.times(1)).findNewsFromThePastHour(Mockito.any(),Mockito.any(),Mockito.eq(newsIdList),Mockito.eq(requestNewsByCategoryAndNewsite.getCategory()),Mockito.eq(requestNewsByCategoryAndNewsite.getWebsite()));
	}

	private RequestMarkNewsAsReadByUser buildRequestMarkNewsAsReadByUser() {
		return RequestMarkNewsAsReadByUser.builder().marked(true).newsId(1L).userId(100L).build();
	}
	
	private UsersNews buildUsersNews() {
		return UsersNews.builder().id(1L).marked(true).newsID(1L).userId(100L).build();
	}
	
	private List<UsersNews> buildUserNewsList(){
		List<UsersNews> usersNewsList = new ArrayList<UsersNews>();
		usersNewsList.add(UsersNews.builder().id(1L).newsID(1L).marked(true).userId(100L).build());
		usersNewsList.add(UsersNews.builder().id(2L).newsID(2L).marked(true).userId(200L).build());
		return usersNewsList;
	}
	
	private RequestNewsByCategoryAndWebsite buildRequestNewsByCategoryAndNewsite() {
		return  RequestNewsByCategoryAndWebsite.builder().category("spor").marked(true).userId(100l).website("spor.com").build();
	}
}
