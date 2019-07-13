package com.gs.learn.device.widget;

import java.util.ArrayList;

import com.gs.learn.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class BettingView extends View {
	private final static String TAG = "BettingView";
	private Context mContext;
	private int mWidth;
	private int mHeight;
	private Bitmap mCompassBg;
	private Rect mRectSrc;
	private Rect mRectDest;
	private ArrayList<Integer> mDiceList = new ArrayList<Integer>();
	private Bitmap mDiceOne, mDiceTwo, mDiceThree, mDiceFour, mDiceFive, mDiceSix;
	private Bitmap mShake01, mShake02, mShake03, mShake04, mShake05;

	public BettingView(Context context) {
		this(context, null);
	}

	public BettingView(Context context, AttributeSet attr) {
		super(context, attr);
		mContext = context;
		mCompassBg = BitmapFactory.decodeResource(getResources(), R.drawable.bobing_bg);
		mRectSrc = new Rect(0, 0, mCompassBg.getWidth(), mCompassBg.getHeight());
		mDiceOne = BitmapFactory.decodeResource(getResources(), R.drawable.dice01);
		mDiceTwo = BitmapFactory.decodeResource(getResources(), R.drawable.dice02);
		mDiceThree = BitmapFactory.decodeResource(getResources(), R.drawable.dice03);
		mDiceFour = BitmapFactory.decodeResource(getResources(), R.drawable.dice04);
		mDiceFive = BitmapFactory.decodeResource(getResources(), R.drawable.dice05);
		mDiceSix = BitmapFactory.decodeResource(getResources(), R.drawable.dice06);
		mShake01 = BitmapFactory.decodeResource(getResources(), R.drawable.shake01);
		mShake02 = BitmapFactory.decodeResource(getResources(), R.drawable.shake02);
		mShake03 = BitmapFactory.decodeResource(getResources(), R.drawable.shake03);
		mShake04 = BitmapFactory.decodeResource(getResources(), R.drawable.shake04);
		mShake05 = BitmapFactory.decodeResource(getResources(), R.drawable.shake05);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	    int width = MeasureSpec.getSize(widthMeasureSpec);
	    int height = MeasureSpec.getSize(heightMeasureSpec);
		mWidth = getMeasuredWidth();
		mHeight = mWidth*mCompassBg.getHeight()/mCompassBg.getWidth();
		if (width < height) {
			super.onMeasure(widthMeasureSpec, widthMeasureSpec);
		} else {
			super.onMeasure(heightMeasureSpec, heightMeasureSpec);
		}
		mRectDest = new Rect(0, 0, mWidth, mHeight);
		Log.d(TAG, "mWidth="+mWidth);
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		int item_width = mWidth/5;
		int item_height = mHeight/5;
		canvas.drawBitmap(mCompassBg, mRectSrc, mRectDest, new Paint());
		for (int i=0; i<mDiceList.size(); i++) {
			Bitmap bitmap = mDiceSix;
			if (mDiceList.get(i) == 0) {
				bitmap = mDiceOne;
			} else if (mDiceList.get(i) == 1) {
				bitmap = mDiceTwo;
			} else if (mDiceList.get(i) == 2) {
				bitmap = mDiceThree;
			} else if (mDiceList.get(i) == 3) {
				bitmap = mDiceFour;
			} else if (mDiceList.get(i) == 4) {
				bitmap = mDiceFive;
			}
			int left = item_width + item_width*(i%3);
			int top = item_height*2 + item_height*(i/3);
			if (item_width > bitmap.getWidth()*2.5) {
				Log.d(TAG, "left="+left+", top="+top+", right="+(left+bitmap.getWidth())+", bottom="+(top+bitmap.getHeight()));
				Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
				Rect dst = new Rect(left, top, 
						(int)(left+bitmap.getWidth()*1.5), (int)(top+bitmap.getHeight()*1.5));
				canvas.drawBitmap(bitmap, src, dst, new Paint());
			} else {
				canvas.drawBitmap(bitmap, left, top, new Paint());
			}
		}
		if (mDiceList==null || mDiceList.size()<=0) {
			for (int j=0; j<6; j++) {
				int seq = (int) (Math.random()*100%5);
				Bitmap bitmap = mShake05;
				if (seq == 0) {
					bitmap = mShake01;
				} else if (seq == 1) {
					bitmap = mShake02;
				} else if (seq == 2) {
					bitmap = mShake03;
				} else if (seq == 3) {
					bitmap = mShake04;
				}
				int left = item_width + (int) (item_width*(Math.random()*10%2.0));
				int top = item_height +(int) (item_height*(Math.random()*10%2.0));
				if (item_width > bitmap.getWidth()*2) {
					Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
					Rect dst = new Rect(left, top, 
							(int)(left+bitmap.getWidth()*1.5), (int)(top+bitmap.getHeight()*1.5));
					canvas.drawBitmap(bitmap, src, dst, new Paint());
				} else {
					canvas.drawBitmap(bitmap, left, top, new Paint());
				}
			}
		}
	}
	
	public void setDiceList(ArrayList<Integer> diceList) {
		mDiceList = diceList;
		invalidate();
	}
	
	public void setRandom() {
		mDiceList = new ArrayList<Integer>();
		invalidate();
	}
	
}
