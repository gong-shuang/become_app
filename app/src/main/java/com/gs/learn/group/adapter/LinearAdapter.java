package com.gs.learn.group.adapter;

import java.util.ArrayList;

import com.gs.learn.R;
import com.gs.learn.group.bean.GoodsInfo;
import com.gs.learn.group.widget.RecyclerExtras.OnItemClickListener;
import com.gs.learn.group.widget.RecyclerExtras.OnItemLongClickListener;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LinearAdapter extends RecyclerView.Adapter<ViewHolder> implements
		OnItemClickListener, OnItemLongClickListener {
	private final static String TAG = "LinearAdapter";
	private Context mContext;
	private LayoutInflater mInflater;
	private ArrayList<GoodsInfo> mPublicArray;

	public LinearAdapter(Context context, ArrayList<GoodsInfo> publicArray) {
		mContext = context;
		mInflater = LayoutInflater.from(context);
		mPublicArray = publicArray;
	}

	@Override
	public int getItemCount() {
		return mPublicArray.size();
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup vg, int viewType) {
		View v = null;
		ViewHolder holder = null;
		v = mInflater.inflate(R.layout.item_linear, vg, false);
		holder = new ItemHolder(v);
		return holder;
	}

	@Override
	public void onBindViewHolder(ViewHolder vh, final int position) {
		ItemHolder holder = (ItemHolder) vh;
		holder.iv_pic.setImageResource(mPublicArray.get(position).pic_id);
		holder.tv_title.setText(mPublicArray.get(position).title);
		holder.tv_desc.setText(mPublicArray.get(position).desc);

		// 列表项的点击事件需要自己实现
		holder.ll_item.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mOnItemClickListener != null) {
					mOnItemClickListener.onItemClick(v, position);
				}
			}
		});
		holder.ll_item.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				if (mOnItemLongClickListener != null) {
					mOnItemLongClickListener.onItemLongClick(v, position);
				}
				return true;
			}
		});
	}

	@Override
	public int getItemViewType(int position) {
		// 这里返回每项的类型，开发者可自定义头部类型与一般类型，
		// 然后在onCreateViewHolder方法中根据类型加载不同的布局，从而实现带头部的网格布局
		return 0;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public class ItemHolder extends RecyclerView.ViewHolder {
		public LinearLayout ll_item;
		public ImageView iv_pic;
		public TextView tv_title;
		public TextView tv_desc;

		public ItemHolder(View v) {
			super(v);
			ll_item = (LinearLayout) v.findViewById(R.id.ll_item);
			iv_pic = (ImageView) v.findViewById(R.id.iv_pic);
			tv_title = (TextView) v.findViewById(R.id.tv_title);
			tv_desc = (TextView) v.findViewById(R.id.tv_desc);
		}

	}

	private OnItemClickListener mOnItemClickListener;
	public void setOnItemClickListener(OnItemClickListener listener) {
		this.mOnItemClickListener = listener;
	}

	private OnItemLongClickListener mOnItemLongClickListener;
	public void setOnItemLongClickListener(OnItemLongClickListener listener) {
		this.mOnItemLongClickListener = listener;
	}

	@Override
	public void onItemClick(View view, int position) {
		String desc = String.format("您点击了第%d项，标题是%s", position + 1,
				mPublicArray.get(position).title);
		Toast.makeText(mContext, desc, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onItemLongClick(View view, int position) {
		String desc = String.format("您长按了第%d项，标题是%s", position + 1,
				mPublicArray.get(position).title);
		Toast.makeText(mContext, desc, Toast.LENGTH_SHORT).show();
	}

}
