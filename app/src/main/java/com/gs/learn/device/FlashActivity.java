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
public class FlashActivity extends AppCompatActivity implements OnCheckedChangeListener {
	private CheckBox ck_flash;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flash);
		ck_flash = (CheckBox) findViewById(R.id.ck_flash);
		if (SwitchUtil.getFlashStatus(this) == true) {
			ck_flash.setChecked(true);
		}
		ck_flash.setOnCheckedChangeListener(this);
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (buttonView.getId() == R.id.ck_flash) {
			SwitchUtil.setFlashStatus(this, isChecked);
		}
	}

}
