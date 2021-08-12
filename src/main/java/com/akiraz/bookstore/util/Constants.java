package com.akiraz.bookstore.util;

public class Constants {

	public static final String HEADER = "Authorization";
	public static final String PREFIX = "Bearer ";
	public static final String SECRET = "secretKey";
	public static final String JWTTOKEN = "JWTTOKEN";
	public static final String AUTHORITIES = "authorities";
	public static final String ROLE_USER = "ROLE_USER";
	public static final String ASTERISK = "*****";

	public static final Integer TOKEN_EXPIRATION = 600000; // 10 minutes timeout

	public static final String DATE_FORMAT_SLASH_ddMMyyyy = "dd/MM/yyyy";
	public static final String DATE_FORMAT_ddMMyyyy = "ddMMyyyy";
	public static final String DATE_FORMAT_MMMMM = "MMMMM";

	public enum Return {

		SUCCESS("0000", "SUCCEED"),
		FAIL("0001", "FAILED"),
		CUSTOMER_NOT_FOUND("0002", "Customer not found!"),
		EMAIL_EXIST("0003", "Email already exits!"), 
		OUT_OF_STOCK("0004", "Out of stock exception!"),
		ORDER_COUNT_NOT_VALID("0005", "Order count not valid exception!"),
		BOOK_NOT_FOUND("0006", "Book not found exception!"),
		ORDER_NOT_FOUND("0007", "Order not found exception!");

		private String code;
		private String message;

		public String getCode() {
			return this.code;
		}

		public String getMessage() {
			return this.message;
		}

		private Return(String code, String message) {
			this.code = code;
			this.message = message;
		}

	}

}
