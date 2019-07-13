package com.gs.learn.event.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class NotDispatchLayout extends LinearLayout {

	public NotDispatchLayout(Context context) {
		super(context);
	}

	public NotDispatchLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (mListener != null) {
			mListener.onNotDispatch();
		}
		// 一般容器默认返回true，即允许分发给下级
		return false;
	}

	private NotDispatchListener mListener;
	public void setNotDispatchListener(NotDispatchListener listener) {
		mListener = listener;
	}

	public static interface NotDispatchListener {
		public abstract void onNotDispatch();
	}

}
