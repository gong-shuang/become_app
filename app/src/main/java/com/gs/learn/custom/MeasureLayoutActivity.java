package com.gs.learn.custom;

import com.gs.learn.R;
import com.gs.learn.custom.util.MeasureUtil;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by ouyangshen on 2016/10/14.
 */
public class MeasureLayoutActivity extends AppCompatActivity {

	private final static String TAG = "MeasureLayoutActivity";
	private LinearLayout ll_header;
	private TextView tv_desc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_measure_layout);
		ll_header = (LinearLayout) findViewById(R.id.ll_header);
		tv_desc = (TextView) findViewById(R.id.tv_desc);
		float height = MeasureUtil.getRealHeight(ll_header);
		String desc = String.format("上面下拉刷新头部的高度是%f", height);
		tv_desc.setText(desc);
	}

}
