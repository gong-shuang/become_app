package com.gs.learn.event;

import com.aqi00.lib.dialog.FileSaveFragment;
import com.aqi00.lib.dialog.FileSaveFragment.FileSaveCallbacks;
import com.gs.learn.event.util.BitmapUtil;
import com.gs.learn.event.widget.SignatureView;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/11/23.
 */
public class SignatureActivity extends AppCompatActivity implements
		OnClickListener, FileSaveCallbacks {
	private SignatureView view_signature;
	private ImageView iv_signature_new;
	private Bitmap mBitmap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signature);
		view_signature = (SignatureView) findViewById(R.id.view_signature);
		iv_signature_new = (ImageView) findViewById(R.id.iv_signature_new);
		findViewById(R.id.btn_add_signature).setOnClickListener(this);
		findViewById(R.id.btn_end_signature).setOnClickListener(this);
		findViewById(R.id.btn_reset_signature).setOnClickListener(this);
		findViewById(R.id.btn_revoke_signature).setOnClickListener(this);
		findViewById(R.id.btn_save_signature).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_save_signature) {
			if (mBitmap == null) {
				Toast.makeText(this, "请先开始然后结束签名", Toast.LENGTH_LONG).show();
				return;
			}
			FileSaveFragment.show(this, "jpg");
		} else if (v.getId() == R.id.btn_add_signature) {
			view_signature.setDrawingCacheEnabled(true);
		} else if (v.getId() == R.id.btn_reset_signature) {
			view_signature.clear();
		} else if (v.getId() == R.id.btn_revoke_signature) {
			view_signature.revoke();
		} else if (v.getId() == R.id.btn_end_signature) {
			if (view_signature.isDrawingCacheEnabled() != true) {
				Toast.makeText(this, "请先开始签名", Toast.LENGTH_LONG).show();
			} else {
				mBitmap = view_signature.getDrawingCache();
				iv_signature_new.setImageBitmap(mBitmap);
				mHandler.postDelayed(mResetCache, 100);
			}
		}
	}

	private Handler mHandler = new Handler();
	private Runnable mResetCache = new Runnable() {
		@Override
		public void run() {
			view_signature.setDrawingCacheEnabled(false);
			view_signature.setDrawingCacheEnabled(true);
		}
	};

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

}
