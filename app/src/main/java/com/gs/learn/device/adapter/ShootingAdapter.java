package com.gs.learn.device.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.gs.learn.R;

public class ShootingAdapter extends BaseAdapter {
	private static final String TAG = "ShootingAdapter";
	private ArrayList<String> mPathList;
	private LayoutInflater mInflater;
	private Context mContext;

	public ShootingAdapter(Context context, ArrayList<String> pathList) {
		mInflater = LayoutInflater.from(context);
		mContext = context;
		mPathList = pathList;
	}

	@Override
	public int getCount() {
		return mPathList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return mPathList.get(arg0);
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
			convertView = mInflater.inflate(R.layout.item_shooting, null);
			holder.iv_shooting = (ImageView) convertView.findViewById(R.id.iv_shooting);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		String path = mPathList.get(position);
		holder.iv_shooting.setImageBitmap(BitmapFactory.decodeFile(path));
		return convertView;
	}

	public final class ViewHolder {
		public ImageView iv_shooting;
	}

}
