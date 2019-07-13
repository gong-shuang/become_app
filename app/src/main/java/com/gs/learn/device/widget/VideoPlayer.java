package com.gs.learn.device.widget;

import java.util.Timer;
import java.util.TimerTask;

import com.gs.learn.R;

import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class VideoPlayer extends LinearLayout implements
		OnCompletionListener, OnCheckedChangeListener {
	private static final String TAG = "VideoPlayer";
	private MediaPlayer mMediaPlayer;
	
	private SurfaceView sv_play;
	private ProgressBar pb_play;
	private CheckBox ck_play;
	private Timer mTimer; // 计时器
	private String mVideoPath;
	private boolean bFinish = true;

	public VideoPlayer(Context context) {
		this(context, null);
	}

	public VideoPlayer(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public VideoPlayer(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		LayoutInflater.from(context).inflate(R.layout.video_player, this);
		sv_play = (SurfaceView) findViewById(R.id.sv_play);
		pb_play = (ProgressBar) findViewById(R.id.pb_play);
		ck_play = (CheckBox) findViewById(R.id.ck_play);
		ck_play.setOnCheckedChangeListener(this);
	}

	public void init(String path) {
		mVideoPath = path;
		ck_play.setEnabled(true);
		ck_play.setTextColor(Color.BLACK);
		mMediaPlayer = new MediaPlayer();
		mMediaPlayer.setOnCompletionListener(this);
	}

	private void play() {
		try {
			mMediaPlayer.reset();
			mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			Log.d(TAG, "video path = " + mVideoPath);
			// 录制完毕要等一秒钟再setDataSource，否则会报异常“java.io.IOException: setDataSourceFD failed”
			mMediaPlayer.setDataSource(mVideoPath);
			// 把视频画面输出到SurfaceView
			mMediaPlayer.setDisplay(sv_play.getHolder());
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
