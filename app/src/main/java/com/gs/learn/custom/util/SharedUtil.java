package com.gs.learn.custom.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedUtil {

	private static SharedUtil mUtil;
	private static SharedPreferences mShared;
	
	public static SharedUtil getIntance(Context ctx) {
		if (mUtil == null) {
			mUtil = new SharedUtil();
		}
		mShared = ctx.getSharedPreferences("share", Context.MODE_PRIVATE);
		return mUtil;
	}

	public void writeShared(String key, String value) {
		SharedPreferences.Editor editor = mShared.edit();
		editor.putString(key, value);
		editor.commit(); 
	}

	public String readShared(String key, String defaultValue) {
		return mShared.getString(key, defaultValue);
	}

	public void writeShared(String key, int value) {
		SharedPreferences.Editor editor = mShared.edit();
		editor.putInt(key, value);
		editor.commit(); 
	}

	public int readShared(String key, int defaultValue) {
		return mShared.getInt(key, defaultValue);
	}
	
}
