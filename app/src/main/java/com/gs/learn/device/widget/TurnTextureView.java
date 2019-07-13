package com.gs.learn.device.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;

public class TurnTextureView extends TextureView implements SurfaceTextureListener, Runnable {
	private Paint mPaint;
	private RectF mRectF;
	private int mBeginAngle = 0;
	private boolean bRunning = false;

	private Canvas mCanvas;
	private Thread mThread;

	public TurnTextureView(Context context) {
		this(context, null);
	}

	public TurnTextureView(Context context, AttributeSet attrs) {
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

    public void start(){
    	bRunning = true;
    	mThread = new Thread(this);
    	mThread.start();
    }

    public void stop(){
    	bRunning = false;
    }

	@Override
	public void run() {
		while (bRunning) {
			draw(false);
			try {
				Thread.sleep(70);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			mBeginAngle += 3;
		}
	}
	
	private void draw(boolean isFirst) {
		mCanvas = lockCanvas();
		if (mCanvas != null) {
			//TextureView上次的绘图结果仍然保留，如果不想保留上次的绘图，则需将整个画布清空
			//mCanvas.drawColor(Color.WHITE);
			if (isFirst != true) {
				mCanvas.drawArc(mRectF, mBeginAngle, 30, true, mPaint);
			}
			unlockCanvasAndPost(mCanvas);
		}
	}

	@Override
	public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
		draw(true);
	}

	@Override
	public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
		return true;
	}

	@Override
	public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
	}

	@Override
	public void onSurfaceTextureUpdated(SurfaceTexture surface) {
	}
}
