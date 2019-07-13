package com.gs.learn.custom;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RemoteViews;
import com.gs.learn.R;
/**
 * Created by ouyangshen on 2016/10/14.
 */
@SuppressLint("NewApi")
public class NotifyCustomActivity extends AppCompatActivity implements OnClickListener {

	private EditText et_song;
	private String PAUSE_EVENT = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notify_custom);
		et_song = (EditText) findViewById(R.id.et_song);
		findViewById(R.id.btn_send_custom).setOnClickListener(this);
		PAUSE_EVENT = getResources().getString(R.string.pause_event_custom);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_send_custom) {
			Notification notify = getNotify(this, PAUSE_EVENT, 
					et_song.getText().toString(), true, 50, SystemClock.elapsedRealtime());
			NotificationManager notifyMgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
			notifyMgr.notify(R.string.app_name, notify);
		}
	}

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
	
}
