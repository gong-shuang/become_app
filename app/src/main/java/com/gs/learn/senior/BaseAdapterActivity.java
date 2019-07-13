package com.gs.learn.senior;

import java.util.ArrayList;

import com.gs.learn.R;
import com.gs.learn.senior.adapter.PlanetAdapter;
import com.gs.learn.senior.bean.Planet;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

/**
 * Created by ouyangshen on 2016/10/7.
 */
public class BaseAdapterActivity extends AppCompatActivity {
	
	private ArrayList<Planet> planetList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_base_adapter);
		initSpinner();
	}
	
	private void initSpinner() {
		planetList = Planet.getDefaultList();
		PlanetAdapter adapter = new PlanetAdapter(this, R.layout.item_list, planetList, Color.WHITE);
		Spinner sp = (Spinner) findViewById(R.id.sp_planet);
		sp.setPrompt("请选择行星");
		sp.setAdapter(adapter);
		sp.setSelection(0);
		sp.setOnItemSelectedListener(new MySelectedListener());
	}

	private class MySelectedListener implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			Toast.makeText(BaseAdapterActivity.this, "您选择的是"+planetList.get(arg2).name, Toast.LENGTH_LONG).show();
		}

		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}
	
}
