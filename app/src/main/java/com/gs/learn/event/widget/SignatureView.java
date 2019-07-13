package com.gs.learn.event.widget;

import java.util.ArrayList;

import com.gs.learn.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import com.gs.learn.R;

public class SignatureView extends View {
	private static final String TAG = "SignatureView";
	private Paint paint;
	private Canvas cacheCanvas;
	private Bitmap cachebBitmap;
	private Path path;
	private int paint_color = Color.BLACK;
	private int stroke_width = 3;
	private PathPosition pos = new PathPosition();
	private ArrayList<PathPosition> pathArray = new ArrayList<PathPosition>();
	private int mWidth=0, mHeight=0;

	public SignatureView(Context context, AttributeSet attrs) {
		super(context, attrs);
		if (attrs != null) {
			TypedArray attrArray = getContext().obtainStyledAttributes(attrs, R.styleable.SignatureView);
			paint_color = attrArray.getColor(R.styleable.SignatureView_paint_color, Color.BLACK);
			stroke_width = attrArray.getInt(R.styleable.SignatureView_stroke_width, 3);
			attrArray.recycle();
		}
	}

	public SignatureView(Context context) {
		super(context);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		mWidth = getMeasuredWidth();
		mHeight = getMeasuredHeight();
		init(mWidth, mHeight);
	}
	
	private void init(int width, int height) {
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setStrokeWidth(stroke_width);
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(paint_color);
		path = new Path();

		setDrawingCacheEnabled(true);
		cachebBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		cacheCanvas = new Canvas(cachebBitmap);
		clear();
	}

	public void clear() {
		if (cacheCanvas != null) {
			pathArray.clear();
			cacheCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR); //透明背景
			invalidate();
		}
	}

	public void revoke() {
		if (pathArray.size() > 0) {
			pathArray.remove(pathArray.size()-1);
			cacheCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
			for (int i=0; i<pathArray.size(); i++) {
				Path posPath = new Path();
				posPath.moveTo(pathArray.get(i).firstX, pathArray.get(i).firstY);
				posPath.quadTo(pathArray.get(i).firstX, pathArray.get(i).firstY, 
						pathArray.get(i).nextX, pathArray.get(i).nextY);
				cacheCanvas.drawPath(posPath, paint);
			}
			invalidate();
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawBitmap(cachebBitmap, 0, 0, null);
		canvas.drawPath(path, paint); //这个是需要的，最近一次的路径保存在这里
	}

	private float mLastX, mLastY;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			path.moveTo(event.getX(), event.getY());
			pos.firstX = event.getX();
			pos.firstY = event.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			path.quadTo(mLastX, mLastY, event.getX(), event.getY());
			pos.nextX = event.getX();
			pos.nextY = event.getY();
			pathArray.add(pos);
			pos = new PathPosition();
			pos.firstX = event.getX();
			pos.firstY = event.getY();
			break;
		case MotionEvent.ACTION_UP:
			cacheCanvas.drawPath(path, paint);
			path.reset();
			break;
		}
		mLastX = event.getX();
		mLastY = event.getY();
		invalidate();
		return true;
	}
	
	private class PathPosition {
		public float firstX;
		public float firstY;
		public float nextX;
		public float nextY;

		public PathPosition() {
			firstX = 0;
			firstY = 0;
			nextX = 0;
			nextY = 0;
		}
	}

}
