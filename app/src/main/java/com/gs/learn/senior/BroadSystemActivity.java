package com.gs.learn.senior;

import java.util.Set;

import com.gs.learn.senior.util.DateUtil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.gs.learn.R;
/**
 * Created by ouyangshen on 2016/10/7.
 */
public class BroadSystemActivity extends AppCompatActivity {

	private static TextView tv_system;
	private static String desc = "开始侦听分钟广播，请稍等";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_broadcast_system);
		tv_system = (TextView) findViewById(R.id.tv_system);
		tv_system.setText(desc);
	}

	@Override
	protected void onStart() {
		super.onStart();
		timeReceiver = new TimeReceiver();
		IntentFilter filter = new IntentFilter(Intent.ACTION_TIME_TICK);
		registerReceiver(timeReceiver, filter);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		unregisterReceiver(timeReceiver);
	}

    private TimeReceiver timeReceiver;
	public static class TimeReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent != null) {
				Bundle bundle = intent.getExtras();
				Set<String> key_set = bundle.keySet();
				String content = "";
				for (String key : key_set) {
					content = String.format("%s\n%s=%s", content, key, bundle.get(key));
				}
				desc = String.format("%s\n%s 收到一个%s广播, 内容是%s", desc, 
						DateUtil.getNowTime(), intent.getAction(), content);
				tv_system.setText(desc);
			}
		}
		
	}

}
