package com.gs.learn.custom.service;

import com.gs.learn.custom.BindImmediateActivity;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by ouyangshen on 2016/10/14.
 */
public class BindImmediateService extends Service {
	private static final String TAG = "BindImmediateService";
	private final IBinder mBinder = new LocalBinder();

	public class LocalBinder extends Binder {
		public BindImmediateService getService() {
			return BindImmediateService.this;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		refresh("onBind");
		return mBinder;
	}

	private void refresh(String text) {
		Log.d(TAG, text);
		BindImmediateActivity.showText(text);
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
