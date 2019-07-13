package com.gs.learn.custom.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class FileUtil {

	public static void saveText(String path, String txt) {
		try {
			FileOutputStream fos = new FileOutputStream(path);
			fos.write(txt.getBytes());
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String openText(String path) {
		String readStr = "";
		try {
			FileInputStream fis = new FileInputStream(path);
			byte[] b = new byte[fis.available()];
			fis.read(b);
			readStr = new String(b);
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return readStr;
	}

	public static void saveImage(String path, Bitmap bitmap) {
		File file = new File(path);
		if (file.exists() == true) {
			return;
		}
		try {
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path));
			bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
			bos.flush();
			bos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Bitmap openImage(String path) {
		Bitmap bitmap = null;
		try {
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(path));
			bitmap = BitmapFactory.decodeStream(bis);
			bis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}
	
}
