package com.gs.learn.device;

import java.util.ArrayList;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.gs.learn.device.widget.BettingView;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/11/4.
 */
public class FindShakeActivity extends AppCompatActivity implements SensorEventListener {
	private final static String TAG = "FindShakeActivity";
	private TextView tv_cake;
	private BettingView bv_cake;
	private SensorManager mSensroMgr;
	private Vibrator mVibrator;
	private ArrayList<Integer> mDiceList = new ArrayList<Integer>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_find_shake);
		tv_cake = (TextView) findViewById(R.id.tv_cake);
		bv_cake = (BettingView) findViewById(R.id.bv_cake);
		mSensroMgr = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		for (int i=0; i<6; i++) {
			mDiceList.add(i);
		}
		bv_cake.setDiceList(mDiceList);
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
				if (bShake != true) {
					bShake = true;
					mCount = 0;
					mHandler.post(mShake);
					mVibrator.vibrate(500);
				}
			}
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

	private boolean bShake = false;
	private int mCount = 0;
	private Handler mHandler = new Handler();
	
	private Runnable mShake = new Runnable() {
		@Override
		public void run() {
			if (mCount < 10) {
				mCount++;
				bv_cake.setRandom();
				mHandler.postDelayed(this, 150);
			} else {
				mDiceList = new ArrayList<Integer>();
				for (int i=0; i<6; i++) {
					mDiceList.add((int)(Math.random()*30%6));
				}
				bv_cake.setDiceList(mDiceList);
				String desc = calculatePrize();
				tv_cake.setText("恭喜，您的博饼结果为："+desc);
				bShake = false;
			}
		}
	};

	private String calculatePrize() {
		int four_count = checkCount(4);
		if (four_count == 6) {
			return "状元(六杯红)";
		} else if (checkCount(1) == 6) {
			return "状元(遍地锦)";
		} else if (four_count == 5) {
			return "状元(五红)";
		} else if (four_count == 4) {
			if (checkCount(1) == 2) {
				return "状元插金花";
			} else {
				return "状元(四点红)";
			}
		} else if (four_count == 3) {
			return "三红";
		} else if (checkCount(6) == 6) {
			return "黑六勃";
		} else if (checkCount(1)==1 && checkCount(2)==1 && checkCount(3)==1
				&& checkCount(4)==1 && checkCount(5)==1 && checkCount(6)==1) {
			return "对堂";
		} else if (checkCount(1)==5 || checkCount(2)==5 || checkCount(3)==5
				|| checkCount(5)==5 || checkCount(6)==5) {
			return "状元(五子登科)";
		} else if (checkCount(1)==4 || checkCount(2)==4 || checkCount(3)==4
				|| checkCount(5)==4 || checkCount(6)==4) {
			return "四进";
		} else if (four_count == 2) {
			return "二举";
		} else if (four_count == 1) {
			return "一秀";
		} else {
			return "别着急，再来一次";
		}
	}
	
	private int checkCount(int number) {
		int count = 0;
		for (int i=0; i<mDiceList.size(); i++) {
			if (mDiceList.get(i)+1 == number) {
				count++;
			}
		}
		return count;
	}
	
}
