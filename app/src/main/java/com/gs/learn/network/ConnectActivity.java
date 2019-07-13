package com.gs.learn.network;

import com.gs.learn.R;
import com.gs.learn.network.util.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.widget.TextView;

/**
 * Created by ouyangshen on 2016/11/11.
 */
public class ConnectActivity extends AppCompatActivity {

	private TextView tv_connect;
	private ConnectivityManager mConnectMgr;
	private TelephonyManager mTelMgr;
	private Handler mHandler = new Handler();
	private String[] mNetStateArray = { "正在连接", "已连接", "暂停", "正在断开", "已断开", "未知" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_connect);
		tv_connect = (TextView) findViewById(R.id.tv_connect);
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
		if (info != null) {
			if (info.getState() == NetworkInfo.State.CONNECTED) {
				desc = String.format("当前网络连接的状态是%s", mNetStateArray[info.getState().ordinal()]);
				if (info.getType() == ConnectivityManager.TYPE_WIFI) {
					desc = String.format("%s\n当前联网的网络类型是WIFI", desc);
				} else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
					int mobile_type = info.getSubtype();
					desc = String.format("%s\n当前联网的网络类型是%s %s", desc,
							Utils.getNetworkTypeName(mTelMgr, mobile_type),
							Utils.getClassName(mTelMgr, mobile_type));
				} else {
					desc = String.format("%s\n当前联网的网络类型是%d", desc, info.getType());
				}
			} else {
				desc = "\n当前无上网连接";
			}
		} else {
			desc = "\n当前无上网连接";
		}
		tv_connect.setText(desc);
	}

}
