package com.gs.learn.media.adapter;

import com.gs.learn.media.util.Utils;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

@SuppressWarnings("deprecation")
public class AlbumAdapter extends BaseAdapter {
	private Context mContext;
	private int[] mImageRes;
	private int[] mBackColors;
	private int dip_pad;
	private int dip_radius;

	public AlbumAdapter(Context context, int[] imageRes, int[] backColors) {
		mContext = context;
		mImageRes = imageRes;
		mBackColors = backColors;
		dip_pad = Utils.dip2px(mContext, 20);
		dip_radius = Utils.dip2px(mContext, 5);
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
		CardView card = new CardView(mContext);
		//这里不能使用LinearLayout.LayoutParams。否则会报错“java.lang.ClassCastException: android.widget.LinearLayout$LayoutParams cannot be cast to android.widget.Gallery$LayoutParams”
		card.setLayoutParams(new Gallery.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		card.setRadius(dip_radius);
		card.setContentPadding(dip_pad, dip_pad, dip_pad, dip_pad);
		card.setCardElevation(3f);
		card.setCardBackgroundColor(mBackColors[position]);

		ImageView iv = new ImageView(mContext);
		iv.setImageResource(mImageRes[position]);
		iv.setLayoutParams(new Gallery.LayoutParams(120, 160));
		iv.setScaleType(ImageView.ScaleType.FIT_XY);
		card.addView(iv);
		return card;
	}

}
