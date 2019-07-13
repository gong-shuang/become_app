package com.gs.learn.group.adapter;

import java.util.ArrayList;

import com.gs.learn.group.fragment.GoodsDetailFragment;
import com.gs.learn.group.fragment.GoodsOverviewFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class GoodsPagerAdapter extends FragmentPagerAdapter {
	private ArrayList<String> mTitleArray;

	public GoodsPagerAdapter(FragmentManager fm, ArrayList<String> titleArray) {
		super(fm);
		mTitleArray = titleArray;
	}

	@Override
	public Fragment getItem(int position) {
		if (position == 0) {
			return new GoodsOverviewFragment();
		} else if (position == 1) {
			return new GoodsDetailFragment();
		}
		return new GoodsOverviewFragment();
	}

	@Override
	public int getCount() {
		return mTitleArray.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return mTitleArray.get(position);
	}
}
