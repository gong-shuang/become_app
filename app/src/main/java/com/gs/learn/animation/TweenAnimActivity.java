package com.gs.learn.animation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/11/27.
 */
public class TweenAnimActivity extends AppCompatActivity implements AnimationListener {
	private ImageView iv_tween_anim;
	private Animation alphaAnim, translateAnim, scaleAnim, rotateAnim;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tween_anim);
		iv_tween_anim = (ImageView) findViewById(R.id.iv_tween_anim);
		initAnim();
		initTweenSpinner();
	}

	private void initAnim() {
		alphaAnim = new AlphaAnimation(1.0f, 0.1f);
		alphaAnim.setDuration(3000);
		alphaAnim.setFillAfter(true);
		
		translateAnim = new TranslateAnimation(1.0f, -200f, 1.0f, 1.0f);
		translateAnim.setDuration(3000);
		translateAnim.setFillAfter(true);
		
		scaleAnim = new ScaleAnimation(1.0f, 1.0f, 1.0f, 0.5f);
		scaleAnim.setDuration(3000);
		scaleAnim.setFillAfter(true);
		
		rotateAnim = new RotateAnimation(0f,  360f, Animation.RELATIVE_TO_SELF, 
				0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		rotateAnim.setDuration(3000);
		rotateAnim.setFillAfter(true);
	}

	private void initTweenSpinner() {
		ArrayAdapter<String> tweenAdapter = new ArrayAdapter<String>(this,
				R.layout.item_select, tweenArray);
		Spinner sp_tween = (Spinner) findViewById(R.id.sp_tween);
		sp_tween.setPrompt("请选择补间动画类型");
		sp_tween.setAdapter(tweenAdapter);
		sp_tween.setOnItemSelectedListener(new TweenSelectedListener());
		sp_tween.setSelection(0);
	}

	private String[] tweenArray={"灰度动画", "平移动画", "缩放动画", "旋转动画"};
	class TweenSelectedListener implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			if (arg2 == 0) {
				iv_tween_anim.startAnimation(alphaAnim);
				alphaAnim.setAnimationListener(TweenAnimActivity.this);
			} else if (arg2 == 1) {
				iv_tween_anim.startAnimation(translateAnim);
				translateAnim.setAnimationListener(TweenAnimActivity.this);
			} else if (arg2 == 2) {
				iv_tween_anim.startAnimation(scaleAnim);
				scaleAnim.setAnimationListener(TweenAnimActivity.this);
			} else if (arg2 == 3) {
				iv_tween_anim.startAnimation(rotateAnim);
				rotateAnim.setAnimationListener(TweenAnimActivity.this);
			}
		}

		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}
	
	@Override
	public void onAnimationStart(Animation animation) {
	}

	@Override
	public void onAnimationEnd(Animation animation) {
		if (animation.equals(alphaAnim)) {
			Animation alphaAnim2 = new AlphaAnimation(0.1f, 1.0f);
			alphaAnim2.setDuration(3000);
			alphaAnim2.setFillAfter(true);
			iv_tween_anim.startAnimation(alphaAnim2);
		} else if (animation.equals(translateAnim)) {
			Animation translateAnim2 = new TranslateAnimation(-200f, 1.0f, 1.0f, 1.0f);
			translateAnim2.setDuration(3000);
			translateAnim2.setFillAfter(true);
			iv_tween_anim.startAnimation(translateAnim2);
		} else if (animation.equals(scaleAnim)) {
			Animation scaleAnim2 = new ScaleAnimation(1.0f, 1.0f, 0.5f, 1.0f);
			scaleAnim2.setDuration(3000);
			scaleAnim2.setFillAfter(true);
			iv_tween_anim.startAnimation(scaleAnim2);
		} else if (animation.equals(rotateAnim)) {
			Animation rotateAnim2 = new RotateAnimation(0f, -360f, 
					Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
			rotateAnim2.setDuration(3000);
			rotateAnim2.setFillAfter(true);
			iv_tween_anim.startAnimation(rotateAnim2);
		}
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
	}

}
