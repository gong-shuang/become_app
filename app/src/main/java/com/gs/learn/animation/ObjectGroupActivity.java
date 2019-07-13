package com.gs.learn.animation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/11/27.
 */
public class ObjectGroupActivity extends AppCompatActivity implements OnClickListener {
	private final static String TAG = "ObjectGroupActivity";
	private ImageView iv_object_group;
	private AnimatorSet animSet;
	private boolean bPause = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_object_group);
		iv_object_group = (ImageView) findViewById(R.id.iv_object_group);
		iv_object_group.setOnClickListener(this);
		initAnim();
	}
	
	private void initAnim() {
		ObjectAnimator anim1 = ObjectAnimator.ofFloat(iv_object_group, "translationX", 0f, 100f);
		ObjectAnimator anim2 = ObjectAnimator.ofFloat(iv_object_group, "alpha", 1f, 0.1f, 1f, 0.5f, 1f);
		ObjectAnimator anim3 = ObjectAnimator.ofFloat(iv_object_group, "rotation", 0f, 360f);
		ObjectAnimator anim4 = ObjectAnimator.ofFloat(iv_object_group, "scaleY", 1f, 0.5f, 1f);
		ObjectAnimator anim5 = ObjectAnimator.ofFloat(iv_object_group, "translationX", 100f, 0f);
		animSet = new AnimatorSet();
		AnimatorSet.Builder builder = animSet.play(anim2);
		// anim1先执行，然后再同步执行anim2、anim3、anim3，最后执行anim5
		builder.with(anim3).with(anim4).after(anim1).before(anim5);
		animSet.setDuration(4500);
		animSet.start();
	}

	@TargetApi(Build.VERSION_CODES.KITKAT)
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.iv_object_group) {
			if (animSet.isStarted() == true) {
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
					if (animSet.isRunning() == true) {
						if (bPause != true) {
							animSet.pause();
						} else {
							animSet.resume();
						}
						bPause = !bPause;
					} else {
						animSet.start();
					}
				} else {
					if (animSet.isRunning() == true) {
						animSet.end();
					} else {
						animSet.start();
					}
				}
			} else {
				animSet.start();
			}
		}
	}

}
