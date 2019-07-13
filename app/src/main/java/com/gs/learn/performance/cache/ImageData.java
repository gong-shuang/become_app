package com.gs.learn.performance.cache;

import android.graphics.Bitmap;

public class ImageData {
	public String uri;
	public Bitmap bitmap;
	
	public ImageData() {
		uri = "";
		bitmap = null;
	}

	public ImageData(String uri, Bitmap bitmap) {
		this.uri = uri;
		this.bitmap = bitmap;
	}
	
}
