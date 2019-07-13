package com.gs.learn.group;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/10/21.
 */
public class ToolbarActivity extends AppCompatActivity {
	private final static String TAG = "ToolbarActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_toolbar);
		Toolbar tl_head = (Toolbar) findViewById(R.id.tl_head);
		tl_head.setNavigationIcon(R.drawable.ic_back);
		tl_head.setTitle("工具栏页面");
		tl_head.setTitleTextColor(Color.RED);
//		tl_head.setTitleTextAppearance(this, R.style.TabButton);
		tl_head.setLogo(R.drawable.ic_app);
		tl_head.setSubtitle("Toolbar");
		tl_head.setSubtitleTextColor(Color.YELLOW);
		tl_head.setBackgroundResource(R.color.blue_light);
		setSupportActionBar(tl_head);
		//setNavigationOnClickListener必须放到setSupportActionBar之后，不然不起作用
		tl_head.setNavigationOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		getMenuInflater().inflate(R.menu.menu_null, menu);
//		return true;
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		int id = item.getItemId();
//		if (id == android.R.id.home) {
//			finish();
//		}
//		return super.onOptionsItemSelected(item);
//	}

}
