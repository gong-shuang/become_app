package com.gs.learn.senior;

import java.util.ArrayList;

import com.gs.learn.senior.adapter.MobilePagerAdapter;
import com.gs.learn.senior.bean.GoodsInfo;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import com.gs.learn.R;
/**
 * Created by ouyangshen on 2016/10/7.
 */
public class FragmentDynamicActivity extends FragmentActivity {
	private static final String TAG = "FragmentDynamicActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fragment_dynamic);
		Log.d(TAG, "onCreate");
		PagerTabStrip pts_tab = (PagerTabStrip) findViewById(R.id.pts_tab);
		pts_tab.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		
		ViewPager vp_content = (ViewPager) findViewById(R.id.vp_content);
		ArrayList<GoodsInfo> goodsList = GoodsInfo.getDefaultList();
		MobilePagerAdapter mAdapter = new MobilePagerAdapter(getSupportFragmentManager(), goodsList);
		vp_content.setAdapter(mAdapter);
		vp_content.setCurrentItem(0);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestroy");
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		Log.d(TAG, "onStart");
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		Log.d(TAG, "onStop");
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		Log.d(TAG, "onResume");
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		Log.d(TAG, "onPause");
	}
	
}
