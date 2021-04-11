package com.akiraz.newsapp.config;

import java.util.Collection;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.HandlerMethod;

import com.akiraz.newsapp.response.ErrorResponse;
import com.akiraz.newsapp.util.Constants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler {
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@Order(Ordered.HIGHEST_PRECEDENCE)
	@ResponseBody
	@ExceptionHandler(TransactionSystemException.class)
	public final ResponseEntity<?> methodArgumentNotValidException(Exception ex, WebRequest request, HttpServletRequest httpRequest, HttpServletResponse response, HandlerMethod handlerMethod) {
		log.error("Custom Exception method name :"+handlerMethod.getMethod().getName()+ "() error! Exception: "+ex);
		
		Throwable cause = ((TransactionSystemException) ex).getRootCause();
	
		String constraintMessage = "";
		if (cause instanceof ConstraintViolationException) {
			Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) cause)
					.getConstraintViolations();
			for (ConstraintViolation<?> constraintViolation : constraintViolations) {
				if (!(constraintViolation.getInvalidValue() instanceof Collection)) {
					constraintMessage += constraintViolation.getMessage() + " Value:"
							+ constraintViolation.getInvalidValue() + " ,";
				} else {
					constraintMessage += constraintViolation.getMessage() + " ,";
				}
			}
			constraintMessage = constraintMessage.substring(0, constraintMessage.length() - 2);
		}

		ErrorResponse errorResponse = ErrorResponse.builder().returnCode(Constants.Return.FAIL.getCode()).returnMessage(Constants.Return.FAIL.getMessage()).details(constraintMessage).build();
		return ResponseEntity.badRequest().body(errorResponse);
	}

}
