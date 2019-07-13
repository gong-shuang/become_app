package com.gs.learn.device;

import com.gs.learn.device.util.Utils;
import com.gs.learn.R;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by ouyangshen on 2016/11/4.
 */
public class AccelerationActivity extends AppCompatActivity implements SensorEventListener {
	private TextView tv_shake;
	private SensorManager mSensroMgr;
	private Vibrator mVibrator;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_acceleration);
		tv_shake = (TextView) findViewById(R.id.tv_shake);
		mSensroMgr = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
	}

	@Override
	protected void onPause() {
		super.onPause();
		mSensroMgr.unregisterListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		mSensroMgr.registerListener(this,
				mSensroMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_NORMAL);
	}
  
	@Override
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			// values[0]:X轴，values[1]：Y轴，values[2]：Z轴
			float[] values = event.values;
			if ((Math.abs(values[0]) > 15 || Math.abs(values[1]) > 15 
					|| Math.abs(values[2]) > 15)) {
				tv_shake.setText(Utils.getNowTime()+" 恭喜您摇一摇啦");
				//系统检测到摇一摇事件后，震动手机提示用户
				mVibrator.vibrate(500);
			}
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		//当传感器精度改变时回调该方法，一般无需处理
	}
	
}
