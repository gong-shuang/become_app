package com.gs.learn.network;

import java.util.HashMap;

import com.gs.learn.network.widget.TextProgressCircle;

import android.app.DownloadManager;
import android.app.DownloadManager.Query;
import android.app.DownloadManager.Request;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/11/11.
 */
public class DownloadImageActivity extends AppCompatActivity {
	private Spinner sp_image_url;
	private ImageView iv_image_url;
	private TextProgressCircle tpc_progress;
	private TextView tv_image_result;
    private boolean bFirst = true;
    private String mImagePath;
    private DownloadManager mDownloadManager;
    private long mDownloadId = 0;
    private static HashMap<Integer,String> mStatusMap = new HashMap<Integer,String>();

	private String[] imageDescArray = {
			"洱海公园", "丹凤亭", "宛在堂", "满庭芳", "玉带桥",
			"眺望洱海", "洱海女儿", "海心亭", "洱海岸边", "烟波浩渺"
	};
	private String[] imageUrlArray = {
			"http://b255.photo.store.qq.com/psb?/V11ZojBI0Zz6pV/nYJcslMIrGeDrujE5KZF2xBW8rjXMIVetZfrOAlSamM!/b/dPwxB5iaEQAA&bo=IANYAgAAAAABB1k!&rf=viewer_4",
			"http://b255.photo.store.qq.com/psb?/V11ZojBI0Zz6pV/Adcl9XVS.RBED4D8shjceYHOhhR*6mcNyCcq24kJG2k!/b/dPwxB5iYEQAA&bo=IANYAgAAAAABB1k!&rf=viewer_4",
			"http://b255.photo.store.qq.com/psb?/V11ZojBI0Zz6pV/bg*X6nT03YUReoJ97ked266WlWG3IzLjBdwHpKqkhYY!/b/dOg5CpjZEAAA&bo=IANYAgAAAAABB1k!&rf=viewer_4",
			"http://b255.photo.store.qq.com/psb?/V11ZojBI0Zz6pV/JOPAKl9BO1wragCEIVzXLlHwj83qVhb8uNuHdmVRwP4!/b/dPwxB5iSEQAA&bo=IANYAgAAAAABB1k!&rf=viewer_4",
			"http://b255.photo.store.qq.com/psb?/V11ZojBI0Zz6pV/7hHOgBEOBshH*7YAUx7RP0JzPuxRBD727mblw9TObhc!/b/dG4WB5i2EgAA&bo=IANYAgAAAAABB1k!&rf=viewer_4",
			"http://b255.photo.store.qq.com/psb?/V11ZojBI0Zz6pV/m4Rjx20D9iFL0D5emuYqMMDji*HGQ2w2BWqv0zK*tRk!/b/dGp**5dYEAAA&bo=IANYAgAAAAABB1k!&rf=viewer_4",
			"http://b255.photo.store.qq.com/psb?/V11ZojBI0Zz6pV/swfCMVl7Oefv8xgboV3OqkrahEs33KO7XwwH6hh7bnY!/b/dECE*5e9EgAA&bo=IANYAgAAAAABB1k!&rf=viewer_4",
			"http://b256.photo.store.qq.com/psb?/V11ZojBI0Zz6pV/tpRlB0oozaD9PyBtCmf3pQ5QY0keJJxYGX93I7n5NwQ!/b/dAyVmZiVEQAA&bo=IANYAgAAAAABB1k!&rf=viewer_4",
			"http://b256.photo.store.qq.com/psb?/V11ZojBI0Zz6pV/wMX2*LM6y.mBsFIYu8spAa7xXWUkPD.GHyazd.vMmYA!/b/dGYwoZjREQAA&bo=IANYAgAAAAABB1k!&rf=viewer_4",
			"http://b255.photo.store.qq.com/psb?/V11ZojBI0Zz6pV/2vl1n0KmKTPCv944MVJgLxKAhMiM*sqajIFQ43c*9DM!/b/dPaoCJhuEQAA&bo=IANYAgAAAAABB1k!&rf=viewer_4",
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_download_image);
		iv_image_url = (ImageView) findViewById(R.id.iv_image_url);
		tpc_progress = (TextProgressCircle) findViewById(R.id.tpc_progress);
		tv_image_result = (TextView) findViewById(R.id.tv_image_result);
		mDownloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
		mStatusMap.put(DownloadManager.STATUS_PENDING, "挂起");
		mStatusMap.put(DownloadManager.STATUS_RUNNING, "运行中");
		mStatusMap.put(DownloadManager.STATUS_PAUSED, "暂停");
		mStatusMap.put(DownloadManager.STATUS_SUCCESSFUL, "成功");
		mStatusMap.put(DownloadManager.STATUS_FAILED, "失败");
		
