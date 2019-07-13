package com.gs.learn.media;

import com.gs.learn.media.adapter.GalleryAdapter;
import com.gs.learn.media.task.GestureTask;
import com.gs.learn.media.task.GestureTask.GestureCallback;
import com.gs.learn.media.util.Utils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ViewSwitcher.ViewFactory;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/12/4.
 */
public class ImageSwitcherActivity extends AppCompatActivity implements
		OnTouchListener, OnItemClickListener, GestureCallback {
	private ImageSwitcher is_switcher;
	private Gallery gl_switcher;
	private int[] mImageRes = { 
			R.drawable.scene1, R.drawable.scene2, R.drawable.scene3, 
			R.drawable.scene4, R.drawable.scene5, R.drawable.scene6 };
	private GestureDetector mGesture;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_switcher);
		is_switcher = (ImageSwitcher) findViewById(R.id.is_switcher);
		is_switcher.setFactory(new ViewFactoryImpl());
		is_switcher.setImageResource(mImageRes[0]);
		GestureTask gestureListener = new GestureTask(this);
		mGesture = new GestureDetector(this, gestureListener);
		gestureListener.setGestureCallback(this);
		is_switcher.setOnTouchListener(this);

		int dip_pad = Utils.dip2px(this, 20);
		gl_switcher = (Gallery) findViewById(R.id.gl_switcher);
		gl_switcher.setPadding(0, dip_pad, 0, dip_pad);
		gl_switcher.setSpacing(dip_pad);
		gl_switcher.setUnselectedAlpha(0.5f);
		gl_switcher.setOnItemClickListener(this);
		gl_switcher.setAdapter(new GalleryAdapter(this, mImageRes));
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		is_switcher.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
		is_switcher.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_out));
		is_switcher.setImageResource(mImageRes[position]);
	}

	public class ViewFactoryImpl implements ViewFactory {
		@Override
		public View makeView() {
			ImageView iv = new ImageView(ImageSwitcherActivity.this);
			iv.setBackgroundColor(0xFFFFFFFF);
			iv.setScaleType(ScaleType.FIT_XY);
			iv.setLayoutParams(new ImageSwitcher.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			return iv;
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		mGesture.onTouchEvent(event);
		return true;
	}

	@Override
	public void gotoNext() {
		is_switcher.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_in));
		is_switcher.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_out));
		int next_pos = (int) (gl_switcher.getSelectedItemId() + 1);
		if (next_pos >= mImageRes.length) {
			next_pos = 0;
		}
		is_switcher.setImageResource(mImageRes[next_pos]);
		gl_switcher.setSelection(next_pos);
	}

	@Override
	public void gotoPre() {
		is_switcher.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_in));
		is_switcher.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_out));
		int pre_pos = (int) (gl_switcher.getSelectedItemId() - 1);
		if (pre_pos < 0) {
			pre_pos = mImageRes.length - 1;
		}
		is_switcher.setImageResource(mImageRes[pre_pos]);
		gl_switcher.setSelection(pre_pos);
	}

}
