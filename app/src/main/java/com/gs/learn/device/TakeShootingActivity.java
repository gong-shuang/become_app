package com.gs.learn.device;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraCharacteristics;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;

import com.gs.learn.device.widget.Camera2View;
import com.gs.learn.R;
/**
 * Created by ouyangshen on 2016/11/4.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class TakeShootingActivity extends AppCompatActivity implements OnClickListener {
	private static final String TAG = "TakeShootingActivity";
	private Camera2View camera2_view;
	private int mTakeType = 0;

	private static final int REQUEST_CAMERA_PERMISSION = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_take_shooting);

		if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
				!= PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
			return;
		}

		int camera_type = getIntent().getIntExtra("type", CameraCharacteristics.LENS_FACING_FRONT);
		camera2_view = (Camera2View)findViewById(R.id.camera2_view);
		camera2_view.open(camera_type);
		findViewById(R.id.btn_shutter).setOnClickListener(this);
		findViewById(R.id.btn_shooting).setOnClickListener(this);
	}

	@Override
	public void onBackPressed() {
        Intent intent = new Intent();
		Bundle bundle = new Bundle();
		String photo_path = camera2_view.getPhotoPath();
		bundle.putInt("type", mTakeType);
		if (photo_path==null && mTakeType==0) {
			bundle.putString("is_null", "yes");
		} else {
			bundle.putString("is_null", "no");
			if (mTakeType == 0) {
				bundle.putString("path", photo_path);
			} else if (mTakeType == 1) {
				bundle.putStringArrayList("path_list", camera2_view.getShootingList());
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
			camera2_view.takePicture();
		} else if (v.getId() == R.id.btn_shooting) {
			mTakeType = 1;
			camera2_view.startShooting(7000);
		}
	}

}
