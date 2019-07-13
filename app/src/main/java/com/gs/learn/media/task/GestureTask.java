package com.gs.learn.media.task;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class GestureTask implements GestureDetector.OnGestureListener {
	private Context mContext;
	private float mFlipGap = 20f;

	public GestureTask(Context context) {
		mContext = context;
	}

	@Override
	public final boolean onDown(MotionEvent event) {
		return true;
	}

	@Override
	public final boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		if (e1.getX() - e2.getX() > mFlipGap) {
			if (mListener != null) {
				mListener.gotoNext();
			}
		}
		if (e1.getX() - e2.getX() < -mFlipGap) {
			if (mListener != null) {
				mListener.gotoPre();
			}
		}
		return true;
	}

	@Override
	public final void onLongPress(MotionEvent event) {
	}

	@Override
	public final boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		return false;
	}

	@Override
	public final void onShowPress(MotionEvent event) {
	}

	@Override
	public boolean onSingleTapUp(MotionEvent event) {
		return false;
	}

	private GestureCallback mListener;
	public void setGestureCallback(GestureCallback listener) {
		mListener = listener;
	}

	public static interface GestureCallback {
		public abstract void gotoNext();
		public abstract void gotoPre();
	}

}
