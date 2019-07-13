package com.gs.learn.animation;

import com.gs.learn.animation.widget.ExpandTextLayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/11/27.
 */
public class ExpandActivity extends AppCompatActivity {
	private ExpandTextLayout etl_content;
	private int[] newsArray = {R.string.news1, R.string.news2, R.string.news3, R.string.news4};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_expand);
		etl_content = (ExpandTextLayout) findViewById(R.id.etl_content);
		int seq = (int) (Math.random()*100%4);
		etl_content.setText(newsArray[seq]);
	}
}
