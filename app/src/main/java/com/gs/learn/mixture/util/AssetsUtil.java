package com.gs.learn.mixture.util;

import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class AssetsUtil {

	public static String getTxtFromAssets(Context context, String fileName) {
		String result = "";
		try {
			InputStream is = context.getAssets().open(fileName);
			int lenght = is.available();
			byte[]  buffer = new byte[lenght];
			is.read(buffer);
			result = new String(buffer, "utf8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static Bitmap getImgFromAssets(Context context, String fileName) {
		Bitmap bitmap = null;
		try {
			InputStream is = context.getAssets().open(fileName);
			bitmap = BitmapFactory.decodeStream(is);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}

}
