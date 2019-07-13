package com.gs.learn.mixture.database;

import java.util.ArrayList;
import java.util.Locale;

import com.gs.learn.mixture.bean.DeviceName;
import com.gs.learn.mixture.bean.MacDevice;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MacManager {
	private static final String TAG = "MacManager";
	private static MacManager mMac = null;
	private DeviceDBHelper mHelper;
	public static MacManager getInstance(Context context) {
		if (mMac == null) {
			mMac = new MacManager();
			mMac.mHelper = DeviceDBHelper.getInstance(context, 0);
		}
		return mMac;
	}

	public String getMacDevice(String mac) {
		String device = "未知";
		String formatMac = mac.substring(0, 8).toUpperCase(Locale.getDefault()).replace(":", "-");
		SQLiteDatabase db = mHelper.openReadLink();
		MacDeviceDB macDeviceDB = new MacDeviceDB(db);
		ArrayList<MacDevice> macList = macDeviceDB.queryByMac(formatMac);
		if (macList.size() > 0) {
			device = macList.get(0).device;
		}
		return device;
	}
	
	public String getDeviceName(String device) {
		String name = "未知";
		String deviceMac = device.toUpperCase(Locale.getDefault());
		SQLiteDatabase db = mHelper.openReadLink();
		DeviceNameDB deviceNameDB = new DeviceNameDB(db);
		ArrayList<DeviceName> deviceList = deviceNameDB.queryByDevice(deviceMac);
		Log.d(TAG, "device="+device+", deviceList.size()="+deviceList.size());
		if (deviceList.size() > 0) {
			name = deviceList.get(0).name;
		}
		return name;
	}

}
