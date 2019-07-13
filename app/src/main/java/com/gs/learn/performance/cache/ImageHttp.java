package com.gs.learn.performance.cache;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageHttp {

	private static void setConnHeader(HttpURLConnection conn) throws ProtocolException {
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(5000);
		conn.setReadTimeout(10000);
		conn.setRequestProperty("Accept", "*/*");
		//IE使用
//		conn.setRequestProperty("Accept-Language", "zh-CN");
//		conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; WOW64; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; InfoPath.2; .NET4.0C)");
		//firefox使用
		conn.setRequestProperty("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
		conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20100101 Firefox/33.0");
		conn.setRequestProperty("Accept-Encoding", "gzip, deflate");
	}

	public static Bitmap getImage(String uri) {
		Bitmap bitmap = null;
		try {
			URL url = new URL(uri);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			setConnHeader(conn);
			conn.connect();

			InputStream is = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(is);
			conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}

}
