package com.gs.learn.senior;

import java.util.ArrayList;

import com.gs.learn.senior.adapter.PlanetAdapter;
import com.gs.learn.senior.bean.Planet;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import com.gs.learn.R;
/**
 * Created by ouyangshen on 2016/10/7.
 */
public class ListViewActivity extends AppCompatActivity {

	private final static String TAG = "ListViewActivity";
	private ListView lv_planet;
	private ArrayList<Planet> planetList;
	private Drawable drawable;
	private int dividerHeight = 5;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_view);
		planetList = Planet.getDefaultList();
		PlanetAdapter adapter = new PlanetAdapter(this, R.layout.item_list, planetList, Color.WHITE);
		lv_planet = (ListView) findViewById(R.id.lv_planet);
		lv_planet.setAdapter(adapter);
		lv_planet.setOnItemClickListener(adapter);
		lv_planet.setOnItemLongClickListener(adapter);
		drawable = getResources().getDrawable(R.drawable.divider_red2);

		ArrayAdapter<String> dividerAdapter = new ArrayAdapter<String>(this,
				R.layout.item_select, dividerArray);
		Spinner sp_list = (Spinner) findViewById(R.id.sp_list);
		sp_list.setPrompt("请选择分隔线显示方式");
		sp_list.setAdapter(dividerAdapter);
		sp_list.setOnItemSelectedListener(new DividerSelectedListener());
		sp_list.setSelection(0);
	}

	private String[] dividerArray={
			"不显示分隔线(分隔线高度为0)",
			"不显示分隔线(分隔线为null)",
			"只显示内部分隔线(先设置分隔线高度)",
			"只显示内部分隔线(后设置分隔线高度)",
			"显示底部分隔线(高度是wrap_content)",
			"显示底部分隔线(高度是match_parent)",
			"显示顶部分隔线(别瞎折腾了，显示不了)",
			"显示全部分隔线(看我用padding大法)"
	};

	class DividerSelectedListener implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			lv_planet.setDivider(drawable);
			lv_planet.setDividerHeight(dividerHeight);
			lv_planet.setPadding(0, 0, 0, 0);
			lv_planet.setBackgroundColor(Color.TRANSPARENT);
			if (arg2 == 0) {
				lv_planet.setDividerHeight(0);
			} else if (arg2 == 1) {
				lv_planet.setDivider(null);
				lv_planet.setDividerHeight(dividerHeight);
			} else if (arg2 == 2) {
				lv_planet.setDividerHeight(dividerHeight);
				lv_planet.setDivider(drawable);
			} else if (arg2 == 3) {
				lv_planet.setDivider(drawable);
				lv_planet.setDividerHeight(dividerHeight);
			} else if (arg2 == 4) {
				lv_planet.setFooterDividersEnabled(true);
			} else if (arg2 == 5) {
				params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0, 1);
				lv_planet.setFooterDividersEnabled(true);
			} else if (arg2 == 6) {
				params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0, 1);
				lv_planet.setFooterDividersEnabled(true);
				lv_planet.setHeaderDividersEnabled(true);
			} else if (arg2 == 7) {
				lv_planet.setPadding(0, dividerHeight, 0, dividerHeight);
				lv_planet.setBackgroundDrawable(drawable);
			}
			lv_planet.setLayoutParams(params);
		}

		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}
	
}
