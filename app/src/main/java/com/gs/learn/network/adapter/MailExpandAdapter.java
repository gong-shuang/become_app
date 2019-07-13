package com.gs.learn.network.adapter;

import java.util.ArrayList;

import com.gs.learn.R;
import com.gs.learn.network.bean.MailBox;
import com.gs.learn.network.bean.MailItem;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MailExpandAdapter implements ExpandableListAdapter,OnGroupClickListener,OnChildClickListener {
	private final static String TAG = "CustomExpandAdapter";
	private LayoutInflater mInflater;
	private Context mContext;
	private ArrayList<MailBox> mBoxList;

	public MailExpandAdapter(Context context, ArrayList<MailBox> box_list) {
		mInflater = LayoutInflater.from(context);
		mContext = context;
		mBoxList = box_list;
	}

	@Override
	public void registerDataSetObserver(DataSetObserver observer) {
	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
	}

	@Override
	public int getGroupCount() {
		return mBoxList.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return mBoxList.get(groupPosition).mail_list.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return mBoxList.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return mBoxList.get(groupPosition).mail_list.get(childPosition);
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
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		ViewHolderBox holder = null;
		if (convertView == null) {
			holder = new ViewHolderBox();
			convertView = mInflater.inflate(R.layout.item_box, null);
			holder.iv_box = (ImageView) convertView.findViewById(R.id.iv_box);
			holder.tv_box = (TextView) convertView.findViewById(R.id.tv_box);
			holder.tv_count = (TextView) convertView.findViewById(R.id.tv_count);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolderBox) convertView.getTag();
		}
		MailBox box = mBoxList.get(groupPosition);
		holder.iv_box.setImageResource(box.box_icon);
		holder.tv_box.setText(box.box_title);
		holder.tv_count.setText(box.mail_list.size()+"封邮件");
		return convertView;
	}

	@Override
	public View getChildView(final int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		ViewHolderMail holder = null;
		if (convertView == null) {
			holder = new ViewHolderMail();
			convertView = mInflater.inflate(R.layout.item_mail, null);
			holder.ck_mail = (CheckBox) convertView.findViewById(R.id.ck_mail);
			holder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolderMail) convertView.getTag();
		}
		MailItem item = mBoxList.get(groupPosition).mail_list.get(childPosition);
		holder.ck_mail.setFocusable(false);
		holder.ck_mail.setFocusableInTouchMode(false);
		holder.ck_mail.setText(item.mail_title);
		holder.ck_mail.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				MailBox box = mBoxList.get(groupPosition);
				MailItem item = box.mail_list.get(childPosition);
				String desc = String.format("您点击了%s的邮件，标题是%s", box.box_title, item.mail_title);
				Toast.makeText(mContext, desc, Toast.LENGTH_SHORT).show();
			}
		});
		holder.tv_date.setText(item.mail_date);
		return convertView;
	}

	//如果子条目需要响应点击事件，这里要返回true
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

	public final class ViewHolderBox {
		public ImageView iv_box;
		public TextView tv_box;
		public TextView tv_count;
	}

	public final class ViewHolderMail {
		public CheckBox ck_mail;
		public TextView tv_date;
	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		ViewHolderMail holder = (ViewHolderMail) v.getTag();
		holder.ck_mail.setChecked(!(holder.ck_mail.isChecked()));
		return true;
	}

	//如果返回true，就不会展示子列表
	@Override
	public boolean onGroupClick(ExpandableListView parent, View v,
			int groupPosition, long id) {
		String desc = String.format("您点击了%s", mBoxList.get(groupPosition).box_title);
		Toast.makeText(mContext, desc, Toast.LENGTH_SHORT).show();
		return false;
	}
	
	//子项目响应点击事件，需满足下面三个条件：
	//1、isChildSelectable方法要返回true
	//2、注册监听器setOnChildClickListener，并重写onChildClick方法
	//3、子项目中若有Button、EditText等默认占用焦点的控件，要去除焦点占用，setFocusable和setFocusableInTouchMode设置为false

}
