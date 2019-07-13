package com.gs.learn.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Locale;

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
	
	public static ArrayList<File> getFileList(String path, String[] extendArray) {
		ArrayList<File> displayedContent = new ArrayList<File>();
		File[] files = null;
		File directory = new File(path);
		if (extendArray != null && extendArray.length>0) {
			FilenameFilter fileFilter = getTypeFilter(extendArray);
			files = directory.listFiles(fileFilter);
		} else {
			files = directory.listFiles();
		}

		if (files != null) {
			for (File f : files) {
				if (!f.isDirectory() && !f.isHidden()) {
					displayedContent.add(f);
				}
			}
		}
		return displayedContent;
	}

	public static FilenameFilter getTypeFilter(String[] extendArray) {
		final ArrayList<String> fileExtensions = new ArrayList<String>();
		for (int i=0; i<extendArray.length; i++) {
			fileExtensions.add(extendArray[i]);
		}
		FilenameFilter fileNameFilter = new FilenameFilter() {
			@Override
			public boolean accept(File directory, String fileName) {
				boolean matched = false;
				File f = new File(String.format("%s/%s",
						directory.getAbsolutePath(), fileName));
				matched = f.isDirectory();
				if (!matched) {
					for (String s : fileExtensions) {
						s = String.format(".{0,}\\%s$", s);
						s = s.toUpperCase(Locale.getDefault());
						fileName = fileName.toUpperCase(Locale.getDefault());
						matched = fileName.matches(s);
						if (matched) {
							break;
						}
					}
				}
				return matched;
			}
		};
		return fileNameFilter;
	}

}
