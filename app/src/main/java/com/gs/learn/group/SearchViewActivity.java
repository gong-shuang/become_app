package com.gs.learn.group;

import com.gs.learn.group.util.Utils;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import com.gs.learn.R;
/**
 * Created by ouyangshen on 2016/10/21.
 */
public class SearchViewActivity extends AppCompatActivity  {
	private final static String TAG = "SearchViewActivity";
	private TextView tv_desc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_view);
		tv_desc = (TextView) findViewById(R.id.tv_desc);
		Toolbar tl_head = (Toolbar) findViewById(R.id.tl_head);
		tl_head.setTitle("搜索框页面");
		setSupportActionBar(tl_head);
	}

	private void initSearchView(Menu menu) {
		MenuItem menuItem = menu.findItem(R.id.menu_search);
		SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
		if (searchView == null) {
			Log.d(TAG, "Fail to get SearchView.");
		} else {
			// 新旧SearchView公用代码开始
			if (getIntent() != null) {
				searchView.setIconifiedByDefault(getIntent().getBooleanExtra("collapse", true));
			} else {
				searchView.setIconifiedByDefault(true);
			}
			searchView.setSubmitButtonEnabled(true);
			SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
			ComponentName cn = new ComponentName(this, SearchResultActvity.class);
			SearchableInfo info = searchManager.getSearchableInfo(cn);
			if (info == null) {
				Log.d(TAG, "Fail to get SearchResultActvity.");
			}
			searchView.setSearchableInfo(info);
			// 新旧SearchView公用代码结束

			sac_key = (SearchView.SearchAutoComplete) searchView.findViewById(R.id.search_src_text);
			sac_key.setTextColor(Color.WHITE);
			sac_key.setHintTextColor(Color.WHITE);
			searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
				@Override
				public boolean onQueryTextSubmit(String query) {
					return false;
				}

				@Override
				public boolean onQueryTextChange(String newText) {
					doSearch(newText);
					return true;
				}
			});

			Bundle bundle = new Bundle();
			bundle.putString("hi", "hello");
			searchView.setAppSearchData(bundle);
		}
	}

	private SearchView.SearchAutoComplete sac_key;
	private String[] hintArray = { "iphone", "iphone7s", "iphone7", "iphone7 plus", "iphone6s", "iphone6", "iphone6 plus"};

	private void doSearch(String text) {
		if (text.indexOf("i") == 0) {
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					R.layout.search_list_auto, hintArray);
			sac_key.setAdapter(adapter);
			sac_key.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					TextView tv_item = (TextView) view;
					sac_key.setText(tv_item.getText());
				}
			});
		}
	}

	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		// 显示菜单项左侧的图标
		Utils.setOverflowIconVisible(featureId, menu);
		return super.onMenuOpened(featureId, menu);
	}
  
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_search, menu);
		//对搜索框做初始化
		initSearchView(menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == android.R.id.home) {
			finish();
		} else if (id == R.id.menu_refresh) {
			tv_desc.setText("当前刷新时间: "+Utils.getNowDateTime("yyyy-MM-dd HH:mm:ss"));
			return true;
		} else if (id == R.id.menu_about) {
			Toast.makeText(this, "这个是工具栏的演示demo", Toast.LENGTH_LONG).show();
			return true;
		} else if (id == R.id.menu_quit) {
			finish();
		}
		return super.onOptionsItemSelected(item);
	}

}
