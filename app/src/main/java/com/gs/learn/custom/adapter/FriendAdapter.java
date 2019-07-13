package com.gs.learn.custom.adapter;

import java.util.List;

import com.gs.learn.R;
import com.gs.learn.custom.bean.Friend;
import com.gs.learn.custom.widget.DialogFriendRelation;
import com.gs.learn.custom.widget.DialogFriendRelation.onSelectRelationListener;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FriendAdapter extends BaseAdapter implements onSelectRelationListener {

	private Context mContext;
	private ViewHolders holder;
	private String[] names;
	private List<Friend> mFriendList;
	
	public FriendAdapter(Context context, List<Friend> friendList, OnDeleteListener listener) {
		mContext = context;
		mFriendList = friendList;
		names = mContext.getResources().getStringArray(R.array.relation_name);
		deleteListener = listener;
	}

	@Override
	public int getCount() {
		return mFriendList.size();
	}

	@Override
	public Object getItem(int position) {
		return mFriendList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		holder = null;
		if (convertView == null) {
			holder = new ViewHolders();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_friend, null);
			holder.rl_relation = (RelativeLayout) convertView.findViewById(R.id.rl_relation);
			holder.tv_relation = (TextView) convertView.findViewById(R.id.tv_relation);
			holder.ib_dropdown = (ImageView) convertView.findViewById(R.id.ib_dropdown);
			holder.tv_phone = (TextView) convertView.findViewById(R.id.tv_phone);
			holder.rg_admit = (RadioGroup) convertView.findViewById(R.id.rg_admit);
			holder.rb_true = (RadioButton) convertView.findViewById(R.id.rb_true);
			holder.rb_false = (RadioButton) convertView.findViewById(R.id.rb_false);
			holder.tv_operation = (TextView) convertView.findViewById(R.id.tv_operation);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolders) convertView.getTag();
		}
		holder.tv_phone.setText(mFriendList.get(position).phone);
		holder.tv_relation.setText(mFriendList.get(position).relation);
		holder.rg_admit.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId == R.id.rb_true){
					mFriendList.get(position).admit_circle = true;
				} else if(checkedId == R.id.rb_false){
					mFriendList.get(position).admit_circle = false;
				}
			}
		});
		holder.tv_operation.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(deleteListener!=null){
					deleteListener.onDeleteClick(position);
				}
			}
		});
		setRelationChangeListener(holder, position);
		holder.rl_relation.setBackgroundResource(R.color.white);
		return convertView;
	}

	private void setRelationChangeListener(final ViewHolders holder, final int position){
		holder.rl_relation.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				int selected = 0;
				for (int i=0; i<names.length; i++) {
					if (names[i].equals(mFriendList.get(position).relation)) {
						selected = i;
						break;
					}
				}
				DialogFriendRelation dialog = new DialogFriendRelation(mContext, FriendAdapter.this);
				dialog.show(getCount()-position, selected);
				holder.rl_relation.setBackgroundResource(R.color.grey);
			}
		});
	}
	
	public List<Friend> getFriends() {
		return mFriendList;
	}
	
	@Override
	public void setRelation(int gap, String name, String value) {
		mFriendList.get(getCount()-gap).relation = name;
		mFriendList.get(getCount()-gap).value = value;
		notifyDataSetChanged();
	}
	
	private OnDeleteListener deleteListener;
	public interface OnDeleteListener{
		public void onDeleteClick(int position);
	}

	class ViewHolders {
		RelativeLayout rl_relation;
		TextView tv_relation;
		ImageView ib_dropdown;
		TextView tv_phone;
		RadioGroup rg_admit;
		RadioButton rb_true;
		RadioButton rb_false;
		TextView tv_operation;
	}

}
