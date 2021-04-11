package com.akiraz.newsapp.sevice;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.akiraz.newsapp.model.entity.News;
import com.akiraz.newsapp.model.entity.UsersNews;
import com.akiraz.newsapp.repository.NewsRepository;
import com.akiraz.newsapp.repository.UserNewsRepository;
import com.akiraz.newsapp.request.RequestMarkNewsAsReadByUser;
import com.akiraz.newsapp.request.RequestNewsByCategoryAndWebsite;
import com.akiraz.newsapp.response.ResponseAllUsersNews;
import com.akiraz.newsapp.response.ResponseMarkNewsAsReadByUser;
import com.akiraz.newsapp.response.ResponseNewsByCategoryAndNewsSite;
import com.akiraz.newsapp.service.intf.UserNewsService;
import com.akiraz.newsapp.util.Constants;
import com.akiraz.newsapp.util.DateUtil;
import com.akiraz.newsapp.util.MapperUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserNewsServiceImpl implements UserNewsService {

	private final UserNewsRepository userNewsRepository;
	private final NewsRepository newsRepository;

	public ResponseNewsByCategoryAndNewsSite getNewsByCategoryAndNewsSiteFromThePastHour(
			RequestNewsByCategoryAndWebsite requestNewsByCategoryAndWebsite) {
		ResponseNewsByCategoryAndNewsSite responseNewsByCategoryAndNewsSite = new ResponseNewsByCategoryAndNewsSite();
		List<News> newsList = Collections.emptyList();

		List<UsersNews> usersNewsList = userNewsRepository
				.findUserNewsByUserId(requestNewsByCategoryAndWebsite.getUserId());

		List<Long> markedNewsIdList = usersNewsList.stream().filter(s -> s.isMarked()).map(UsersNews::getNewsID)
				.collect(Collectors.toList());

		if (!CollectionUtils.isEmpty(markedNewsIdList)) {
			newsList = newsRepository.findNewsFromThePastHour(DateUtil.getOneHourAgo(), DateUtil.now(),
					markedNewsIdList, requestNewsByCategoryAndWebsite.getCategory(),
					requestNewsByCategoryAndWebsite.getWebsite());
		}

		log.info(
				"getNewsByCategoryAndNewsSiteFromThePastHour() Category: {} News site: {} userId: {} marked:{} newsCount:{}  listed!",
				requestNewsByCategoryAndWebsite.getCategory(), requestNewsByCategoryAndWebsite.getWebsite(),
				requestNewsByCategoryAndWebsite.getUserId(), requestNewsByCategoryAndWebsite.isMarked(),
				newsList.size());

		responseNewsByCategoryAndNewsSite.setNews(newsList);
		responseNewsByCategoryAndNewsSite.setReturnCode(Constants.Return.SUCCESS.getCode());
		responseNewsByCategoryAndNewsSite.setReturnMessage(Constants.Return.SUCCESS.getMessage());

		return responseNewsByCategoryAndNewsSite;
	}

	public ResponseMarkNewsAsReadByUser markNewsAsReadByUser(RequestMarkNewsAsReadByUser requestMarkNewsAsReadByUser) {
		ResponseMarkNewsAsReadByUser responseMarkNewsAsReadByUser = new ResponseMarkNewsAsReadByUser();
		UsersNews usersNews = null;

		usersNews = userNewsRepository.findById(requestMarkNewsAsReadByUser.getNewsId()).orElse(new UsersNews());
		if (usersNews.getId() == null) {
			usersNews.setNewsID(requestMarkNewsAsReadByUser.getNewsId());
			usersNews.setUserId(requestMarkNewsAsReadByUser.getUserId());
		}
		usersNews.setMarked(requestMarkNewsAsReadByUser.isMarked());
		userNewsRepository.save(usersNews);

		log.info("markNewsAsReadByUser() News Id: {} userId: {} marked :{}  marked!",
				requestMarkNewsAsReadByUser.getNewsId(), requestMarkNewsAsReadByUser.getUserId(),
				requestMarkNewsAsReadByUser.isMarked());
		responseMarkNewsAsReadByUser.setReturnCode(Constants.Return.SUCCESS.getCode());
		responseMarkNewsAsReadByUser.setReturnMessage(Constants.Return.SUCCESS.getMessage());

		return responseMarkNewsAsReadByUser;
	}

	public ResponseAllUsersNews getAllUsersNews() {
		ResponseAllUsersNews responseAllUsersNews = new ResponseAllUsersNews();
		List<UsersNews> usersNewsList = null;
		usersNewsList = userNewsRepository.findAll();
		if (!CollectionUtils.isEmpty(usersNewsList)) {
			responseAllUsersNews.setReturnCode(Constants.Return.SUCCESS.getCode());
			responseAllUsersNews.setReturnMessage(Constants.Return.SUCCESS.getMessage());
			log.info("getAllUsersNews()  listed! Count: {}", usersNewsList.size());
			MapperUtil.mapResponseAllUsersNewsFromModel(usersNewsList, responseAllUsersNews);
		}

		return responseAllUsersNews;
	}

}
