package com.akiraz.newsapp.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akiraz.newsapp.request.RequestMarkNewsAsReadByUser;
import com.akiraz.newsapp.request.RequestNewsByCategoryAndWebsite;
import com.akiraz.newsapp.response.ResponseAllUsersNews;
import com.akiraz.newsapp.response.ResponseMarkNewsAsReadByUser;
import com.akiraz.newsapp.response.ResponseNewsByCategoryAndNewsSite;
import com.akiraz.newsapp.service.intf.UserNewsService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/usernews")
public class UserNewsController {
	
	
    private final UserNewsService userNewsService;

	@ApiOperation("getNewsByCategoryAndNewsSiteFromThePastHour - returns hourly recent news with a summary and image")
	@PostMapping("/getNewsByCategoryAndNewsSiteFromThePastHour")
	public ResponseEntity<ResponseNewsByCategoryAndNewsSite> getNewsByCategoryAndNewsSiteFromThePastHour(@Valid @RequestBody RequestNewsByCategoryAndWebsite requestNewsByCategoryAndNewsite) {
        return new ResponseEntity<>(userNewsService.getNewsByCategoryAndNewsSiteFromThePastHour(requestNewsByCategoryAndNewsite), HttpStatus.OK);
    }

	@ApiOperation("markNewsAsReadByUser - marks a new as unread or read")
	@PutMapping("/markNewsAsReadByUser")
    public ResponseEntity<ResponseMarkNewsAsReadByUser> markNewsAsReadByUser(@Valid @RequestBody RequestMarkNewsAsReadByUser requestMarkNewsAsReadByUser) {
        return new ResponseEntity<>(userNewsService.markNewsAsReadByUser(requestMarkNewsAsReadByUser), HttpStatus.OK);
    }

	@ApiOperation("getAllUsersNews - get all usernews ")
    @GetMapping("/getAllUsersNews")
    public ResponseEntity<ResponseAllUsersNews> getAllUsersNews() {
        return new ResponseEntity<>(userNewsService.getAllUsersNews(), HttpStatus.OK);
    }
	

}
