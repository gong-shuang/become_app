package com.gs.learn.mixture;

import java.util.ArrayList;
import java.util.HashMap;

import com.gs.learn.mixture.adapter.ClientListAdapter;
import com.gs.learn.mixture.bean.ClientScanResult;
import com.gs.learn.mixture.database.MacManager;
import com.gs.learn.mixture.task.GetClientListTask;
import com.gs.learn.mixture.task.GetClientListTask.GetClientListener;
import com.gs.learn.mixture.task.GetClientNameTask;
import com.gs.learn.mixture.task.GetClientNameTask.FindNameListener;
import com.gs.learn.mixture.util.WifiUtil;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/12/11.
 */
public class WifiShareActivity extends AppCompatActivity implements 
		OnClickListener,OnCheckedChangeListener,GetClientListener,FindNameListener {
	private static final String TAG = "WifiShareActivity";
	private CheckBox ck_wifi_switch;
	private EditText et_wifi_name, et_wifi_password;
	private Spinner sp_wifi_des;
	private TextView tv_connect;
	private LinearLayout ll_client_title;
	private ListView lv_wifi_client;
	private WifiManager mWifiManager;
	private WifiConfiguration mWifiConfig = new WifiConfiguration();
	private int mDesType = 0;
	private ArrayList<ClientScanResult> mClientArray = new ArrayList<ClientScanResult>();
	private HashMap<String, String> mapName = new HashMap<String, String>();
	private Handler mHandler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wifi_share);
		ck_wifi_switch = (CheckBox) findViewById(R.id.ck_wifi_switch);
		et_wifi_name = (EditText) findViewById(R.id.et_wifi_name);
		et_wifi_password = (EditText) findViewById(R.id.et_wifi_password);
		tv_connect = (TextView) findViewById(R.id.tv_connect);
		ll_client_title = (LinearLayout) findViewById(R.id.ll_client_title);
		lv_wifi_client = (ListView) findViewById(R.id.lv_wifi_client);
		findViewById(R.id.btn_wifi_save).setOnClickListener(this);
		et_wifi_name.setText(Build.SERIAL);
		et_wifi_password.setText("");
		ck_wifi_switch.setOnCheckedChangeListener(this);
		mWifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		setWifiConfig();

		sp_wifi_des = (Spinner) findViewById(R.id.sp_wifi_des);
		ArrayAdapter<String> starAdapter = new ArrayAdapter<String>(this,
				R.layout.item_select, desNameArray);
		starAdapter.setDropDownViewResource(R.layout.item_select);
		sp_wifi_des.setAdapter(starAdapter);
		sp_wifi_des.setSelection(mDesType);
		sp_wifi_des.setPrompt("请选择加密方式");
		sp_wifi_des.setOnItemSelectedListener(new DesTypeSelectedListener());
		sp_wifi_des.setSelection(mDesType);
		mHandler.postDelayed(mClientTask, 50);
	}

	private String[] desNameArray = {"无", "WPA PSK", "WPA2 PSK"};
	private int[] desTypeArray = {WifiConfiguration.KeyMgmt.NONE, WifiConfiguration.KeyMgmt.WPA_PSK, 4};
	class DesTypeSelectedListener implements OnItemSelectedListener {
		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			mDesType = arg2;
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}

	private Runnable mClientTask = new Runnable() {
		@Override
		public void run() {
			GetClientListTask getClientTask = new GetClientListTask();
			getClientTask.setGetClientListener(WifiShareActivity.this);
			getClientTask.execute();
			mHandler.postDelayed(this, 3000);
		}
	};

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_wifi_save) {
			if (et_wifi_name.getText().length() < 4) {
				Toast.makeText(this, "WIFI名称长度需不小于四位", Toast.LENGTH_SHORT).show();
				return;
			} else if (mDesType!=0 && et_wifi_password.getText().length() < 8) {
				Toast.makeText(this, "WIFI密码长度需不小于八位", Toast.LENGTH_SHORT).show();
				return;
			}
			Toast.makeText(this, "已保存本次WIFI设置", Toast.LENGTH_SHORT).show();
			//只有当前已开启WIFI的，才需要断开并重连。当前未开启WIFI的，保存设置后不自动开WIFI
			int timeout = 0;
			if (ck_wifi_switch.isChecked() == true) {
				ck_wifi_switch.setChecked(false);
				timeout = 2000;
			}
			setWifiConfig();
			mHandler.postDelayed(mReOpenTask, timeout);
		}
	}

	private void setWifiConfig() {
		mWifiConfig.allowedKeyManagement.clear();
		mWifiConfig.SSID = et_wifi_name.getText().toString();
		if (mDesType == 0) {
			mWifiConfig.preSharedKey = "";
			mWifiConfig.wepKeys[0] = et_wifi_password.getText().toString();
			mWifiConfig.wepTxKeyIndex = 0;
		} else {
			mWifiConfig.allowedKeyManagement.set(desTypeArray[mDesType]);
			mWifiConfig.preSharedKey = et_wifi_password.getText().toString();
			mWifiConfig.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
			mWifiConfig.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
			mWifiConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
			mWifiConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
			mWifiConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
			mWifiConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
		}
	}
	
	private Runnable mReOpenTask = new Runnable() {
		@Override
		public void run() {
			if (WifiUtil.getWifiApState(mWifiManager) == WifiUtil.WIFI_AP_STATE_DISABLED) {
				ck_wifi_switch.setChecked(true);
			} else {
				mHandler.postDelayed(this, 2000);
			}
		}
	};

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (buttonView.getId() == R.id.ck_wifi_switch) {
			String result = "";
			if (isChecked == false) {
				result = WifiUtil.setWifiApEnabled(mWifiManager, mWifiConfig, isChecked);
			} else {
				setWifiConfig();
				result = WifiUtil.setWifiApEnabled(mWifiManager, mWifiConfig, isChecked);
			}
			Log.d(TAG, "onCheckedChanged: "+isChecked+". "+result);
			if (result!=null && result.length()>0) {
				Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
				ck_wifi_switch.setChecked(!isChecked);
			}
		}
	}

	@Override
	public void onGetClient(ArrayList<ClientScanResult> clientList) {
		mClientArray = clientList;
		Log.d(TAG, "mClientArray.size()=" + mClientArray.size());
		if (WifiUtil.getWifiApState(mWifiManager) != WifiUtil.WIFI_AP_STATE_ENABLING
				&& WifiUtil.getWifiApState(mWifiManager) != WifiUtil.WIFI_AP_STATE_ENABLED) {
			mClientArray.clear();
		} else if (mClientArray == null) {
			mClientArray = new ArrayList<ClientScanResult>();
		}
		if (mClientArray.size() <= 0) {
			tv_connect.setText("当前没有设备连接");
			ll_client_title.setVisibility(View.GONE);
		} else {
			String desc = String.format("当前已有%d台设备连接", mClientArray.size());
			tv_connect.setText(desc);
			ll_client_title.setVisibility(View.VISIBLE);
		}
		for (int i = 0; i < mClientArray.size(); i++) {
			ClientScanResult item = mClientArray.get(i);
			String ipAddr = item.getIpAddr();
			item.setDevice(MacManager.getInstance(this).getMacDevice(item.getHWAddr()));
			if (mapName.containsKey(ipAddr)) {
				item.setHostName(mapName.get(ipAddr));
			} else {
				item.setHostName(MacManager.getInstance(this).getDeviceName(item.getDevice()));
				String upperDevice = item.getDevice().toUpperCase();
				if (upperDevice.equals("INTEL") || upperDevice.equals("HEWLETT")
						|| upperDevice.equals("DELL") || upperDevice.equals("ASUS")
						|| upperDevice.equals("ACER") || upperDevice.equals("TOSHIBA")) {
					Log.d(TAG, "new GetClientNameTask");
					GetClientNameTask getNameTask = new GetClientNameTask();
					getNameTask.setFindNameListener(WifiShareActivity.this);
					getNameTask.execute(ipAddr);
				}
			}
		}
		ClientListAdapter clientAdapter = new ClientListAdapter(this, mClientArray);
		lv_wifi_client.setAdapter(clientAdapter);
	}

	public static native String nameFromJNI(String ip);
	public static native String unimplementedNameFromJNI(String ip);
	static {
		System.loadLibrary("jni_mix");
	}

	@Override
	public void onFindName(String info) {
		if (info != null && info.length() > 0) {
			String[] split = info.split("\\|");
			if (split.length > 1 && split[1].length() > 0) {
				mapName.put(split[0], split[1]);
			}
		}
	}

}
