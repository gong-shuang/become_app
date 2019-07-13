package com.gs.learn.network;

import com.gs.learn.network.task.GetAddressTask;
import com.gs.learn.network.task.GetAddressTask.OnAddressListener;
import com.gs.learn.network.util.DateUtil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/11/11.
 */
public class HttpRequestActivity extends AppCompatActivity implements OnAddressListener {
	private final static String TAG = "HttpRequestActivity";
	private TextView tv_location;
	private String mLocation="";
	private LocationManager mLocationMgr;
	private Criteria mCriteria = new Criteria();
	private Handler mHandler = new Handler();
	private boolean bLocationEnable = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_http_request);
		initWidget();
		initLocation();
		mHandler.postDelayed(mRefresh, 100);
	}

	private void initWidget() {
		tv_location = (TextView) findViewById(R.id.tv_location);
		mLocationMgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		mCriteria.setAccuracy(Criteria.ACCURACY_FINE);
		mCriteria.setAltitudeRequired(true);
		mCriteria.setBearingRequired(true);
		mCriteria.setCostAllowed(true);
		mCriteria.setPowerRequirement(Criteria.POWER_LOW);
	}

	private void initLocation() {
		String bestProvider = mLocationMgr.getBestProvider(mCriteria, true);
		if (bestProvider == null) {
			bestProvider = LocationManager.NETWORK_PROVIDER;
		}
		if (mLocationMgr.isProviderEnabled(bestProvider)) {
			tv_location.setText("正在获取"+bestProvider+"定位对象");
    		mLocation = String.format("定位类型=%s", bestProvider);
    		beginLocation(bestProvider);
			bLocationEnable = true;
		} else {
			tv_location.setText("\n"+bestProvider+"定位不可用");
			bLocationEnable = false;
		}
	}

	private String mAddress = "";
	@Override
	public void onFindAddress(String address) {
		mAddress = address;
	}
	
	@SuppressLint("DefaultLocale")
	private void setLocationText(Location location) {
		if (location != null) {
			String desc = String.format("%s\n定位对象信息如下： " +
					"\n\t其中时间：%s" + "\n\t其中经度：%f，纬度：%f" +
					"\n\t其中高度：%d米，精度：%d米" + "\n\t其中地址：%s", 
					mLocation, DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss"),
					location.getLongitude(), location.getLatitude(), 
					Math.round(location.getAltitude()), Math.round(location.getAccuracy()), mAddress);
			Log.d(TAG, desc);
			tv_location.setText(desc);
			GetAddressTask addressTask = new GetAddressTask();
			addressTask.setOnAddressListener(this);
			addressTask.execute(location);
		} else {
			tv_location.setText(mLocation+"\n暂未获取到定位对象");
		}
	}

    private void beginLocation(String method) {
		mLocationMgr.requestLocationUpdates(method, 3000, 0, mLocationListener);
		Location location = mLocationMgr.getLastKnownLocation(method);
		setLocationText(location);
    }

	// 位置监听器
	private LocationListener mLocationListener = new LocationListener() {
		@Override
		public void onLocationChanged(Location location) {
			setLocationText(location);
		}

		@Override
		public void onProviderDisabled(String arg0) {
		}

		@Override
		public void onProviderEnabled(String arg0) {
		}

		@Override
		public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		}
	};

	private Runnable mRefresh = new Runnable() {
		@Override
		public void run() {
			if (bLocationEnable == false) {
				initLocation();
				mHandler.postDelayed(this, 1000);
			}
		}
	};

	@Override
	protected void onDestroy() {
		if (mLocationMgr != null) {
			mLocationMgr.removeUpdates(mLocationListener);
		}
		super.onDestroy();
	}

}
