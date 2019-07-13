package com.gs.learn.group;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.ViewPagerOnTabSelectedListener;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.gs.learn.group.adapter.ClassPagerAdapter;
import com.gs.learn.group.util.Utils;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/10/21.
 */
public class DepartmentClassActivity extends AppCompatActivity {
	private final static String TAG = "DepartmentClassActivity";
	private Toolbar tl_head;
	private ViewPager vp_content;
	private TabLayout tab_title;
	private ArrayList<String> mTitleArray = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_department_class);
		tl_head = (Toolbar) findViewById(R.id.tl_head);
		tab_title = (TabLayout) findViewById(R.id.tab_title);
		vp_content = (ViewPager) findViewById(R.id.vp_content);
		setSupportActionBar(tl_head);
		mTitleArray.add("服装");
		mTitleArray.add("电器");
		initTabLayout();
		initTabViewPager();
	}
	
	private void initTabLayout() {
		tab_title.addTab(tab_title.newTab().setText(mTitleArray.get(0)));
		tab_title.addTab(tab_title.newTab().setText(mTitleArray.get(1)));
		tab_title.setOnTabSelectedListener(new ViewPagerOnTabSelectedListener(vp_content));
	}

	private void initTabViewPager() {
		ClassPagerAdapter adapter = new ClassPagerAdapter(
				getSupportFragmentManager(), mTitleArray);
		vp_content.setAdapter(adapter);
		vp_content.addOnPageChangeListener(new SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				tab_title.getTabAt(position).select();
			}
		});
	}

	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		// 显示菜单项左侧的图标
		Utils.setOverflowIconVisible(featureId, menu);
		return super.onMenuOpened(featureId, menu);
	}
  
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_overflow, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == android.R.id.home) {
			finish();
		} else if (id == R.id.menu_refresh) {
			Toast.makeText(this, "当前刷新时间: "+Utils.getNowDateTime("yyyy-MM-dd HH:mm:ss")
					, Toast.LENGTH_LONG).show();
			return true;
		} else if (id == R.id.menu_about) {
			Toast.makeText(this, "这个是分类页面", Toast.LENGTH_LONG).show();
			return true;
		} else if (id == R.id.menu_quit) {
			finish();
		}
		return super.onOptionsItemSelected(item);
	}

}
