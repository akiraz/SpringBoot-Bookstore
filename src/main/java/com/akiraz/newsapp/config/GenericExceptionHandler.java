package com.akiraz.newsapp.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.akiraz.newsapp.exception.NewsNotFoundException;
import com.akiraz.newsapp.response.ErrorResponse;
import com.akiraz.newsapp.util.Constants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GenericExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ErrorResponse> handleError(final Exception ex) {
		ErrorResponse errorResponse = null;
		
		if (ex.getCause() instanceof NewsNotFoundException || ex instanceof NewsNotFoundException) {
			log.error("News Not Found Exception!", ex);
			 errorResponse = ErrorResponse.builder().returnCode(Constants.Return.NEWS_NOT_FOUND.getCode())
					.returnMessage(Constants.Return.NEWS_NOT_FOUND.getMessage()).details("News not found!").build();
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		}else {
			log.error("Generic Exception!", ex);
			 errorResponse = ErrorResponse.builder().returnCode(Constants.Return.FAIL.getCode())
						.returnMessage(Constants.Return.FAIL.getMessage()).details("Generic Exception!").build();
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		}
		
		
	
	}
}