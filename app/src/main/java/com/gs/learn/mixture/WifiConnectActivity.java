package com.gs.learn.mixture;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.gs.learn.mixture.adapter.WifiListAdapter;
import com.gs.learn.mixture.bean.WifiConnect;
import com.gs.learn.mixture.util.Utils;
import com.gs.learn.mixture.widget.InputDialogFragment.InputCallbacks;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.Toast;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/12/11.
 */
public class WifiConnectActivity extends AppCompatActivity implements
		OnCheckedChangeListener, InputCallbacks {
	private static final String TAG = "WifiConnectActivity";
	private WifiManager mWifiManager;
	private CheckBox ck_wlan;
	private ListView lv_wifi;
	private Handler mHandler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wifi_connect);
		ck_wlan = (CheckBox) findViewById(R.id.ck_wlan);
		lv_wifi = (ListView) findViewById(R.id.lv_wifi);
		if (Utils.getWlanStatus(this) == true) {
			ck_wlan.setChecked(true);
		}
		ck_wlan.setOnCheckedChangeListener(this);
		mWifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		mHandler.postDelayed(mRefresh, 50);
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (buttonView.getId() == R.id.ck_wlan) {
			Utils.setWlanStatus(this, isChecked);
		}
	}

	private Runnable mRefresh = new Runnable() {
		@Override
		public void run() {
			scanWifiList();
			mHandler.postDelayed(this, 3000);
		}
	};

	private void scanWifiList() {
		ArrayList<WifiConnect> wifiList = new ArrayList<WifiConnect>();
		WifiInfo wifiInfo = mWifiManager.getConnectionInfo();
		int state = mWifiManager.getWifiState();
		String SSID = "";
		if (state == WifiManager.WIFI_STATE_ENABLED || state == WifiManager.WIFI_STATE_ENABLING) {
			SSID = wifiInfo.getSSID();
		} else {
			WifiListAdapter wifiAdapter = new WifiListAdapter(this, mWifiManager, wifiList);
			lv_wifi.setAdapter(wifiAdapter);
			return;
		}

		mWifiManager.startScan();
		ArrayList<ScanResult> newResultList = getResultList();
		List<WifiConfiguration> configList = mWifiManager.getConfiguredNetworks();
		for (int i = 0; i < newResultList.size(); i++) {
			ScanResult item = newResultList.get(i);
			WifiConnect wifi = new WifiConnect();
			wifi.SSID = item.SSID;
			wifi.level = WifiManager.calculateSignalLevel(item.level, 4);
			if (SSID.indexOf(wifi.SSID) >= 0) {
				wifi.status = true;
			}
			if (item.capabilities.toUpperCase(Locale.getDefault()).indexOf("WPA2") >= 0) {
				wifi.type = 4;
			} else if (item.capabilities.toUpperCase(Locale.getDefault()).indexOf("WPA") >= 0) {
				wifi.type = WifiConfiguration.KeyMgmt.WPA_PSK;
			} else {
				wifi.type = WifiConfiguration.KeyMgmt.NONE;
			}
			for (int j = 0; j < configList.size(); j++) {
				if (configList.get(j).SSID.indexOf(wifi.SSID) >= 0) {
					wifi.networkId = configList.get(j).networkId;
					break;
				}
			}
			wifiList.add(wifi);
		}
		WifiListAdapter wifiAdapter = new WifiListAdapter(this, mWifiManager, wifiList);
		lv_wifi.setAdapter(wifiAdapter);
	}

	private ArrayList<ScanResult> getResultList() {
		List<ScanResult> resultList = mWifiManager.getScanResults();
		ArrayList<ScanResult> newResultList = new ArrayList<ScanResult>();
		for (int i = 0; i < resultList.size(); i++) {
			ScanResult item = resultList.get(i);
			int j;
			for (j = 0; j < newResultList.size(); j++) {
				ScanResult newItem = newResultList.get(j);
				if (item.SSID.equals(newItem.SSID)) {
					if (item.level > newItem.level) {
						newResultList.set(j, item);
					}
					break;
				}
			}
			if (j >= newResultList.size()) {
				newResultList.add(item);
			}
		}
		return newResultList;
	}

	@Override
	public void onInput(String ssid, String password, int type) {
		WifiConfiguration config = createWifiInfo(ssid, password, type);
		int netId = mWifiManager.addNetwork(config);
		if (netId == -1) {
			Toast.makeText(this, "密码错误", Toast.LENGTH_LONG).show();
		} else {
			mWifiManager.enableNetwork(netId, true);
		}
	}

	private WifiConfiguration createWifiInfo(String ssid, String password, int type) {
		WifiConfiguration config = new WifiConfiguration();
		config.SSID = "\"" + ssid + "\"";
		if (type == WifiConfiguration.KeyMgmt.NONE) {// 明文密码
			config.wepKeys[0] = "";
			config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
			config.wepTxKeyIndex = 0;
		} else {// WPA加密或者WPA2加密
			config.preSharedKey = "\"" + password + "\"";
			config.hiddenSSID = true;
			config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
			config.allowedKeyManagement.set(type);
			config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
			config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
			config.status = WifiConfiguration.Status.ENABLED;
		}
		return config;
	}

}
