package com.akiraz.newsapp.util;

public class Constants {

	public enum Return {

		SUCCESS("0000", "SUCCEED"), FAIL("0001", "FAILED"), NEWS_NOT_FOUND("0002", "News not found!");

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
