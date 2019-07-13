package com.gs.learn.media.util;

import android.app.Activity;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

public class MeasureUtil {
	private final static String TAG = "MeasureUtil";
	
	public static float getTextWidth(String text, float textSize) {
		if (text==null || text.length()<=0) {
			return 0;
		}
		Paint paint = new Paint();
		paint.setTextSize(textSize);
		return paint.measureText(text);
	}

	public static float getTextHeight(String text, float textSize) {
		Paint paint = new Paint();
		paint.setTextSize(textSize);
		FontMetrics fm = paint.getFontMetrics();
		//文本自身的高度
		return fm.descent - fm.ascent;
		//文本所在行的行高
		//return fm.bottom - fm.top + fm.leading;
	}

	public static float getRealHeight(Activity act, int resid) {
		LinearLayout llayout = (LinearLayout) act.findViewById(resid);
		return getRealHeight(llayout);
	}
	
	public static float getRealHeight(View parent, int resid) {
		LinearLayout llayout = (LinearLayout) parent.findViewById(resid);
		return getRealHeight(llayout);
	}
	
	public static float getRealHeight(View child) {
		LinearLayout llayout = (LinearLayout) child;
		LayoutParams params = llayout.getLayoutParams();
		if (params == null) {
			params = new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		}
		int widthSpec = ViewGroup.getChildMeasureSpec(0, 0, params.width);
		int heightSpec;
		if (params.height > 0) {
			heightSpec = MeasureSpec.makeMeasureSpec(params.height, MeasureSpec.EXACTLY);
		} else {
			heightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		}
		llayout.measure(widthSpec, heightSpec);
		//获得布局视图的高度，若想得到布局宽度，则可调用getMeasuredWidth方法
		return llayout.getMeasuredHeight();
	}

}
