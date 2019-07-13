package com.gs.learn.animation.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Scroller;
import android.widget.TextView;

public class ScrollTextView extends TextView {
	private Scroller mScroller;

	public ScrollTextView(Context context) {
		this(context, null);
	}

	public ScrollTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mScroller = new Scroller(context);
	}

	public void smoothScrollTo(int fx, int fy) {
		int dx = fx - mScroller.getFinalX();
		int dy = fy - mScroller.getFinalY();
		smoothScrollBy(dx, dy);
	}

	public void smoothScrollBy(int dx, int dy) {
		// 设置滚动偏移量，注意正数是往左滚往上滚，负数才是往右滚往下滚
		mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), -dx, -dy);
		// 调用invalidate()才能保证computeScroll()会被调用
		invalidate();
	}

	@Override
	public void computeScroll() {
		// 先判断mScroller滚动是否完成
		if (mScroller.computeScrollOffset()) {
			// 这里调用View的scrollTo()完成实际的滚动
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			// 刷新页面
			postInvalidate();
		}
		super.computeScroll();
	}
}
