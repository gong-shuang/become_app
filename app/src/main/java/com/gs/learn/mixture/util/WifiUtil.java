package com.gs.learn.mixture.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.util.ArrayList;

import com.gs.learn.mixture.bean.ClientScanResult;

import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;

public class WifiUtil {
	private static final String TAG = "ApUtil";
	public static final int WIFI_AP_STATE_DISABLING = 0;
	public static final int WIFI_AP_STATE_DISABLED = 1;
	public static final int WIFI_AP_STATE_ENABLING = 2;
	public static final int WIFI_AP_STATE_ENABLED = 3;
	public static final int WIFI_AP_STATE_FAILED = 4;
	public static String[] stateArray = { "正在断开", "已断开", "正在连接", "已连接", "失败" };

	// wifi热点开关
	public static String setWifiApEnabled(WifiManager wifiMgr,
			WifiConfiguration apConfig, boolean enabled) {
		String desc = "";
		if (apConfig.SSID == null || apConfig.SSID.length() <= 0) {
			desc = "热点名称为空";
			return desc;
		}
		try {
			if (enabled) {
				// wifi和热点不能同时打开，所以打开热点的时候需要关闭wifi
				wifiMgr.setWifiEnabled(false);
			}
			// 通过反射调用设置热点
			Method method = wifiMgr.getClass().getMethod("setWifiApEnabled",
					WifiConfiguration.class, Boolean.TYPE);
			// 返回热点打开状态
			if ((Boolean) method.invoke(wifiMgr, apConfig, enabled) != true) {
				desc = "热点操作失败";
			}
		} catch (Exception e) {
			e.printStackTrace();
			desc = "热点操作异常：" + e.getMessage();
		}
		return desc;
	}

	public static int getWifiApState(WifiManager wifiMgr) {
		try {
			Method method = wifiMgr.getClass().getMethod("getWifiApState");
			int i = (Integer) method.invoke(wifiMgr);
			if (i > 9) {
				i -= 10;
			}
			Log.d(TAG, "wifi state:  " + i);
			return i;
		} catch (Exception e) {
			e.printStackTrace();
			return WIFI_AP_STATE_FAILED;
		}
	}

	public static WifiConfiguration getWifiApConfiguration(WifiManager wifiMgr) {
		WifiConfiguration config = new WifiConfiguration();
		try {
			Method method = wifiMgr.getClass().getMethod(
					"getWifiApConfiguration");
			config = (WifiConfiguration) method.invoke(wifiMgr);
			return config;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getSerialNumber() {
		String serial = "";
		try {
			Class<?> c = Class.forName("android.os.SystemProperties");
			Method get = c.getMethod("get", String.class);
			serial = (String) get.invoke(c, "ro.serialno");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return serial;
	}

	public static ArrayList<ClientScanResult> getClientList(boolean only_alive) {
		return getClientList(only_alive, 1000);
	}

	public static ArrayList<ClientScanResult> getClientList(boolean only_alive, int time_out) {
		BufferedReader br = null;
		ArrayList<ClientScanResult> result = null;

		try {
			result = new ArrayList<ClientScanResult>();
			br = new BufferedReader(new FileReader("/proc/net/arp"));
			String line;
			while ((line = br.readLine()) != null) {
				Log.d(TAG, "设备连接信息：" + line);
				String[] splitted = line.split(" +");
				if ((splitted != null) && (splitted.length >= 4)) {
					String ip = splitted[0];
					String mac = splitted[3];
					String device = splitted[5];

					if (mac.matches("..:..:..:..:..:..")) {
						Log.d(TAG, "ip=" + ip + ", mac=" + mac);
						if (mac.equals("00:00:00:00:00:00")) {
							continue;
						}
						// Inet4Address inet4
						InetAddress address = InetAddress.getByName(ip);
						boolean isReachable = address.isReachable(time_out);
						Log.d(TAG, "ip=" + ip + ", mac=" + mac + ", device="
								+ device + ", isReachable=" + isReachable);
						result.add(new ClientScanResult(ip, mac, device,
								isReachable, ip));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

}
