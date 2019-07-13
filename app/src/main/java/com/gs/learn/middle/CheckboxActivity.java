package com.gs.learn.middle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;


import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/9/24.
 */
public class CheckboxActivity extends AppCompatActivity implements OnCheckedChangeListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checkbox);
		CheckBox ck_system = (CheckBox) findViewById(R.id.ck_system);
		CheckBox ck_custom = (CheckBox) findViewById(R.id.ck_custom);
		ck_system.setOnCheckedChangeListener(this);
		ck_custom.setOnCheckedChangeListener(this);
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		String desc = String.format("您%s了这个CheckBox", isChecked?"勾选":"取消勾选");
		buttonView.setText(desc);
	}

}
