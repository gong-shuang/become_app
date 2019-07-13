package com.gs.learn.media;

import java.util.Map;

import com.aqi00.lib.dialog.FileSelectFragment;
import com.aqi00.lib.dialog.FileSelectFragment.FileSelectCallbacks;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.VideoView;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/12/4.
 */
public class VideoViewActivity extends AppCompatActivity implements
		OnClickListener, FileSelectCallbacks, OnSeekBarChangeListener {
	private static final String TAG = "VideoViewActivity";
	private VideoView vv_play;
	private SeekBar sb_play;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video_view);
		findViewById(R.id.btn_open).setOnClickListener(this);
		vv_play = (VideoView) findViewById(R.id.vv_play);
		sb_play = (SeekBar) findViewById(R.id.sb_play);
		sb_play.setOnSeekBarChangeListener(this);
		sb_play.setEnabled(false);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_open) {
			FileSelectFragment.show(this, new String[] { "mp4" }, null);
		}
	}

	@Override
	public void onConfirmSelect(String absolutePath, String fileName, Map<String, Object> map_param) {
		String file_path = absolutePath + "/" + fileName;
		Log.d(TAG, "file_path=" + file_path);
		vv_play.setVideoPath(file_path);
		vv_play.requestFocus();
		vv_play.start();
		sb_play.setEnabled(true);
		mHandler.post(mRefresh);
	}

	@Override
	public boolean isFileValid(String absolutePath, String fileName, Map<String, Object> map_param) {
		return true;
	}
	
	private Handler mHandler = new Handler();
	private Runnable mRefresh = new Runnable() {
		@Override
		public void run() {
			sb_play.setProgress(100 * vv_play.getCurrentPosition()/vv_play.getDuration());
			mHandler.postDelayed(this, 500);
		}
	};

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		int pos = seekBar.getProgress() * vv_play.getDuration() / 100;
		vv_play.seekTo(pos);
	}

}
