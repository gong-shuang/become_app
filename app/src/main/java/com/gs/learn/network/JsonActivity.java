package com.gs.learn.network;

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/11/11.
 */
public class JsonActivity extends AppCompatActivity implements OnClickListener {
	
	private TextView tv_json;
	private String mJsonStr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_json);
		tv_json = (TextView) findViewById(R.id.tv_json);
		findViewById(R.id.btn_construct_json).setOnClickListener(this);
		findViewById(R.id.btn_parser_json).setOnClickListener(this);
		findViewById(R.id.btn_traverse_json).setOnClickListener(this);
		mJsonStr = getJsonStr();
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_construct_json) {
			tv_json.setText(mJsonStr);
		} else if (v.getId() == R.id.btn_parser_json) {
			tv_json.setText(parserJson(mJsonStr));
		} else if (v.getId() == R.id.btn_traverse_json) {
			tv_json.setText(traverseJson(mJsonStr));
		}
	}

	private String getJsonStr() {
		String str = "";
		JSONObject obj = new JSONObject();
		try {
			obj.put("name", "address");
			JSONArray array = new JSONArray();
			for (int i = 0; i < 3; i++) {
				JSONObject item = new JSONObject();
				item.put("item", "第" + (i + 1) + "个元素");
				array.put(item);
			}
			obj.put("list", array);
			obj.put("count", array.length());
			obj.put("desc", "这是测试串");
			str = obj.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return str;
	}

	private String parserJson(String jsonStr) {
		String result = "";
		try {
			JSONObject obj = new JSONObject(jsonStr);
			String name = obj.getString("name");
			String desc = obj.getString("desc");
			int count = obj.getInt("count");
			result = String.format("%sname=%s\n", result, name);
			result = String.format("%sdesc=%s\n", result, desc);
			result = String.format("%scount=%d\n", result, count);
			JSONArray listArray = obj.getJSONArray("list");
			for (int i=0; i<listArray.length(); i++) {
                JSONObject list_item = listArray.getJSONObject(i);
                String item = list_item.getString("item");
				result = String.format("%s\titem=%s\n", result, item);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}

	private String traverseJson(String jsonStr) {
		String result = "";
		try {
			JSONObject obj = new JSONObject(jsonStr);
			Iterator<String> it = obj.keys();
			String key;
			String value = "";
			while (it.hasNext()) {// 遍历JSONObject
				key = (String) it.next().toString();
				value = obj.getString(key);
				result = String.format("%skey=%s, value=%s\n", result, key, value);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}

}
