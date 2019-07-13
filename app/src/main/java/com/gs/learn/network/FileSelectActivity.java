package com.gs.learn.network;

import java.util.Map;

import com.aqi00.lib.dialog.FileSelectFragment;
import com.aqi00.lib.dialog.FileSelectFragment.FileSelectCallbacks;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/11/11.
 */
public class FileSelectActivity extends AppCompatActivity implements
		OnClickListener, FileSelectCallbacks {
	private ImageView iv_image_select;
	private TextView tv_image_select;

	@Override
	protected void onCreate(Bundle selectdInstanceState) {
		super.onCreate(selectdInstanceState);
		setContentView(R.layout.activity_file_select);
		iv_image_select = (ImageView) findViewById(R.id.iv_image_select);
		tv_image_select = (TextView) findViewById(R.id.tv_image_select);
		findViewById(R.id.btn_image_select).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_image_select) {
			String[] imgExt = new String[]{"jpg", "png"};
			FileSelectFragment.show(this, imgExt, null);
		}
	}

	@Override
	public void onConfirmSelect(String absolutePath, String fileName, Map<String, Object> map_param) {
		String path = String.format("%s/%s", absolutePath, fileName);
		iv_image_select.setImageURI(Uri.parse(path));
		tv_image_select.setText("打开图片的路径为："+path);
	}

	@Override
	public boolean isFileValid(String absolutePath, String fileName, Map<String, Object> map_param) {
		return true;
	}

}
