package com.gs.learn.mixture;

import com.gs.learn.mixture.service.ImportService;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/12/11.
 */
public class MixtureMainActivity extends AppCompatActivity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_mixture);

		findViewById(R.id.btn_assets_text).setOnClickListener(this);
		findViewById(R.id.btn_assets_image).setOnClickListener(this);
		findViewById(R.id.btn_web_local).setOnClickListener(this);
		findViewById(R.id.btn_web_span).setOnClickListener(this);
		findViewById(R.id.btn_web_browser).setOnClickListener(this);
		findViewById(R.id.btn_jni_cpu).setOnClickListener(this);
		findViewById(R.id.btn_jni_secret).setOnClickListener(this);
		findViewById(R.id.btn_wifi_info).setOnClickListener(this);
		findViewById(R.id.btn_wifi_connect).setOnClickListener(this);
		findViewById(R.id.btn_bluetooth).setOnClickListener(this);
		findViewById(R.id.btn_wifi_share).setOnClickListener(this);
		mHandler.postDelayed(mService, 100);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_assets_text) {
			Intent intent = new Intent(this, AssetsTextActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_assets_image) {
			Intent intent = new Intent(this, AssetsImageActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_web_local) {
			Intent intent = new Intent(this, WebLocalActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_web_span) {
			Intent intent = new Intent(this, WebSpanActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_web_browser) {
			Intent intent = new Intent(this, WebBrowserActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_jni_cpu) {
			Intent intent = new Intent(this, JniCpuActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_jni_secret) {
			Intent intent = new Intent(this, JniSecretActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_wifi_info) {
			Intent intent = new Intent(this, WifiInfoActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_wifi_connect) {
			Intent intent = new Intent(this, WifiConnectActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_bluetooth) {
			Intent intent = new Intent(this, BluetoothActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_wifi_share) {
			Intent intent = new Intent(this, WifiShareActivity.class);
			startActivity(intent);
		}
	}

	private Handler mHandler = new Handler();
	private Runnable mService = new Runnable() {
		@Override
		public void run() {
			Intent intent = new Intent(MixtureMainActivity.this, ImportService.class);
			startService(intent);
		}
	};
	
}
