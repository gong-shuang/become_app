package com.gs.learn.media.widget;

import com.gs.learn.R;
import com.gs.learn.media.util.DisplayUtil;
import com.gs.learn.media.util.Utils;
import com.gs.learn.media.widget.VolumeDialog.VolumeAdjustListener;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.VideoView;

//支持以下功能：自动全屏、调节音量、收缩控制栏、设置背景
@TargetApi(Build.VERSION_CODES.JELLY_BEAN)  //setBackground需要
public class MovieView extends VideoView implements 
		OnTouchListener, OnKeyListener, VolumeAdjustListener {
	private static final String TAG = "MovieView";
	private Context mContext;
	private int screenWidth, screenHeight;
	private int videoWidth, videoHeight;
	private int realWidth, realHeight;
	private int mXpos, mYpos, mOffset;
	// 自动隐藏顶部和底部View的时间
	public static final int HIDE_TIME = 5000;

	private View mTopView;
	private View mBottomView;
	private AudioManager mAudioMgr;
	private VolumeDialog dialog;
	private Handler mHandler = new Handler();

	public MovieView(Context context) {
		this(context, null);
	}

	public MovieView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MovieView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		screenWidth = DisplayUtil.getSreenWidth(mContext);
		screenHeight = DisplayUtil.getSreenHeight(mContext);
		mOffset = Utils.dip2px(mContext, 10);
		mAudioMgr = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = getDefaultSize(realWidth, widthMeasureSpec);
		int height = getDefaultSize(realHeight, heightMeasureSpec);
		if (realWidth > 0 && realHeight > 0) {
			if (realWidth * height > width * realHeight) {
				height = width * realHeight / realWidth;
			} else if (realWidth * height < width * realHeight) {
				width = height * realWidth / realHeight;
			}
		}
		setMeasuredDimension(width, height);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mXpos = (int) event.getX();
			mYpos = (int) event.getY();
			break;
		case MotionEvent.ACTION_UP:
			if (Math.abs(event.getX()-mXpos) < mOffset && 
					Math.abs(event.getY()-mYpos) < mOffset) {
				showOrHide();
			}
			break;
		default:
			break;
		}
		return true;
	}
	
	public void prepare(View topTiew, View bottomView) {
		mTopView = topTiew;
		mBottomView = bottomView;
		setBackgroundResource(R.drawable.video_bg1);
	}

	public void begin(MediaPlayer mp) {
		setBackground(null);
		if (mp != null) {
			videoWidth = mp.getVideoWidth();
			videoHeight = mp.getVideoHeight();
		}
		realWidth = videoWidth;
		realHeight = videoHeight;
		start();
	}

	public void end(MediaPlayer mp) {
		setBackgroundResource(R.drawable.video_bg3);
		realWidth = screenWidth;
		realHeight = screenHeight;
	}

	public void showOrHide() {
		if (mTopView==null || mBottomView==null) {
			return;
		}
		if (mTopView.getVisibility() == View.VISIBLE) {
			mTopView.clearAnimation();
			Animation animTop = AnimationUtils.loadAnimation(mContext, R.anim.leave_from_top);
			animTop.setAnimationListener(new AnimationImp() {
				@Override
				public void onAnimationEnd(Animation animation) {
					mTopView.setVisibility(View.GONE);
				}
			});
			mTopView.startAnimation(animTop);

			mBottomView.clearAnimation();
			Animation animBottom = AnimationUtils.loadAnimation(mContext, R.anim.leave_from_bottom);
			animBottom.setAnimationListener(new AnimationImp() {
				@Override
				public void onAnimationEnd(Animation animation) {
					mBottomView.setVisibility(View.GONE);
				}
			});
			mBottomView.startAnimation(animBottom);
		} else {
			mTopView.setVisibility(View.VISIBLE);
			mTopView.clearAnimation();
			Animation animTop = AnimationUtils.loadAnimation(mContext, R.anim.entry_from_top);
			mTopView.startAnimation(animTop);

			mBottomView.setVisibility(View.VISIBLE);
			mBottomView.clearAnimation();
			Animation animBottom = AnimationUtils.loadAnimation(mContext, R.anim.entry_from_bottom);
			mBottomView.startAnimation(animBottom);
			mHandler.removeCallbacks(mHide);
			mHandler.postDelayed(mHide, HIDE_TIME);
		}
	}

	private Runnable mHide = new Runnable() {
		@Override
		public void run() {
			showOrHide();
		}
	};

	private class AnimationImp implements AnimationListener {
		@Override
		public void onAnimationEnd(Animation animation) {
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
		}

		@Override
		public void onAnimationStart(Animation animation) {
		}
	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
			showVolumeDialog(AudioManager.ADJUST_RAISE);
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
			showVolumeDialog(AudioManager.ADJUST_LOWER);
			return true;
		}
		return false;
	}

	private void showVolumeDialog(int direction) {
		if (dialog==null || dialog.isShowing()!=true) {
			dialog = new VolumeDialog(mContext);
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
