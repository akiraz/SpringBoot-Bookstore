package com.akiraz.bookstore.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

	public static String localDateTimeToDateWithSlash(LocalDateTime localDateTime) {
		return DateTimeFormatter.ofPattern(Constants.DATE_FORMAT_SLASH_ddMMyyyy).format(localDateTime);
	}

	public static String getMonthName(String date) throws ParseException {
		Date convertedDate = new SimpleDateFormat(Constants.DATE_FORMAT_SLASH_ddMMyyyy).parse(date);
		SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT_MMMMM, new Locale("en", "US"));
		return format.format(convertedDate);
	}

	public static LocalDateTime toLocalDateTime(String date) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT_ddMMyyyy);
		LocalDate localDate = LocalDate.parse(date, dtf);
		return LocalDateTime.of(localDate, LocalTime.of(0,0)); // 2018-03-06T00:00
		
	}
}
