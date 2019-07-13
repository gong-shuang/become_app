package com.gs.learn.performance;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/12/27.
 */
public class BaseActivity extends AppCompatActivity {
	
	@Override
	protected void onResume() {
		super.onResume();
		Toolbar tl_head = (Toolbar) findViewById(R.id.tl_head);
		setSupportActionBar(tl_head);
		tl_head.setNavigationOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});
		findViewById(R.id.iv_share).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(BaseActivity.this, "请先实现分享功能噢", Toast.LENGTH_LONG).show();
			}
		});
	}
	
	protected void setTitle(String title) {
		TextView tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText(title);
	}

}
