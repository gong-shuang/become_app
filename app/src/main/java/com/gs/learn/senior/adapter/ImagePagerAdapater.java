package com.gs.learn.senior.adapter;

import java.util.ArrayList;

import com.gs.learn.senior.bean.GoodsInfo;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class ImagePagerAdapater extends PagerAdapter {
	
	private Context mContext;
	private ArrayList<ImageView> mViewList = new ArrayList<ImageView>();
	private ArrayList<GoodsInfo> mGoodsList = new ArrayList<GoodsInfo>();
	
	public ImagePagerAdapater(Context context, ArrayList<GoodsInfo> goodsList) {
		mContext = context;
		mGoodsList = goodsList;
		for (int i=0; i<mGoodsList.size(); i++) {
			ImageView view = new ImageView(mContext);
			view.setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			view.setImageResource(mGoodsList.get(i).pic);
			view.setScaleType(ScaleType.FIT_CENTER);
			mViewList.add(view);
		}
	}
	
	@Override
	public int getCount() {
		return mViewList.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(mViewList.get(position));
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		container.addView(mViewList.get(position));
		return mViewList.get(position);
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return mGoodsList.get(position).name;
	}

}
