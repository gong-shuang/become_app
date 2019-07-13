package com.gs.learn.device.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class TurnSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
	private Paint mPaint1, mPaint2;
	private RectF mRectF;
	private int mBeginAngle1=0, mBeginAngle2=180;
	private int mInterval = 70;
	private boolean bRunning = false;

	private SurfaceHolder mHolder;
	private Canvas mCanvas;

	public TurnSurfaceView(Context context) {
		this(context, null);
	}

	public TurnSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mHolder = getHolder();
		mHolder.addCallback(this);
		//下面两行设置背景为透明，因为SurfaceView默认背景是黑色
		setZOrderOnTop(true);
		mHolder.setFormat(PixelFormat.TRANSLUCENT);
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
    	new Thread() {
    		@Override
    		public void run() {
    			while (bRunning) {
    				draw(mPaint1, mBeginAngle1);
    				try {
    					Thread.sleep(mInterval);
    				} catch (InterruptedException e) {
    					e.printStackTrace();
    				}
    				mBeginAngle1 += 3;
    			}
    		}
    	}.start();
    	new Thread() {
    		@Override
    		public void run() {
    			while (bRunning) {
    				draw(mPaint2, mBeginAngle2);
    				try {
    					Thread.sleep(mInterval);
    				} catch (InterruptedException e) {
    					e.printStackTrace();
    				}
    				mBeginAngle2 += 3;
    			}
    		}
    	}.start();
    }

    public void stop(){
    	bRunning = false;
    }

	private void draw(Paint paint, int beinAngle) {
		synchronized (mHolder) {
			mCanvas = mHolder.lockCanvas();
			if (mCanvas != null) {
				// SurfaceView上次的绘图结果仍然保留，如果不想保留上次的绘图，则需将整个画布清空
				// mCanvas.drawColor(Color.WHITE);
				mCanvas.drawArc(mRectF, beinAngle, 10, true, paint);
				mHolder.unlockCanvasAndPost(mCanvas);
			}
		}
	}
	
	private Paint getPaint(int color) {
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(color);
		paint.setStrokeWidth(10);
		paint.setStyle(Style.FILL);
		return paint;
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		mPaint1 = getPaint(Color.RED);
		mPaint2 = getPaint(Color.CYAN);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
	}
}
