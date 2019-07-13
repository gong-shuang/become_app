package com.gs.learn.event.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class MeituView extends View {
	private Context mContext;
	private Paint mPaintShade;

	public MeituView(Context context) {
		this(context, null);
	}

	public MeituView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		mPaintShade = new Paint();
		mPaintShade.setStyle(Style.FILL);
		mPaintShade.setColor(0x99000000);
	}

	private Bitmap mOrigBitmap = null;
	private Bitmap mCropBitmap = null;
	private Rect mRect = new Rect(0,0,0,0);
	
	public void setOrigBitmap(Bitmap orig) {
		mOrigBitmap = orig;
	}
	
	public Bitmap getCropBitmap() {
		return mCropBitmap;
	}
	
	public boolean setBitmapRect(Rect rect) {
		if (mOrigBitmap == null) {
			return false;
		}
		if (rect.left<0 || rect.left>mOrigBitmap.getWidth()) {
			return false;
		}
		if (rect.top<0 || rect.top>mOrigBitmap.getHeight()) {
			return false;
		}
		if (rect.right<=0 || rect.left+rect.right>mOrigBitmap.getWidth()) {
			return false;
		}
		if (rect.bottom<=0 || rect.top+rect.bottom>mOrigBitmap.getHeight()) {
			return false;
		}
		mRect = rect;
		mCropBitmap = Bitmap.createBitmap(mOrigBitmap, 
				mRect.left, mRect.top, mRect.right, mRect.bottom);
		postInvalidate();
		return true;
	}
	
	public Rect getBitmapRect() {
		return mRect;
	}

	private boolean bReset = false;
	private float mLastOffsetX, mLastOffsetY;
	private float mLastOffsetXTwo, mLastOffsetYTwo;
	private long mOriginTime;
	
	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		if (mOrigBitmap == null) {
			return;
		}
		// 画外圈阴影
		Rect rectShade = new Rect(0, 0, getMeasuredWidth(), getMeasuredHeight());
		canvas.drawRect(rectShade, mPaintShade);
		// 画高亮处的图像
		canvas.drawBitmap(mCropBitmap, mRect.left, mRect.top, new Paint());
	}
	
	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction() & MotionEvent.ACTION_MASK;
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			mOriginTime = event.getEventTime();
			mOriginX = event.getX();
			mOriginY = event.getY();
			mOriginRect = mRect;
			mDragMode = getDragMode(mOriginX, mOriginY);
			bReset = true;
			break;
		case MotionEvent.ACTION_UP:
			// 判断点击和长按
			if (mListener != null && Math.abs(event.getX()-mOriginX)<10 &&
					Math.abs(event.getY()-mOriginY)<10) {
				if (event.getEventTime() - mOriginTime < 500) {
					mListener.onImageClick();
				} else {
					mListener.onImageLongClick();
				}
			}
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
			// 判断缩放和旋转
			mDragMode = IMAGE_SCALE_OR_ROTATE;
			bReset = true;
			break;
		case MotionEvent.ACTION_POINTER_UP:
			mDragMode = DRAG_NONE;
			break;
		case MotionEvent.ACTION_MOVE:
			int offsetX = (int) (event.getX()-mOriginX);
			int offsetY = (int) (event.getY()-mOriginY);
			Rect rect = null;
			int left = mOriginRect.left;
			int top = mOriginRect.top;
			int right = mOriginRect.right;
			int bottom = mOriginRect.bottom;
			if (mDragMode == DRAG_NONE) {
				return true;
			} else if (mDragMode == DRAG_WHOLE) {
				rect = new Rect(left+offsetX, top+offsetY, right, bottom);
			} else if (mDragMode == DRAG_LEFT) {
				rect = new Rect(left+offsetX, top, right-offsetX, bottom);
			} else if (mDragMode == DRAG_RIGHT) {
				rect = new Rect(left, top, right+offsetX, bottom);
			} else if (mDragMode == DRAG_TOP) {
				rect = new Rect(left, top+offsetY, right, bottom-offsetY);
			} else if (mDragMode == DRAG_BOTTOM) {
				rect = new Rect(left, top, right, bottom+offsetY);
			} else if (mDragMode == DRAG_LEFT_TOP) {
				rect = new Rect(left+offsetX, top+offsetY, right-offsetX, bottom-offsetY);
			} else if (mDragMode == DRAG_RIGHT_TOP) {
				rect = new Rect(left, top+offsetY, right+offsetX, bottom-offsetY);
			} else if (mDragMode == DRAG_LEFT_BOTTOM) {
				rect = new Rect(left+offsetX, top, right-offsetX, bottom+offsetY);
			} else if (mDragMode == DRAG_RIGHT_BOTTOM) {
				rect = new Rect(left, top, right+offsetX, bottom+offsetY);
			} else if (mDragMode == IMAGE_TRANSLATE) {
				if (mListener != null) {
					mListener.onImageTraslate(offsetX, offsetY, bReset);
					bReset = false;
				}
			} else if (mDragMode == IMAGE_SCALE_OR_ROTATE) {
				if (mListener != null) {
					float nowWholeDistance = distance(event.getX(), event.getY(),
							event.getX(1), event.getY(1));
					float preWholeDistance = distance(mLastOffsetX, mLastOffsetY,
							mLastOffsetXTwo, mLastOffsetYTwo);
					float primaryDistance = distance(event.getX(), event.getY(),
							mLastOffsetX, mLastOffsetY);
					float secondaryDistance = distance(event.getX(1), event.getY(1),
							mLastOffsetXTwo, mLastOffsetYTwo);
					if (Math.abs(nowWholeDistance-preWholeDistance) >
						(float) Math.sqrt(2)/2.0f*(primaryDistance+secondaryDistance)) {
						// 缩放
						mListener.onImageScale(nowWholeDistance / preWholeDistance);
					} else {
						// 旋转
						int preDegree = degree(mLastOffsetX, mLastOffsetY, 
								mLastOffsetXTwo, mLastOffsetYTwo);
						int nowDegree = degree(event.getX(), event.getY(), 
								event.getX(1), event.getY(1));
						mListener.onImageRotate(nowDegree - preDegree);
					}
				}
			}
			if (mDragMode!=IMAGE_TRANSLATE && mDragMode!=IMAGE_SCALE_OR_ROTATE) {
				setBitmapRect(rect);
			}
			break;
		case MotionEvent.ACTION_CANCEL:
			break;
		default:
			break;
		}
		mLastOffsetX = event.getX();
		mLastOffsetY = event.getY();
		if (event.getPointerCount() >= 2) {
			mLastOffsetXTwo = event.getX(1);
			mLastOffsetYTwo = event.getY(1);
		}
		return true;
	}
	
	private float distance(float x1, float y1, float x2, float y2) {
		float offsetX = x2 - x1;
		float offsetY = y2 - y1;
		return (float) Math.sqrt(offsetX*offsetX + offsetY*offsetY);
	}
	
	private int degree(float x1, float y1, float x2, float y2) {
		 return (int) (Math.atan((y2-y1) / (x2-x1)) / Math.PI * 180);
	}

	private int DRAG_NONE = 0, DRAG_WHOLE = 1, DRAG_LEFT = 2,  DRAG_RIGHT = 3;
	private int DRAG_TOP = 4, DRAG_BOTTOM = 5, DRAG_LEFT_TOP = 6, DRAG_RIGHT_TOP = 7;
	private int DRAG_LEFT_BOTTOM = 8, DRAG_RIGHT_BOTTOM = 9;
	private int IMAGE_TRANSLATE = 10, IMAGE_SCALE_OR_ROTATE = 11;
	
	private int mDragMode = DRAG_NONE;
	private int mInterval = 15;
	private float mOriginX, mOriginY;
	private Rect mOriginRect;
	
	private int getDragMode(float f, float g) {
		int left = mRect.left;
		int top = mRect.top;
		int right = mRect.left + mRect.right;
		int bottom = mRect.top + mRect.bottom;
		if (Math.abs(f-left)<=mInterval && Math.abs(g-top)<=mInterval) {
			return DRAG_LEFT_TOP;
		} else if (Math.abs(f-right)<=mInterval && Math.abs(g-top)<=mInterval) {
			return DRAG_RIGHT_TOP;
		} else if (Math.abs(f-left)<=mInterval && Math.abs(g-bottom)<=mInterval) {
			return DRAG_LEFT_BOTTOM;
		} else if (Math.abs(f-right)<=mInterval && Math.abs(g-bottom)<=mInterval) {
			return DRAG_RIGHT_BOTTOM;
		} else if (Math.abs(f-left)<=mInterval && g>top+mInterval && g<bottom-mInterval) {
			return DRAG_LEFT;
		} else if (Math.abs(f-right)<=mInterval && g>top+mInterval && g<bottom-mInterval) {
			return DRAG_RIGHT;
		} else if (Math.abs(f-left)<=mInterval && g>top+mInterval && g<bottom-mInterval) {
			return DRAG_LEFT;
		} else if (Math.abs(g-top)<=mInterval && f>left+mInterval && f<right-mInterval) {
			return DRAG_TOP;
		} else if (Math.abs(g-bottom)<=mInterval && f>left+mInterval && f<right-mInterval) {
			return DRAG_BOTTOM;
		} else if (f>left+mInterval && f<right-mInterval
				&& g>top+mInterval && g<bottom-mInterval) {
			return DRAG_WHOLE;
		} else if (f+mInterval<left || f-mInterval>right || g+mInterval<top || g-mInterval>bottom) {
			return IMAGE_TRANSLATE;
		} else {
			return DRAG_NONE;
		}
	}

	private ImageChangetListener mListener;
	public void setImageChangetListener(ImageChangetListener listener) {
		mListener = listener;
	}

	public static interface ImageChangetListener {
		public abstract void onImageClick();
		public abstract void onImageLongClick();
		public abstract void onImageTraslate(int offsetX, int offsetY, boolean bReset);
		public abstract void onImageScale(float ratio);
		public abstract void onImageRotate(int degree);
	}

}
