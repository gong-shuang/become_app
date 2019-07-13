package com.gs.learn.device.widget;

import java.util.HashMap;
import java.util.Map;

import com.gs.learn.R;
import com.gs.learn.device.bean.Satellite;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

@SuppressLint({ "UseSparseArrays", "DrawAllocation" })
public class CompassView extends View {
	private final static String TAG = "CompassView";
	private Context mContext;
	private int mWidth;
	private Paint mPaintLine;
	private Paint mPaintText;
	private Paint mPaintAngle;
	private Bitmap mCompassBg;
	private Rect mRectSrc;
	private Rect mRectDest;
	private RectF mRectAngle;
	private RectF mRectSourth;
	private Bitmap mSatelliteChina;
	private Bitmap mSatelliteAmerica;
	private Bitmap mSatelliteRussia;
	private Map<Integer, Satellite> mapSatellite = new HashMap<Integer, Satellite>();
	private int mScaleLength = 25;
	private float mBorder = 0.9f;
	
	private int mDirection = -1024;
	private Paint mPaintSourth;
	
	public CompassView(Context context) {
		this(context, null);
	}

	public CompassView(Context context, AttributeSet attr) {
		super(context, attr);
		mContext = context;
		mPaintLine = new Paint();
		mPaintLine.setAntiAlias(true);
		mPaintLine.setColor(Color.GREEN);
		mPaintLine.setStrokeWidth(2);
		mPaintLine.setStyle(Style.STROKE);
		mPaintText = new Paint();
		mPaintText.setAntiAlias(true);
		mPaintText.setColor(Color.RED);
		mPaintText.setStrokeWidth(1);
		mPaintText.setStyle(Style.STROKE);
		mPaintText.setTextSize(28);
		mPaintAngle = new Paint();
		mPaintAngle.setAntiAlias(true);
		mPaintAngle.setColor(Color.GRAY);
		mPaintAngle.setStrokeWidth(1);
		mPaintAngle.setStyle(Style.STROKE);
		mPaintAngle.setTextSize(23);
		mPaintSourth = new Paint();
		mPaintSourth.setAntiAlias(true);
		mPaintSourth.setColor(Color.RED);
		mPaintSourth.setStrokeWidth(4);
		mPaintSourth.setStyle(Style.STROKE);
		mCompassBg = BitmapFactory.decodeResource(getResources(), R.drawable.compass_bg);
		mRectSrc = new Rect(0, 0, mCompassBg.getWidth(), mCompassBg.getHeight());
		mSatelliteChina = BitmapFactory.decodeResource(getResources(), R.drawable.satellite_china);
		mSatelliteAmerica = BitmapFactory.decodeResource(getResources(), R.drawable.satellite_america);
		mSatelliteRussia = BitmapFactory.decodeResource(getResources(), R.drawable.satellite_russia);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	    int width = MeasureSpec.getSize(widthMeasureSpec);
	    int height = MeasureSpec.getSize(heightMeasureSpec);
		mWidth = getMeasuredWidth();
		if (width < height) {
			super.onMeasure(widthMeasureSpec, widthMeasureSpec);
		} else {
			super.onMeasure(heightMeasureSpec, heightMeasureSpec);
		}
		mRectDest = new Rect(0, 0, mWidth, mWidth);
		mRectAngle = new RectF(mWidth/10, mWidth/10, mWidth*9/10, mWidth*9/10);
		mRectSourth = new RectF(mWidth*0.3f/10, mWidth*0.3f/10, mWidth*9.7f/10, mWidth*9.7f/10);
		Log.d(TAG, "mWidth="+mWidth);
	}
	
	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		int radius = mWidth/2;
		int margin = radius/10;
		canvas.drawBitmap(mCompassBg, mRectSrc, mRectDest, new Paint());
		canvas.drawCircle(radius, radius, radius*3/10, mPaintLine);
		canvas.drawCircle(radius, radius, radius*5/10, mPaintLine);
		canvas.drawCircle(radius, radius, radius*7/10, mPaintLine);
		canvas.drawCircle(radius, radius, radius*9/10, mPaintLine);
		canvas.drawLine(radius, margin, radius, mWidth-margin, mPaintLine);
		canvas.drawLine(margin, radius, mWidth-margin, radius, mPaintLine);
		//画罗盘的刻度
		for (int i=0; i<360; i+=30) {
			Path path = new Path();
			path.addArc(mRectAngle, i-3, i+3);
			int angle = (i+90)%360;
			canvas.drawTextOnPath(""+angle, path, 0, 0, mPaintAngle);
			canvas.drawLine(getXpos(radius, angle, radius*mBorder),
					getYpos(radius, angle, radius*mBorder),
					getXpos(radius, angle, (radius-mScaleLength)*mBorder),
					getYpos(radius, angle, (radius-mScaleLength)*mBorder),
					mPaintAngle);
		}
		//画卫星分布图
		for (Map.Entry<Integer, Satellite> item_map : mapSatellite.entrySet()) {
			Satellite item = item_map.getValue();
			Bitmap bitmap;
			if (item.nation.equals("中国")) {
				bitmap = mSatelliteChina;
			} else if (item.nation.equals("美国")) {
				bitmap = mSatelliteAmerica;
			} else if (item.nation.equals("俄罗斯")) {
				bitmap = mSatelliteRussia;
			} else {
				continue;
			}
			float left = getXpos(radius, item.azimuth, radius*mBorder*getCos(item.elevation));
			float top = getYpos(radius, item.azimuth, radius*mBorder*getCos(item.elevation));
			canvas.drawBitmap(bitmap, left-bitmap.getWidth()/2, 
					top-bitmap.getHeight()/2, new Paint());
		}
		//画指南针
		if (mDirection > -1024) {
			int angle = (-mDirection+450)%360;
			canvas.drawLine(getXpos(radius, angle, radius*mBorder),
					getYpos(radius, angle, radius*mBorder),
					getXpos(radius, angle, 0),
					getYpos(radius, angle, 0),
					mPaintSourth);
			canvas.drawLine(getXpos(radius, angle, radius*mBorder),
					getYpos(radius, angle, radius*mBorder),
					getXpos(radius, angle-10, radius*7/10),
					getYpos(radius, angle-10, radius*7/10),
					mPaintSourth);
			canvas.drawLine(getXpos(radius, angle, radius*mBorder),
					getYpos(radius, angle, radius*mBorder),
					getXpos(radius, angle+10, radius*7/10),
					getYpos(radius, angle+10, radius*7/10),
					mPaintSourth);
			canvas.drawLine(getXpos(radius, angle-10, radius*7/10),
					getYpos(radius, angle-10, radius*7/10),
					getXpos(radius, angle+10, radius*7/10),
					getYpos(radius, angle+10, radius*7/10),
					mPaintSourth);
			Path path = new Path();
			path.addArc(mRectSourth, angle-2, angle+2);
			canvas.drawTextOnPath("南", path, 0, 0, mPaintText);
		} else {
			canvas.drawText("北", radius-15, margin-15, mPaintText);
		}
	}
	
	private float getXpos(int radius, int angle, double length) {
		return (float) (radius + getCos(angle) * length);
	}

	private float getYpos(int radius, int angle, double length) {
		return (float) (radius + getSin(angle) * length);
	}
	
	private double getSin(int angle) {
		return Math.sin(Math.PI*angle/180.0);
	}

	private double getCos(int angle) {
		return Math.cos(Math.PI*angle/180.0);
	}
	
	public void setSatelliteMap(Map<Integer, Satellite> map) {
		mapSatellite = map;
		invalidate();
	}

	public void setDirection(int direction) {
		mDirection = direction;
		invalidate();
	}
	
}
