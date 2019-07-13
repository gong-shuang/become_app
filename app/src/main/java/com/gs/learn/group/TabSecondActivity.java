package com.gs.learn.group;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/10/21.
 */
public class TabSecondActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_tab_second);

		String desc = String.format("我是%s页面，来自%s", 
				"分类", getIntent().getExtras().getString("tag"));
		TextView tv_second = (TextView) findViewById(R.id.tv_second);
		tv_second.setText(desc);
	}

}
