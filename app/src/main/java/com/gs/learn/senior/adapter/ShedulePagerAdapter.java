package com.gs.learn.senior.adapter;

import com.gs.learn.senior.calendar.Constant;
import com.gs.learn.senior.fragment.ScheduleFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ShedulePagerAdapter extends FragmentStatePagerAdapter {

	public ShedulePagerAdapter(FragmentManager fm) {
		super(fm);
	}

	public int getCount() {
		return 52;
	}

	public Fragment getItem(int position) {
		return ScheduleFragment.newInstance(position + 1);
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return new String("第" + Constant.xuhaoArray[position + 1] + "周");
	}

}
