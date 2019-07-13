package com.gs.learn.senior.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gs.learn.MainApplication;
import com.gs.learn.R;
import com.gs.learn.senior.ShoppingDetailActivity;
import com.gs.learn.senior.bean.GoodsInfo;

public class GoodsAdapter extends BaseAdapter implements OnItemClickListener {

	private LayoutInflater mInflater;
	private Context mContext;
	private ArrayList<GoodsInfo> mGoodsArray;

	public GoodsAdapter(Context context, ArrayList<GoodsInfo> goods_list, addCartListener listener) {
		mInflater = LayoutInflater.from(context);
		mContext = context;
		mGoodsArray = goods_list;
		mAddCartListener = listener;
	}

	@Override
	public int getCount() {
		return mGoodsArray.size();
	}

	@Override
	public Object getItem(int arg0) {
		return mGoodsArray.get(arg0);
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
			convertView = mInflater.inflate(R.layout.item_goods, null);
			holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			holder.iv_thumb = (ImageView) convertView.findViewById(R.id.iv_thumb);
			holder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
			holder.btn_add = (Button) convertView.findViewById(R.id.btn_add);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final GoodsInfo info = mGoodsArray.get(position);
		holder.tv_name.setText(info.name);
		holder.iv_thumb.setImageBitmap(MainApplication.getInstance().mIconMap.get(info.rowid));
		holder.tv_price.setText(""+(int)info.price);
		holder.btn_add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mAddCartListener.addToCart(info.rowid);
				Toast.makeText(mContext, 
						"已添加一部"+info.name+"到购物车", Toast.LENGTH_SHORT).show();
			}
		});
		return convertView;
	}

	public final class ViewHolder {
		public TextView tv_name;
		public ImageView iv_thumb;
		public TextView tv_price;
		public Button btn_add;
	}

	private addCartListener mAddCartListener;
	public static interface addCartListener {
		public void addToCart(long goods_id);
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		GoodsInfo info = mGoodsArray.get(position);
		Intent intent = new Intent(mContext, ShoppingDetailActivity.class);
		intent.putExtra("goods_id", info.rowid);
		mContext.startActivity(intent);
	}
	
}
