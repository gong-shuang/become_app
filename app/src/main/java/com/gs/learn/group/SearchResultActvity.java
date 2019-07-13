package com.gs.learn.group;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import com.gs.learn.R;
/**
 * Created by ouyangshen on 2016/10/21.
 */
public class SearchResultActvity extends AppCompatActivity {
	private static final String TAG = "SearchResultActvity";
	private TextView tv_search_result;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_result);
		tv_search_result = (TextView) findViewById(R.id.tv_search_result);
		doSearchQuery(getIntent()); 
		
		Toolbar tl_result = (Toolbar) findViewById(R.id.tl_result);
		tl_result.setBackgroundResource(R.color.blue_light);
		tl_result.setLogo(R.drawable.ic_app);
		tl_result.setTitle("搜索结果页");
		tl_result.setNavigationIcon(R.drawable.ic_back);
		setSupportActionBar(tl_result);
	}

	private void doSearchQuery(Intent intent) {
		if (intent == null) {
			return;
		} else {
			// 如果是通过ACTION_SEARCH来调用，即如果通过搜索调用
			if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
				// 获取额外信息
				Bundle bundle = intent.getBundleExtra(SearchManager.APP_DATA);
				String value = bundle.getString("hi");
				// 获取搜索内容
				String queryString = intent.getStringExtra(SearchManager.QUERY);
				tv_search_result.setText("您输入的搜索文字是："+queryString+", 额外信息："+value);
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_null, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == android.R.id.home) {
			finish();
		}
		return super.onOptionsItemSelected(item);
	}
}
