package com.gs.learn.animation;

import com.gs.learn.animation.widget.SwingAnimation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.widget.ImageView;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/11/27.
 */
public class SwingAnimActivity extends AppCompatActivity implements OnClickListener {
	private ImageView iv_swing;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_swing_anim);
		iv_swing = (ImageView) findViewById(R.id.iv_swing);
		findViewById(R.id.ll_swing).setOnClickListener(this);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		showSwingAnimation();
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.ll_swing) {
			showSwingAnimation();
		}
	}
	
	private void showSwingAnimation() {
		//参数取值说明：中间度数、摆到左侧的度数、摆到右侧的度数、圆心X坐标类型、圆心X坐标相对比例、圆心Y坐标类型、圆心Y坐标相对比例
		//坐标类型有三种：ABSOLUTE 绝对坐标，RELATIVE_TO_SELF 相对自身的坐标，RELATIVE_TO_PARENT 相对上级视图的坐标
		//X坐标相对比例，为0时表示左边顶点，为1表示右边顶点，为0.5表示水平中心点
		//Y坐标相对比例，为0时表示上边顶点，为1表示下边顶点，为0.5表示垂直中心点
		SwingAnimation swingAnimation = new SwingAnimation(
				0f, 60f, -60f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.0f); 
		swingAnimation.setDuration(4000);     //动画持续时间
		swingAnimation.setRepeatCount(0);     //动画重播次数
		swingAnimation.setFillAfter(false);  //是否保持动画结束画面
		swingAnimation.setStartOffset(500);   //动画播放延迟
		iv_swing.startAnimation(swingAnimation);
//		iv_swing.clearAnimation();
//		iv_swing.setAnimation(swingAnimation);
//		swingAnimation.startNow();
//		//setAnimation可能只会播放一次或不播放。如果想点击播放，要用startAnimation
//		iv_swing.setAnimation(swingAnimation);
//		swingAnimation.startNow();
	}

}
