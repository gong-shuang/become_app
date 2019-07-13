package com.gs.learn.senior;

import java.util.ArrayList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.widget.LinearLayout;

import com.gs.learn.senior.adapter.BroadcastPagerAdapter;
import com.gs.learn.senior.bean.GoodsInfo;
import com.gs.learn.senior.fragment.BroadcastFragment;
import com.gs.learn.R;
/**
 * Created by ouyangshen on 2016/10/7.
 */
public class BroadTempActivity extends FragmentActivity {
	private static final String TAG = "BroadTempActivity";
	private LinearLayout ll_brd_temp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_broadcast_temp);
		ll_brd_temp = (LinearLayout) findViewById(R.id.ll_brd_temp);
		PagerTabStrip pts_tab = (PagerTabStrip) findViewById(R.id.pts_tab);
		pts_tab.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		
		ViewPager vp_content = (ViewPager) findViewById(R.id.vp_content);
		ArrayList<GoodsInfo> goodsList = GoodsInfo.getDefaultList();
		BroadcastPagerAdapter mAdapter = new BroadcastPagerAdapter(getSupportFragmentManager(), goodsList);
		vp_content.setAdapter(mAdapter);
		vp_content.setCurrentItem(0);
	}

	@Override
	public void onStart() {
		super.onStart();
		bgChangeReceiver = new BgChangeReceiver();
		IntentFilter filter = new IntentFilter(BroadcastFragment.EVENT);
        LocalBroadcastManager.getInstance(this).registerReceiver(bgChangeReceiver, filter);
	}

	@Override
	public void onStop() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(bgChangeReceiver);
		super.onStop();
	}

    private BgChangeReceiver bgChangeReceiver;
    private class BgChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                int color = intent.getIntExtra("color", Color.WHITE);
                ll_brd_temp.setBackgroundColor(color);
            }
        }
    }

}
