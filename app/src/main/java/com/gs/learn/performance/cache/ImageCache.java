package com.gs.learn.performance.cache;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.LruCache;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

public class ImageCache {
	private final static String TAG = "ImageCache";
	//内存中的图片缓存
	private HashMap<String, Bitmap> mImageMap = new HashMap<String, Bitmap>();
	//uri与视图控件的映射关系
	private HashMap<String, ImageView> mViewMap = new HashMap<String, ImageView>();
	//缓存队列，采用FIFO先进先出策略，需操作队列首尾两端，故采用双端队列
	private LinkedList<String> mFifoList = new LinkedList<String>();
	//缓存队列，采用LRU近期最少使用策略，Android专门提供了LruCache实现该算法
	private LruCache<String, Bitmap> mImageLru;
	
	private ImageCacheConfig mConfig;
	private String mDir = "";
	private ThreadPoolExecutor mPool;
	private static Handler mMyHandler;
	private static ImageCache mCache = null;
	private static Context mContext;
	
	public static ImageCache getInstance(Context context) {
		if (mCache == null) {
			mCache = new ImageCache();
			mCache.mContext = context;
		}
		return mCache;
	}
	
	public ImageCache initConfig(ImageCacheConfig config) {
		mCache.mConfig = config;
		mCache.mDir = mCache.mConfig.mDir;
		if (mCache.mDir==null || mCache.mDir.length()<=0) {
			mCache.mDir = Environment.getExternalStorageDirectory() + "/image_cache";
		}
		Log.d(TAG, "mDir="+mCache.mDir);
		//若目录不存在，则先创建新目录
		File dir = new File(mCache.mDir);
		if (dir.exists() != true) {
			dir.mkdirs();
		}
		mCache.mPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(mCache.mConfig.mThreadCount);
		mCache.mMyHandler = new MyHandler((Activity)mCache.mContext);
		if (mCache.mConfig.mCacheStyle == ImageCacheConfig.LRU) {
			mImageLru = new LruCache(mCache.mConfig.mMemoryFileCount);
		}
		return mCache;
	}
	
	public void show(String uri, ImageView iv) {
		if (mConfig.mBeginImage != 0) {
			iv.setImageResource(mConfig.mBeginImage);
		}
		mViewMap.put(uri, iv);
		if (checkExist(uri) == true) {
			mCache.render(uri, getBitmap(uri));
		} else {
			String path = getFilePath(uri);
			if ((new File(path)).exists() == true) {
				Bitmap bitmap = ImageUtil.openBitmap(path);
				if (bitmap != null) {
					mCache.render(uri, bitmap);
				} else {
					mPool.execute(new MyRunnable(uri));
				}
			} else {
				mPool.execute(new MyRunnable(uri));
			}
		}
	}
	
	private boolean checkExist(String uri) {
		if (mCache.mConfig.mCacheStyle == ImageCacheConfig.LRU) {
			return (mImageLru.get(uri)==null)?false:true;
		} else {
			return mImageMap.containsKey(uri);
		}
	}
	
	private Bitmap getBitmap(String uri) {
		if (mCache.mConfig.mCacheStyle == ImageCacheConfig.LRU) {
			return mImageLru.get(uri);
		} else {
			return mImageMap.get(uri);
		}
	}
	
	private String getFilePath(String uri) {
		String file_path = String.format("%s/%d.jpg", mDir, uri.hashCode());
		return file_path;
	}
	
	private static class MyHandler extends Handler {
		public static WeakReference<Activity> mActivity;
		public MyHandler(Activity activity) {
			mActivity = new WeakReference<Activity>(activity);
		}

		@Override
		public void handleMessage(Message msg) {
			Activity act = mActivity.get();
			if (act != null) {
				ImageData data = (ImageData) (msg.obj);
				if (data!=null && data.bitmap!=null) {
					mCache.render(data.uri, data.bitmap);
				} else {
					mCache.showError(data.uri);
				}
			}
		}
	}

	private class MyRunnable implements Runnable {
		private String mUri;
		public MyRunnable(String uri) {
			mUri = uri;
		}
		
		@Override
		public void run() {
			Activity act = MyHandler.mActivity.get();
			if (act != null) {
				Bitmap bitmap = ImageHttp.getImage(mUri);
				if (bitmap != null) {
					if (mConfig.mSize != null) {
						bitmap = Bitmap.createScaledBitmap(bitmap, mConfig.mSize.x, mConfig.mSize.y, false);
					}
					ImageUtil.saveBitmap(getFilePath(mUri), bitmap);
				}
				ImageData data = new ImageData(mUri, bitmap);
				Message msg = mMyHandler.obtainMessage();
				msg.obj = data;
				mMyHandler.sendMessage(msg);
			}
		}
	};

	private void render(String uri, Bitmap bitmap) {
		ImageView iv = mViewMap.get(uri);
		if (mConfig.mFadeInterval <= 0) {
			iv.setImageBitmap(bitmap);
		} else {
			//内存中已有图片的就直接显示，不再展示淡入淡出动画
			if (checkExist(uri) == true) {
				iv.setImageBitmap(bitmap);
			} else {
				iv.setAlpha(0.0f);
				AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
				alphaAnimation.setDuration(mConfig.mFadeInterval);
				alphaAnimation.setFillAfter(true);
				iv.setImageBitmap(bitmap);
				iv.setAlpha(1.0f);
				iv.setAnimation(alphaAnimation);
				alphaAnimation.start();
				mCache.refreshList(uri, bitmap);
			}
		}
	}
	
	private synchronized void refreshList(String uri, Bitmap bitmap) {
		if (mCache.mConfig.mCacheStyle == ImageCacheConfig.LRU) {
			mImageLru.put(uri, bitmap);
		} else {
			if (mFifoList.size() >= mConfig.mMemoryFileCount) {
				String out_uri = mFifoList.pollFirst();
				mImageMap.remove(out_uri);
			}
			mImageMap.put(uri, bitmap);
			mFifoList.addLast(uri);
		}
	}
	
	private void showError(String uri) {
		ImageView iv = mViewMap.get(uri);
		if (mConfig.mErrorImage != 0) {
			iv.setImageResource(mConfig.mErrorImage);
		}
	}
	
	public void clear() {
		for (Map.Entry<String, Bitmap> item_map : mImageMap.entrySet()) {
			Bitmap bitmap = item_map.getValue();
			bitmap.recycle();
		}
		if (mImageLru != null) {
			mImageLru.evictAll();
		}
		mCache = null;
	}

}
