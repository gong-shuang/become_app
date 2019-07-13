package com.gs.learn.custom.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AbsoluteLayout;

@SuppressWarnings("deprecation")
public class OffsetLayout extends AbsoluteLayout {
	
	private final static String TAG = "OffsetLayout";
	private int mOffsetHorizontal = 0;
	private int mOffsetVertical = 0;

	public OffsetLayout(Context context) {
		super(context);
	}

	public OffsetLayout(Context context,AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
        		int new_left = (r-1)/2-child.getMeasuredWidth()/2+mOffsetHorizontal;
        		int new_top = (b-t)/2-child.getMeasuredHeight()/2+mOffsetVertical;
                child.layout(new_left, new_top, 
                		new_left+child.getMeasuredWidth(), new_top+child.getMeasuredHeight());
            }
        }
	}
	
	public void setOffsetHorizontal(int offset) {
		mOffsetHorizontal = offset;
		mOffsetVertical = 0;
		requestLayout();
	}

	public void setOffsetVertical(int offset) {
		mOffsetHorizontal = 0;
		mOffsetVertical = offset;
		requestLayout();
	}
	
}
