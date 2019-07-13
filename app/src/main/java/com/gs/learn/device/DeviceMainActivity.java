package com.gs.learn.device;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/11/4.
 */
public class DeviceMainActivity extends AppCompatActivity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_device);

		findViewById(R.id.btn_turn_view).setOnClickListener(this);
		findViewById(R.id.btn_turn_surface).setOnClickListener(this);
		findViewById(R.id.btn_camera_info).setOnClickListener(this);
		findViewById(R.id.btn_photograph).setOnClickListener(this);
		findViewById(R.id.btn_trun_texture).setOnClickListener(this);
		findViewById(R.id.btn_shooting).setOnClickListener(this);
		findViewById(R.id.btn_seekbar).setOnClickListener(this);
		findViewById(R.id.btn_volumn).setOnClickListener(this);
		findViewById(R.id.btn_audio).setOnClickListener(this);
		findViewById(R.id.btn_video).setOnClickListener(this);
		findViewById(R.id.btn_sensor).setOnClickListener(this);
		findViewById(R.id.btn_acceleration).setOnClickListener(this);
		findViewById(R.id.btn_light).setOnClickListener(this);
		findViewById(R.id.btn_direction).setOnClickListener(this);
		findViewById(R.id.btn_step).setOnClickListener(this);
		findViewById(R.id.btn_flash).setOnClickListener(this);
		findViewById(R.id.btn_location_setting).setOnClickListener(this);
		findViewById(R.id.btn_location_begin).setOnClickListener(this);
		findViewById(R.id.btn_wechat).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_turn_view) {
			Intent intent = new Intent(this, TurnViewActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_turn_surface) {
			Intent intent = new Intent(this, TurnSurfaceActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_camera_info) {
			Intent intent = new Intent(this, CameraInfoActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_photograph) {
			Intent intent = new Intent(this, PhotographActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_trun_texture) {
			Intent intent = new Intent(this, TurnTextureActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_shooting) {
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
				Toast.makeText(DeviceMainActivity.this, "Andorid版本低于5.0",
						Toast.LENGTH_SHORT).show();
			} else {
				Intent intent = new Intent(this, ShootingActivity.class);
				startActivity(intent);
			}
		} else if (v.getId() == R.id.btn_seekbar) {
			Intent intent = new Intent(this, SeekbarActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_volumn) {
			Intent intent = new Intent(this, VolumnActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_audio) {
			Intent intent = new Intent(this, AudioActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_video) {
			Intent intent = new Intent(this, VideoActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_sensor) {
			Intent intent = new Intent(this, SensorActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_acceleration) {
			Intent intent = new Intent(this, AccelerationActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_light) {
			Intent intent = new Intent(this, LightActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_direction) {
			Intent intent = new Intent(this, DirectionActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_step) {
			Intent intent = new Intent(this, StepActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_flash) {
			Intent intent = new Intent(this, FlashActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_location_setting) {
			Intent intent = new Intent(this, LocationSettingActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_location_begin) {
			Intent intent = new Intent(this, LocationActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_wechat) {
			Intent intent = new Intent(this, WeChatActivity.class);
			startActivity(intent);
		}
	}

}
