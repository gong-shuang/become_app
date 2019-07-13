package com.gs.learn.performance;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.gs.learn.MainApplication;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/12/27.
 */
public class PowerSavingActivity extends AppCompatActivity {
	private static TextView tv_screen;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_power_saving);
		tv_screen = (TextView) findViewById(R.id.tv_screen);
	}

	@Override
	protected void onStart() {
		super.onStart();
		tv_screen.setText(MainApplication.getInstance().getChangeDesc());
	}

}
