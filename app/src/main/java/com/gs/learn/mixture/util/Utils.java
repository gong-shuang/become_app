package com.gs.learn.mixture.util;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.Log;

public class Utils {
	private final static String TAG = "Utils";

	public static String[] mClassArray = {"UNKNOWN", "2G", "3G", "4G"}; 

	public static int TYPE_UNKNOWN = 0;
	public static int TYPE_2G = 1;
	public static int TYPE_3G = 2;
	public static int TYPE_4G = 3;
    
	public static int TYPE_GSM = 1;
	public static int TYPE_CDMA = 2;
	public static int TYPE_LTE = 3;
	public static int TYPE_WCDMA = 4;
    
	@SuppressLint("SimpleDateFormat")
	public static String getNowDateTime() {
		SimpleDateFormat s_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d_date = new Date();
		String s_date = "";
		s_date = s_format.format(d_date);
		return s_date;
	}

	public static String getTimeStamp(long timestamp) {
		Timestamp ts = new Timestamp(System.currentTimeMillis());  
		return ts.toString();
	}
	
	public static String getNetworkTypeName(TelephonyManager tm, int net_type) {
		String type_name = "";
		try {
			Method method = tm.getClass().getMethod("getNetworkTypeName", Integer.TYPE);
			type_name = (String) method.invoke(tm, net_type);
		} catch (Exception e) {
			Log.d(TAG, "getNetworkTypeName error: "+e.getMessage());
		}
		return type_name;
	}

	public static int getClassType(TelephonyManager tm, int net_type) {
		int class_type = 0;
		try {
			Method method = tm.getClass().getMethod("getNetworkClass", Integer.TYPE);
			class_type = (Integer) method.invoke(tm, net_type);
		} catch (Exception e) {
			Log.d(TAG, "getNetworkClass error: "+e.getMessage());
		}
		return class_type;
	}

	public static String getClassName(TelephonyManager tm, int net_type) {
		int class_type = getClassType(tm, net_type);
		return mClassArray[class_type];
	}

	//获取Wifi的开关状态
	public static boolean getWlanStatus(Context context) {
		WifiManager wifiMgr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		return wifiMgr.isWifiEnabled();
	}

	//打开或关闭Wifi
	public static void setWlanStatus(Context context, boolean enabled) {
		WifiManager wifiMgr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		wifiMgr.setWifiEnabled(enabled);
	}

}
