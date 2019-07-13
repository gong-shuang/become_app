package com.gs.learn.performance.cache;

import android.graphics.Point;

public final class ImageCacheConfig {
	public final String mDir;
	public final int mMemoryFileCount;
	public final int mDiskFileCount;
	public final int mThreadCount;
	public final int mFadeInterval;
	public final int mBeginImage;
	public final int mErrorImage;
	public final Point mSize;
	public final int mCacheStyle;

	public final static int FIFO = 0;
	public final static int LRU = 1;

	private ImageCacheConfig(Builder builder) {
		this.mDir = builder.mDir;
		this.mMemoryFileCount = builder.mMemoryFileCount;
		this.mDiskFileCount = builder.mDiskFileCount;
		this.mThreadCount = builder.mThreadCount;
		this.mFadeInterval = builder.mFadeInterval;
		this.mBeginImage = builder.mBeginImage;
		this.mErrorImage = builder.mErrorImage;
		this.mCacheStyle = builder.mCacheStyle;
		this.mSize = builder.mSize;
	}

	public static class Builder {

		private String mDir = null;
		private int mMemoryFileCount = 10;
		private int mDiskFileCount = 50;
		private int mThreadCount = 3;
		private int mFadeInterval = 3000;
		private int mBeginImage = 0;
		private int mErrorImage = 0;
		private int mCacheStyle = FIFO;
		private Point mSize = null;

		public Builder setCacheDir(String dir) {
			mDir = dir;
			return this;
		}
		
		public Builder setMemoryFileCount(int memory_file_count) {
			mMemoryFileCount = memory_file_count;
			return this;
		}

		public Builder setDiskFileCount(int disk_file_count) {
			mDiskFileCount = disk_file_count;
			return this;
		}

		public Builder setThreadCount(int thread_count) {
			mThreadCount = thread_count;
			return this;
		}

		public Builder setFadeInterval(int fade_interval) {
			mFadeInterval = fade_interval;
			return this;
		}

		public Builder setBeginImage(int begin_image) {
			mBeginImage = begin_image;
			return this;
		}

		public Builder setErrorImage(int error_image) {
			mErrorImage = error_image;
			return this;
		}

		public Builder setCacheStyle(int cache_style) {
			mCacheStyle = cache_style;
			return this;
		}
		
		public Builder resize(int width, int height) {
			mSize = new Point(width, height);
			return this;
		}

		public ImageCacheConfig build() {
			return new ImageCacheConfig(this);
		}
	}
}
