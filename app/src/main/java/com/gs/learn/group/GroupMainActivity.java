package com.gs.learn.group;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;

import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/10/21.
 */
public class GroupMainActivity extends AppCompatActivity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_group);

		findViewById(R.id.btn_tab_button).setOnClickListener(this);
		findViewById(R.id.btn_tab_host).setOnClickListener(this);
		findViewById(R.id.btn_tab_group).setOnClickListener(this);
		findViewById(R.id.btn_tab_fragment).setOnClickListener(this);
		findViewById(R.id.btn_toolbar).setOnClickListener(this);
		findViewById(R.id.btn_toolbar_custom).setOnClickListener(this);
		findViewById(R.id.btn_overflow_menu).setOnClickListener(this);
		findViewById(R.id.btn_search_view).setOnClickListener(this);
		findViewById(R.id.btn_tab_layout).setOnClickListener(this);
		findViewById(R.id.btn_tab_custom).setOnClickListener(this);
		findViewById(R.id.btn_banner_indicator).setOnClickListener(this);
		findViewById(R.id.btn_banner_pager).setOnClickListener(this);
		findViewById(R.id.btn_recycler_linear).setOnClickListener(this);
		findViewById(R.id.btn_recycler_grid).setOnClickListener(this);
		findViewById(R.id.btn_recycler_combine).setOnClickListener(this);
		findViewById(R.id.btn_recycler_staggered).setOnClickListener(this);
		findViewById(R.id.btn_recycler_dynamic).setOnClickListener(this);
		findViewById(R.id.btn_swipe_refresh).setOnClickListener(this);
		findViewById(R.id.btn_swipe_recycler).setOnClickListener(this);
		findViewById(R.id.btn_department_store).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_tab_button) {
			Intent intent = new Intent(this, TabButtonActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_tab_host) {
			Intent intent = new Intent(this, TabHostActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_tab_group) {
			Intent intent = new Intent(this, TabGroupActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_tab_fragment) {
			Intent intent = new Intent(this, TabFragmentActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_toolbar) {
			Intent intent = new Intent(this, ToolbarActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_overflow_menu) {
			Intent intent = new Intent(this, OverflowMenuActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_search_view) {
			Intent intent = new Intent(this, SearchViewActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_toolbar_custom) {
			Intent intent = new Intent(this, ToolbarCustomActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_tab_layout) {
			Intent intent = new Intent(this, TabLayoutActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_tab_custom) {
			Intent intent = new Intent(this, TabCustomActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_banner_indicator) {
			Intent intent = new Intent(this, BannerIndicatorActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_banner_pager) {
			Intent intent = new Intent(this, BannerPagerActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_recycler_linear) {
			Intent intent = new Intent(this, RecyclerLinearActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_recycler_grid) {
			Intent intent = new Intent(this, RecyclerGridActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_recycler_combine) {
			Intent intent = new Intent(this, RecyclerCombineActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_recycler_staggered) {
			Intent intent = new Intent(this, RecyclerStaggeredActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_recycler_dynamic) {
			Intent intent = new Intent(this, RecyclerDynamicActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_swipe_refresh) {
			Intent intent = new Intent(this, SwipeRefreshActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_swipe_recycler) {
			Intent intent = new Intent(this, SwipeRecyclerActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_department_store) {
			Intent intent = new Intent(this, DepartmentStoreActivity.class);
			startActivity(intent);
		}
	}

}
