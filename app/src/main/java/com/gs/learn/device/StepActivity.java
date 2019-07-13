package com.gs.learn.device;

import java.util.List;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.gs.learn.R;
/**
 * Created by ouyangshen on 2016/11/4.
 */
public class StepActivity extends AppCompatActivity implements SensorEventListener {
	private TextView tv_step;
	private SensorManager mSensroMgr;
	private int mStepDetector = 0;
	private int mStepCounter = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_step);
		tv_step = (TextView) findViewById(R.id.tv_step);
		mSensroMgr = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
	}

	@Override
	protected void onPause() {
		super.onPause();
		mSensroMgr.unregisterListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		int suitable = 0;
		List<Sensor> sensorList = mSensroMgr.getSensorList(Sensor.TYPE_ALL);
		for (Sensor sensor : sensorList) {
			if (sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
				suitable += 1;
			} else if (sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
				suitable += 10;
			}
		}
		if (suitable/10>0 && suitable%10>0) {
			mSensroMgr.registerListener(this,
					mSensroMgr.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR),
					SensorManager.SENSOR_DELAY_NORMAL);
			mSensroMgr.registerListener(this,
					mSensroMgr.getDefaultSensor(Sensor.TYPE_STEP_COUNTER),
					SensorManager.SENSOR_DELAY_NORMAL);
		} else {
			tv_step.setText("当前设备不支持计步器，请检查是否存在步行检测传感器和计步器传感器");
		}
	}
  
	@Override
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
			if (event.values[0] == 1.0f) {
				mStepDetector++;
			}
		} else if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
			mStepCounter = (int) event.values[0];
		}
		String desc = String.format("设备检测到您当前走了%d步，总计数为%d步", 
				mStepDetector, mStepCounter);
		tv_step.setText(desc);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}
	
}
