package com.gs.learn.custom.service;

import com.gs.learn.custom.CustomMainActivity;
import com.gs.learn.R;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * Created by ouyangshen on 2016/10/14.
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class MusicService extends Service {
	private static final String TAG = "MusicService";
	private final IBinder mBinder = new LocalBinder();

	public class LocalBinder extends Binder {
		public MusicService getService() {
			return MusicService.this;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		Log.d(TAG, "onBind");
		return mBinder;
	}

	private String mSong;
	private String PAUSE_EVENT = "";
	private boolean bPlay = true;

	private long mBaseTime;
	private long mPauseTime = 0;
	private int mProcess = 0;
	private Handler mHandler = new Handler();
	private Runnable mPlay = new Runnable() {
		@Override
		public void run() {
			if (bPlay == true) {
				if (mProcess < 100) {
					mProcess += 2;
				} else {
					mProcess = 0;
				}
				mHandler.postDelayed(this, 1000);
			}
			Notification notify = getNotify(MusicService.this, PAUSE_EVENT, mSong, bPlay, mProcess, mBaseTime);
			startForeground(2, notify);
		}
	};
	
	private Notification getNotify(Context ctx, String event, String song, boolean isPlay, int progress, long time) {
		Intent pIntent = new Intent(event);
		PendingIntent nIntent = PendingIntent.getBroadcast(
				ctx, R.string.app_name, pIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		RemoteViews notify_music = new RemoteViews(ctx.getPackageName(), R.layout.notify_music);
		if (isPlay == true) {
			notify_music.setTextViewText(R.id.btn_play, "暂停");
			notify_music.setTextViewText(R.id.tv_play, song+"正在播放");
			notify_music.setChronometer(R.id.chr_play, time, "%s", true);
		} else {
			notify_music.setTextViewText(R.id.btn_play, "继续");
			notify_music.setTextViewText(R.id.tv_play, song+"暂停播放");
			notify_music.setChronometer(R.id.chr_play, time, "%s", false);
		}
		notify_music.setProgressBar(R.id.pb_play, 100, progress, false);
		notify_music.setOnClickPendingIntent(R.id.btn_play, nIntent);
		Intent intent = new Intent(ctx, CustomMainActivity.class);
		PendingIntent contentIntent = PendingIntent.getActivity(ctx,
				R.string.app_name, intent, PendingIntent.FLAG_UPDATE_CURRENT);

		Notification.Builder builder = new Notification.Builder(ctx);
		builder.setContentIntent(contentIntent)
				.setContent(notify_music)
				.setTicker(song)
				.setSmallIcon(R.drawable.tt_s);
		Notification notify = builder.build();
		return notify;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startid) {
		mBaseTime = SystemClock.elapsedRealtime();
		bPlay = intent.getBooleanExtra("is_play", true);
		mSong = intent.getStringExtra("song");
		Log.d(TAG, "bPlay="+bPlay+", mSong="+mSong);
		mHandler.postDelayed(mPlay, 200);
		return START_STICKY;
	}
	
	@Override
	public void onCreate() {
		PAUSE_EVENT = getResources().getString(R.string.pause_event_custom);
		pauseReceiver = new PauseReceiver();
		IntentFilter filter = new IntentFilter(PAUSE_EVENT);
		registerReceiver(pauseReceiver, filter);
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		unregisterReceiver(pauseReceiver);
		super.onDestroy();
	}

	private PauseReceiver pauseReceiver;
	public class PauseReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent != null) {
				bPlay = !bPlay;
				if (bPlay == true) {
					mHandler.postDelayed(mPlay, 200);
					if (mPauseTime > 0) {
						long gap = SystemClock.elapsedRealtime() - mPauseTime;
						mBaseTime += gap;
					}
				} else {
					mPauseTime = SystemClock.elapsedRealtime();
				}
			}
		}
	}

}
