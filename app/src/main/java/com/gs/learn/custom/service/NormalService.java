package com.gs.learn.custom.service;

import com.gs.learn.custom.ServiceNormalActivity;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by ouyangshen on 2016/10/14.
 */
public class NormalService extends Service {
	private static final String TAG = "NormalService";
	
	private void refresh(String text) {
		Log.d(TAG, text);
		ServiceNormalActivity.showText(text);
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
	public int onStartCommand(Intent intent, int flags, int startid) {
		refresh("onStartCommand. flags="+flags);
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		refresh("onDestroy");
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		refresh("onBind");
		return null;
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
