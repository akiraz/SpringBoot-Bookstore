package com.akiraz.newsapp.util;

import java.util.Date;

public class DateUtil {
	
	public static Date now() {
		return new Date();
	}
	
	public static Date getOneHourAgo() {
		return new Date(System.currentTimeMillis() - 3600 * 1000);
	}
}
