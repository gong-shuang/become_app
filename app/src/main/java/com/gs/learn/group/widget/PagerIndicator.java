package com.gs.learn.group.widget;

import com.gs.learn.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

public class PagerIndicator extends LinearLayout {
	private static final String TAG = "PagerIndicator";
	private Context mContext;
	private int mCount = 5;
	private int mPad = 30;
	private int mSeq = 0;
	private float mRatio = 0.0f;
	private Paint mPaint;
	private Bitmap mBackImage;
	private Bitmap mForeImage;

	public PagerIndicator(Context context) {
		this(context, null);
	}

	public PagerIndicator(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		init();
	}
	
	private void init() {
		mPaint = new Paint();
		mBackImage = BitmapFactory.decodeResource(getResources(), R.drawable.icon_point_n);
		mForeImage = BitmapFactory.decodeResource(getResources(), R.drawable.icon_point_c);
	}
	
	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		int left = (getMeasuredWidth() - mCount*mPad) / 2;
		for (int i=0; i<mCount; i++) {
			canvas.drawBitmap(mBackImage, left+i*mPad, 0, mPaint);
		}
		canvas.drawBitmap(mForeImage, left+(mSeq+mRatio)*mPad, 0, mPaint);
	}

	public void setCount(int count, int pad) {
		mCount = count;
		mPad = pad;
		invalidate();
	}
	
	public void setCurrent(int seq, float ratio) {
		mSeq = seq;
		mRatio = ratio;
		invalidate();
	}

}
