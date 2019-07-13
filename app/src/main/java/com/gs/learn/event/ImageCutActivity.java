package com.gs.learn.event;

import java.util.Map;

import com.aqi00.lib.dialog.FileSaveFragment;
import com.aqi00.lib.dialog.FileSaveFragment.FileSaveCallbacks;
import com.aqi00.lib.dialog.FileSelectFragment;
import com.aqi00.lib.dialog.FileSelectFragment.FileSelectCallbacks;
import com.gs.learn.event.util.BitmapUtil;
import com.gs.learn.event.widget.CropImageView;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/11/23.
 */
public class ImageCutActivity extends AppCompatActivity implements
		OnClickListener, FileSelectCallbacks, FileSaveCallbacks {
	private View v_shade;
	private CropImageView civ_over;
	private ImageView iv_old;
	private ImageView iv_new;
	private Bitmap mBitmap = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_cut);
		findViewById(R.id.btn_open_image).setOnClickListener(this);
		findViewById(R.id.btn_save_image).setOnClickListener(this);
		findViewById(R.id.btn_cut_begin).setOnClickListener(this);
		findViewById(R.id.btn_cut_end).setOnClickListener(this);
		v_shade = (View) findViewById(R.id.v_shade);
		civ_over = (CropImageView) findViewById(R.id.civ_over);
		iv_old = (ImageView) findViewById(R.id.iv_old);
		iv_new = (ImageView) findViewById(R.id.iv_new);
		iv_old.setDrawingCacheEnabled(true);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_open_image) {
			FileSelectFragment.show(this, new String[] { "jpg", "png" }, null);
		} else if (v.getId() == R.id.btn_save_image) {
			if (mBitmap == null) {
				Toast.makeText(this, "请先打开并裁剪图片文件", Toast.LENGTH_LONG).show();
				return;
			}
			FileSaveFragment.show(this, "jpg");
		} else if (v.getId() == R.id.btn_cut_begin) {
			v_shade.setVisibility(View.VISIBLE);
			civ_over.setVisibility(View.VISIBLE);
			Bitmap bitmap = iv_old.getDrawingCache();
			int left = bitmap.getWidth() / 4;
			int top = bitmap.getHeight() / 4;
			civ_over.setOrigBitmap(bitmap);
			civ_over.setBitmapRect(new Rect(left, top, left*2, top*2));
		} else if (v.getId() == R.id.btn_cut_end) {
			v_shade.setVisibility(View.GONE);
			civ_over.setVisibility(View.GONE);
			mBitmap = civ_over.getCropBitmap();
			iv_new.setImageBitmap(mBitmap);
		}
	}

	@Override
	public boolean onCanSave(String absolutePath, String fileName) {
		return true;
	}

	@Override
	public void onConfirmSave(String absolutePath, String fileName) {
		String path = String.format("%s/%s", absolutePath, fileName);
		BitmapUtil.saveBitmap(path, mBitmap, "jpg", 80);
		Toast.makeText(this, "成功保存图片文件：" + path, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onConfirmSelect(String absolutePath, String fileName, Map<String, Object> map_param) {
		String path = String.format("%s/%s", absolutePath, fileName);
		Bitmap bitmap = BitmapUtil.openBitmap(path);
		iv_old.setImageBitmap(bitmap);
	}

	@Override
	public boolean isFileValid(String absolutePath, String fileName, Map<String, Object> map_param) {
		return true;
	}

}
