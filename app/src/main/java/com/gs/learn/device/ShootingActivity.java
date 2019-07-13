package com.gs.learn.device;

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Toast;

import com.gs.learn.device.adapter.ShootingAdapter;
import com.gs.learn.R;
/**
 * Created by ouyangshen on 2016/11/4.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class ShootingActivity extends AppCompatActivity implements OnClickListener {
	private static final String TAG = "ShootingActivity";
	private FrameLayout fl_content;
	private ImageView iv_photo;
	private GridView gv_shooting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_shooting);

        fl_content = (FrameLayout) findViewById(R.id.fl_content);
        iv_photo = (ImageView) findViewById(R.id.iv_photo);
        gv_shooting = (GridView) findViewById(R.id.gv_shooting);
        findViewById(R.id.btn_catch_behind).setOnClickListener(this);
        findViewById(R.id.btn_catch_front).setOnClickListener(this);
    }

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		Log.d(TAG, "onActivityResult. requestCode="+requestCode+", resultCode="+resultCode);
		Bundle resp = intent.getExtras();
		String is_null = resp.getString("is_null");
		if (is_null.equals("yes") != true) {
			int type = resp.getInt("type");
			Log.d(TAG, "type="+type);
			if (type == 0) {
				iv_photo.setVisibility(View.VISIBLE);
				gv_shooting.setVisibility(View.GONE);
				String path = resp.getString("path");
				fillBitmap(BitmapFactory.decodeFile(path, null));
			} else if (type == 1) {
				iv_photo.setVisibility(View.GONE);
				gv_shooting.setVisibility(View.VISIBLE);
				ArrayList<String> pathList = resp.getStringArrayList("path_list");
				Log.d(TAG, "pathList.size()="+pathList.size());
				ShootingAdapter adapter = new ShootingAdapter(this, pathList);
				gv_shooting.setAdapter(adapter);
			}
		}
	}

	private void fillBitmap(Bitmap bitmap) {
        Log.d(TAG, "fillBitmap width="+bitmap.getWidth()+",height="+bitmap.getHeight());
        if (bitmap.getHeight() > fl_content.getMeasuredHeight()) {
        	LayoutParams params = iv_photo.getLayoutParams();
        	params.height = fl_content.getMeasuredHeight();
        	params.width = bitmap.getWidth()*fl_content.getMeasuredHeight()/bitmap.getHeight();
        	iv_photo.setLayoutParams(params);
        }
		iv_photo.setDrawingCacheEnabled(true);
		iv_photo.setScaleType(ScaleType.FIT_CENTER);
		iv_photo.setImageBitmap(bitmap);
	}

	@Override
	public void onClick(View v) {
		CameraManager cm = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
		String[] ids;
		try {
			ids = cm.getCameraIdList();
		} catch (CameraAccessException e) {
			e.printStackTrace();
			return;
		}
		if (v.getId() == R.id.btn_catch_behind) {
			if (checkCamera(ids, CameraCharacteristics.LENS_FACING_FRONT+"") == true) {
				Intent intent = new Intent(this, TakeShootingActivity.class);
				intent.putExtra("type", CameraCharacteristics.LENS_FACING_FRONT);
				startActivityForResult(intent, 1);
			} else {
				Toast.makeText(this, "当前设备不支持后置摄像头", Toast.LENGTH_SHORT).show();
			}
		} else if (v.getId() == R.id.btn_catch_front) {
			if (checkCamera(ids, CameraCharacteristics.LENS_FACING_BACK+"") == true) {
				Intent intent = new Intent(this, TakeShootingActivity.class);
				intent.putExtra("type", CameraCharacteristics.LENS_FACING_BACK);
				startActivityForResult(intent, 1);
			} else {
				Toast.makeText(this, "当前设备不支持前置摄像头", Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	private boolean checkCamera(String[] ids, String type) {
		boolean result = false;
		for (int i=0; i<ids.length; i++) {
			if (ids[i].equals(type) == true) {
				result = true;
				break;
			}
		}
		return result;
	}

}
