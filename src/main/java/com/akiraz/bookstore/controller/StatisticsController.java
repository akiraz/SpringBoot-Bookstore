package com.akiraz.bookstore.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akiraz.bookstore.model.entity.Statistics;
import com.akiraz.bookstore.service.intf.StatisticsService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/statistics")
public class StatisticsController {

	private final StatisticsService statisticsService;

	@ApiOperation("getStatistics() - gets statistisc")
	@GetMapping("/")
	public ResponseEntity<List<Statistics> > getStatistics() {
		return new ResponseEntity<>(statisticsService.getStatistics(), HttpStatus.OK);
	}

}
