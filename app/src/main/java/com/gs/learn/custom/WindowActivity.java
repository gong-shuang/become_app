package com.gs.learn.custom;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;
import com.gs.learn.R;
/**
 * Created by ouyangshen on 2016/10/14.
 */
public class WindowActivity extends Activity {
	
	private TextView tv_info;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		getWindow().setContentView(R.layout.activity_window);
//		getWindow().setLayout(400, 400);
//		getWindow().setBackgroundDrawableResource(R.drawable.icon_header);
		tv_info = (TextView) getWindow().findViewById(R.id.tv_info);
		tv_info.setText("我在直接操作窗口啦");
	}

}
