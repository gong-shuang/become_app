package com.gs.learn.test.util;

import android.content.Context;
import android.widget.Toast;

public class ToastTool {

	public static boolean isShow = false;// 上线模式
	// public static boolean isShow = true;//开发模式

	public static void showShort(Context ctx, String msg) {
		if (isShow == true) {
			Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
		}
	}

	public static void showLong(Context ctx, String msg) {
		if (isShow == true) {
			Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
		}
	}

	public static void showQuit(Context ctx) {
		Toast.makeText(ctx, "再按一次返回键退出！", Toast.LENGTH_SHORT).show();
	}

}
