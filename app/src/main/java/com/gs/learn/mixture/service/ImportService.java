package com.gs.learn.mixture.service;

import java.util.ArrayList;

import com.gs.learn.mixture.bean.DeviceName;
import com.gs.learn.mixture.bean.MacDevice;
import com.gs.learn.mixture.database.DeviceDBHelper;
import com.gs.learn.mixture.database.DeviceNameDB;
import com.gs.learn.mixture.database.MacDeviceDB;
import com.gs.learn.mixture.util.AssetsUtil;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

public class ImportService extends IntentService {
	private static final String TAG = "ImportService";
	public ImportService() {
		super("com.gs.learn.mixture.service.ImportService");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startid) {
		return super.onStartCommand(intent, flags, startid); 
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Log.d(TAG, "begin onHandleIntent");
		importDevice();
		Log.d(TAG, "end onHandleIntent");
	}

	private void importDevice() {
		SharedPreferences sps= getSharedPreferences("share", MODE_PRIVATE);
		int version = sps.getInt("version", 0);
		if (version >= DeviceDBHelper.DB_VERSION) {
			Log.d(TAG, "当前无新数据导入");
			return;
		}
		DeviceDBHelper helper = DeviceDBHelper.getInstance(this, DeviceDBHelper.DB_VERSION);

		ArrayList<DeviceName> nameArray = new ArrayList<DeviceName>();
		String nameContent = AssetsUtil.getTxtFromAssets(this, "device/device_name.txt");
		nameContent = nameContent.replace("\r", "");
		String[] nameList = nameContent.split("\n");
		for (int i=0; i<nameList.length; i++) {
			String line = nameList[i];
			String[] itemList = line.split(",");
			if (itemList.length >= 2) {
				DeviceName name = new DeviceName(itemList[0].toUpperCase(), itemList[1]);
				nameArray.add(name);
			}
		}
		Log.d(TAG, "nameArray.size()="+nameArray.size());
		DeviceNameDB nameDB = new DeviceNameDB(helper.openLink());
		nameDB.insert(nameArray);

		ArrayList<MacDevice> macArray = new ArrayList<MacDevice>();
		String macContent = AssetsUtil.getTxtFromAssets(this, "device/mac_device.txt");
		macContent = macContent.replace("\r", "");
		String[] macList = macContent.split("\n");
		for (int i=0; i<macList.length; i++) {
			String line = macList[i];
			String[] itemList = line.split(",");
			if (itemList.length >= 2) {
				MacDevice mac = new MacDevice(itemList[0], itemList[1]);
				macArray.add(mac);
			}
		}
		Log.d(TAG, "macArray.size()="+macArray.size());
		MacDeviceDB macDB = new MacDeviceDB(helper.openLink());
		macDB.insert(macArray);
		
		SharedPreferences.Editor editor = sps.edit();
		editor.putInt("version", DeviceDBHelper.DB_VERSION);
		editor.commit(); 
		String desc = String.format("已成功导入%d条设备名称记录，导入%d条设备MAC记录", 
				nameArray.size(), macArray.size());
		Log.d(TAG, desc);
	}

}
