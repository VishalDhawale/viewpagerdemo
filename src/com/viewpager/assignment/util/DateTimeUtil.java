package com.viewpager.assignment.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateTimeUtil {

	private static SimpleDateFormat simpleDateFormat;

	public static java.util.Date convertToDate(String date, String format)
			throws ParseException {
		simpleDateFormat = new SimpleDateFormat(format);
		return simpleDateFormat.parse(date);
	}

}
