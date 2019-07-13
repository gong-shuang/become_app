package com.gs.learn.event;

import java.util.Map;

import com.aqi00.lib.dialog.FileSaveFragment;
import com.aqi00.lib.dialog.FileSaveFragment.FileSaveCallbacks;
import com.aqi00.lib.dialog.FileSelectFragment;
import com.aqi00.lib.dialog.FileSelectFragment.FileSelectCallbacks;
import com.gs.learn.event.util.BitmapUtil;
import com.gs.learn.event.widget.BitmapView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/11/23.
 */
public class ImageChangeActivity extends AppCompatActivity implements
		OnClickListener, FileSelectCallbacks, FileSaveCallbacks {
	private BitmapView bv_image;
	private Bitmap mBitmap = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_change);
		bv_image = (BitmapView) findViewById(R.id.bv_image);
		findViewById(R.id.btn_open_image).setOnClickListener(this);
		findViewById(R.id.btn_save_image).setOnClickListener(this);
		initScaleSpinner();
		initRotateSpinner();
	}

	@Override
	protected void onStart() {
		super.onStart();
		bv_image.setDrawingCacheEnabled(true);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		bv_image.setDrawingCacheEnabled(false);
	}
	
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_open_image) {
			FileSelectFragment.show(this, new String[] { "jpg", "png" }, null);
		} else if (v.getId() == R.id.btn_save_image) {
			if (mBitmap == null) {
				Toast.makeText(this, "请先打开图片文件", Toast.LENGTH_LONG).show();
				return;
			}
			FileSaveFragment.show(this, "jpg");
		}
	}
	
	private void initScaleSpinner() {
		ArrayAdapter<String> scaleAdapter = new ArrayAdapter<String>(this,
				R.layout.item_select, scaleArray);
		Spinner sp_style = (Spinner) findViewById(R.id.sp_scale);
		sp_style.setPrompt("请选择缩放比率");
		sp_style.setAdapter(scaleAdapter);
		sp_style.setOnItemSelectedListener(new ScaleSelectedListener());
		sp_style.setSelection(3);
	}

	private String[] scaleArray={"0.25", "0.5", "0.75", "1.0", "1.5", "2.0", "4.0"};
	class ScaleSelectedListener implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			bv_image.setScaleRatio(Float.parseFloat(scaleArray[arg2]), true);
		}

		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}

	private void initRotateSpinner() {
		ArrayAdapter<String> rotateAdapter = new ArrayAdapter<String>(this,
				R.layout.item_select, rotateArray);
		Spinner sp_style = (Spinner) findViewById(R.id.sp_rotate);
		sp_style.setPrompt("请选择旋转角度");
		sp_style.setAdapter(rotateAdapter);
		sp_style.setOnItemSelectedListener(new RotateSelectedListener());
		sp_style.setSelection(0);
	}

	private String[] rotateArray={"0", "45", "90", "135", "180", "225", "270", "315"};
	class RotateSelectedListener implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			bv_image.setRotateDegree(Integer.parseInt(rotateArray[arg2]), true);
		}

		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}
	
	@Override
	public boolean onCanSave(String absolutePath, String fileName) {
		return true;
	}

	@Override
	public void onConfirmSave(String absolutePath, String fileName) {
		String path = String.format("%s/%s", absolutePath, fileName);
		Bitmap bitmap = bv_image.getDrawingCache();
		BitmapUtil.saveBitmap(path, bitmap, "jpg", 80);
		bitmap.recycle();
	}

	@Override
	public void onConfirmSelect(String absolutePath, String fileName, Map<String, Object> map_param) {
		String path = String.format("%s/%s", absolutePath, fileName);
		mBitmap = BitmapFactory.decodeFile(path);
		mBitmap = BitmapUtil.openBitmap(path);
		bv_image.setImageBitmap(mBitmap);
	}

	@Override
	public boolean isFileValid(String absolutePath, String fileName, Map<String, Object> map_param) {
		return true;
	}

}
