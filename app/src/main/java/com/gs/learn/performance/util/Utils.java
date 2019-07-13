package com.gs.learn.performance.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;

public class Utils {
	private static final String TAG = "Utils";

	@SuppressLint("SimpleDateFormat")
	public static String getNowTime() {
		String format = "HH:mm:ss";
		SimpleDateFormat s_format = new SimpleDateFormat(format);
		Date d_date = new Date();
		String s_date = "";
		s_date = s_format.format(d_date);
		return s_date;
	}

}
