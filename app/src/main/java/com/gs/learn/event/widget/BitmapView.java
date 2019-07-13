package com.gs.learn.event.widget;

import com.gs.learn.event.util.BitmapUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class BitmapView extends View {
	private static final String TAG = "BitmapView";
	private float mScaleRatio = 1.0f;
	private float mRotateDegree = 0;
	private Bitmap mBitmap;
	private int mBitmapWidth;
	private int mBitmapHeight;
	private int mOffsetX = 0, mOffsetY = 0;
	private int mLastOffsetX = 0, mLastOffsetY = 0;

	public BitmapView(Context context) {
		this(context, null);
	}

	public BitmapView(Context context,AttributeSet attrs) {
		super(context, attrs);
	}

	public void setImageBitmap(Bitmap bitmap) {
		mBitmap = bitmap;
		mBitmapWidth = mBitmap.getWidth();
		mBitmapHeight = mBitmap.getHeight();
		invalidate();
	}
	
	public void setScaleRatio(float ratio, boolean bReset) {
		if (bReset == true) {
			mScaleRatio = ratio;
		} else {
			mScaleRatio = mScaleRatio*ratio;
		}
		invalidate();
	}
	
	public void setRotateDegree(int degree, boolean bReset) {
		if (bReset == true) {
			mRotateDegree = degree;
		} else {
			mRotateDegree = mRotateDegree+degree;
		}
		invalidate();
	}
	
	public void setOffset(int offsetX, int offsetY, boolean bReset) {
		if (bReset == true) {
			mLastOffsetX = mOffsetX;
			mLastOffsetY = mOffsetY;
		}
		mOffsetX = mLastOffsetX+offsetX;
		mOffsetY = mLastOffsetY+offsetY;
		invalidate();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (mBitmap == null) {
			return;
		}
		int width = getMeasuredWidth();
		int height = getMeasuredHeight();
		int bm_width = (int) (mBitmapWidth*mScaleRatio);
		int bm_height = (int) (mBitmapHeight*mScaleRatio);
		Bitmap bm1 = Bitmap.createScaledBitmap(mBitmap, bm_width, bm_height, false);
		Bitmap new_bm = BitmapUtil.getRotateBitmap(bm1, mRotateDegree);
		canvas.drawBitmap(new_bm, (width-bm_width)/2+mOffsetX, 
				(height-bm_height)/2+mOffsetY, new Paint());
	}
	
}
