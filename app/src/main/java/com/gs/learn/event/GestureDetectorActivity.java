package com.gs.learn.event;

import com.gs.learn.R;
import com.gs.learn.event.util.DateUtil;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by ouyangshen on 2016/11/23.
 */
public class GestureDetectorActivity extends AppCompatActivity {
	private TextView tv_gesture;
	private GestureDetector mGesture;
	private String desc = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gesture_detector);
		tv_gesture = (TextView) findViewById(R.id.tv_gesture);
		mGesture = new GestureDetector(this, new MyGestureListener());
	}

	public boolean dispatchTouchEvent(MotionEvent event) {
		mGesture.onTouchEvent(event);
		return true;
	}

	final class MyGestureListener implements GestureDetector.OnGestureListener {

		@Override
		public final boolean onDown(MotionEvent event) {
//			desc = String.format("%s%s 您按下来了\n", desc, DateUtil.getNowTime());
//			tv_gesture.setText(desc);
			//onDown的返回值没有作用，不影响其它手势的处理
			return true;
		}

		@Override
		public final boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			float offsetX = e1.getX() - e2.getX();
			float offsetY = e1.getY() - e2.getY();
			if (Math.abs(offsetX) > Math.abs(offsetY)) {
				if (offsetX > 0) {
					desc = String.format("%s%s 您向左滑动了一下\n", desc, DateUtil.getNowTime());
				} else {
					desc = String.format("%s%s 您向右滑动了一下\n", desc, DateUtil.getNowTime());
				}
			} else {
				if (offsetY > 0) {
					desc = String.format("%s%s 您向上滑动了一下\n", desc, DateUtil.getNowTime());
				} else {
					desc = String.format("%s%s 您向下滑动了一下\n", desc, DateUtil.getNowTime());
				}
			}
			tv_gesture.setText(desc);
			return true;
		}

		@Override
		public final void onLongPress(MotionEvent event) {
			desc = String.format("%s%s 您长按了一下下\n", desc, DateUtil.getNowTime());
			tv_gesture.setText(desc);
		}

		@Override
		public final boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
			return false;
		}

		@Override
		public final void onShowPress(MotionEvent event) {
		}

		@Override
		public boolean onSingleTapUp(MotionEvent event) {
			desc = String.format("%s%s 您轻轻点了一下\n", desc, DateUtil.getNowTime());
			tv_gesture.setText(desc);
			//返回true表示我已经处理了，别处不要再处理这个手势
			return true;
		}

	}

}
