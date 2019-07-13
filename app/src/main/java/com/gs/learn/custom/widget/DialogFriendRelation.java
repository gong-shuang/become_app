package com.gs.learn.custom.widget;

import com.gs.learn.R;
import com.gs.learn.custom.adapter.FriendRelationAdapter;
import com.gs.learn.custom.util.Utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;

public class DialogFriendRelation implements OnItemClickListener, OnDismissListener {
	private Dialog dialog;
	private View view;
	private Context mContext;
	private GridView gv_relation;
	private LinearLayout ll_relation_gap;
	private FriendRelationAdapter friendRelationAdapter;
	private String[] relation_name_array;
	private String[] relation_value_array;
	private int mGap;
	private int mSelected;

	public DialogFriendRelation(Context context, onSelectRelationListener listener) {
		mContext = context;
		view = LayoutInflater.from(context).inflate(R.layout.dialog_friend_relation, null);
		dialog = new Dialog(context, R.style.dialog_layout_bottom_transparent);
		
		gv_relation = (GridView) view.findViewById(R.id.gv_relation);
		ll_relation_gap = (LinearLayout) view.findViewById(R.id.ll_relation_gap);
		relation_name_array = context.getResources().getStringArray(R.array.relation_name);
		relation_value_array = context.getResources().getStringArray(R.array.relation_value);
		mOnSelectRelationListener = listener;
	}

	public void show(final int gap, final int selected) {
		mGap = gap;
		mSelected = selected;
		int dip_48 = Utils.dip2px(mContext, 48);
		int dip_2 = Utils.dip2px(mContext, 2);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, dip_48*(gap+1)-dip_2+gap);
		ll_relation_gap.setLayoutParams(params);
		ll_relation_gap.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
		friendRelationAdapter = new FriendRelationAdapter(mContext, relation_name_array, selected);
		gv_relation.setAdapter(friendRelationAdapter);
		gv_relation.setOnItemClickListener(this);
		dialog.getWindow().setContentView(view);
		dialog.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		dialog.getWindow().setGravity(Gravity.BOTTOM);
		dialog.show();
		dialog.setOnDismissListener(this);
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

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		mSelected = position;
		dismiss();
	}

	private onSelectRelationListener mOnSelectRelationListener;
	public interface onSelectRelationListener {
		public void setRelation(int gap, String name, String value);
	}
	
	@Override
	public void onDismiss(DialogInterface dialog) {
		if (mOnSelectRelationListener != null) {
			mOnSelectRelationListener.setRelation(mGap,
					relation_name_array[mSelected], relation_value_array[mSelected]);
		}
	}
	
}
