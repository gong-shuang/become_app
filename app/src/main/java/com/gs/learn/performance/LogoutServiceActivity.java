package com.gs.learn.performance;

import com.gs.learn.performance.util.Utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
public class LogoutServiceActivity extends AppCompatActivity implements OnClickListener {
	private static final String TAG = "LogoutServiceActivity";
	private boolean bRun = false;
	private CheckBox ck_logout;
	private Button btn_alarm;
	private static TextView tv_alarm;
	private PendingIntent pIntent;
	private AlarmManager mAlarmManager;
	private static String mDesc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logout_service);
		ck_logout = (CheckBox) findViewById(R.id.ck_logout);
		tv_alarm = (TextView) findViewById(R.id.tv_alarm);
		btn_alarm = (Button) findViewById(R.id.btn_alarm);
		btn_alarm.setOnClickListener(this);
		Intent intent = new Intent(SERVICE_EVENT);
		pIntent = PendingIntent.getBroadcast(this, 0, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		mDesc = "";
		TextView tv_start = (TextView) findViewById(R.id.tv_start);
		tv_start.setText("页面打开时间为："+Utils.getNowTime());
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (ck_logout.isChecked() == true) {
			mAlarmManager.cancel(pIntent);
		}
	}
	
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_alarm) {
			if (bRun != true) {
				mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, 
						System.currentTimeMillis(), 3000, pIntent);
				mDesc = Utils.getNowTime() + " 设置闹钟";
				tv_alarm.setText(mDesc);
				btn_alarm.setText("取消闹钟");
			} else {
				mAlarmManager.cancel(pIntent);
				btn_alarm.setText("设置闹钟");
			}
			bRun = !bRun;
		}
	}

	private String SERVICE_EVENT = "com.gs.learn.performance.service1";
	public static class ServiceReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent != null) {
				Log.d(TAG, "ServiceReceiver onReceive");
				if (tv_alarm != null) {
					mDesc = String.format("%s\n%s 闹钟时间到达", mDesc, Utils.getNowTime());
					tv_alarm.setText(mDesc);
				}
			}
		}
	}

}
