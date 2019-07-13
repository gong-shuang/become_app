package com.gs.learn.test.util;

import android.app.AlertDialog;
import android.content.Context;

public class DialogTool {

	public static boolean isShow = false;// 上线模式
	// public static boolean isShow = true;//开发模式

	public static int SYSTEM = 0;
	public static int IO = 1;
	public static int NETWORK = 2;
	private static String[] mError = { "系统异常，请稍候再试", "读写失败，请清理内存空间后再试",
			"网络连接失败，请检查网络设置是否开启" };

	public static void showError(Context ctx, int type, String title, Exception e) {
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		builder.setTitle(title);
		if (isShow == true) {
			String desc = String.format("%s\n异常描述：%s", mError[type], e.getMessage());
			builder.setMessage(desc);
		} else {
			builder.setMessage(mError[type]);
		}
		builder.setPositiveButton("确定", null);
		builder.create().show();
	}

	public static void showError(Context ctx, int type, String title, int code, String msg) {
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		builder.setTitle(title);
		if (isShow == true) {
			String desc = String.format("%s\n异常代码：%d\n异常描述：%s", mError[type], code, msg);
			builder.setMessage(desc);
		} else {
			builder.setMessage(mError[type]);
		}
		builder.setPositiveButton("确定", null);
		builder.create().show();
	}

}
