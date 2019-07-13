package com.gs.learn.performance;

import com.gs.learn.performance.util.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/12/27.
 */
public class RemoveTaskActivity extends AppCompatActivity implements OnClickListener {
	private final static String TAG = "RemoveTaskActivity";
	private boolean bRun = false;
	private CheckBox ck_remove;
	private TextView tv_remove;
	private Button btn_remove;
	private String mDesc = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_remove_task);
		ck_remove = (CheckBox) findViewById(R.id.ck_remove);
		tv_remove = (TextView) findViewById(R.id.tv_remove);
		btn_remove = (Button) findViewById(R.id.btn_remove);
		btn_remove.setOnClickListener(this);
		TextView tv_start = (TextView) findViewById(R.id.tv_start);
		tv_start.setText("页面打开时间为："+Utils.getNowTime());
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_remove) {
			if (bRun != true) {
				btn_remove.setText("取消定时任务");
				mHandler.post(mTask);
			} else {
				btn_remove.setText("开始定时任务");
				mHandler.removeCallbacks(mTask);
			}
			bRun = !bRun;
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (ck_remove.isChecked() == true) {
			mHandler.removeCallbacks(mTask);
		}
	}
	
	private Handler mHandler = new Handler();
	private Runnable mTask = new Runnable() {
		@Override
		public void run() {
			Intent intent = new Intent(TASK_EVENT);
			LocalBroadcastManager.getInstance(RemoveTaskActivity.this).sendBroadcast(intent);
			mHandler.postDelayed(this, 2000);
		}
	};

	@Override
	public void onStart() {
		super.onStart();
		taskReceiver = new TaskReceiver();
		IntentFilter filter = new IntentFilter(TASK_EVENT);
		LocalBroadcastManager.getInstance(this).registerReceiver(taskReceiver, filter);
	}

	@Override
	public void onStop() {
		LocalBroadcastManager.getInstance(this).unregisterReceiver(taskReceiver);
		super.onStop();
	}

	private String TASK_EVENT = "com.gs.learn.performance.task";
	private TaskReceiver taskReceiver;
	private class TaskReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent != null) {
				mDesc = String.format("%s%s 打印了一行测试日志\n", mDesc, Utils.getNowTime());
				tv_remove.setText(mDesc);
			}
		}
	}

}
