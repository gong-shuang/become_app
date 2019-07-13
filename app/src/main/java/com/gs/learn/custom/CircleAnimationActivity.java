package com.gs.learn.custom;

import com.gs.learn.custom.widget.CircleAnimation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/10/14.
 */
public class CircleAnimationActivity extends AppCompatActivity implements OnClickListener {

	private CircleAnimation mAnimation;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_circle_animation);
		Button btn_play = (Button) findViewById(R.id.btn_play);
		btn_play.setOnClickListener(this);
		LinearLayout ll_layout = (LinearLayout) findViewById(R.id.ll_layout);
		mAnimation = new CircleAnimation(this);
		ll_layout.addView(mAnimation);
		mAnimation.render();
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_play) {
			mAnimation.play();
		}
	}

}
