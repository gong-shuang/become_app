package com.gs.learn.custom.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.gs.learn.custom.BindDelayActivity;

/**
 * Created by ouyangshen on 2016/10/14.
 */
public class BindDelayService extends Service {
	private static final String TAG = "BindDelayService";
	private final IBinder mBinder = new LocalBinder();

	public class LocalBinder extends Binder {
		public BindDelayService getService() {
			return BindDelayService.this;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		refresh("onBind");
		return mBinder;
	}

	private void refresh(String text) {
		Log.d(TAG, text);
		BindDelayActivity.showText(text);
	}

	@Override
	public void onCreate() {
		refresh("onCreate");
		super.onCreate();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onStart(Intent intent, int startid) {
		refresh("onStart");
		super.onStart(intent, startid);
	}

	@Override
	public void onDestroy() {
		refresh("onDestroy");
		super.onDestroy();
	}

	@Override
	public void onRebind(Intent intent) {
		refresh("onRebind");
		super.onRebind(intent);
	}

	@Override
	public boolean onUnbind(Intent intent) {
		refresh("onUnbind");
		return true;
	}
	
}
