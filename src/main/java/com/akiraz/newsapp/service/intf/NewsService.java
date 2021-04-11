package com.akiraz.newsapp.service.intf;

import com.akiraz.newsapp.request.RequestAddNews;
import com.akiraz.newsapp.request.RequestRemoveNews;
import com.akiraz.newsapp.request.RequestUpdateNews;
import com.akiraz.newsapp.response.ResponseAddNews;
import com.akiraz.newsapp.response.ResponseAllNews;
import com.akiraz.newsapp.response.ResponseRemoveNews;
import com.akiraz.newsapp.response.ResponseUpdateNews;

public interface NewsService {

	public ResponseAddNews addNews(RequestAddNews requestAddNews);

	public ResponseRemoveNews removeNews(RequestRemoveNews requestRemoveNews);

	public ResponseUpdateNews updateNews(RequestUpdateNews requestUpdateNews);

	public ResponseAllNews getAllNews();
}
