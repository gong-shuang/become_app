package com.gs.learn.network.adapter;

import java.util.ArrayList;

import com.gs.learn.network.ChatMainActivity;
import com.gs.learn.R;
import com.gs.learn.network.bean.Friend;
import com.gs.learn.network.bean.FriendGroup;
import com.gs.learn.network.util.DateUtil;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;

public class FriendExpandAdapter implements ExpandableListAdapter,OnGroupClickListener,OnChildClickListener {
	private final static String TAG = "FriendExpandAdapter";
	private LayoutInflater mInflater;
	private Context mContext;
	private ArrayList<FriendGroup> mGroupList;
	private int[] mFaceArray = {
			R.drawable.qq01, R.drawable.qq02, R.drawable.qq03, R.drawable.qq04, R.drawable.qq05,
			R.drawable.qq06, R.drawable.qq07, R.drawable.qq08, R.drawable.qq09, R.drawable.qq10,
			R.drawable.qq11, R.drawable.qq12, R.drawable.qq13, R.drawable.qq14, R.drawable.qq15,
			R.drawable.qq16, R.drawable.qq17, R.drawable.qq18, R.drawable.qq19, R.drawable.qq20,
			R.drawable.qq21, R.drawable.qq22, R.drawable.qq23, R.drawable.qq24, R.drawable.qq25,
			R.drawable.qq26, R.drawable.qq27, R.drawable.qq28, R.drawable.qq29, R.drawable.qq30,
			R.drawable.qq31, R.drawable.qq32, R.drawable.qq33, R.drawable.qq34, R.drawable.qq35,
			R.drawable.qq36, R.drawable.qq37, R.drawable.qq38, R.drawable.qq39, R.drawable.qq40,
	};

	public FriendExpandAdapter(Context context, ArrayList<FriendGroup> group_list) {
		mInflater = LayoutInflater.from(context);
		mContext = context;
		mGroupList = group_list;
	}

	@Override
	public void registerDataSetObserver(DataSetObserver observer) {
	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
	}

	@Override
	public int getGroupCount() {
		return mGroupList.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return mGroupList.get(groupPosition).friend_list.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return mGroupList.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return mGroupList.get(groupPosition).friend_list.get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		ViewHolderGroup holder = null;
		if (convertView == null) {
			holder = new ViewHolderGroup();
			convertView = mInflater.inflate(R.layout.item_group, null);
			holder.tv_group_name = (TextView) convertView.findViewById(R.id.tv_group_name);
			holder.tv_group_count = (TextView) convertView.findViewById(R.id.tv_group_count);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolderGroup) convertView.getTag();
		}
		FriendGroup group = mGroupList.get(groupPosition);
		holder.tv_group_name.setText(group.title);
		holder.tv_group_count.setText(group.friend_list.size()+"个好友");
		return convertView;
	}

	@Override
	public View getChildView(final int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		ViewHolderFriend holder = null;
		if (convertView == null) {
			holder = new ViewHolderFriend();
			convertView = mInflater.inflate(R.layout.item_friend_network, null);
			holder.iv_face = (ImageView) convertView.findViewById(R.id.iv_face);
			holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolderFriend) convertView.getTag();
		}
		Friend item = mGroupList.get(groupPosition).friend_list.get(childPosition);
		holder.iv_face.setImageResource(mFaceArray[(int) (Math.random()*200%40)]);
		holder.tv_name.setText(item.nick_name);
		if (groupPosition == 0) { //在线好友列表
			holder.tv_time.setText(DateUtil.formatTime(item.login_time));
		} else {
			holder.tv_time.setText("");
		}
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	@Override
	public boolean areAllItemsEnabled() {
		return true;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public void onGroupExpanded(int groupPosition) {
	}

	@Override
	public void onGroupCollapsed(int groupPosition) {
	}

	@Override
	public long getCombinedChildId(long groupId, long childId) {
		return 0;
	}

	@Override
	public long getCombinedGroupId(long groupId) {
		return 0;
	}

	public final class ViewHolderGroup {
		public TextView tv_group_name;
		public TextView tv_group_count;
	}

	public final class ViewHolderFriend {
		public ImageView iv_face;
		public TextView tv_name;
		public TextView tv_time;
	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
		if (groupPosition == 0) { //在线好友列表
			Friend friend = mGroupList.get(groupPosition).friend_list.get(childPosition);
			Intent intent = new Intent(mContext, ChatMainActivity.class);
			intent.putExtra("otherId", friend.device_id);
			intent.putExtra("otherName", friend.nick_name);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			mContext.startActivity(intent);
		} else {
			String desc = String.format("您点击了好友：%s", mGroupList.get(groupPosition).friend_list.get(childPosition).nick_name);
			Toast.makeText(mContext, desc, Toast.LENGTH_SHORT).show();
		}
		return true;
	}

	@Override
	public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
		String desc = String.format("您点击了分组：%s", mGroupList.get(groupPosition).title);
		Toast.makeText(mContext, desc, Toast.LENGTH_SHORT).show();
		return false;
	}
	
}
