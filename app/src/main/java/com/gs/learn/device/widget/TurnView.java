package com.gs.learn.device.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

public class TurnView extends View {
	private Paint mPaint;
	private RectF mRectF;
	private int mBeginAngle = 0;
	private boolean bRunning = false;

    private Handler mHandler = new Handler();

	public TurnView(Context context) {
		this(context, null);
	}

	public TurnView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setColor(Color.RED);
		mPaint.setStrokeWidth(10);
		mPaint.setStyle(Style.FILL);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int diameter = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
		mRectF = new RectF(getPaddingLeft(), getPaddingTop(), 
				getPaddingLeft()+diameter, getPaddingTop()+diameter);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawArc(mRectF, mBeginAngle, 45, true, mPaint);
	}

    public void start(){
    	bRunning = true;
        mHandler.postDelayed(drawRunnable, 0);
    }

    public void stop(){
    	bRunning = false;
    }

    private Runnable drawRunnable = new Runnable() {
        @Override
        public void run() {
        	if (bRunning == true) {
                mHandler.postDelayed(this, 70);
                mBeginAngle += 3;
                invalidate();
        	} else {
                mHandler.removeCallbacks(this);
        	}
        }
    };
}
