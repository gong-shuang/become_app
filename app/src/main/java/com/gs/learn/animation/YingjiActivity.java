package com.gs.learn.animation;

import com.gs.learn.animation.widget.MosaicView;
import com.gs.learn.animation.widget.ShutterView;

import android.animation.Animator;
import android.animation.RectEvaluator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/11/27.
 */
public class YingjiActivity extends AppCompatActivity implements 
		AnimatorListener, AnimationListener, OnClickListener {
	private RelativeLayout rl_yingji;
	private TextView tv_anim_title;
	private LayoutParams mParams;

	private ImageView view1, view4, view5, view6;
	private ShutterView view2;
	private MosaicView view3;
	private int[] mImageArray = {
			R.drawable.bdg01, R.drawable.bdg02, R.drawable.bdg03, R.drawable.bdg04, R.drawable.bdg05,
			R.drawable.bdg06, R.drawable.bdg07, R.drawable.bdg08, R.drawable.bdg09, R.drawable.bdg10
	};
	private ObjectAnimator anim1, anim2, anim3, anim4;
	private Animation translateAnim, setAnim;
	private int mDuration = 5000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_yingji);
		rl_yingji = (RelativeLayout) findViewById(R.id.rl_yingji);
		tv_anim_title = (TextView) findViewById(R.id.tv_anim_title);
		playYingji();
	}
	
	private void initView() {
		mParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		view1 = new ImageView(this);
		view1.setLayoutParams(mParams);
		view1.setImageResource(mImageArray[0]);
		view1.setScaleType(ScaleType.FIT_START);
		view1.setAlpha(0f);

		view2 = new ShutterView(this);
		view2.setLayoutParams(mParams);
		view2.setImageBitmap(BitmapFactory.decodeResource(getResources(), mImageArray[1]));
		view2.setMode(PorterDuff.Mode.DST_OUT);
		
		view3 = new MosaicView(this);
		view3.setLayoutParams(mParams);
		view3.setImageBitmap(BitmapFactory.decodeResource(getResources(), mImageArray[2]));
		view3.setMode(PorterDuff.Mode.DST_OUT);
		view3.setRatio(-5);

		view4 = new ImageView(this);
		view4.setLayoutParams(mParams);
		view4.setImageResource(mImageArray[3]);
		view4.setScaleType(ScaleType.FIT_START);

		view5 = new ImageView(this);
		view5.setLayoutParams(mParams);
		view5.setImageResource(mImageArray[5]);
		view5.setScaleType(ScaleType.FIT_START);

		view6 = new ImageView(this);
		view6.setLayoutParams(mParams);
		view6.setImageResource(mImageArray[6]);
		view6.setScaleType(ScaleType.FIT_START);
	}
	
	private void playYingji() {
		rl_yingji.removeAllViews();
		initView();
		rl_yingji.addView(view1);
		anim1 = ObjectAnimator.ofFloat(view1, "alpha", 0f, 1f);
		anim1.setDuration(mDuration);
		anim1.addListener(this);
		anim1.start();
	}

	@Override
	public void onAnimationStart(Animator animation) {
		if (animation.equals(anim1)) {
			tv_anim_title.setText("正在播放灰度动画");
		} else if (animation.equals(anim2)) {
			tv_anim_title.setText("正在播放裁剪动画");
		} else if (animation.equals(anim3)) {
			tv_anim_title.setText("正在播放百叶窗动画");
		} else if (animation.equals(anim4)) {
			tv_anim_title.setText("正在播放马赛克动画");
		}
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
	@Override
	public void onAnimationEnd(Animator animation) {
		if (animation.equals(anim1)) {
			rl_yingji.addView(view2, 0);

			Bitmap bitmap = BitmapFactory.decodeResource(getResources(), mImageArray[0]);
			int width = view1.getWidth();
			int height = bitmap.getHeight()*width/bitmap.getWidth();
			anim2 = ObjectAnimator.ofObject(view1, "clipBounds", 
					new RectEvaluator(), new Rect(0,0,width,height), 
					new Rect(width/2,height/2,width/2,height/2));
			anim2.setDuration(mDuration);
			anim2.addListener(this);
			anim2.start();
		} else if (animation.equals(anim2)) {
			rl_yingji.removeView(view1);
			rl_yingji.addView(view3, 0);

			anim3 = ObjectAnimator.ofInt(view2, "ratio", 0, 100);
			anim3.setDuration(mDuration);
			anim3.addListener(this);
			anim3.start();
		} else if (animation.equals(anim3)) {
			rl_yingji.removeView(view2);
			rl_yingji.addView(view4, 0);

			int offset = 5;
			view3.setOffset(offset);
			anim4 = ObjectAnimator.ofInt(view3, "ratio", 0-offset, 101+offset);
			anim4.setDuration(mDuration);
			anim4.addListener(this);
			anim4.start();
		} else if (animation.equals(anim4)) {
			rl_yingji.removeView(view3);

			Drawable[] drawableArray = { getResources().getDrawable(mImageArray[3]),
					getResources().getDrawable(mImageArray[4]) };
			TransitionDrawable td_fade = new TransitionDrawable(drawableArray);
			td_fade.setCrossFadeEnabled(false);
			view4.setImageDrawable(td_fade);
			int delay = mDuration;
			td_fade.startTransition(delay);
			tv_anim_title.setText("正在播放淡入淡出动画");
			mHandler.postDelayed(mTransitionEnd, delay);
		}
	}
	
	private Handler mHandler = new Handler();
	private Runnable mTransitionEnd = new Runnable() {
		@Override
		public void run() {
			rl_yingji.addView(view5, 0);
			translateAnim = new TranslateAnimation(0f, -view4.getWidth(), 0f, 0f);
			translateAnim.setDuration(mDuration);
			translateAnim.setFillAfter(true);
			view4.startAnimation(translateAnim);
			translateAnim.setAnimationListener(YingjiActivity.this);
		}
	};
	
	private void startSetAnim() {
		Animation alpha = new AlphaAnimation(1.0f, 0.1f);
		alpha.setDuration(mDuration);
		alpha.setFillAfter(true);
		
		Animation translate = new TranslateAnimation(1.0f, -200f, 1.0f, 1.0f);
		translate.setDuration(mDuration);
		translate.setFillAfter(true);
		
		Animation scale = new ScaleAnimation(1.0f, 1.0f, 1.0f, 0.5f);
		scale.setDuration(mDuration);
		scale.setFillAfter(true);
		
		Animation rotate = new RotateAnimation(0f,  360f, Animation.RELATIVE_TO_SELF, 
				0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		rotate.setDuration(mDuration);
		rotate.setFillAfter(true);

		setAnim = new AnimationSet(true);
		((AnimationSet) setAnim).addAnimation(alpha);
		((AnimationSet) setAnim).addAnimation(translate);
		((AnimationSet) setAnim).addAnimation(scale);
		((AnimationSet) setAnim).addAnimation(rotate);
		setAnim.setFillAfter(true);
		view5.startAnimation(setAnim);
		setAnim.setAnimationListener(this);
	}

	@Override
	public void onAnimationCancel(Animator animation) {
	}

	@Override
	public void onAnimationRepeat(Animator animation) {
	}

	@Override
	public void onAnimationStart(Animation animation) {
		if (animation.equals(translateAnim)) {
			tv_anim_title.setText("正在播放平移动画");
		} else if (animation.equals(setAnim)) {
			tv_anim_title.setText("正在播放集合动画");
		}
	}

	@Override
	public void onAnimationEnd(Animation animation) {
		if (animation.equals(translateAnim)) {
			rl_yingji.removeView(view4);
			rl_yingji.addView(view6, 0);
			startSetAnim();
		} else if (animation.equals(setAnim)) {
			rl_yingji.removeView(view5);
			tv_anim_title.setText("动感影集播放结束，谢谢观看");
			view6.setOnClickListener(this);
		}
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
	}

	@Override
	public void onClick(View v) {
		if (v.equals(view6)) {
			playYingji();
		}
	}

}
