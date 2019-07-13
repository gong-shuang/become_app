package com.gs.learn.mixture;

import com.gs.learn.mixture.util.IPv4Util;
import com.gs.learn.mixture.util.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.widget.TextView;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/12/11.
 */
public class WifiInfoActivity extends AppCompatActivity {
	private static final String TAG = "WifiInfoActivity";
	private ConnectivityManager mConnectMgr;
	private TelephonyManager mTelMgr;
	private TextView tv_info;
	private Handler mHandler = new Handler();
	private String[] mWifiStateArray = { "正在断开", "已断开", "正在连接", "已连接", "未知" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wifi_info);
		tv_info = (TextView) findViewById(R.id.tv_info);
		mHandler.postDelayed(mRefresh, 50);
	}

	private Runnable mRefresh = new Runnable() {
		@Override
		public void run() {
			getAvailableNet();
			mHandler.postDelayed(this, 1000);
		}
	};

	private void getAvailableNet() {
		String desc = "";
		mTelMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		mConnectMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = mConnectMgr.getActiveNetworkInfo();
		if (info != null && info.getState() == NetworkInfo.State.CONNECTED) {
			if (info.getType() == ConnectivityManager.TYPE_WIFI) {
				WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
				WifiInfo wifiInfo = wifiManager.getConnectionInfo();
				int state = wifiManager.getWifiState();
				String SSID = wifiInfo.getSSID();
				if (SSID == null || SSID.length() <= 0 || SSID.indexOf("unknown") >= 0) {
					desc = "\n当前联网的网络类型是WIFI，但未成功连接已知的wifi信号";
				} else {
					desc = String.format("%s当前联网的网络类型是WIFI，", desc);
					desc = String.format("%s状态是%s。\n", desc, mWifiStateArray[state]);
					desc = String.format("%s\tWIFI名称是：%s\n", desc, SSID);
					desc = String.format("%s\t路由器MAC是：%s\n", desc, wifiInfo.getBSSID());
					desc = String.format("%s\tWIFI信号强度是：%d\n", desc, wifiInfo.getRssi());
					desc = String.format("%s\t连接速率是：%s\n", desc, wifiInfo.getLinkSpeed());
					desc = String.format("%s\t手机的IP地址是：%s\n", desc, IPv4Util.intToIp(wifiInfo.getIpAddress()));
					desc = String.format("%s\t手机的MAC地址是：%s\n", desc, wifiInfo.getMacAddress());
					desc = String.format("%s\t网络编号是：%s\n", desc, wifiInfo.getNetworkId());
				}
			} else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
				int net_type = info.getSubtype();
				desc = String.format("\n当前联网的网络类型是%s %s",
						Utils.getNetworkTypeName(mTelMgr, net_type),
						Utils.getClassName(mTelMgr, net_type));
			} else {
				desc = String.format("\n当前联网的网络类型是%d", info.getType());
			}
		} else {
			desc = "\n当前无上网连接";
		}
		tv_info.setText(desc);
	}

}
