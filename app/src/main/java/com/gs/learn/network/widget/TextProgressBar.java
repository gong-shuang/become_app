package com.gs.learn.network.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ProgressBar;

public class TextProgressBar extends ProgressBar {
	
	private String mProgressText = "";
	private Paint mPaint;
	private int mTextColor = Color.WHITE;
	private int mTextSize = 30;

	public TextProgressBar(Context context) {
		this(context, null);
	}

	public TextProgressBar(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public TextProgressBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initPaint();
	}
	
	private void initPaint() {
		mPaint = new Paint();
		mPaint.setColor(mTextColor);
		mPaint.setTextSize(mTextSize);
	}
	
	public void setProgressText(String text) {
		mProgressText = text;
	}
	
	public String getProgressText() {
		return mProgressText;
	}

	public void setTextColor(int color) {
		mTextColor = color;
	}
	
	public int getTextColor() {
		return mTextColor;
	}

	public void setTextSize(int size) {
		mTextSize = size;
	}
	
	public int getTextSize() {
		return mTextSize;
	}

	@Override
	protected synchronized void onDraw(Canvas canvas) {
	    super.onDraw(canvas);
	    Rect rect = new Rect();
	    mPaint.getTextBounds(mProgressText, 0, mProgressText.length(), rect);
	    int x = (getWidth() / 2) - rect.centerX();
	    int y = (getHeight() / 2) - rect.centerY();
	    canvas.drawText(mProgressText, x, y, this.mPaint);
	}
	
}
