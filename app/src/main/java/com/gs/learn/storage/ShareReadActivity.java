package com.gs.learn.storage;

import java.util.Map;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.gs.learn.R;
/**
 * Created by ouyangshen on 2016/10/1.
 */
public class ShareReadActivity extends AppCompatActivity {

	private SharedPreferences mShared;
	private TextView tv_share;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_share_read);
		tv_share = (TextView) findViewById(R.id.tv_share);
		readSharedPreferences();
	}
	
	private void readSharedPreferences() {
		mShared = getSharedPreferences("share", MODE_PRIVATE);
		String desc = "共享参数中保存的信息如下：";
		Map<String, Object> mapParam = (Map<String, Object>) mShared.getAll();
		for (Map.Entry<String, Object> item_map : mapParam.entrySet()) {
			String key = item_map.getKey();
			Object value = item_map.getValue();
			if (value instanceof String) {
				desc = String.format("%s\n　%s的取值为%s", desc, key, 
						mShared.getString(key, ""));
			} else if (value instanceof Integer) {
				desc = String.format("%s\n　%s的取值为%d", desc, key, 
						mShared.getInt(key, 0));
			} else if (value instanceof Float) {
				desc = String.format("%s\n　%s的取值为%f", desc, key, 
						mShared.getFloat(key, 0.0f));
			} else if (value instanceof Boolean) {
				desc = String.format("%s\n　%s的取值为%b", desc, key, 
						mShared.getBoolean(key, false));
			} else if (value instanceof Long) {
				desc = String.format("%s\n　%s的取值为%d", desc, key, 
						mShared.getLong(key, 0l));
			} else {
				desc = String.format("%s\n参数%s的取值为未知类型", desc, key);
			}
		}
		if (mapParam==null || mapParam.size()<=0) {
			desc = "共享参数中保存的信息为空";
		}
		tv_share.setText(desc);
	}
	
}
