package com.gs.learn.media;

import java.util.ArrayList;

import com.gs.learn.MainApplication;
import com.gs.learn.media.bean.LrcContent;
import com.gs.learn.media.bean.MusicInfo;
import com.gs.learn.media.service.MusicService;
import com.gs.learn.media.task.AudioPlayTask;
import com.gs.learn.media.util.LyricsLoader;
import com.gs.learn.media.util.MeasureUtil;
import com.gs.learn.media.util.Utils;
import com.gs.learn.media.widget.AudioController;
import com.gs.learn.media.widget.VolumeDialog;
import com.gs.learn.media.widget.AudioController.OnSeekChangeListener;
import com.gs.learn.media.widget.VolumeDialog.VolumeAdjustListener;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/12/4.
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
public class MusicDetailActivity extends AppCompatActivity implements 
		AnimatorListener, OnSeekChangeListener, VolumeAdjustListener {
	private static final String TAG = "MusicDetailActivity";
	private TextView tv_title;
	private TextView tv_artist;
	private TextView tv_music;
	private MusicInfo mMusic;
	private AudioController ac_play;
	private LyricsLoader mLoader;
	private ArrayList<LrcContent> mLrcList;
	private AudioManager mAudioMgr;
	private VolumeDialog dialog;
	private MainApplication app;
	private Handler mHandler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_music_detail);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_artist = (TextView) findViewById(R.id.tv_artist);
		tv_music = (TextView) findViewById(R.id.tv_music);
		ac_play = (AudioController) findViewById(R.id.ac_play);
		ac_play.setOnSeekChangeListener(this);
		mMusic = getIntent().getParcelableExtra("music");
		tv_title.setText(mMusic.getTitle());
		tv_artist.setText(mMusic.getArtist());
		mLoader = LyricsLoader.getInstance(mMusic.getUrl());
		mLrcList = mLoader.getLrcList();
		mLineHeight = Math.round(MeasureUtil.getTextHeight("好", tv_music.getTextSize()));
		mAudioMgr = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		app = MainApplication.getInstance();
		playMusic();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mHandler.removeCallbacksAndMessages(null);
	}

	private int frequence = 8000;
	private int channelConfig = AudioFormat.CHANNEL_IN_STEREO;
	private int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
	// 播放歌曲
	private void playMusic() {
		Log.d(TAG, "song="+mMusic.getTitle());
		if (Utils.getExtendName(mMusic.getUrl()).equals("pcm")) {
			ac_play.setVisibility(View.GONE);
			AudioPlayTask playTask = new AudioPlayTask();
			//playTask.setOnPlayListener(this);
			playTask.execute(mMusic.getUrl(), ""+frequence, ""+channelConfig, ""+audioFormat);
		} else {
			//以下处理歌词
			if (mLoader.getLrcList()!=null && mLrcList.size()>0) {
				mLrcStr = "";
				for (int i=0; i<mLrcList.size(); i++) {
					LrcContent item = mLrcList.get(i);
					mLrcStr = mLrcStr + item.getLrcStr() + "\n";
				}
				tv_music.setText(mLrcStr);
				tv_music.setAnimation(AnimationUtils.loadAnimation(this,R.anim.alpha_music));
			}
			//播放音乐
	    	if (app.mFilePath==null || !app.mFilePath.equals(mMusic.getUrl())) {
	    		Intent intent = new Intent(this, MusicService.class);
	    		intent.putExtra("is_play", true);
	    		intent.putExtra("music", mMusic);
	    		startService(intent);
				mHandler.postDelayed(mRefreshLrc, 150);
	    	} else {
	    		onMusicSeek(0, app.mMediaPlayer.getCurrentPosition());
	    	}
			mHandler.postDelayed(mRefreshCtrl, 100);
		}
    }
    
	//刷新进度条
	private Runnable mRefreshCtrl = new Runnable() {
		@Override
		public void run() {
			ac_play.setCurrentTime(app.mMediaPlayer.getCurrentPosition(), 0);
			if (app.mMediaPlayer.getCurrentPosition() >= app.mMediaPlayer.getDuration()) {
				ac_play.setCurrentTime(0, 0);
			}
			mHandler.postDelayed(this, 500);
		}
	};

	private int mCount = 0;
	private float mCurrentHeight = 0;
	private float mLineHeight = 0;
	//计算每行歌词的动画
	private Runnable mRefreshLrc = new Runnable() {
		@Override
		public void run() {
			if (mLoader.getLrcList()==null || mLrcList.size()<=0) {
				return;
			}
			int offset = mLrcList.get(mCount).getLrcTime()
					- ((mCount==0)?0:mLrcList.get(mCount-1).getLrcTime()) - 50;
			if (offset <= 0) {
				return;
			}
			startAnimation(mCurrentHeight - mLineHeight, offset);
			Log.d(TAG, "mLineHeight="+mLineHeight+",mCurrentHeight="+mCurrentHeight+",getHeight="+tv_music.getHeight());
		}
	};

	private int mPrePos = -1, mNextPos = 0;
	private String mLrcStr;
	private ObjectAnimator animTranY;
	//歌词滚动动画
	public void startAnimation(float aimHeight, int offset) {
		Log.d(TAG, "mCurrentHeight="+mCurrentHeight+", aimHeight="+aimHeight);
		animTranY = ObjectAnimator.ofFloat(tv_music, "translationY", mCurrentHeight, aimHeight);
		animTranY.setDuration(offset);
		animTranY.setRepeatCount(0);
		animTranY.addListener(this);
		animTranY.start();
		mCurrentHeight = aimHeight;
		if (app.mMediaPlayer.isPlaying() != true) {
			mHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					animTranY.pause();
				}
			}, offset+100);
		}
	}

	@Override
	public void onAnimationStart(Animator animation) {
	}

	@Override
	public void onAnimationEnd(Animator animation) {
		if (mCount < mLrcList.size()) {
			mNextPos = mLrcStr.indexOf("\n", mPrePos+1);
			SpannableString spanText = new SpannableString(mLrcStr);
			spanText.setSpan(new ForegroundColorSpan(Color.RED), mPrePos+1, 
					mNextPos>0?mNextPos:mLrcStr.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			mCount++;
			tv_music.setText(spanText);
			if (mNextPos > 0 && mNextPos < mLrcStr.length()-1) {
				mPrePos = mLrcStr.indexOf("\n", mNextPos);
				mHandler.postDelayed(mRefreshLrc, 50);
			}
		}
	}

	@Override
	public void onAnimationCancel(Animator animation) {
	}

	@Override
	public void onAnimationRepeat(Animator animation) {
	}

	//音乐控制条的拖动操作
	@Override
	public void onMusicSeek(int current, int seekto) {
		Log.d(TAG, "current="+current+", seekto="+seekto);
		if (animTranY != null) {
			animTranY.cancel();
		}
		mHandler.removeCallbacks(mRefreshLrc);
		int i;
		for (i=0; i<mLrcList.size(); i++) {
			LrcContent item = mLrcList.get(i);
			if (item.getLrcTime() > seekto) {
				break;
			}
		}
		mCount = i;
		mPrePos = -1;
		mNextPos = 0;
		if (mCount > 0) {
			for (int j = 0; j < mCount; j++) {
				mNextPos = mLrcStr.indexOf("\n", mPrePos + 1);
				mPrePos = mLrcStr.indexOf("\n", mNextPos);
			}
		}
		startAnimation(-mLineHeight*i, 100);
	}

	@Override
	public void onMusicPause() {
		animTranY.pause();
	}

	@Override
	public void onMusicResume() {
		animTranY.resume();
	}
	
	//音量调节对话框
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
			showVolumeDialog(AudioManager.ADJUST_RAISE);
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
			showVolumeDialog(AudioManager.ADJUST_LOWER);
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
		}
		return false;
	}

	private void showVolumeDialog(int direction) {
		if (dialog==null || dialog.isShowing()!=true) {
			dialog = new VolumeDialog(this);
			dialog.setVolumeAdjustListener(this);
			dialog.show();
		}
		dialog.adjustVolume(direction, true);
		onVolumeAdjust(mAudioMgr.getStreamVolume(AudioManager.STREAM_MUSIC));
	}

	@Override
	public void onVolumeAdjust(int volume) {
	}
	
}
