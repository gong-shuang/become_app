package com.gs.learn.senior;

import com.gs.learn.senior.adapter.CalendarPagerAdapter;
import com.gs.learn.senior.util.DateUtil;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.widget.TextView;
import com.gs.learn.R;
/**
 * Created by ouyangshen on 2016/10/7.
 */
public class CalendarActivity extends FragmentActivity {
	private static final String TAG = "CalendarActivity";
	private ViewPager vp_calendar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar);
		PagerTabStrip pts_calendar = (PagerTabStrip) findViewById(R.id.pts_calendar);
		pts_calendar.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
		pts_calendar.setTextColor(Color.BLACK);
		vp_calendar = (ViewPager) findViewById(R.id.vp_calendar);
		TextView tv_calendar = (TextView) findViewById(R.id.tv_calendar);
		tv_calendar.setText(DateUtil.getNowYearCN() + " 日历");

		CalendarPagerAdapter adapter = new CalendarPagerAdapter(getSupportFragmentManager());
		vp_calendar.setAdapter(adapter);
		vp_calendar.setCurrentItem(DateUtil.getNowMonth() - 1);
	}

}
