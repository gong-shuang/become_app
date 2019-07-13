package com.gs.learn.senior.adapter;

import java.util.ArrayList;

import com.gs.learn.senior.bean.GoodsInfo;
import com.gs.learn.senior.fragment.DynamicFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class MobilePagerAdapter extends FragmentStatePagerAdapter {

	private ArrayList<GoodsInfo> mGoodsList = new ArrayList<GoodsInfo>();
	
	public MobilePagerAdapter(FragmentManager fm, ArrayList<GoodsInfo> goodsList) {
		super(fm);
		mGoodsList = goodsList;
	}

	public int getCount() {
		return mGoodsList.size();
	}

	public Fragment getItem(int position) {
		return DynamicFragment.newInstance(position, 
				mGoodsList.get(position).pic, mGoodsList.get(position).desc);
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return mGoodsList.get(position).name;
	}

}

