package com.gs.learn.device.widget;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.gs.learn.R;

public class AudioPlayer extends LinearLayout implements 
		OnCompletionListener, OnCheckedChangeListener {
	private static final String TAG = "AudioPlayer";
	private MediaPlayer mMediaPlayer;

	private ProgressBar pb_play;
	private CheckBox ck_play;
	private Timer mTimer; // 计时器
	private String mAudioPath;
	private boolean bFinish = true;

	public AudioPlayer(Context context) {
		this(context, null);
	}

	public AudioPlayer(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public AudioPlayer(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		LayoutInflater.from(context).inflate(R.layout.audio_player, this);
		pb_play = (ProgressBar) findViewById(R.id.pb_play);
		ck_play = (CheckBox) findViewById(R.id.ck_play);
		ck_play.setOnCheckedChangeListener(this);
	}

	public void init(String path) {
		mAudioPath = path;
		ck_play.setEnabled(true);
		ck_play.setTextColor(Color.BLACK);
		mMediaPlayer = new MediaPlayer();
		mMediaPlayer.setOnCompletionListener(this);
	}

	private void play() {
		try {
			mMediaPlayer.reset();
			// mMediaPlayer.setVolume(0.5f, 0.5f); //设置音量，可选
			mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			Log.d(TAG, "audio path = " + mAudioPath);
			// 录制完毕要等一秒钟再setDataSource，否则会报异常“java.io.IOException:
			// setDataSourceFD failed”
			mMediaPlayer.setDataSource(mAudioPath);
			mMediaPlayer.prepare();
			mMediaPlayer.start();
			pb_play.setMax(mMediaPlayer.getDuration());
			mTimer = new Timer();
			mTimer.schedule(new TimerTask() {
				@Override
				public void run() {
					pb_play.setProgress(mMediaPlayer.getCurrentPosition());
				}
			}, 0, 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		bFinish = true;
		ck_play.setChecked(false);
		if (mTimer != null) {
			mTimer.cancel();
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (buttonView.getId() == R.id.ck_play) {
			if (isChecked == true) {
				ck_play.setText("暂停播放");
				if (bFinish == true) {
					play();
				} else {
					mMediaPlayer.start();
				}
				bFinish = false;
			} else {
				ck_play.setText("开始播放");
				mMediaPlayer.pause();
			}
		}
	}

}
