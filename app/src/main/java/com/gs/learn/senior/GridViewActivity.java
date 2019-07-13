package com.gs.learn.senior;

import java.util.ArrayList;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

import com.gs.learn.senior.adapter.PlanetAdapter;
import com.gs.learn.senior.bean.Planet;
import com.gs.learn.R;
/**
 * Created by ouyangshen on 2016/10/7.
 */
public class GridViewActivity extends AppCompatActivity {

	private final static String TAG = "GridViewActivity";
	private GridView gv_planet;
	private ArrayList<Planet> planetList;
	private int dividerPad = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_grid_view);
		planetList = Planet.getDefaultList();
		PlanetAdapter adapter = new PlanetAdapter(this, R.layout.item_grid, planetList, Color.WHITE);
		gv_planet = (GridView) findViewById(R.id.gv_planet);
		gv_planet.setAdapter(adapter);
		gv_planet.setOnItemClickListener(adapter);
		gv_planet.setOnItemLongClickListener(adapter);

		ArrayAdapter<String> dividerAdapter = new ArrayAdapter<String>(this,
				R.layout.item_select, dividerArray);
		Spinner sp_grid = (Spinner) findViewById(R.id.sp_grid);
		sp_grid.setPrompt("请选择分隔线显示方式");
		sp_grid.setAdapter(dividerAdapter);
		sp_grid.setOnItemSelectedListener(new DividerSelectedListener());
		sp_grid.setSelection(0);
	}

	private String[] dividerArray={
			"不显示分隔线",
			"只显示内部分隔线(NO_STRETCH)",
			"只显示内部分隔线(COLUMN_WIDTH)",
			"只显示内部分隔线(STRETCH_SPACING)",
			"只显示内部分隔线(SPACING_UNIFORM)",
			"显示全部分隔线(看我用padding大法)"
	};
	class DividerSelectedListener implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			gv_planet.setBackgroundColor(Color.RED);
			gv_planet.setHorizontalSpacing(dividerPad);
			gv_planet.setVerticalSpacing(dividerPad);
			gv_planet.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
			gv_planet.setColumnWidth(250);
			gv_planet.setPadding(0, 0, 0, 0);
			if (arg2 == 0) {
				gv_planet.setBackgroundColor(Color.WHITE);
				gv_planet.setHorizontalSpacing(0);
				gv_planet.setVerticalSpacing(0);
			} else if (arg2 == 1) {
				gv_planet.setStretchMode(GridView.NO_STRETCH);
			} else if (arg2 == 2) {
				gv_planet.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
			} else if (arg2 == 3) {
				gv_planet.setStretchMode(GridView.STRETCH_SPACING);
			} else if (arg2 == 4) {
				gv_planet.setStretchMode(GridView.STRETCH_SPACING_UNIFORM);
			} else if (arg2 == 5) {
				gv_planet.setPadding(dividerPad, dividerPad, dividerPad, dividerPad);
			}
		}

		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}
	
}
