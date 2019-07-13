package com.gs.learn.device;

import java.util.List;

import com.gs.learn.device.widget.CompassView;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import com.gs.learn.R;
/**
 * Created by ouyangshen on 2016/11/4.
 */
public class DirectionActivity extends AppCompatActivity implements SensorEventListener {
	private TextView tv_direction;
	private CompassView cv_sourth;
	private SensorManager mSensroMgr;
	private float[] mAcceValues;
	private float[] mMagnValues;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_direction);
		tv_direction = (TextView) findViewById(R.id.tv_direction);
		cv_sourth = (CompassView) findViewById(R.id.cv_sourth);
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
			if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
				suitable += 1;
			} else if (sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
				suitable += 10;
			}
		}
		if (suitable/10>0 && suitable%10>0) {
			mSensroMgr.registerListener(this,
					mSensroMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
					SensorManager.SENSOR_DELAY_NORMAL);
			mSensroMgr.registerListener(this,
					mSensroMgr.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
					SensorManager.SENSOR_DELAY_NORMAL);
		} else {
			cv_sourth.setVisibility(View.GONE);
			tv_direction.setText("当前设备不支持指南针，请检查是否存在加速度传感器和磁场传感器");
		}
	}
  
	@Override
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			mAcceValues = event.values;
		} else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
			mMagnValues = event.values;
		}
		if (mAcceValues!=null && mMagnValues!=null) {
			calculateOrientation();
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}
	
	// 计算方向
	private void calculateOrientation() {
		float[] values = new float[3];
		float[] R = new float[9];
		SensorManager.getRotationMatrix(R, null, mAcceValues, mMagnValues);
		SensorManager.getOrientation(R, values);
		values[0] = (float) Math.toDegrees(values[0]);
		cv_sourth.setDirection((int) values[0]);
		if (values[0] >= -10 && values[0] < 10) {
			tv_direction.setText("手机上部方向是正北");
		} else if (values[0] >= 10 && values[0] < 80) {
			tv_direction.setText("手机上部方向是东北");
		} else if (values[0] >= 80 && values[0] <= 100) {
			tv_direction.setText("手机上部方向是正东");
		} else if (values[0] >= 100 && values[0] < 170) {
			tv_direction.setText("手机上部方向是东南");
		} else if ((values[0] >= 170 && values[0] <= 180)
				|| (values[0]) >= -180 && values[0] < -170) {
			tv_direction.setText("手机上部方向是正南");
		} else if (values[0] >= -170 && values[0] < -100) {
			tv_direction.setText("手机上部方向是西南");
		} else if (values[0] >= -100 && values[0] < -80) {
			tv_direction.setText("手机上部方向是正西");
		} else if (values[0] >= -80 && values[0] < -10) {
			tv_direction.setText("手机上部方向是西北");
		}
	}
}
