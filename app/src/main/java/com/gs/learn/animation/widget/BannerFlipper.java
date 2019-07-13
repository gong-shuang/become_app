package com.gs.learn.animation.widget;

import java.util.ArrayList;

import com.gs.learn.R;
import com.gs.learn.animation.util.Utils;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ViewFlipper;

public class BannerFlipper extends RelativeLayout {
	private static final String TAG = "BannerFlipper";
	private Context mContext;
	private ViewFlipper mFlipper;
	private RadioGroup mGroup;
	private int mCount;
	private LayoutInflater mInflater;
	private int dip_15;
	private GestureDetector mGesture;
	private float mFlipGap = 20f;

	public BannerFlipper(Context context) {
		this(context, null);
	}

	public BannerFlipper(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		init();
	}

	public void setImage(ArrayList<Integer> imageList) {
		for (int i = 0; i < imageList.size(); i++) {
			Integer imageID = ((Integer) imageList.get(i)).intValue();
			ImageView iv_item = new ImageView(mContext);
			iv_item.setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			iv_item.setScaleType(ImageView.ScaleType.FIT_XY);
			iv_item.setImageResource(imageID);
			mFlipper.addView(iv_item);
		}

		mCount = imageList.size();
		for (int i = 0; i < mCount; i++) {
			RadioButton radio = new RadioButton(mContext);
			radio.setLayoutParams(new RadioGroup.LayoutParams(dip_15, dip_15));
			radio.setGravity(Gravity.CENTER);
			radio.setButtonDrawable(R.drawable.indicator_selector);
			mGroup.addView(radio);
		}
		mFlipper.setDisplayedChild(mCount - 1);
		startFlip();
	}

	private void init() {
		mInflater = ((Activity) mContext).getLayoutInflater();
		View view = mInflater.inflate(R.layout.banner_flipper, null);
		mFlipper = (ViewFlipper) view.findViewById(R.id.banner_flipper);
		mGroup = (RadioGroup) view.findViewById(R.id.rg_indicator);
		addView(view);
		dip_15 = Utils.dip2px(mContext, 15);
		// 该手势的onSingleTapUp事件是点击时进入广告页
		mGesture = new GestureDetector(mContext, new BannerGestureListener());
	}

	public boolean dispatchTouchEvent(MotionEvent event) {
		mGesture.onTouchEvent(event);
		return true;
	}

	final class BannerGestureListener implements GestureDetector.OnGestureListener {

		@Override
		public final boolean onDown(MotionEvent event) {
			return true;
		}

		@Override
		public final boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			if (e1.getX() - e2.getX() > mFlipGap) {
				startFlip();
				return true;
			}
			if (e1.getX() - e2.getX() < -mFlipGap) {
				backFlip();
				return true;
			}
			return false;
		}

		@Override
		public final void onLongPress(MotionEvent event) {
		}

		@Override
		public final boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
			boolean result = false; // true表示要继续处理
			if (Math.abs(distanceY) < Math.abs(distanceX)) {
				BannerFlipper.this.getParent().requestDisallowInterceptTouchEvent(false);
				result = true;
			}
			return result;
		}

		@Override
		public final void onShowPress(MotionEvent event) {
		}

		@Override
		public boolean onSingleTapUp(MotionEvent event) {
			int position = mFlipper.getDisplayedChild();
			mListener.onBannerClick(position);
			return true;
		}

	}

	private void startFlip() {
		mFlipper.startFlipping();
		mFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.push_left_in));
		mFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.push_left_out));
		mFlipper.getOutAnimation().setAnimationListener(new BannerAnimationListener(this));
		mFlipper.showNext();
	}

	private void backFlip() {
		mFlipper.startFlipping();
		mFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.push_right_in));
		mFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.push_right_out));
		mFlipper.getOutAnimation().setAnimationListener(new BannerAnimationListener(this));
		mFlipper.showPrevious();
		mFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.push_left_in));
		mFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.push_left_out));
		mFlipper.getOutAnimation().setAnimationListener(new BannerAnimationListener(this));
	}

	private class BannerAnimationListener implements Animation.AnimationListener {
		private BannerAnimationListener(BannerFlipper bannerFlipper) {
		}

		@Override
		public final void onAnimationEnd(Animation paramAnimation) {
			int position = mFlipper.getDisplayedChild();
			((RadioButton) mGroup.getChildAt(position)).setChecked(true);
		}

		@Override
		public final void onAnimationRepeat(Animation paramAnimation) {
		}

		@Override
		public final void onAnimationStart(Animation paramAnimation) {
		}
	}

	private BannerClickListener mListener;
	public void setOnBannerListener(BannerClickListener listener) {
		mListener = listener;
	}

	public static interface BannerClickListener {
		public abstract void onBannerClick(int position);
	}

}
