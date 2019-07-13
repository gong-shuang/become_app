package com.gs.learn.group;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

import com.gs.learn.R;
import com.gs.learn.group.adapter.CombineAdapter;
import com.gs.learn.group.adapter.GridAdapter;
import com.gs.learn.group.bean.GoodsInfo;
import com.gs.learn.group.util.DisplayUtil;
import com.gs.learn.group.util.Utils;
import com.gs.learn.group.widget.BannerPager;
import com.gs.learn.group.widget.SpacesItemDecoration;
import com.gs.learn.group.widget.BannerPager.BannerClickListener;

/**
 * Created by ouyangshen on 2016/10/21.
 */
public class DepartmentHomeActivity extends AppCompatActivity implements BannerClickListener  {
	private final static String TAG = "DepartmentHomeActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_department_home);
		Toolbar tl_head = (Toolbar) findViewById(R.id.tl_head);
		tl_head.setTitle("商城首页");
		setSupportActionBar(tl_head);
		initBanner();
		initGrid();
		initCombine();
	}
	
	private void initBanner() {
		BannerPager banner = (BannerPager) findViewById(R.id.banner_pager);
		LayoutParams params = (LayoutParams) banner.getLayoutParams();
		params.height = (int) (DisplayUtil.getSreenWidth(this) * 250f/ 640f);
		banner.setLayoutParams(params);
		
		ArrayList<Integer> bannerArray = new ArrayList<Integer>();
		bannerArray.add(Integer.valueOf(R.drawable.banner_1));
		bannerArray.add(Integer.valueOf(R.drawable.banner_2));
		bannerArray.add(Integer.valueOf(R.drawable.banner_3));
		bannerArray.add(Integer.valueOf(R.drawable.banner_4));
		bannerArray.add(Integer.valueOf(R.drawable.banner_5));
		banner.setImage(bannerArray);
		banner.setOnBannerListener(this);
		banner.start();
	}

	@Override
	public void onBannerClick(int position) {
		String desc = String.format("您点击了第%d张图片", position+1);
		Toast.makeText(this, desc, Toast.LENGTH_LONG).show();
	}

	private void initGrid() {
		RecyclerView rv_grid = (RecyclerView) findViewById(R.id.rv_grid);
		
		GridLayoutManager manager = new GridLayoutManager(this, 5);
		rv_grid.setLayoutManager(manager);
		
		GridAdapter adapter = new GridAdapter(this, GoodsInfo.getDefaultGrid());
		adapter.setOnItemClickListener(adapter);
		adapter.setOnItemLongClickListener(adapter);
		rv_grid.setAdapter(adapter);
		rv_grid.setItemAnimator(new DefaultItemAnimator());
		rv_grid.addItemDecoration(new SpacesItemDecoration(1));
	}

	private void initCombine() {
		RecyclerView rv_combine = (RecyclerView) findViewById(R.id.rv_combine);
		
		GridLayoutManager manager = new GridLayoutManager(this, 4);
		manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
            	if (position == 0 || position==1) {
                    return 2;
            	} else {
                    return 1;
            	}
            }
        });
		rv_combine.setLayoutManager(manager);
		
		CombineAdapter adapter = new CombineAdapter(this, GoodsInfo.getDefaultCombine());
		adapter.setOnItemClickListener(adapter);
		adapter.setOnItemLongClickListener(adapter);
		rv_combine.setAdapter(adapter);
		rv_combine.setItemAnimator(new DefaultItemAnimator());
		rv_combine.addItemDecoration(new SpacesItemDecoration(1));
	}
	
	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		// 显示菜单项左侧的图标
		Utils.setOverflowIconVisible(featureId, menu);
		return super.onMenuOpened(featureId, menu);
	}
  
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_home, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == android.R.id.home) {
			finish();
		} else if (id == R.id.menu_search) {
			Intent intent = new Intent(this, SearchViewActivity.class);
			intent.putExtra("collapse", false);
			startActivity(intent);
		} else if (id == R.id.menu_refresh) {
			Toast.makeText(this, "当前刷新时间: "+
					Utils.getNowDateTime("yyyy-MM-dd HH:mm:ss"), Toast.LENGTH_LONG).show();
			return true;
		} else if (id == R.id.menu_about) {
			Toast.makeText(this, "这个是商城首页", Toast.LENGTH_LONG).show();
			return true;
		} else if (id == R.id.menu_quit) {
			finish();
		}
		return super.onOptionsItemSelected(item);
	}
}