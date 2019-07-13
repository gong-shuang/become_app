package com.gs.learn.senior.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

import com.gs.learn.MainApplication;
import com.gs.learn.R;
import com.gs.learn.senior.bean.CartInfo;

public class CartAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private Context mContext;
	private ArrayList<CartInfo> mCartArray;

	public CartAdapter(Context context, ArrayList<CartInfo> cart_list) {
		mInflater = LayoutInflater.from(context);
		mContext = context;
		mCartArray = cart_list;
	}

	@Override
	public int getCount() {
		return mCartArray.size();
	}

	@Override
	public Object getItem(int arg0) {
		return mCartArray.get(arg0);
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
			convertView = mInflater.inflate(R.layout.item_cart, null);
			holder.iv_thumb = (ImageView) convertView.findViewById(R.id.iv_thumb);
			holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			holder.tv_desc = (TextView) convertView.findViewById(R.id.tv_desc);
			holder.tv_count = (TextView) convertView.findViewById(R.id.tv_count);
			holder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
			holder.tv_sum = (TextView) convertView.findViewById(R.id.tv_sum);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		CartInfo info = mCartArray.get(position);
		holder.iv_thumb.setImageBitmap(MainApplication.getInstance().mIconMap.get(info.goods_id));
		holder.tv_name.setText(info.goods.name);
		holder.tv_desc.setText(info.goods.desc);
		holder.tv_count.setText(""+info.count);
		holder.tv_price.setText(""+(int)info.goods.price);
		holder.tv_sum.setText(""+(int)(info.count*info.goods.price));
		return convertView;
	}

	public final class ViewHolder {
		public ImageView iv_thumb;
		public TextView tv_name;
		public TextView tv_desc;
		public TextView tv_count;
		public TextView tv_price;
		public TextView tv_sum;
	}

}
