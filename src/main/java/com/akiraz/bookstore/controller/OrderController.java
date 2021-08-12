package com.akiraz.bookstore.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akiraz.bookstore.model.entity.Order;
import com.akiraz.bookstore.service.intf.OrderService;
import com.akiraz.bookstore.util.DateUtil;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/order")
public class OrderController {

	private final OrderService orderService;

	@ApiOperation("buyBook() - Order book")
	@PostMapping("/")
	public ResponseEntity<Order> buyBook(@Valid @RequestBody Order order) {
		return new ResponseEntity<>(orderService.buyBook(order), HttpStatus.OK);
	}

	@ApiOperation("getOrder() - gets order")
	@PostMapping("/{orderId}")
	public ResponseEntity<Order> getOrder(@PathVariable(value = "orderId") Long orderId) {
		return new ResponseEntity<>(orderService.getOrder(orderId), HttpStatus.OK);
	}

	@ApiOperation("listOrders() - lists orders between start date and end date")
	@PostMapping("/list/{startDate}/{endDate}")
	public ResponseEntity<List<Order>> listOrders(
			@PathVariable(value = "startDate") @ApiParam(value = "ddMMyyyy", example = "{ddMMyyyy}") String startDate,
			@PathVariable(value = "endDate")   @ApiParam(value = "ddMMyyy",   example = "{ddMMyyyy}") String endDate) {

		return new ResponseEntity<>(
				orderService.listOrders(DateUtil.toLocalDateTime(startDate), DateUtil.toLocalDateTime(endDate)),
				HttpStatus.OK);
	}

}
