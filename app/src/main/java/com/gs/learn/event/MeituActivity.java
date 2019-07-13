package com.gs.learn.event;

import java.util.Map;

import com.aqi00.lib.dialog.FileSaveFragment;
import com.aqi00.lib.dialog.FileSelectFragment;
import com.aqi00.lib.dialog.FileSaveFragment.FileSaveCallbacks;
import com.aqi00.lib.dialog.FileSelectFragment.FileSelectCallbacks;
import com.gs.learn.event.util.BitmapUtil;
import com.gs.learn.event.widget.BitmapView;
import com.gs.learn.event.widget.MeituView;
import com.gs.learn.event.widget.MeituView.ImageChangetListener;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/11/23.
 */
public class MeituActivity extends AppCompatActivity implements
		FileSelectCallbacks, FileSaveCallbacks, ImageChangetListener {
	private final static String TAG = "MeituActivity";
	private MeituView mv_content;
	private TextView tv_intro;
	private BitmapView bv_content;
	private Bitmap mBitmap = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_meitu);
		mv_content = (MeituView) findViewById(R.id.mv_content);
		tv_intro = (TextView) findViewById(R.id.tv_intro);
		bv_content = (BitmapView) findViewById(R.id.bv_content);
		bv_content.setDrawingCacheEnabled(true);
		mv_content.setImageChangetListener(this);
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
		tv_intro.setVisibility(View.GONE);
		String path = String.format("%s/%s", absolutePath, fileName);
		Bitmap bitmap = BitmapUtil.openBitmap(path);
		bv_content.setImageBitmap(bitmap);
		refreshImage(true);
	}

	@Override
	public boolean isFileValid(String absolutePath, String fileName, Map<String, Object> map_param) {
		return true;
	}

	private void refreshImage(boolean is_first) {
		Bitmap bitmap = bv_content.getDrawingCache();
		mv_content.setOrigBitmap(bitmap);
		if (is_first) {
			int left = bitmap.getWidth() / 4;
			int top = bitmap.getHeight() / 4;
			mv_content.setBitmapRect(new Rect(left, top, left*2, top*2));
		} else {
			mv_content.setBitmapRect(mv_content.getBitmapRect());
		}
	}

	@Override
	public void onImageTraslate(int offsetX, int offsetY, boolean bReset) {
		bv_content.setOffset(offsetX, offsetY, bReset);
		refreshImage(false);
	}

	@Override
	public void onImageScale(float ratio) {
		bv_content.setScaleRatio(ratio, false);
		refreshImage(false);
	}

	@Override
	public void onImageRotate(int degree) {
		bv_content.setRotateDegree(degree, false);
		refreshImage(false);
	}

	@Override
	public void onImageClick() {
	}

	@Override
	public void onImageLongClick() {
		registerForContextMenu(mv_content);
		openContextMenu(mv_content);
		unregisterForContextMenu(mv_content);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		getMenuInflater().inflate(R.menu.menu_meitu, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.menu_file_open) {
			FileSelectFragment.show(this, new String[] { "jpg", "png" }, null);
		} else if (id == R.id.menu_file_save) {
			mBitmap = mv_content.getCropBitmap();
			FileSaveFragment.show(this, "jpg");
		}
		return true;
	}


}
