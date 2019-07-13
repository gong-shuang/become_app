package com.gs.learn.custom.service;

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.TrafficStats;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import com.gs.learn.custom.CustomMainActivity;
import com.gs.learn.MainApplication;
import com.gs.learn.R;
import com.gs.learn.custom.bean.AppInfo;
import com.gs.learn.custom.util.AppUtil;
import com.gs.learn.custom.util.DateUtil;
import com.gs.learn.custom.util.SharedUtil;
import com.gs.learn.custom.util.StringUtil;

/**
 * Created by ouyangshen on 2016/10/14.
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class TrafficService extends Service {
	private static final String TAG = "TrafficService";
	private MainApplication app;
	private int limit_day;
	private int mNowDay;

	@Override
	public int onStartCommand(Intent intent, int flags, int startid) {
		app = MainApplication.getInstance();
		limit_day = Integer.parseInt(SharedUtil.getIntance(this).readShared("limit_day", "30"));
		mHandler.post(mRefresh);
		return START_STICKY;
	}

	private Handler mHandler = new Handler();
	private Runnable mRefresh = new Runnable() {
		@Override
		public void run() {
			refreshData();
			refreshNotify();
			mHandler.postDelayed(this, 10000);
		}
	};
	
	private void refreshData() {
		mNowDay = Integer.parseInt(DateUtil.getNowDateTime("yyyyMMdd"));
		//获取最新的流量信息
		ArrayList<AppInfo> appinfoList = AppUtil.getAppInfo(this, 1);
		for (int i=0; i<appinfoList.size(); i++) {
			AppInfo item = appinfoList.get(i);
			item.traffic = TrafficStats.getUidRxBytes(item.uid);
			item.month = mNowDay/100;
			item.day = mNowDay;
			appinfoList.set(i, item);
		}
		app.mTrafficHelper.insert(appinfoList);
	}

	private void refreshNotify() {
		String last_date = DateUtil.getAddDate(""+mNowDay, -1);
		ArrayList<AppInfo> lastArray = app.mTrafficHelper.query("day="+last_date);
		ArrayList<AppInfo> thisArray = app.mTrafficHelper.query("day="+mNowDay);
		long traffic_day = 0;
		for (int i=0; i<thisArray.size(); i++) {
			AppInfo item = thisArray.get(i);
			for (int j=0; j<lastArray.size(); j++) {
				if (item.uid == lastArray.get(j).uid) {
					item.traffic -= lastArray.get(j).traffic;
					break;
				}
			}
			traffic_day += item.traffic;
		}
		String desc = "今日已用流量" + StringUtil.formatTraffic(traffic_day);

		int progress = 0;
		int layoutId = R.layout.notify_traffic_green;
		float trafficM = traffic_day/1024.0f/1024.0f;
		if (trafficM > limit_day*2) {
			progress = (int) ((trafficM>limit_day*3)?100:(trafficM-limit_day*2)*100/limit_day);
			layoutId = R.layout.notify_traffic_red;
		} else if (trafficM > limit_day) {
			progress = (int) ((trafficM>limit_day*2)?100:(trafficM-limit_day)*100/limit_day);
			layoutId = R.layout.notify_traffic_yellow;
		} else {
			progress = (int) (trafficM*100/limit_day);
		}
		Log.d(TAG, "progress="+progress);
		RemoteViews notify_traffic = new RemoteViews(this.getPackageName(), layoutId);
		notify_traffic.setTextViewText(R.id.tv_flow, desc);
		notify_traffic.setProgressBar(R.id.pb_flow, 100, progress, false);
		Intent intent = new Intent(this, CustomMainActivity.class);
		PendingIntent contentIntent = PendingIntent.getActivity(this,
				R.string.app_name, intent, PendingIntent.FLAG_UPDATE_CURRENT);

		Notification.Builder builder = new Notification.Builder(this);
		builder.setContentIntent(contentIntent)
				.setContent(notify_traffic)
				.setTicker("手机安全助手运行中")
				.setSmallIcon(R.drawable.ic_app);
		mNotify = builder.build();
		startForeground(9, mNotify);
	}
	
	private Notification mNotify;
	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mNotify != null) {
			stopForeground(true);
		}
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
}
