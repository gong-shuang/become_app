package com.gs.learn.performance;

import com.gs.learn.performance.util.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/12/27.
 */
public class BatteryInfoActivity extends AppCompatActivity {
	private TextView tv_battery_change;
	private static TextView tv_power_status;
	private static String[] mStatus = { "不存在", "未知", "正在充电", "正在断电", "不在充电", "充满" };
	private static String[] mHealthy = { "不存在", "未知", "良好", "过热", "坏了", "短路", "未知错误", "冷却" };
	private static String[] mPlugged = { "电池", "充电器", "USB", "不存在", "无线" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_battery_info);
		tv_battery_change = (TextView) findViewById(R.id.tv_battery_change);
		tv_power_status = (TextView) findViewById(R.id.tv_power_status);
	}

	@Override
	protected void onStart() {
		super.onStart();
		batteryChangeReceiver = new BatteryChangeReceiver();
		IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		registerReceiver(batteryChangeReceiver, filter);
	}

	@Override
	protected void onStop() {
		super.onStop();
		unregisterReceiver(batteryChangeReceiver);
	}

	private BatteryChangeReceiver batteryChangeReceiver;

	private class BatteryChangeReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent != null) {
				int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
				int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
				int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, 0);
				int healthy = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0);
				int voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0);
				int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 3);
				String technology = intent.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY);
				int temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0);
				boolean present = intent.getBooleanExtra(BatteryManager.EXTRA_PRESENT, false);

				String desc = String.format("%s : 收到广播：%s", Utils.getNowTime(), intent.getAction());
				desc = String.format("%s\n电量刻度=%d", desc, scale);
				desc = String.format("%s\n当前电量=%d", desc, level);
				desc = String.format("%s\n当前状态=%s", desc, mStatus[status]);
				desc = String.format("%s\n健康程度=%s", desc, mHealthy[healthy]);
				desc = String.format("%s\n当前电压=%d", desc, voltage);
				desc = String.format("%s\n当前电源=%s", desc, mPlugged[plugged]);
				desc = String.format("%s\n当前技术=%s", desc, technology);
				desc = String.format("%s\n当前温度=%d", desc, temperature / 10);
				desc = String.format("%s\n是否提供电池=%s", desc, present ? "是" : "否");
				tv_battery_change.setText(desc);
			}
		}
	}

}
