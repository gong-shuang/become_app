package com.gs.learn.custom.adapter;

import java.util.ArrayList;

import com.gs.learn.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class FriendRelationAdapter extends BaseAdapter {

	private ArrayList<String> mContentList;
	private LayoutInflater mInflater;
	private Context mContext;
	private int mSelected;

	public FriendRelationAdapter(Context context, String[] content_list, int selected) {
		mInflater = LayoutInflater.from(context);
		mContext = context;
		mContentList = new ArrayList<String>();
		for (int i = 0; i < content_list.length; i++) {
			mContentList.add(content_list[i]);
		}
		mSelected = selected;
	}

	@Override
	public int getCount() {
		return mContentList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return mContentList.get(arg0);
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
			convertView = mInflater.inflate(R.layout.item_friend_relation, null);
			holder.tv_friend_relation = (TextView) convertView.findViewById(R.id.tv_friend_relation);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.tv_friend_relation.setText(mContentList.get(position));
		if (position == mSelected) {
			holder.tv_friend_relation.setBackgroundResource(R.color.blue);
			holder.tv_friend_relation.setTextColor(mContext.getResources().getColor(R.color.white));
		}
		return convertView;
	}

	public final class ViewHolder {
		public TextView tv_friend_relation;
	}

}
