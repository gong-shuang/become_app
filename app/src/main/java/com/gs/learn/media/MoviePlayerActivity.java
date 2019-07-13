package com.gs.learn.media;

import java.util.Map;

import com.aqi00.lib.dialog.FileSelectFragment;
import com.aqi00.lib.dialog.FileSelectFragment.FileSelectCallbacks;
import com.gs.learn.media.widget.MovieView;
import com.gs.learn.media.widget.VideoController;
import com.gs.learn.media.widget.VideoController.OnSeekChangeListener;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/12/4.
 */
public class MoviePlayerActivity extends AppCompatActivity implements
		OnClickListener, FileSelectCallbacks, OnSeekChangeListener {
	private static final String TAG = "MoviePlayerActivity";
	private MovieView mv_content;
	private TextView tv_open;
	private RelativeLayout rl_top;
	private VideoController vc_play;
	private Handler mHandler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_movie_player);
		mv_content = (MovieView) findViewById(R.id.mv_content);
		vc_play = (VideoController) findViewById(R.id.vc_play);
		tv_open = (TextView) findViewById(R.id.tv_open);
		rl_top = (RelativeLayout) findViewById(R.id.rl_top);
		mv_content.prepare(rl_top, vc_play);
		tv_open.setOnClickListener(this);
		vc_play.setOnSeekChangeListener(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mHandler.removeCallbacksAndMessages(null);
	}

	private void playVideo(String video_path) {
		mv_content.setVideoPath(video_path);
		mv_content.requestFocus();
		mv_content.setOnPreparedListener(new OnPreparedListener() {
			@Override
			public void onPrepared(MediaPlayer mp) {
				mv_content.begin(mp);
				vc_play.setVideoView(mv_content);

				mHandler.removeCallbacks(mHide);
				mHandler.postDelayed(mHide, MovieView.HIDE_TIME);
				mHandler.post(mRefresh);
			}
		});
		mv_content.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				mv_content.end(mp);
				vc_play.setCurrentTime(0, 0);
			}
		});
		mv_content.setOnTouchListener(mv_content);
		mv_content.setOnKeyListener(mv_content);
	}

	private Runnable mHide = new Runnable() {
		@Override
		public void run() {
			mv_content.showOrHide();
		}
	};

	private Runnable mRefresh = new Runnable() {
		@Override
		public void run() {
			if (mv_content.isPlaying()) {
				vc_play.setCurrentTime(mv_content.getCurrentPosition(),
						mv_content.getBufferPercentage());
			}
			mHandler.postDelayed(this, 500);
		}
	};

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.tv_open) {
			FileSelectFragment.show(this, new String[] { "mp4" }, null);
		}
	}

	@Override
	public void onConfirmSelect(String absolutePath, String fileName, Map<String, Object> map_param) {
		String file_path = absolutePath + "/" + fileName;
		Log.d(TAG, "file_path=" + file_path);
		vc_play.enableController();
		playVideo(file_path);
	}

	@Override
	public boolean isFileValid(String absolutePath, String fileName, Map<String, Object> map_param) {
		return true;
	}

	@Override
	public void onStartSeek() {
		mHandler.removeCallbacks(mHide);
	}

	@Override
	public void onStopSeek() {
		mHandler.postDelayed(mHide, MovieView.HIDE_TIME);
	}

}
