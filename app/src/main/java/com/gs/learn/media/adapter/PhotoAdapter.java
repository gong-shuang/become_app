package com.gs.learn.media.adapter;

import com.gs.learn.R;
import com.gs.learn.media.widget.RecyclerExtras.OnItemClickListener;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class PhotoAdapter extends RecyclerView.Adapter<ViewHolder> {
	private final static String TAG = "PhotoAdapter";
	private Context mContext;
	private LayoutInflater mInflater;
	private int[] mImageArray;

	public PhotoAdapter(Context context, int[] imageArray) {
		mContext = context;
		mInflater = LayoutInflater.from(context);
		mImageArray = imageArray;
	}

	@Override
	public int getItemCount() {
		return mImageArray.length;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup vg, int viewType) {
		View v = null;
		ViewHolder holder = null;
		v = mInflater.inflate(R.layout.item_photo, vg, false);
		holder = new ItemHolder(v);
		return holder;
	}

	@Override
	public void onBindViewHolder(ViewHolder vh, final int position) {
		ItemHolder holder = (ItemHolder) vh;
		Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), mImageArray[position]);
		LayoutParams params = holder.iv_photo.getLayoutParams();
		params.width = bitmap.getWidth() * params.height / bitmap.getHeight();
		holder.iv_photo.setLayoutParams(params);
		holder.iv_photo.setImageBitmap(bitmap);
		holder.ll_photo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mOnItemClickListener != null) {
					mOnItemClickListener.onItemClick(v, position);
				}
			}
		});
	}

	@Override
	public int getItemViewType(int position) {
		return 0;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public class ItemHolder extends ViewHolder {
		public LinearLayout ll_photo;
		public ImageView iv_photo;

		public ItemHolder(View v) {
			super(v);
			ll_photo = (LinearLayout) v.findViewById(R.id.ll_photo);
			iv_photo = (ImageView) v.findViewById(R.id.iv_photo);
		}

	}

	private OnItemClickListener mOnItemClickListener;
	public void setOnItemClickListener(OnItemClickListener listener) {
		this.mOnItemClickListener = listener;
	}

}
