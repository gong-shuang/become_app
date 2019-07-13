package com.gs.learn.mixture.adapter;

import java.util.ArrayList;

import com.gs.learn.R;
import com.gs.learn.mixture.bean.ClientScanResult;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ClientListAdapter extends BaseAdapter {
	private static final String TAG = "ClientListAdapter";
	private LayoutInflater mInflater;
	private Context mContext;
	private ArrayList<ClientScanResult> mClientList;

	public ClientListAdapter(Context context, ArrayList<ClientScanResult> wifi_list) {
		mInflater = LayoutInflater.from(context);
		mContext = context;
		mClientList = wifi_list;
	}

	@Override
	public int getCount() {
		return mClientList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return mClientList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_client, null);
			holder.tv_device = (TextView) convertView.findViewById(R.id.tv_device);
			holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			holder.tv_ip = (TextView) convertView.findViewById(R.id.tv_ip);
			holder.tv_mac = (TextView) convertView.findViewById(R.id.tv_mac);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		ClientScanResult client = mClientList.get(position);
		holder.tv_device.setText(client.getDevice());
		holder.tv_name.setText(client.getHostName());
		holder.tv_ip.setText(client.getIpAddr());
		holder.tv_mac.setText(client.getHWAddr().toUpperCase());
		return convertView;
	}
	
	public final class ViewHolder {
		public TextView tv_device;
		public TextView tv_name;
		public TextView tv_ip;
		public TextView tv_mac;
	}

}
