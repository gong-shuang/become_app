package com.gs.learn.animation.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

public class ShutterView extends View {
	private final static String TAG = "ShutterView";
	private Context mContext;
	private Paint mPaint;
	private int mOriention = LinearLayout.HORIZONTAL;
	private int mLeafCount = 10;
	private Mode mMode = Mode.DST_IN;
	private Bitmap mBitmap;
	private int mRatio = 0;

	public ShutterView(Context context) {
		this(context, null);
	}

	public ShutterView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		mPaint = new Paint();
	}

	public void setOriention(int oriention) {
		mOriention = oriention;
	}

	public void setLeafCount(int leaf_count) {
		mLeafCount = leaf_count;
	}

	public void setMode(Mode mode) {
		mMode = mode;
	}

	public void setImageBitmap(Bitmap bitmap) {
		mBitmap = bitmap;
	}

	public void setRatio(int ratio) {
		mRatio = ratio;
		invalidate();
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (mBitmap == null) {
			return;
		}
		int width = getMeasuredWidth();
		int height = getMeasuredHeight();
		canvas.drawColor(Color.TRANSPARENT);
		// 创建图片的遮罩
		Bitmap mask = Bitmap.createBitmap(width, height, mBitmap.getConfig());
		Canvas canvasMask = new Canvas(mask);
		for (int i = 0; i < mLeafCount; i++) {
			if (mOriention == LinearLayout.HORIZONTAL) {
				int column_width = (int) Math.ceil(width*1f / mLeafCount);
				int left = column_width * i;
				int right = left + column_width * mRatio / 100;
				canvasMask.drawRect(left, 0, right, height, mPaint);
			} else {
				int row_height = (int) Math.ceil(height*1f / mLeafCount);
				int top = row_height * i;
				int bottom = top + row_height * mRatio / 100;
				canvasMask.drawRect(0, top, width, bottom, mPaint);
			}
		}
		// 设置离屏缓存
		int saveLayer = canvas.saveLayer(0, 0, width, height, null, Canvas.ALL_SAVE_FLAG);
		// 绘制目标图像
		Rect src = new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
		Rect dst = new Rect(0, 0, width, width*mBitmap.getHeight()/mBitmap.getWidth());
		canvas.drawBitmap(mBitmap, src, dst, mPaint);
		// 设置混合模式 （只在源图像和目标图像相交的地方绘制目标图像）
		mPaint.setXfermode(new PorterDuffXfermode(mMode));
		// 再绘制src源图
		canvas.drawBitmap(mask, 0, 0, mPaint);
		// 还原混合模式
		mPaint.setXfermode(null);
		// 还原画布
		canvas.restoreToCount(saveLayer);
	}

}
