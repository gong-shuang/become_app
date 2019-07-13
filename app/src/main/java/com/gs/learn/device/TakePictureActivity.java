package com.gs.learn.device;

import com.gs.learn.device.widget.CameraView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import com.gs.learn.R;
/**
 * Created by ouyangshen on 2016/11/4.
 */
public class TakePictureActivity extends AppCompatActivity implements OnClickListener {
	private static final String TAG = "TakePictureActivity";
	private CameraView camera_view;
	private int mTakeType = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_take_picture);
		int camera_type = getIntent().getIntExtra("type", CameraView.CAMERA_BEHIND);
		camera_view = (CameraView)findViewById(R.id.camera_view);
		camera_view.setCameraType(camera_type);
		findViewById(R.id.btn_shutter).setOnClickListener(this);
		findViewById(R.id.btn_shooting).setOnClickListener(this);
	}

	@Override
	public void onBackPressed() {
        Intent intent = new Intent();
		Bundle bundle = new Bundle();
		String photo_path = camera_view.getPhotoPath();
		bundle.putInt("type", mTakeType);
		if (photo_path==null && mTakeType==0) {
			bundle.putString("is_null", "yes");
		} else {
			bundle.putString("is_null", "no");
			if (mTakeType == 0) {
				bundle.putString("path", photo_path);
			} else if (mTakeType == 1) {
				bundle.putStringArrayList("path_list", camera_view.getShootingList());
			}
		}
		intent.putExtras(bundle);
        setResult(Activity.RESULT_OK, intent);
        finish();
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_shutter) {
			mTakeType = 0;
			camera_view.doTakePicture();
		} else if (v.getId() == R.id.btn_shooting) {
			mTakeType = 1;
			camera_view.doTakeShooting();
		}
	}

}
