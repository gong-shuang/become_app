package com.gs.learn.animation;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/11/27.
 */
public class FadeAnimActivity extends AppCompatActivity implements OnClickListener {
	private ImageView iv_fade_anim;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fade_anim);
		iv_fade_anim = (ImageView) findViewById(R.id.iv_fade_anim);
		iv_fade_anim.setOnClickListener(this);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		showFadeAnimation();
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.iv_fade_anim) {
			showFadeAnimation();
		}
	}
	
	private void showFadeAnimation() {
		//淡入淡出动画需要先设置一个Drawable数组，用于变换图片
		Drawable[] drawableArray = {
				getResources().getDrawable(R.drawable.fade_begin),
				getResources().getDrawable(R.drawable.fade_end)
				};
		TransitionDrawable td_fade = new TransitionDrawable(drawableArray);
		iv_fade_anim.setImageDrawable(td_fade);
		td_fade.startTransition(3000);
	}

}
