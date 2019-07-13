package com.gs.learn.animation;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ArgbEvaluator;
import android.animation.FloatEvaluator;
import android.animation.ObjectAnimator;
import android.animation.RectEvaluator;
import android.annotation.TargetApi;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;
import android.widget.Toast;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/11/27.
 */
public class InterpolatorActivity extends AppCompatActivity implements AnimatorListener {
	private TextView tv_interpolator;
	private ObjectAnimator animAcce, animDece, animLinear, animBounce;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_interpolator);
		tv_interpolator = (TextView) findViewById(R.id.tv_interpolator);
		initAnimator();
		initInterpolatorSpinner();
	}

	private void initInterpolatorSpinner() {
		ArrayAdapter<String> interpolatorAdapter = new ArrayAdapter<String>(this,
				R.layout.item_select, interpolatorArray);
		Spinner sp_interpolator = (Spinner) findViewById(R.id.sp_interpolator);
		sp_interpolator.setPrompt("请选择插值器类型");
		sp_interpolator.setAdapter(interpolatorAdapter);
		sp_interpolator.setOnItemSelectedListener(new InterpolatorSelectedListener());
		sp_interpolator.setSelection(0);
	}

	private String[] interpolatorArray={
			"背景色+加速插值器+颜色估值器", 
			"旋转+减速插值器+浮点型估值器", 
			"裁剪+匀速插值器+矩形估值器", 
			"文字大小+震荡插值器+浮点型估值器"};
	class InterpolatorSelectedListener implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			showInterpolator(arg2);
		}

		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}

	private void initAnimator() {
		animAcce = ObjectAnimator.ofInt(tv_interpolator, "backgroundColor", Color.RED, Color.LTGRAY);
		animAcce.setInterpolator(new AccelerateInterpolator());
		animAcce.setEvaluator(new ArgbEvaluator());

		animDece = ObjectAnimator.ofFloat(tv_interpolator, "rotation", 0f, 360f);
		animDece.setInterpolator(new DecelerateInterpolator());
		animDece.setEvaluator(new FloatEvaluator());

		animBounce = ObjectAnimator.ofFloat(tv_interpolator, "textSize", 20f, 60f);
		animBounce.setInterpolator(new BounceInterpolator());
		animBounce.setEvaluator(new FloatEvaluator());
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
	private void showInterpolator(int type) {
		ObjectAnimator anim = null;
		if (type == 0) {
			anim = animAcce;
		} else if (type == 1) {
			anim = animDece;
		} else if (type == 2) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
				int width = tv_interpolator.getWidth();
				int height = tv_interpolator.getHeight();
				animLinear = ObjectAnimator.ofObject(tv_interpolator, "clipBounds", 
						new RectEvaluator(), new Rect(0,0,width,height), 
						new Rect(width/3,height/3,width/3*2,height/3*2), 
						new Rect(0,0,width,height));
				animLinear.setInterpolator(new LinearInterpolator());
				anim = animLinear;
			} else {
				Toast.makeText(this, 
						"矩形估值器要求Android为4.3以上版本", Toast.LENGTH_SHORT).show();
				return;
			}
		} else if (type == 3) {
			anim = animBounce;
			// 添加监听器的目的是在动画结束时恢复文字大小
			anim.addListener(this);
		}
		if (anim != null) {
			anim.setDuration(2000);
			anim.start();
		}
	}

	@Override
	public void onAnimationStart(Animator animation) {
	}

	@Override
	public void onAnimationEnd(Animator animation) {
		if (((ObjectAnimator)animation).equals(animBounce)) {
			ObjectAnimator anim = ObjectAnimator.ofFloat(tv_interpolator, "textSize", 60f, 20f);
			anim.setInterpolator(new BounceInterpolator());
			anim.setEvaluator(new FloatEvaluator());
			anim.setDuration(2000);
			anim.start();
		}
	}

	@Override
	public void onAnimationCancel(Animator animation) {
	}

	@Override
	public void onAnimationRepeat(Animator animation) {
	}
	
}
