package com.gs.learn.custom.widget;

import java.util.List;

import com.gs.learn.R;
import com.gs.learn.custom.adapter.FriendAdapter;
import com.gs.learn.custom.adapter.FriendAdapter.OnDeleteListener;
import com.gs.learn.custom.bean.Friend;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

public class DialogFriend implements OnClickListener, OnDeleteListener {
	private Context mContext;
	private Dialog dialog;
	private View view;
	private TextView tv_title;
	private ListView lv_friend;
	private List<Friend> mFriendList;
	private FriendAdapter friendAdapter;

	public DialogFriend(Context context, List<Friend> friendList, onAddFriendListener listener) {
		mContext = context;
		view = LayoutInflater.from(context).inflate(R.layout.dialog_friend, null);
		dialog = new Dialog(context, R.style.dialog_layout_bottom);
		tv_title = (TextView) view.findViewById(R.id.tv_title);
		lv_friend = (ListView) view.findViewById(R.id.lv_friend);
		view.findViewById(R.id.tv_ok).setOnClickListener(this);
		mOnAddFriendListener = listener;
		mFriendList = friendList;
		friendAdapter = new FriendAdapter(mContext, mFriendList, this);
		lv_friend.setAdapter(friendAdapter);
	}

	public void show() {
		dialog.setCancelable(true);
		dialog.show();
		dialog.getWindow().setContentView(view);
		dialog.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		dialog.getWindow().setGravity(Gravity.BOTTOM);
	}

	public void dismiss() {
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
	}

	public boolean isShowing() {
		if (dialog != null)
			return dialog.isShowing();
		return false;
	}

	public void setCancelable(boolean flag) {
		dialog.setCancelable(flag);
	}

	public void setCancelableOnTouchOutside(boolean flag) {
		dialog.setCanceledOnTouchOutside(flag);
	}

	public void setTitle(String title) {
		tv_title.setText(title);
	}

	private onAddFriendListener mOnAddFriendListener;
	public interface onAddFriendListener {
		public void addFriend(List<Friend> friendList);
	}
	
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.tv_ok) {
			if (mOnAddFriendListener != null) {
				mOnAddFriendListener.addFriend(friendAdapter.getFriends());
				dialog.dismiss();
			}
		}
	}

	@Override
	public void onDeleteClick(int position) {
		mFriendList.remove(position);
		friendAdapter.notifyDataSetChanged();
	}
	
}
