package com.gs.learn.animation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/11/27.
 */
public class AnimSetActivity extends AppCompatActivity implements 
		OnClickListener, AnimationListener {
	private ImageView iv_anim_set;
	private Animation alphaAnim, translateAnim, scaleAnim, rotateAnim;
	private AnimationSet setAnim;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_anim_set);
		iv_anim_set = (ImageView) findViewById(R.id.iv_anim_set);
		iv_anim_set.setOnClickListener(this);
		initAnim();
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

		setAnim = new AnimationSet(true);
		//代码添加集合动画
		setAnim.addAnimation(translateAnim);
		setAnim.addAnimation(alphaAnim);
		setAnim.addAnimation(scaleAnim);
		setAnim.addAnimation(rotateAnim);
		//从xml中获取集合动画
//		setAnim.addAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_set));
		setAnim.setFillAfter(true);
		startAnim();
	}
	
	private void startAnim() {
		iv_anim_set.startAnimation(setAnim);
		setAnim.setAnimationListener(this);
	}

	@Override
	public void onAnimationStart(Animation animation) {
	}

	@Override
	public void onAnimationEnd(Animation animation) {
		if (animation.equals(setAnim)) {
			Animation alphaAnim2 = new AlphaAnimation(0.1f, 1.0f);
			alphaAnim2.setDuration(3000);
			alphaAnim2.setFillAfter(true);
			Animation translateAnim2 = new TranslateAnimation(-200f, 1.0f, 1.0f, 1.0f);
			translateAnim2.setDuration(3000);
			translateAnim2.setFillAfter(true);
			Animation scaleAnim2 = new ScaleAnimation(1.0f, 1.0f, 0.5f, 1.0f);
			scaleAnim2.setDuration(3000);
			scaleAnim2.setFillAfter(true);
			Animation rotateAnim2 = new RotateAnimation(0f, -360f, 
					Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
			rotateAnim2.setDuration(3000);
			rotateAnim2.setFillAfter(true);
			
			AnimationSet setAnim2 = new AnimationSet(true);
			setAnim2.addAnimation(alphaAnim2);
			setAnim2.addAnimation(translateAnim2);
			setAnim2.addAnimation(scaleAnim2);
			setAnim2.addAnimation(rotateAnim2);
			setAnim2.setFillAfter(true);
			iv_anim_set.startAnimation(setAnim2);
		}
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.iv_anim_set) {
			startAnim();
		}
	}

}
