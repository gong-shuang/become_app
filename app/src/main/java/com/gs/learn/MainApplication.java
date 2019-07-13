package com.gs.learn;

import java.util.HashMap;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Message;
import android.util.Log;

import com.gs.learn.custom.database.TrafficDBHelper;
import com.gs.learn.network.thread.ClientThread;
import com.gs.learn.network.util.DateUtil;
import com.gs.learn.performance.util.Utils;

/**
 * Created by ouyangshen on 2016/10/1.
 */
public class MainApplication extends Application {

	private final static String TAG = "MainApplication";
	private static MainApplication mApp;
	public HashMap<String, String> mInfoMap = new HashMap<String, String>(); // storage
	public HashMap<Long, Bitmap> mIconMap = new HashMap<Long, Bitmap>();
	public TrafficDBHelper mTrafficHelper;
	private String mNickName;
	private ClientThread mClientThread;
	public MediaPlayer mMediaPlayer;
	public String mSong;
	public String mFilePath;
	private LockScreenReceiver mReceiver;
	private String mChange = "";

	public static MainApplication getInstance() {
		return mApp;
	}

	public String getChangeDesc() {
		return mApp.mChange;
	}

	public void setChangeDesc(String change) {
		mApp.mChange = mApp.mChange + change;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mApp = this;
		mTrafficHelper = TrafficDBHelper.getInstance(this, 1);
		mTrafficHelper.openWriteLink();
		Log.d(TAG, "onCreate");
		mClientThread = new ClientThread(mApp);
		Log.d(TAG, "mClientThread start");
		new Thread(mClientThread).start();
		mMediaPlayer = new MediaPlayer();

		mReceiver = new LockScreenReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_SCREEN_ON);
		filter.addAction(Intent.ACTION_SCREEN_OFF);
		filter.addAction(Intent.ACTION_USER_PRESENT);
		registerReceiver(mReceiver, filter);
	}
	
	@Override
	public void onTerminate() {
		Log.d(TAG, "onTerminate");
		super.onTerminate();
	}

	public void sendAction(String action, String otherId, String msgText) {
		String content = String.format("%s,%s,%s,%s,%s%s%s\r\n",
				action, Build.SERIAL, getNickName(), DateUtil.getNowDateTime(""),
				otherId, ClientThread.SPLIT_LINE, msgText);
		Log.d(TAG, "sendAction : " + content);
		Message msg = Message.obtain();
		msg.obj = content;
		if (mClientThread==null || mClientThread.mRecvHandler==null) {
			Log.d(TAG, "mClientThread or its mRecvHandler is null");
		} else {
			mClientThread.mRecvHandler.sendMessage(msg);
		}
	}

	public void setNickName(String nickName) {
		mApp.mNickName = nickName;
	}

	public String getNickName() {
		return mApp.mNickName;
	}

	private class LockScreenReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent != null) {
				String change = "";
				change = String.format("%s\n%s : 收到广播：%s", change,
						Utils.getNowTime(), intent.getAction());
				if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
					change = String.format("%s\n这是屏幕点亮事件，可在此开启日常操作", change);
				} else if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
					change = String.format("%s\n这是屏幕关闭事件，可在此暂停耗电操作", change);
				} else if (intent.getAction()
						.equals(Intent.ACTION_USER_PRESENT)) {
					change = String.format("%s\n这是用户解锁事件", change);
				}
				Log.d(TAG, change);
				com.gs.learn.MainApplication.getInstance().setChangeDesc(change);
			}
		}
	}
	
}
