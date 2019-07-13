package com.gs.learn.network;

import com.aqi00.lib.dialog.FileSaveFragment;
import com.aqi00.lib.dialog.FileSaveFragment.FileSaveCallbacks;
import com.gs.learn.network.util.BitmapUtil;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/11/11.
 */
public class FileSaveActivity extends AppCompatActivity implements 
		OnClickListener, FileSaveCallbacks {
	private EditText et_image_save;
	private TextView tv_image_save;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_file_save);
		et_image_save = (EditText) findViewById(R.id.et_image_save);
		tv_image_save = (TextView) findViewById(R.id.tv_image_save);
		findViewById(R.id.btn_image_save).setOnClickListener(this);
	}

	@Override
	protected void onStart() {
		super.onStart();
		et_image_save.setDrawingCacheEnabled(true);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		et_image_save.setDrawingCacheEnabled(false);
	}
	
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_image_save) {
			FileSaveFragment.show(this, "jpg");
		}
	}

	@Override
	public boolean onCanSave(String absolutePath, String mFileName) {
		return true;
	}

	@Override
	public void onConfirmSave(String absolutePath, String fileName) {
		String path = String.format("%s/%s", absolutePath, fileName);
		Bitmap bitmap = et_image_save.getDrawingCache();
		BitmapUtil.saveBitmap(path, bitmap, "jpg", 80);
		bitmap.recycle();
		tv_image_save.setText("截图的保存路径为："+path);
	}

}
