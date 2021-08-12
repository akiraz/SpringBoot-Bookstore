package com.akiraz.bookstore.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.akiraz.bookstore.response.ErrorResponse;
import com.akiraz.bookstore.util.Constants;
import com.akiraz.bookstore.exception.BookNotFoundException;
import com.akiraz.bookstore.exception.CustomerNotFoundException;
import com.akiraz.bookstore.exception.EmailExistException;
import com.akiraz.bookstore.exception.OrderCountNotValidException;
import com.akiraz.bookstore.exception.OrderNotFoundException;
import com.akiraz.bookstore.exception.OutOfStockException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GenericExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ErrorResponse> handleError(final Exception ex) {
		ErrorResponse errorResponse = null;

		if (ex.getCause() instanceof CustomerNotFoundException || ex instanceof CustomerNotFoundException) {
			log.error("Customer Not Found Exception!", ex);
			errorResponse = ErrorResponse.builder().returnCode(Constants.Return.CUSTOMER_NOT_FOUND.getCode())
					.returnMessage(Constants.Return.FAIL.getMessage()).details("Customer not found!").build();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		} else if (ex.getCause() instanceof EmailExistException || ex instanceof EmailExistException) {
			log.error("Email Already Exist Exception!", ex);
			errorResponse = ErrorResponse.builder().returnCode(Constants.Return.EMAIL_EXIST.getCode())
					.returnMessage(Constants.Return.FAIL.getMessage()).details("Email already exsit!").build();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		} else if (ex.getCause() instanceof OutOfStockException || ex instanceof OutOfStockException) {
			log.error("Out of stock Exception!", ex);
			errorResponse = ErrorResponse.builder().returnCode(Constants.Return.OUT_OF_STOCK.getCode())
					.returnMessage(Constants.Return.FAIL.getMessage()).details("Out of stock!").build();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		} else if (ex.getCause() instanceof OrderCountNotValidException || ex instanceof OrderCountNotValidException) {
			log.error("Order count not valid Exception!", ex);
			errorResponse = ErrorResponse.builder().returnCode(Constants.Return.ORDER_COUNT_NOT_VALID.getCode())
					.returnMessage(Constants.Return.FAIL.getMessage()).details("Order count not valid!").build();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		} else if (ex.getCause() instanceof BookNotFoundException || ex instanceof BookNotFoundException) {
			log.error("Book not found Exception!", ex);
			errorResponse = ErrorResponse.builder().returnCode(Constants.Return.BOOK_NOT_FOUND.getCode())
					.returnMessage(Constants.Return.FAIL.getMessage()).details("Book not found!").build();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		} else if (ex.getCause() instanceof OrderNotFoundException || ex instanceof OrderNotFoundException) {
			log.error("Order not found Exception!", ex);
			errorResponse = ErrorResponse.builder().returnCode(Constants.Return.ORDER_NOT_FOUND.getCode())
					.returnMessage(Constants.Return.FAIL.getMessage()).details("Order not found!").build();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		} else {
			log.error("Generic Exception!", ex);
			errorResponse = ErrorResponse.builder().returnCode(Constants.Return.FAIL.getCode())
					.returnMessage(Constants.Return.FAIL.getMessage()).details("Generic Exception!").build();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		}

	}
}