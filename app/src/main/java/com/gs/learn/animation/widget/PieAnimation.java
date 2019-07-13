package com.gs.learn.animation.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class PieAnimation extends View {
	private final static String TAG = "PieAnimation";
	private Context mContext;
	private Paint mPaint;
	private int mEndAngle = 270;
	private int mInterval = 70;
	private int mIncrease = 3;
	private int mDrawingAngle = 0;
	private Handler mHandler = new Handler();
	private boolean mRunning = false;

	public PieAnimation(Context context) {
		this(context, null);
	}

	public PieAnimation(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setColor(Color.GREEN);
		mPaint.setStyle(Style.FILL);
	}

	public void start() {
		mDrawingAngle = 0;
		mRunning = true;
		mHandler.postDelayed(mRefresh, mInterval);
	}

	public boolean isRunning() {
		return mRunning;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (mRunning == true) {
			int width = getMeasuredWidth();
			int height = getMeasuredHeight();
			int diameter = Math.min(width, height);
			RectF rectf = new RectF((width - diameter) / 2, (height - diameter) / 2,
					(width + diameter) / 2, (height + diameter) / 2);
			canvas.drawArc(rectf, 0, mDrawingAngle, true, mPaint);
		}
	}

	private Runnable mRefresh = new Runnable() {
		@Override
		public void run() {
			mDrawingAngle += mIncrease;
			if (mDrawingAngle <= mEndAngle) {
				postInvalidate();
				mHandler.postDelayed(this, mInterval);
			} else {
				mRunning = false;
			}
		}
	};

}
