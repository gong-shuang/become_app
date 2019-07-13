package com.gs.learn.animation.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

public class MosaicView extends View {
	private final static String TAG = "MosaicView";
	private Context mContext;
	private Paint mPaint;
	private int mOriention = LinearLayout.HORIZONTAL;
	private int mGridCount = 20;
	private int mOffset = 5;
	private float mFenmu = 100;
	private PorterDuff.Mode mMode = PorterDuff.Mode.DST_IN;
	private Bitmap mBitmap;
	private int mRatio = 0;

	public MosaicView(Context context) {
		this(context, null);
	}

	public MosaicView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		mPaint = new Paint();
	}

	public void setOriention(int oriention) {
		mOriention = oriention;
	}

	public void setGridCount(int grid_count) {
		mGridCount = grid_count;
	}

	public void setImageBitmap(Bitmap bitmap) {
		mBitmap = bitmap;
	}

	public void setOffset(int offset) {
		mOffset = offset;
	}

	public void setMode(PorterDuff.Mode mode) {
		mMode = mode;
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
		Bitmap mask = Bitmap.createBitmap(width, height, mBitmap.getConfig());
		Canvas canvasMask = new Canvas(mask);
		if (mOriention == LinearLayout.HORIZONTAL) {
			float grid_width = height / mGridCount;
			int column_count = (int) Math.ceil(width / grid_width);
			int total_count = mGridCount * column_count;
			int draw_count = 0;
			for (int i = 0; i < column_count; i++) {
				for (int j = 0; j < mGridCount; j++) {
					int now_ratio = (int) ((mGridCount * i + j) * mFenmu / total_count);
					if (now_ratio < mRatio - mOffset
							|| (now_ratio >= mRatio - mOffset && now_ratio < mRatio && 
								((j % 2 == 0 && i % 2 == 0) || (j % 2 == 1 && i % 2 == 1)))
							|| (now_ratio >= mRatio && now_ratio < mRatio + mOffset && 
								((j % 2 == 0 && i % 2 == 1) || (j % 2 == 1 && i % 2 == 0)))) {
							int left = (int) (grid_width * i);
							int top = (int) (grid_width * j);
							canvasMask.drawRect(left, top, left+grid_width, top+grid_width, mPaint);
						if (i < column_count && j < mGridCount) {
							draw_count++;
						}
						if (draw_count * mFenmu / total_count > mRatio) {
							break;
						}
					}
				}
				if (draw_count * mFenmu / total_count > mRatio) {
					break;
				}
			}
		} else {
			float grid_width = width / mGridCount;
			int row_count = (int) Math.ceil(height / grid_width);
			int total_count = mGridCount * row_count;
			int draw_count = 0;
			for (int i = 0; i < row_count; i++) {
				for (int j = 0; j < mGridCount; j++) {
					int now_ratio = (int) ((mGridCount * i + j) * mFenmu / total_count);
					if (now_ratio < mRatio - mOffset
							|| (now_ratio >= mRatio - mOffset && now_ratio < mRatio && 
								((j % 2 == 0 && i % 2 == 0) || (j % 2 == 1 && i % 2 == 1)))
							|| (now_ratio >= mRatio && now_ratio < mRatio + mOffset && 
								((j % 2 == 0 && i % 2 == 1) || (j % 2 == 1 && i % 2 == 0)))) {
							int left = (int) (grid_width * j);
							int top = (int) (grid_width * i);
							canvasMask.drawRect(left, top, left+grid_width, top+grid_width, mPaint);
						if (i < row_count && j < mGridCount) {
							draw_count++;
						}
						if (draw_count * mFenmu / total_count > mRatio) {
							break;
						}
					}
				}
				if (draw_count * mFenmu / total_count > mRatio) {
					break;
				}
			}
		}
		int saveLayer = canvas.saveLayer(0, 0, width, height, null, Canvas.ALL_SAVE_FLAG);
		Rect src = new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
		Rect dst = new Rect(0, 0, width, width*mBitmap.getHeight()/mBitmap.getWidth());
		canvas.drawBitmap(mBitmap, src, dst, mPaint);
		mPaint.setXfermode(new PorterDuffXfermode(mMode));
		canvas.drawBitmap(mask, 0, 0, mPaint);
		mPaint.setXfermode(null);
		canvas.restoreToCount(saveLayer);
	}

}
