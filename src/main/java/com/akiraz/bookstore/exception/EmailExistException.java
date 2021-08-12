package com.akiraz.bookstore.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.OK, reason = "Customer email  exist!")
public class EmailExistException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
