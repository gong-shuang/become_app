package com.gs.learn.performance;

import com.gs.learn.performance.adapter.PlanetAdapter;
import com.gs.learn.performance.bean.Planet;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewStub;
import android.widget.GridView;
import android.widget.ListView;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/12/27.
 */
public class ScreenSuitableActivity extends BaseActivity {
	private static final String TAG = "ScreenSuitableActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_screen_suitable);
		setTitle("自适应布局演示页面");
		Configuration config = getResources().getConfiguration();
		if(config.orientation == Configuration.ORIENTATION_PORTRAIT){
			showList();
		} else {
			showGrid();
		}
	}
	
	private void showList() {
		ViewStub vs_list = (ViewStub) findViewById(R.id.vs_list);
		vs_list.inflate();
		ListView lv_hello = (ListView) findViewById(R.id.lv_hello);
		PlanetAdapter adapter = new PlanetAdapter(this, R.layout.item_list, 
				Planet.getDefaultList(), Color.WHITE);
		lv_hello.setAdapter(adapter);
	}

	private void showGrid() {
		ViewStub vs_grid = (ViewStub) findViewById(R.id.vs_grid);
		vs_grid.inflate();
		GridView gv_hello = (GridView) findViewById(R.id.gv_hello);
		PlanetAdapter adapter = new PlanetAdapter(this, R.layout.item_grid, 
				Planet.getDefaultList(), Color.WHITE);
		gv_hello.setAdapter(adapter);
	}

}
