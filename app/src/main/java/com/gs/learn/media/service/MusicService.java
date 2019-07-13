package com.gs.learn.media.service;

import com.gs.learn.MainApplication;
import com.gs.learn.media.MusicDetailActivity;
import com.gs.learn.R;
import com.gs.learn.media.bean.MusicInfo;
import com.gs.learn.media.util.Utils;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class MusicService extends Service {
	private static final String TAG = "MusicService";
	private MainApplication app;
	private String PAUSE_EVENT = "";
	private boolean bPlay = true;
	private MusicInfo mMusic;
	private Handler mHandler = new Handler();
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		if (app == null) {
			app = MainApplication.getInstance();
		}
		PAUSE_EVENT = getResources().getString(R.string.pause_event_media);
		pauseReceiver = new PauseReceiver();
		IntentFilter filter = new IntentFilter(PAUSE_EVENT);
		registerReceiver(pauseReceiver, filter);
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startid) {
		bPlay = intent.getBooleanExtra("is_play", true);
		mMusic = intent.getParcelableExtra("music");
		app.mSong = mMusic.getTitle();
		final String file_path = mMusic.getUrl();
		Log.d(TAG, "bPlay="+bPlay+", file_path="+file_path);
		if (bPlay && app.mFilePath!=null && !app.mFilePath.equals(file_path)) {
			//播放新歌之前，先停止旧歌
			stopPlayer();
			mHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					app.mFilePath = file_path;
					startPlayer();
				}
			}, 100);
		} else if (!bPlay) {
			app.mFilePath = file_path;
			stopPlayer();
		} else if (bPlay && !app.mMediaPlayer.isPlaying()) {
			app.mFilePath = file_path;
			startPlayer();
		}
		mHandler.postDelayed(mPlay, 200);
		return START_STICKY;
	}
	
	private void startPlayer() {
		try {
			app.mMediaPlayer.reset();
			//app.mMediaPlayer.setVolume(0.5f, 0.5f);  //设置音量，可选
			app.mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			app.mMediaPlayer.setDataSource(app.mFilePath);
			app.mMediaPlayer.prepare();
			app.mMediaPlayer.start();
		} catch (Exception e) {
            e.printStackTrace();
		}
	}

	private void stopPlayer() {
		if (app.mMediaPlayer.isPlaying()) {
			app.mMediaPlayer.stop();
		}
	}
	
	@Override
	public void onDestroy() {
		unregisterReceiver(pauseReceiver);
		super.onDestroy();
	}

	private Runnable mPlay = new Runnable() {
		@Override
		public void run() {
			int process = 0;
			if (bPlay == true && app.mMediaPlayer.isPlaying()) {
				process = app.mMediaPlayer.getCurrentPosition()*100 / app.mMediaPlayer.getDuration();
			}
			mHandler.postDelayed(this, 1000);
			Notification notify = getNotify(MusicService.this, PAUSE_EVENT, 
					app.mSong, bPlay, process, app.mMediaPlayer.getCurrentPosition());
			startForeground(2, notify);
		}
	};
	
	private Notification getNotify(Context ctx, String event, String song, boolean isPlay, int progress, long time) {
		Intent pIntent = new Intent(event);
		PendingIntent nIntent = PendingIntent.getBroadcast(
				ctx, R.string.app_name, pIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		RemoteViews notify_music = new RemoteViews(ctx.getPackageName(), R.layout.notify_music_media);
		if (isPlay == true && app.mMediaPlayer.isPlaying()==true) {
			notify_music.setImageViewResource(R.id.iv_play, R.drawable.btn_pause);
			notify_music.setTextViewText(R.id.tv_play, song+"正在播放");
			notify_music.setTextViewText(R.id.tv_time, Utils.formatTime(time));
		} else {
			notify_music.setImageViewResource(R.id.iv_play, R.drawable.btn_play);
			notify_music.setTextViewText(R.id.tv_play, song+"暂停播放");
			notify_music.setTextViewText(R.id.tv_time, Utils.formatTime(time));
		}
		notify_music.setProgressBar(R.id.pb_play, 100, progress, false);
		notify_music.setOnClickPendingIntent(R.id.iv_play, nIntent);
		Intent intent = new Intent(ctx, MusicDetailActivity.class);
		intent.putExtra("music", mMusic);
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
	
	private PauseReceiver pauseReceiver;
	public class PauseReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent != null) {
				bPlay = !bPlay;
			}
		}
	}

}
