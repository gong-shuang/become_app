package com.gs.learn.network;

import com.gs.learn.network.util.DateUtil;

import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/11/11.
 */
public class DownloadApkActivity extends AppCompatActivity {
    private static final String TAG = "DownloadApkActivity";
	private static Spinner sp_apk_url;
	private static TextView tv_apk_result;
    private boolean bFirst = true;
    private DownloadManager mDownloadManager;
    private static long mDownloadId = 0;

	private String[] apkDescArray = {
			"支付宝", "微信", "手机QQ", "手机淘宝", "爱奇艺",
			"酷狗音乐", "UC浏览器", "搜狗输入法", "微博", "手机京东"
	};
	private String[] apkUrlArray = {
			"http://www.lenovomm.com/appdown/20987421-2",
			"http://www.lenovomm.com/appdown/21000510-2",
			"http://www.lenovomm.com/appdown/20972357-2",
			"http://www.lenovomm.com/appdown/20958124-2",
			"http://www.lenovomm.com/appdown/20984399-2",
			"http://www.lenovomm.com/appdown/20960309-2",
			"http://www.lenovomm.com/appdown/21003503-2",
			"http://www.lenovomm.com/appdown/20986049-2",
			"http://www.lenovomm.com/appdown/20978059-2",
			"http://www.lenovomm.com/appdown/20983976-2"
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_download_apk);
		tv_apk_result = (TextView) findViewById(R.id.tv_apk_result);
        mDownloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
		
		ArrayAdapter<String> apkUrlAdapter = new ArrayAdapter<String>(this,
				R.layout.item_select, apkDescArray);
		sp_apk_url = (Spinner) findViewById(R.id.sp_apk_url);
		sp_apk_url.setPrompt("请选择要下载的安装包");
		sp_apk_url.setAdapter(apkUrlAdapter);
		sp_apk_url.setOnItemSelectedListener(new ApkUrlSelectedListener());
		sp_apk_url.setSelection(0);
	}

	class ApkUrlSelectedListener implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			if (bFirst == true) {
				bFirst = false;
				return;
			}
			sp_apk_url.setEnabled(false);

			Uri uri = Uri.parse(apkUrlArray[arg2]);
			Request down = new Request(uri);
			down.setTitle(apkDescArray[arg2]+"下载信息");
			down.setDescription(apkDescArray[arg2]+"安装包正在下载");
			down.setAllowedNetworkTypes(Request.NETWORK_MOBILE | Request.NETWORK_WIFI);
			down.setNotificationVisibility(Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
			down.setVisibleInDownloadsUi(true);
			down.setDestinationInExternalFilesDir(
					DownloadApkActivity.this, Environment.DIRECTORY_DOWNLOADS, arg2 + ".apk");
			mDownloadId = mDownloadManager.enqueue(down);
		}

		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}

	// 接收下载完成事件
	public static class DownloadCompleteReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
					&& tv_apk_result != null) {
				long downId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
				Log.d(TAG, " download complete! id : " + downId + ", mDownloadId=" + mDownloadId);
				tv_apk_result.setVisibility(View.VISIBLE);
				tv_apk_result.setText(DateUtil.getNowDateTime(null) + " 编号"
						+ downId + "的下载任务已完成");
				sp_apk_url.setEnabled(true);
			}
		}
	}

	// 接收下载通知栏的点击事件，在下载过程中有效，下载完成后失效
	public static class NotificationClickReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.d(TAG, " NotificationClickReceiver onReceive");
			if (intent.getAction().equals(DownloadManager.ACTION_NOTIFICATION_CLICKED)
					&& tv_apk_result != null) {
				long[] downIds = intent.getLongArrayExtra(DownloadManager.EXTRA_NOTIFICATION_CLICK_DOWNLOAD_IDS);
				for (long downId : downIds) {
					Log.d(TAG, " notify click! id : " + downId + ", mDownloadId=" + mDownloadId);
					if (downId == mDownloadId) {
						tv_apk_result.setText(DateUtil.getNowDateTime(null) + " 编号"
								+ downId + "的下载进度条被点击了一下");
					}
				}
			}
		}
	}

}
