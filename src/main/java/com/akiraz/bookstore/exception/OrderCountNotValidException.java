package com.akiraz.bookstore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.OK, reason = "Order count not valid exception!")
public class OrderCountNotValidException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}