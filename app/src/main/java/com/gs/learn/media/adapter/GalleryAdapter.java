package com.gs.learn.media.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

@SuppressWarnings("deprecation")
public class GalleryAdapter extends BaseAdapter {
	private Context mContext;
	private int[] mImageRes;

	public GalleryAdapter(Context context, int[] imageRes) {
		mContext = context;
		mImageRes = imageRes;
	}

	@Override
	public int getCount() {
		return mImageRes.length;
	}

	@Override
	public Object getItem(int position) {
		return mImageRes[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView iv = new ImageView(mContext);
		iv.setImageResource(mImageRes[position]);
		iv.setLayoutParams(new Gallery.LayoutParams(120, 160));
		iv.setScaleType(ImageView.ScaleType.FIT_XY);
		return iv;
	}

}
