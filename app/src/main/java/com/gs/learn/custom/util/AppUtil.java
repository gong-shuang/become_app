package com.gs.learn.custom.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.gs.learn.custom.bean.AppInfo;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.util.SparseIntArray;

public class AppUtil {

	public static ArrayList<AppInfo> getAppInfo(Context ctx, int type) {
		ArrayList<AppInfo> appList = new ArrayList<AppInfo>();
		SparseIntArray siArray = new SparseIntArray();
		PackageManager pm = ctx.getPackageManager();
		List<ApplicationInfo> installList = pm.getInstalledApplications(PackageManager.PERMISSION_GRANTED);
		for (int i=0; i<installList.size(); i++) {
			ApplicationInfo item = installList.get(i);
			//去掉重复的应用信息
			if (siArray.indexOfKey(item.uid) >= 0) {
				continue;
			}
			siArray.put(item.uid, 1);
			try {
				String[] permissions = pm.getPackageInfo(item.packageName, PackageManager.GET_PERMISSIONS).requestedPermissions;
				if (permissions == null) {
					continue;
				}
				boolean bNet = false;
				for (String permission : permissions) {
					if (permission.equals("android.permission.INTERNET")) {
						bNet = true;
						break;
					}
				}
				if (type==0 || (type==1 && bNet)) {
					AppInfo app = new AppInfo();
					app.uid = item.uid;
					app.label = item.loadLabel(pm).toString();
					app.package_name = item.packageName;
					app.icon = item.loadIcon(pm);
					appList.add(app);
				}
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
		return appList;
	}

	public static ArrayList<AppInfo> fillAppInfo(Context ctx, ArrayList<AppInfo> originArray) {
		ArrayList<AppInfo> fullArray = new ArrayList<AppInfo>();
		fullArray = (ArrayList<AppInfo>) originArray.clone();

		PackageManager pm = ctx.getPackageManager();
		List<ApplicationInfo> installList = pm.getInstalledApplications(PackageManager.PERMISSION_GRANTED);
		for (int i=0; i<fullArray.size(); i++) {
			AppInfo app = fullArray.get(i);
			for (int j=0; j<installList.size(); j++) {
				ApplicationInfo item = installList.get(j);
				if (app.uid == item.uid) {
					app.icon = item.loadIcon(pm);
					break;
				}
			}
			fullArray.set(i, app);
		}

		//各应用按照流量降序排列
		Collections.sort(fullArray, new Comparator<AppInfo>() {
			@Override
			public int compare(AppInfo o1, AppInfo o2) {
				return (o1.traffic<o2.traffic)?1:-1;
			}
		});
		return fullArray;
	}
	
}
