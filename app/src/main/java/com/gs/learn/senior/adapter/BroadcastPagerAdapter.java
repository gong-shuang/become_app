package com.gs.learn.senior.adapter;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.gs.learn.senior.bean.GoodsInfo;
import com.gs.learn.senior.fragment.BroadcastFragment;

public class BroadcastPagerAdapter extends FragmentStatePagerAdapter {

	private ArrayList<GoodsInfo> mGoodsList = new ArrayList<GoodsInfo>();
	
	public BroadcastPagerAdapter(FragmentManager fm, ArrayList<GoodsInfo> goodsList) {
		super(fm);
		mGoodsList = goodsList;
	}

	public int getCount() {
		return mGoodsList.size();
	}

	public Fragment getItem(int position) {
		return BroadcastFragment.newInstance(position, 
				mGoodsList.get(position).pic, mGoodsList.get(position).desc);
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return mGoodsList.get(position).name;
	}

}

