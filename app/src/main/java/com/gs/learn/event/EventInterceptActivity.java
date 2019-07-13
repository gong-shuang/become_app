package com.gs.learn.event;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.gs.learn.event.util.DateUtil;
import com.gs.learn.event.widget.InterceptLayout;
import com.gs.learn.event.widget.InterceptLayout.InterceptListener;
import com.gs.learn.R;

public class EventInterceptActivity extends AppCompatActivity implements
		OnClickListener, InterceptListener {
	private TextView tv_intercept_no;
	private TextView tv_intercept_yes;
	private InterceptLayout il_yes;
	private String desc_no = "";
	private String desc_yes = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_intercept);
		tv_intercept_no = (TextView) findViewById(R.id.tv_intercept_no);
		tv_intercept_yes = (TextView) findViewById(R.id.tv_intercept_yes);
		il_yes = (InterceptLayout) findViewById(R.id.il_yes);
		findViewById(R.id.btn_intercept_no).setOnClickListener(this);
		findViewById(R.id.btn_intercept_yes).setOnClickListener(this);
		il_yes.setInterceptListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_intercept_no) {
			desc_no = String.format("%s%s 您点击了按钮\n", desc_no, DateUtil.getNowTime());
			tv_intercept_no.setText(desc_no);
		} else if (v.getId() == R.id.btn_intercept_yes) {
			desc_yes = String.format("%s%s 您点击了按钮\n", desc_yes, DateUtil.getNowTime());
			tv_intercept_yes.setText(desc_yes);
		}
	}

	@Override
	public void onIntercept() {
		desc_yes = String.format("%s%s 触摸动作被拦截，按钮点击不了了\n", desc_yes,
				DateUtil.getNowTime());
		tv_intercept_yes.setText(desc_yes);
	}

}
