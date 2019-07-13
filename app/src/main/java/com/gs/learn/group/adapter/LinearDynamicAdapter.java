package com.gs.learn.group.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gs.learn.R;
import com.gs.learn.group.bean.GoodsInfo;
import com.gs.learn.group.widget.RecyclerExtras.OnItemClickListener;
import com.gs.learn.group.widget.RecyclerExtras.OnItemDeleteClickListener;
import com.gs.learn.group.widget.RecyclerExtras.OnItemLongClickListener;

public class LinearDynamicAdapter extends RecyclerView.Adapter<ViewHolder>
		implements OnClickListener, OnLongClickListener {
	private final static String TAG = "LinearDynamicAdapter";
	private Context mContext;
	private LayoutInflater mInflater;
	private ArrayList<GoodsInfo> mPublicArray;

	public LinearDynamicAdapter(Context context,
			ArrayList<GoodsInfo> publicArray) {
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
	
	private int getPosition(int item_id) {
		int pos = 0;
		for (int i=0; i<mPublicArray.size(); i++) {
			if (mPublicArray.get(i).id == item_id) {
				pos = i;
				break;
			}
		}
		return pos;
	}

	private int CLICK = 0;
	private int DELETE = 1;
	@Override
	public void onClick(View v) {
		int position = getPosition(v.getId()/10);
		int type = v.getId()%10;
		if (type == CLICK) {
			if (mOnItemClickListener != null) {
				mOnItemClickListener.onItemClick(v, position);
			}
		} else if (type == DELETE) {
			if (mOnItemDeleteClickListener != null) {
				mOnItemDeleteClickListener.onItemDeleteClick(v, position);
			}
		}
	}

	@Override
	public boolean onLongClick(View v) {
		int position = getPosition(v.getId()/10);
		if (mOnItemLongClickListener != null) {
			mOnItemLongClickListener.onItemLongClick(v, position);
		}
		return true;
	}

	@Override
	public void onBindViewHolder(ViewHolder vh, final int position) {
		GoodsInfo item = mPublicArray.get(position);
		ItemHolder holder = (ItemHolder) vh;
		holder.iv_pic.setImageResource(item.pic_id);
		holder.tv_title.setText(item.title);
		holder.tv_desc.setText(item.desc);
		holder.tv_delete.setVisibility((item.bPressed)?View.VISIBLE:View.GONE);
		holder.tv_delete.setId(item.id*10 + DELETE);
		holder.tv_delete.setOnClickListener(this);

		holder.ll_item.setId(item.id*10 + CLICK);
		holder.ll_item.setOnClickListener(this);
		holder.ll_item.setOnLongClickListener(this);
	}

	@Override
	public int getItemViewType(int position) {
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
		public TextView tv_delete;

		public ItemHolder(View v) {
			super(v);
			ll_item = (LinearLayout) v.findViewById(R.id.ll_item);
			iv_pic = (ImageView) v.findViewById(R.id.iv_pic);
			tv_title = (TextView) v.findViewById(R.id.tv_title);
			tv_desc = (TextView) v.findViewById(R.id.tv_desc);
			tv_delete = (TextView) v.findViewById(R.id.tv_delete);
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

	private OnItemDeleteClickListener mOnItemDeleteClickListener;
	public void setOnItemDeleteClickListener(OnItemDeleteClickListener listener) {
		this.mOnItemDeleteClickListener = listener;
	}

}
