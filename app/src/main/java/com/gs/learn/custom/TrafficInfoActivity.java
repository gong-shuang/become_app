package com.gs.learn.custom;

import java.util.ArrayList;

import android.net.TrafficStats;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.gs.learn.custom.adapter.TrafficInfoAdapter;
import com.gs.learn.custom.bean.AppInfo;
import com.gs.learn.custom.util.AppUtil;
import com.gs.learn.custom.util.StringUtil;
import com.gs.learn.R;
/**
 * Created by ouyangshen on 2016/10/14.
 */
public class TrafficInfoActivity extends AppCompatActivity {

	private final static String TAG = "TrafficInfoActivity";
	private TextView tv_trafficinfo;
	private ListView lv_traffic;
	private Handler mHandler = new Handler();
	private String mDesc = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_traffic_info);
		tv_trafficinfo = (TextView) findViewById(R.id.tv_trafficinfo);
		lv_traffic = (ListView) findViewById(R.id.lv_traffic);
		
		mHandler.postDelayed(mRefresh, 50);
	}
	
	private Runnable mRefresh = new Runnable() {
		@Override
		public void run() {
			mDesc = String.format("%s当前总共接收流量：%s", "", 
					StringUtil.formatTraffic(TrafficStats.getTotalRxBytes()));
			mDesc = String.format("%s\n　　其中接收数据流量：%s", mDesc, 
					StringUtil.formatTraffic(TrafficStats.getMobileRxBytes()));
			mDesc = String.format("%s\n当前总共发送流量：%s", mDesc, 
					StringUtil.formatTraffic(TrafficStats.getTotalTxBytes()));
			mDesc = String.format("%s\n　　其中发送数据流量：%s", mDesc, 
					StringUtil.formatTraffic(TrafficStats.getMobileTxBytes()));
			tv_trafficinfo.setText(mDesc);

			ArrayList<AppInfo> appinfoList = AppUtil.getAppInfo(TrafficInfoActivity.this, 1);
			for (int i=0; i<appinfoList.size(); i++) {
				AppInfo item = appinfoList.get(i);
				item.traffic = TrafficStats.getUidRxBytes(item.uid);
				appinfoList.set(i, item);
			}
			TrafficInfoAdapter adapter = new TrafficInfoAdapter(TrafficInfoActivity.this, appinfoList);
			lv_traffic.setAdapter(adapter);
		}
	};

}
