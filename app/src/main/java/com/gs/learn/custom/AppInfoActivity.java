package com.gs.learn.custom;

import java.util.ArrayList;

import com.gs.learn.R;
import com.gs.learn.custom.adapter.AppInfoAdapter;
import com.gs.learn.custom.bean.AppInfo;
import com.gs.learn.custom.util.AppUtil;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

/**
 * Created by ouyangshen on 2016/10/14.
 */
public class AppInfoActivity extends AppCompatActivity {

	private final static String TAG = "AppInfoActivity";
	private ListView lv_appinfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_info);
		lv_appinfo = (ListView) findViewById(R.id.lv_appinfo);

		ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(this,
				R.layout.item_select, typeArray);
		Spinner sp_list = (Spinner) findViewById(R.id.sp_type);
		sp_list.setPrompt("请选择应用类型");
		sp_list.setAdapter(typeAdapter);
		sp_list.setOnItemSelectedListener(new TypeSelectedListener());
		sp_list.setSelection(0);
	}

	private String[] typeArray={"所有应用", "联网应用"};
	class TypeSelectedListener implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			ArrayList<AppInfo> appinfoList = AppUtil.getAppInfo(AppInfoActivity.this, arg2);
			AppInfoAdapter adapter = new AppInfoAdapter(AppInfoActivity.this, appinfoList);
			lv_appinfo.setAdapter(adapter);
		}

		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}
	
}