		ArrayAdapter<String> imageUrlAdapter = new ArrayAdapter<String>(this,
				R.layout.item_select, imageDescArray);
		sp_image_url = (Spinner) findViewById(R.id.sp_image_url);
		sp_image_url.setPrompt("请选择要下载的图片");
		sp_image_url.setAdapter(imageUrlAdapter);
		sp_image_url.setOnItemSelectedListener(new ImageUrlSelectedListener());
		sp_image_url.setSelection(0);
	}

	class ImageUrlSelectedListener implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			if (bFirst == true) {
				bFirst = false;
				return;
			}
			sp_image_url.setEnabled(false);
			iv_image_url.setImageDrawable(null);
			tpc_progress.setProgress(0, 100);
			tpc_progress.setVisibility(View.VISIBLE);

			Uri uri = Uri.parse(imageUrlArray[arg2]);
			Request down = new Request(uri);
			down.setAllowedNetworkTypes(Request.NETWORK_MOBILE | Request.NETWORK_WIFI);
			down.setNotificationVisibility(Request.VISIBILITY_HIDDEN);
			down.setVisibleInDownloadsUi(false);
			down.setDestinationInExternalFilesDir(
					DownloadImageActivity.this, Environment.DIRECTORY_DCIM, arg2 + ".jpg");
			mDownloadId = mDownloadManager.enqueue(down);
			mHandler.postDelayed(mRefresh, 100);
		}

		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}

	private Handler mHandler = new Handler();
	private Runnable mRefresh = new Runnable() {
		@Override
		public void run() {
			boolean bFinish = false;
			Query down_query = new Query();
			down_query.setFilterById(mDownloadId);
			Cursor cursor = mDownloadManager.query(down_query);
			if (cursor.moveToFirst()) {
				for (;; cursor.moveToNext()) {
					int nameIdx = cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME);
					int mediaTypeIdx = cursor.getColumnIndex(DownloadManager.COLUMN_MEDIA_TYPE);
					int totalSizeIdx = cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES);
					int nowSizeIdx = cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR);
					int statusIdx = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
					int progress = (int) (100 * cursor.getLong(nowSizeIdx) / cursor.getLong(totalSizeIdx));
					if (cursor.getString(nameIdx) == null) {
						break;
					}
					tpc_progress.setProgress(progress, 100);
					mImagePath = cursor.getString(nameIdx);
					String desc = "";
					desc = String.format("%s文件路径：%s\n", desc, cursor.getString(nameIdx));
					desc = String.format("%s媒体类型：%s\n", desc, cursor.getString(mediaTypeIdx));
					desc = String.format("%s文件总大小：%d\n", desc, cursor.getLong(totalSizeIdx));
					desc = String.format("%s已下载大小：%d\n", desc, cursor.getLong(nowSizeIdx));
					desc = String.format("%s下载进度：%d%%\n", desc, progress);
					desc = String.format("%s下载状态：%s\n", desc, mStatusMap.get(cursor.getInt(statusIdx)));
					tv_image_result.setText(desc);
					if (progress == 100) {
						bFinish = true;
					}
					if (cursor.isLast() == true) {
						break;
					}
				}
			}
			cursor.close();
			if (bFinish != true) {
				mHandler.postDelayed(this, 100);
			} else {
				sp_image_url.setEnabled(true);
				tpc_progress.setVisibility(View.INVISIBLE);
				iv_image_url.setImageURI(Uri.parse(mImagePath));
			}
		}
	};

}
