package com.gs.learn.group;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.gs.learn.R;
/**
 * Created by ouyangshen on 2016/10/21.
 */
public class TabFirstActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_tab_first);
		
		String desc = String.format("我是%s页面，来自%s", 
				"首页", getIntent().getExtras().getString("tag"));
		TextView tv_first = (TextView) findViewById(R.id.tv_first);
		Log.d("TabFirstActivity", "getId="+tv_first.getId());
		tv_first.setText(desc);
	}

}
