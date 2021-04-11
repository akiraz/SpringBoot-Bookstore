package com.akiraz.newsapp.util;

import java.util.List;

import org.springframework.util.CollectionUtils;

import com.akiraz.newsapp.model.entity.News;
import com.akiraz.newsapp.model.entity.UsersNews;
import com.akiraz.newsapp.request.RequestUpdateNews;
import com.akiraz.newsapp.response.ResponseAddNews;
import com.akiraz.newsapp.response.ResponseAllNews;
import com.akiraz.newsapp.response.ResponseAllUsersNews;
import com.akiraz.newsapp.response.ResponseRemoveNews;
import com.akiraz.newsapp.response.ResponseUpdateNews;

public class MapperUtil {

	public static void mapResponseAddNewsFromModel(News news, ResponseAddNews responseAddNews) {
		responseAddNews.setNews(news != null ? news : null);
	}

	public static void mapResponseRemoveNewsFromModel(News news, ResponseRemoveNews responseRemoveNews) {
		responseRemoveNews.setNews(news != null ? news : null);
	}

	public static void mapResponseUpdateNewsFromModel(News news, ResponseUpdateNews responseUpdateNews) {
		responseUpdateNews.setNews(news != null ? news : null);
	}

	public static void mapResponseListNewsFromModel(List<News> newsList, ResponseAllNews responseListNews) {
		if (!CollectionUtils.isEmpty(newsList)) {
			responseListNews.setNewList(newsList);
		}
	}

	public static void mapRequestUpdateNewstoModel(News news, RequestUpdateNews requestUpdateNews) {
		if (requestUpdateNews != null && requestUpdateNews.getNews() != null) {
			if (requestUpdateNews.getNews().getCategory() != null) {
				news.setCategory(requestUpdateNews.getNews().getCategory());
			}
			if (requestUpdateNews.getNews().getImageLink() != null) {
				news.setImageLink(requestUpdateNews.getNews().getImageLink());
			}
			if (requestUpdateNews.getNews().getSummaryText() != null) {
				news.setSummaryText(requestUpdateNews.getNews().getSummaryText());
			}
			if (requestUpdateNews.getNews().getTitle() != null) {
				news.setTitle(requestUpdateNews.getNews().getTitle());
			}
			if (requestUpdateNews.getNews().getWebsite() != null) {
				news.setWebsite(requestUpdateNews.getNews().getWebsite());
			}
		}
	}

	public static void mapResponseAllUsersNewsFromModel(List<UsersNews> usersNewsList,
			ResponseAllUsersNews responseAllUsersNews) {
		if (!CollectionUtils.isEmpty(usersNewsList)) {
			responseAllUsersNews.setUsersNewList(usersNewsList);
		}
	}

}
