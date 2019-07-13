package com.gs.learn.device;

import com.gs.learn.device.util.SwitchUtil;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.gs.learn.R;
/**
 * Created by ouyangshen on 2016/11/4.
 */
public class LocationSettingActivity extends AppCompatActivity implements OnCheckedChangeListener {
	private CheckBox ck_gps, ck_wlan, ck_mobiledata;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_location_setting);
		
		ck_gps = (CheckBox) findViewById(R.id.ck_gps);
		ck_wlan = (CheckBox) findViewById(R.id.ck_wlan);
		ck_mobiledata = (CheckBox) findViewById(R.id.ck_mobiledata);
		if (SwitchUtil.getGpsStatus(this) == true) {
			ck_gps.setChecked(true);
		}
		if (SwitchUtil.getWlanStatus(this) == true) {
			ck_wlan.setChecked(true);
		}
		if (SwitchUtil.getMobileDataStatus(this) == true) {
			ck_mobiledata.setChecked(true);
		}
		ck_gps.setOnCheckedChangeListener(this);
		ck_wlan.setOnCheckedChangeListener(this);
		ck_mobiledata.setOnCheckedChangeListener(this);
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		int id = buttonView.getId();
		if (id == R.id.ck_gps) {
			SwitchUtil.setGpsStatus(this, isChecked);
		} else if (id == R.id.ck_wlan) {
			SwitchUtil.setWlanStatus(this, isChecked);
		} else if (id == R.id.ck_mobiledata) {
			SwitchUtil.setMobileDataStatus(this, isChecked);
		}
	}

}
