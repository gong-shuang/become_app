package com.gs.learn.event;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/11/23.
 */
public class EventMainActivity extends AppCompatActivity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_event);

		findViewById(R.id.btn_key_soft).setOnClickListener(this);
		findViewById(R.id.btn_key_hard).setOnClickListener(this);
		findViewById(R.id.btn_volume_set).setOnClickListener(this);
		findViewById(R.id.btn_gesture_detector).setOnClickListener(this);
		findViewById(R.id.btn_view_flipper).setOnClickListener(this);
		findViewById(R.id.btn_banner_flipper).setOnClickListener(this);
		findViewById(R.id.btn_event_dispatch).setOnClickListener(this);
		findViewById(R.id.btn_event_intercept).setOnClickListener(this);
		findViewById(R.id.btn_touch_single).setOnClickListener(this);
		findViewById(R.id.btn_touch_multiple).setOnClickListener(this);
		findViewById(R.id.btn_signature).setOnClickListener(this);
		findViewById(R.id.btn_custom_scroll).setOnClickListener(this);
		findViewById(R.id.btn_disallow_scroll).setOnClickListener(this);
		findViewById(R.id.btn_drawer_layout).setOnClickListener(this);
		findViewById(R.id.btn_image_change).setOnClickListener(this);
		findViewById(R.id.btn_image_cut).setOnClickListener(this);
		findViewById(R.id.btn_meitu).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_key_soft) {
			Intent intent = new Intent(this, KeySoftActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_key_hard) {
			Intent intent = new Intent(this, KeyHardActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_volume_set) {
			Intent intent = new Intent(this, VolumeSetActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_gesture_detector) {
			Intent intent = new Intent(this, GestureDetectorActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_view_flipper) {
			Intent intent = new Intent(this, ViewFlipperActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_banner_flipper) {
			Intent intent = new Intent(this, BannerFlipperActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_event_dispatch) {
			Intent intent = new Intent(this, EventDispatchActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_event_intercept) {
			Intent intent = new Intent(this, EventInterceptActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_touch_single) {
			Intent intent = new Intent(this, TouchSingleActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_touch_multiple) {
			Intent intent = new Intent(this, TouchMultipleActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_signature) {
			Intent intent = new Intent(this, SignatureActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_custom_scroll) {
			Intent intent = new Intent(this, CustomScrollActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_disallow_scroll) {
			Intent intent = new Intent(this, DisallowScrollActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_drawer_layout) {
			Intent intent = new Intent(this, DrawerLayoutActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_image_change) {
			Intent intent = new Intent(this, ImageChangeActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_image_cut) {
			Intent intent = new Intent(this, ImageCutActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_meitu) {
			Intent intent = new Intent(this, MeituActivity.class);
			startActivity(intent);
		}
	}

	private boolean bExit = false;
	
//	@Override
//	public void onBackPressed() {
//		if (bExit) {
//			finish();
//			return;
//		}
//		bExit = true;
//		Toast.makeText(this, "再按一次返回键退出!", Toast.LENGTH_SHORT).show();
//	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (bExit) {
				finish();
			}
			bExit = true;
			Toast.makeText(this, "再按一次返回键退出!", Toast.LENGTH_SHORT).show();
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}
	
}
