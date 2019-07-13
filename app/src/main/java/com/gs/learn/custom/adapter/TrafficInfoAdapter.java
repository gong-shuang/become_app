package com.gs.learn.custom.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gs.learn.R;
import com.gs.learn.custom.bean.AppInfo;
import com.gs.learn.custom.util.StringUtil;

public class TrafficInfoAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private Context mContext;
	private ArrayList<AppInfo> mAppInfoList;

	public TrafficInfoAdapter(Context context, ArrayList<AppInfo> appinfoList) {
		mInflater = LayoutInflater.from(context);
		mContext = context;
		mAppInfoList = appinfoList;
	}

	@Override
	public int getCount() {
		return mAppInfoList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return mAppInfoList.get(arg0);
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
			convertView = mInflater.inflate(R.layout.item_traffic, null);
			holder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
			holder.tv_label = (TextView) convertView.findViewById(R.id.tv_label);
			holder.tv_package_name = (TextView) convertView.findViewById(R.id.tv_package_name);
			holder.tv_traffic = (TextView) convertView.findViewById(R.id.tv_traffic);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		AppInfo item = mAppInfoList.get(position);
		if (item.icon != null) {
			holder.iv_icon.setImageDrawable(item.icon);
		}
		holder.tv_label.setText(item.label);
		holder.tv_package_name.setText(item.package_name);
		holder.tv_traffic.setText(StringUtil.formatTraffic(item.traffic));
		return convertView;
	}

	public final class ViewHolder {
		public ImageView iv_icon;
		public TextView tv_label;
		public TextView tv_package_name;
		public TextView tv_traffic;
	}

}
