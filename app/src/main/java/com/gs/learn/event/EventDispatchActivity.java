package com.gs.learn.event;

import com.gs.learn.event.util.DateUtil;
import com.gs.learn.event.widget.NotDispatchLayout;
import com.gs.learn.event.widget.NotDispatchLayout.NotDispatchListener;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.gs.learn.R;

public class EventDispatchActivity extends AppCompatActivity implements 
		OnClickListener, NotDispatchListener {
	private TextView tv_dispatch_yes;
	private TextView tv_dispatch_no;
	private NotDispatchLayout ndl_no;
	private String desc_yes = "";
	private String desc_no = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_dispatch);
		tv_dispatch_yes = (TextView) findViewById(R.id.tv_dispatch_yes);
		tv_dispatch_no = (TextView) findViewById(R.id.tv_dispatch_no);
		ndl_no = (NotDispatchLayout) findViewById(R.id.ndl_no);
		findViewById(R.id.btn_dispatch_yes).setOnClickListener(this);
		findViewById(R.id.btn_dispatch_no).setOnClickListener(this);
		ndl_no.setNotDispatchListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_dispatch_yes) {
			desc_yes = String.format("%s%s 您点击了按钮\n", desc_yes, DateUtil.getNowTime());
			tv_dispatch_yes.setText(desc_yes);
		} else if (v.getId() == R.id.btn_dispatch_no) {
			desc_no = String.format("%s%s 您点击了按钮\n", desc_no, DateUtil.getNowTime());
			tv_dispatch_no.setText(desc_no);
		}
	}

	@Override
	public void onNotDispatch() {
		desc_no = String.format("%s%s 触摸动作未分发，按钮点击不了了\n"
				, desc_no, DateUtil.getNowTime());
		tv_dispatch_no.setText(desc_no);
	}

}
