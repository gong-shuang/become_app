package com.gs.learn.event.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class CustomScrollView extends ScrollView {
	private float mOffsetX, mOffsetY;
	private float mLastPosX, mLastPosY;

	public CustomScrollView(Context context) {
		this(context, null);
	}

	public CustomScrollView(Context context, AttributeSet attr) {
		super(context, attr);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		boolean result = false;
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mOffsetX = 0.0F;
			mOffsetY = 0.0F;
			mLastPosX = event.getX();
			mLastPosY = event.getY();
			result = super.onInterceptTouchEvent(event); // false传给子控件
			break;
		default:
			float thisPosX = event.getX();
			float thisPosY = event.getY();
			mOffsetX += Math.abs(thisPosX - mLastPosX); // x轴偏差
			mOffsetY += Math.abs(thisPosY - mLastPosY); // y轴偏差
			mLastPosX = thisPosX;
			mLastPosY = thisPosY;
			if (mOffsetX < 3 && mOffsetY < 3) {
				result = false; // false传给子控件（点击事件）
			} else if (mOffsetX < mOffsetY) {
				result = true; // true不传给子控件（垂直滑动）
			} else {
				result = false; // false传给子控件
			}
			break;
		}
		return result;
	}
}
