package com.gs.learn.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;

import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/10/28.
 */
public class TestMainActivity extends AppCompatActivity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_test);

		findViewById(R.id.btn_version).setOnClickListener(this);
		findViewById(R.id.btn_encrypt).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_version) {
			Intent intent = new Intent(this, VersionActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_encrypt) {
			Intent intent = new Intent(this, EncryptActivity.class);
			startActivity(intent);
		}
	}

}
