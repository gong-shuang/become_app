package com.gs.learn.network;

import com.gs.learn.network.service.AsyncService;
import com.gs.learn.network.util.DateUtil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/11/11.
 */
public class IntentServiceActivity extends AppCompatActivity implements OnClickListener {
	private TextView tv_intent;
	private Handler mHandler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_intent_service);
		tv_intent = (TextView) findViewById(R.id.tv_intent);
		findViewById(R.id.btn_intent).setOnClickListener(this);
		mHandler.postDelayed(mService, 100);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_intent) {
			tv_intent.setText(DateUtil.getNowTime()+" 您轻轻点了一下下(异步服务正在运行，不影响您在界面操作)");
		}
	}
	
	private Runnable mService = new Runnable() {
		@Override
		public void run() {
			Intent intent = new Intent(IntentServiceActivity.this, AsyncService.class);
			startService(intent);
		}
	};
	
}
