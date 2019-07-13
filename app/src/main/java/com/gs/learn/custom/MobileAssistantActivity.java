package com.gs.learn.custom;

import java.util.ArrayList;
import java.util.Calendar;

import com.gs.learn.MainApplication;
import com.gs.learn.custom.adapter.TrafficInfoAdapter;
import com.gs.learn.custom.bean.AppInfo;
import com.gs.learn.custom.service.TrafficService;
import com.gs.learn.custom.util.AppUtil;
import com.gs.learn.custom.util.DateUtil;
import com.gs.learn.custom.util.SharedUtil;
import com.gs.learn.custom.util.StringUtil;
import com.gs.learn.custom.widget.CircleAnimation;
import com.gs.learn.custom.widget.CustomDateDialog;
import com.gs.learn.custom.widget.CustomDateDialog.OnDateSetListener;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Style;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.gs.learn.R;
/**
 * Created by ouyangshen on 2016/10/14.
 */
public class MobileAssistantActivity extends Activity  implements
		OnClickListener, OnDateSetListener {

	private final static String TAG = "MobileAssistantActivity";
	private TextView tv_day;
	private RelativeLayout rl_month;
	private TextView tv_month_traffic;
	private RelativeLayout rl_day;
	private TextView tv_day_traffic;
	private ListView lv_traffic;
	private int mDay;
	private int mNowDay;
	private long traffic_month = 0;
	private long traffic_day = 0;
	private int limit_month;
	private int limit_day;
	private int line_width = 10;
	private Intent mIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mobile_assistant);
		mIntent = new Intent(this, TrafficService.class);
		startService(mIntent);
		initView();
	}
	
