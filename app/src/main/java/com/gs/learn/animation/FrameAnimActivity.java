package com.gs.learn.animation;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/11/27.
 */
public class FrameAnimActivity extends AppCompatActivity implements OnClickListener {
	private ImageView iv_frame_anim;
	private AnimationDrawable ad_frame;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_frame_anim);
		iv_frame_anim = (ImageView) findViewById(R.id.iv_frame_anim);
		iv_frame_anim.setOnClickListener(this);
		showFrameAnimByCode();
		//showFrameAnimByXml();
	}

	private void showFrameAnimByCode() {
		//帧动画需要把每帧图片加入AnimationDrawable队列
		ad_frame = new AnimationDrawable();
		ad_frame.addFrame(getResources().getDrawable(R.drawable.flow_p1), 50);
		ad_frame.addFrame(getResources().getDrawable(R.drawable.flow_p2), 50);
		ad_frame.addFrame(getResources().getDrawable(R.drawable.flow_p3), 50);
		ad_frame.addFrame(getResources().getDrawable(R.drawable.flow_p4), 50);
		ad_frame.addFrame(getResources().getDrawable(R.drawable.flow_p5), 50);
		ad_frame.addFrame(getResources().getDrawable(R.drawable.flow_p6), 50);
		ad_frame.addFrame(getResources().getDrawable(R.drawable.flow_p7), 50);
		ad_frame.addFrame(getResources().getDrawable(R.drawable.flow_p8), 50);
		//setOneShot为true表示只播放一次，为false表示循环播放
		ad_frame.setOneShot(false);
		iv_frame_anim.setImageDrawable(ad_frame);
		ad_frame.start();
	}

	private void showFrameAnimByXml() {
		iv_frame_anim.setImageResource(R.drawable.frame_anim);
		ad_frame = (AnimationDrawable) iv_frame_anim.getDrawable();
		ad_frame.start();
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.iv_frame_anim) {
			if (ad_frame.isRunning() == true) {
				ad_frame.stop();
			} else {
				ad_frame.start();
			}
		}
	}

}
