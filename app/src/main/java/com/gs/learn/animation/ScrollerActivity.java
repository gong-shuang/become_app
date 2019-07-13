package com.gs.learn.animation;

import com.gs.learn.animation.widget.ScrollTextView;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/11/27.
 */
public class ScrollerActivity extends AppCompatActivity implements OnTouchListener,OnGestureListener {
	private ScrollTextView tv_rough;
	private GestureDetector mGesture;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scroller);
		tv_rough = (ScrollTextView) findViewById(R.id.tv_rough);
		tv_rough.setOnTouchListener(this);
		mGesture = new GestureDetector(this, this);
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return mGesture.onTouchEvent(event);
	}

	@Override
	public boolean onDown(MotionEvent e) {
		return true;
	}

	@Override
	public void onShowPress(MotionEvent e) {
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		tv_rough.setText("您轻轻点击了一下");
		return true;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		tv_rough.setText("您拖动我啦");
		float offsetX = e2.getRawX() - e1.getRawX();
		float offsetY = e2.getRawY() - e1.getRawY();
		tv_rough.smoothScrollBy((int)offsetX, (int)offsetY);
		return true;
	}

}
