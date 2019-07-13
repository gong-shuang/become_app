package com.gs.learn.event.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class InterceptLayout extends LinearLayout {

	public InterceptLayout(Context context) {
		super(context);
	}

	public InterceptLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (mListener != null) {
			mListener.onIntercept();
		}
		// 一般容器默认返回false，即不拦截。但滚动视图ScrollView会拦截
		return true;
	}
	
	private InterceptListener mListener;
	public void setInterceptListener(InterceptListener listener) {
		mListener = listener;
	}

	public static interface InterceptListener {
		public abstract void onIntercept();
	}

}
