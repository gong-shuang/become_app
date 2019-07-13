package com.gs.learn.animation;

import com.gs.learn.animation.widget.PieAnimation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/11/27.
 */
public class PieActivity extends AppCompatActivity implements OnClickListener {
	private PieAnimation pa_circle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pie);
		pa_circle = (PieAnimation) findViewById(R.id.pa_circle);
		pa_circle.setOnClickListener(this);
		pa_circle.start();
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.pa_circle) {
			if (pa_circle.isRunning() != true) {
				pa_circle.start();
			}
		}
	}

}
