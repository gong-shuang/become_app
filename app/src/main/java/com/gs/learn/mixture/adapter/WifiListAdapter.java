package com.gs.learn.mixture.adapter;

import java.util.ArrayList;

import com.gs.learn.R;
import com.gs.learn.mixture.bean.WifiConnect;
import com.gs.learn.mixture.widget.InputDialogFragment;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;

public class WifiListAdapter extends BaseAdapter {
	private static final String TAG = "WifiListAdapter";
	private LayoutInflater mInflater;
	private Context mContext;
	private WifiManager mWifiManager;
	private ArrayList<WifiConnect> mWifiList;
	private int[] mSignalList = {
			R.drawable.signal_1, R.drawable.signal_2, R.drawable.signal_3, R.drawable.signal_4};

	public WifiListAdapter(Context context, WifiManager wifiMgr, ArrayList<WifiConnect> wifi_list) {
		mInflater = LayoutInflater.from(context);
		mContext = context;
		mWifiManager = wifiMgr;
		mWifiList = wifi_list;
	}
	
	@Override
	public int getCount() {
		return mWifiList.size();
	}
	
	@Override
	public Object getItem(int position) {
		return mWifiList.get(position);
	}
	
	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_wifi, null);
			holder.ck_wifi = (CheckBox) convertView.findViewById(R.id.ck_wifi);
			holder.iv_wifi = (ImageView) convertView.findViewById(R.id.iv_wifi);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final WifiConnect client = mWifiList.get(position);
		holder.ck_wifi.setText(client.SSID);
		if (client.status == true) {
			holder.ck_wifi.setChecked(true);
		} else {
			holder.ck_wifi.setChecked(false);
		}
		holder.ck_wifi.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked == true) {
					if (client.networkId >= 0) {
			            mWifiManager.enableNetwork(client.networkId, true);
					} else {
						if (client.type == 0) {
					        WifiConfiguration config = new WifiConfiguration();  
					        config.SSID = "\"" + client.SSID + "\"";  
				            config.wepKeys[0] = "";  
				            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);  
				            config.wepTxKeyIndex = 0;  
				            int netId = mWifiManager.addNetwork(config);
				            mWifiManager.enableNetwork(netId, true);  
						} else {
							InputDialogFragment dialog = InputDialogFragment.newInstance(
									client.SSID, client.type, "请输入wifi密码");
							String fragTag = mContext.getResources().getString(R.string.app_name);
							dialog.show(((Activity) mContext).getFragmentManager(), fragTag);
						}
					}
				} else {
					mWifiManager.disconnect();
				}
			}
		});
		holder.iv_wifi.setImageResource(mSignalList[client.level]);
		return convertView;
	}

	public final class ViewHolder {
		public CheckBox ck_wifi;
		public ImageView iv_wifi;
	}

}
