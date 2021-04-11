package com.akiraz.newsapp.service.intf;

import com.akiraz.newsapp.request.RequestMarkNewsAsReadByUser;
import com.akiraz.newsapp.request.RequestNewsByCategoryAndWebsite;
import com.akiraz.newsapp.response.ResponseAllUsersNews;
import com.akiraz.newsapp.response.ResponseMarkNewsAsReadByUser;
import com.akiraz.newsapp.response.ResponseNewsByCategoryAndNewsSite;

public interface UserNewsService {

	public ResponseNewsByCategoryAndNewsSite getNewsByCategoryAndNewsSiteFromThePastHour(
			RequestNewsByCategoryAndWebsite requestNewsByCategoryAndNewsite);

	public ResponseMarkNewsAsReadByUser markNewsAsReadByUser(RequestMarkNewsAsReadByUser requestMarkNewsAsReadByUser);

	public ResponseAllUsersNews getAllUsersNews();
}
