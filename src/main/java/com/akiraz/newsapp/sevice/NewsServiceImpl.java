package com.akiraz.newsapp.sevice;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
import com.akiraz.newsapp.util.Constants;
import com.akiraz.newsapp.util.MapperUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class NewsServiceImpl implements NewsService {

	private final NewsRepository newsRepository;

	public ResponseAddNews addNews(RequestAddNews requestAddNews) {
		ResponseAddNews responseAddNews = new ResponseAddNews();
		try {
			newsRepository.save(requestAddNews.getNews());

			responseAddNews.setReturnCode(Constants.Return.SUCCESS.getCode());
			responseAddNews.setReturnMessage(Constants.Return.SUCCESS.getMessage());
			log.info("addNews() News Id: {} Title: {} SummaryText: {}  added!", requestAddNews.getNews().getId(),
					requestAddNews.getNews().getTitle(), requestAddNews.getNews().getSummaryText());
			MapperUtil.mapResponseAddNewsFromModel(requestAddNews.getNews(), responseAddNews);
		} catch (Exception ex) {
			responseAddNews.setReturnCode(Constants.Return.FAIL.getCode());
			responseAddNews.setReturnMessage(Constants.Return.FAIL.getMessage());
			log.error("addNews() error!", ex);
		}
		return responseAddNews;
	}

	public ResponseRemoveNews removeNews(RequestRemoveNews requestRemoveNews) {
		ResponseRemoveNews responseRemoveNews = new ResponseRemoveNews();
		News news = null;
		try {
			news = newsRepository.findById(requestRemoveNews.getNewsId()).orElseThrow(NewsNotFoundException::new);
			newsRepository.delete(news);

			responseRemoveNews.setReturnCode(Constants.Return.SUCCESS.getCode());
			responseRemoveNews.setReturnMessage(Constants.Return.SUCCESS.getMessage());
			log.info("removeNews() News Id: {} removed!", requestRemoveNews.getNewsId());
			MapperUtil.mapResponseRemoveNewsFromModel(news, responseRemoveNews);
		} catch (NewsNotFoundException ex) {
			responseRemoveNews.setReturnCode(Constants.Return.NEWS_NOT_FOUND.getCode());
			responseRemoveNews.setReturnMessage(Constants.Return.NEWS_NOT_FOUND.getMessage());
			log.error("removeNews() error!", ex);
		} catch (Exception ex) {
			responseRemoveNews.setReturnCode(Constants.Return.FAIL.getCode());
			responseRemoveNews.setReturnMessage(Constants.Return.FAIL.getMessage());
			log.error("removeNews() error!", ex);
		}
		return responseRemoveNews;
	}

	public ResponseUpdateNews updateNews(RequestUpdateNews requestUpdateNews) {
		ResponseUpdateNews responseUpdateNews = new ResponseUpdateNews();
		News news = null;
		try {
			Long newsId = requestUpdateNews.getNews().getId();
			news = newsRepository.findById(newsId).orElseThrow(NewsNotFoundException::new);
			MapperUtil.mapRequestUpdateNewstoModel(news, requestUpdateNews);
			newsRepository.save(news);

			responseUpdateNews.setReturnCode(Constants.Return.SUCCESS.getCode());
			responseUpdateNews.setReturnMessage(Constants.Return.SUCCESS.getMessage());
			log.info("updateNews() News Id: {} updated!", newsId);
			MapperUtil.mapResponseUpdateNewsFromModel(news, responseUpdateNews);
		} catch (NewsNotFoundException ex) {
			responseUpdateNews.setReturnCode(Constants.Return.NEWS_NOT_FOUND.getCode());
			responseUpdateNews.setReturnMessage(Constants.Return.NEWS_NOT_FOUND.getMessage());
			log.error("updateNews() error!", ex);
		} catch (Exception ex) {
			responseUpdateNews.setReturnCode(Constants.Return.FAIL.getCode());
			responseUpdateNews.setReturnMessage(Constants.Return.FAIL.getMessage());
			log.error("updateNews() error!", ex);
		}
		return responseUpdateNews;
	}

	public ResponseAllNews getAllNews() {
		ResponseAllNews responseListNews = new ResponseAllNews();
		List<News> newsList = null;
		try {
			newsList = newsRepository.findAll();
			if (!CollectionUtils.isEmpty(newsList)) {
				responseListNews.setReturnCode(Constants.Return.SUCCESS.getCode());
				responseListNews.setReturnMessage(Constants.Return.SUCCESS.getMessage());
				log.info("getAllNews() News listed! Count: {}", newsList.size());
				MapperUtil.mapResponseListNewsFromModel(newsList, responseListNews);
			}
		} catch (Exception ex) {
			responseListNews.setReturnCode(Constants.Return.FAIL.getCode());
			responseListNews.setReturnMessage(Constants.Return.FAIL.getMessage());
			log.error("getAllNews() error!", ex);
		}
		return responseListNews;
	}

}
