package com.gs.learn.network;

import com.gs.learn.network.task.GetImageCodeTask;
import com.gs.learn.network.task.GetImageCodeTask.OnImageCodeListener;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/11/11.
 */
public class HttpImageActivity extends AppCompatActivity implements 
		OnClickListener, OnImageCodeListener {
	
	private ImageView iv_image_code;
	private boolean bRunning = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_http_image);
		iv_image_code = (ImageView) findViewById(R.id.iv_image_code);
		iv_image_code.setOnClickListener(this);
		getImageCode();
	}
	
	private void getImageCode() {
		if (bRunning != true) {
			bRunning = true;
			GetImageCodeTask codeTask = new GetImageCodeTask(this);
			codeTask.setOnImageCodeListener(this);
			codeTask.execute();
		}
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.iv_image_code) {
			getImageCode();
		}
	}

	@Override
	public void onGetCode(String path) {
		Uri uri = Uri.parse(path);
		iv_image_code.setImageURI(uri);
		bRunning = false;
	}

}