//	@Override
//	protected void onDestroy() {
//		super.onDestroy();
//		if (mIntent != null) {
//			stopService(mIntent);
//		}
//	}

	private void initView() {
		tv_day = (TextView) findViewById(R.id.tv_day);
		rl_month = (RelativeLayout) findViewById(R.id.rl_month);
		tv_month_traffic = (TextView) findViewById(R.id.tv_month_traffic);
		rl_day = (RelativeLayout) findViewById(R.id.rl_day);
		tv_day_traffic = (TextView) findViewById(R.id.tv_day_traffic);
		lv_traffic = (ListView) findViewById(R.id.lv_traffic);
		findViewById(R.id.iv_menu).setOnClickListener(this);
		findViewById(R.id.iv_refresh).setOnClickListener(this);
		limit_month = Integer.parseInt(SharedUtil.getIntance(this).readShared("limit_month", "1024"));
		limit_day = Integer.parseInt(SharedUtil.getIntance(this).readShared("limit_day", "30"));
		mNowDay = Integer.parseInt(DateUtil.getNowDateTime("yyyyMMdd"));
		mDay = mNowDay;
		String day = DateUtil.getNowDateTime("yyyy年MM月dd日");
		tv_day.setText(day);
		tv_day.setOnClickListener(this);
		mHandler.postDelayed(mDayRefresh, 500);
	}

	private Handler mHandler = new Handler();
	private Runnable mDayRefresh = new Runnable() {
		@Override
		public void run() {
			refreshTraffic(mDay);
		}
	};

	@Override
	public void onClick(View v) {
		int resid = v.getId();
		if (resid == R.id.tv_day) {
			Calendar calendar = Calendar.getInstance();
			CustomDateDialog dialog = new CustomDateDialog(this);
			dialog.setDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
					calendar.get(Calendar.DAY_OF_MONTH), this);
			dialog.show();
		} else if (resid == R.id.iv_menu) {
			Intent intent = new Intent(this, MobileConfigActivity.class);
			startActivity(intent);
		} else if (resid == R.id.iv_refresh) {
			mDay = mNowDay;
			mHandler.post(mDayRefresh);
		}
	}

	@Override
	public void onDateSet(int year, int month, int day) {
		String date = String.format("%d年%d月%d日", year, month, day);
		tv_day.setText(date);
		mDay = year*10000 + month*100 + day;
		mHandler.post(mDayRefresh);
	}

	private void refreshTraffic(int day) {
		String last_date = DateUtil.getAddDate(""+day, -1);
		ArrayList<AppInfo> lastArray = MainApplication.getInstance().mTrafficHelper.query("day="+last_date);
		ArrayList<AppInfo> thisArray = MainApplication.getInstance().mTrafficHelper.query("day="+day);
		ArrayList<AppInfo> newArray = new ArrayList<AppInfo>();
		traffic_day = 0;
		for (int i=0; i<thisArray.size(); i++) {
			AppInfo item = thisArray.get(i);
			for (int j=0; j<lastArray.size(); j++) {
				if (item.uid == lastArray.get(j).uid) {
					item.traffic -= lastArray.get(j).traffic;
					break;
				}
			}
			traffic_day += item.traffic;
			newArray.add(item);
		}
		ArrayList<AppInfo> fullArray = AppUtil.fillAppInfo(this, newArray);
		TrafficInfoAdapter adapter = new TrafficInfoAdapter(MobileAssistantActivity.this, fullArray);
		lv_traffic.setAdapter(adapter);

		//日流量
		String desc = "今日已用流量" + StringUtil.formatTraffic(traffic_day);
		rl_day.removeAllViews();
		int diameter = Math.min(rl_day.getWidth(), rl_day.getHeight())-line_width*2;
		CircleAnimation dayAnimation = new CircleAnimation(MobileAssistantActivity.this);
		dayAnimation.setRect((rl_day.getWidth()-diameter)/2+line_width, 
				(rl_day.getHeight()-diameter)/2+line_width, 
				(rl_day.getWidth()+diameter)/2-line_width, 
				(rl_day.getHeight()+diameter)/2-line_width);
		float trafficM = traffic_day/1024.0f/1024.0f;
		if (trafficM > limit_day*2) {
			int end_angle = (int) ((trafficM>limit_day*3)?360:(trafficM-limit_day*2)*360/limit_day);
			dayAnimation.setAngle(0, end_angle);
			dayAnimation.setFront(Color.RED, line_width, Style.STROKE);
			desc = String.format("%s\n超出限额%s", desc, 
					StringUtil.formatTraffic(traffic_day-limit_day*1024*1024));
		} else if (trafficM > limit_day) {
			int end_angle = (int) ((trafficM>limit_day*2)?360:(trafficM-limit_day)*360/limit_day);
			dayAnimation.setAngle(0, end_angle);
			dayAnimation.setFront(0xffff9900, line_width, Style.STROKE);
			desc = String.format("%s\n超出限额%s", desc, 
					StringUtil.formatTraffic(traffic_day-limit_day*1024*1024));
		} else {
			int end_angle = (int) (trafficM*360/limit_day);
			dayAnimation.setAngle(0, end_angle);
			dayAnimation.setFront(Color.GREEN, line_width, Style.STROKE);
			desc = String.format("%s\n剩余流量%s", desc, 
					StringUtil.formatTraffic(limit_day*1024*1024-traffic_day));
		}
		rl_day.addView(dayAnimation);
		dayAnimation.render();
		tv_day_traffic.setText(desc);

		//月流量。未实现，读者可实践之
		rl_month.removeAllViews();
		tv_month_traffic.setText("本月已用流量待统计");
		CircleAnimation monthAnimation = new CircleAnimation(MobileAssistantActivity.this);
		monthAnimation.setRect((rl_month.getWidth()-diameter)/2+line_width, 
				(rl_month.getHeight()-diameter)/2+line_width, 
				(rl_month.getWidth()+diameter)/2-line_width, 
				(rl_month.getHeight()+diameter)/2-line_width);
		monthAnimation.setAngle(0, 0);
		monthAnimation.setFront(Color.GREEN, line_width, Style.STROKE);
		rl_month.addView(monthAnimation);
		monthAnimation.render();
	}

}
