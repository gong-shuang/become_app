package com.gs.learn.performance;

import java.util.ArrayList;
import java.util.Map;

import com.gs.learn.performance.util.Utils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.LruCache;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/12/27.
 */
public class LruCacheActivity extends AppCompatActivity implements OnClickListener {
	private TextView tv_lru_cache;
	private LruCache<String, String> mLanguageLru;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lru_cache);
		tv_lru_cache = (TextView) findViewById(R.id.tv_lru_cache);
		findViewById(R.id.btn_android).setOnClickListener(this);
		findViewById(R.id.btn_ios).setOnClickListener(this);
		findViewById(R.id.btn_java).setOnClickListener(this);
		findViewById(R.id.btn_cpp).setOnClickListener(this);
		findViewById(R.id.btn_python).setOnClickListener(this);
		findViewById(R.id.btn_net).setOnClickListener(this);
		findViewById(R.id.btn_php).setOnClickListener(this);
		findViewById(R.id.btn_perl).setOnClickListener(this);
		mLanguageLru = new LruCache<String, String>(5);
	}

	@Override
	public void onClick(View v) {
		String language = ((Button) v).getText().toString();
		mLanguageLru.put(language, Utils.getNowTime());
		printCache();
	}
	
	private void printCache() {
		String desc = "";
		Map<String, String> cache = mLanguageLru.snapshot();
		for (Map.Entry<String, String> item : cache.entrySet()) {
			desc = String.format("%s%s 最后一次更新时间为%s\n", 
					desc, item.getKey(), item.getValue());
		}
		tv_lru_cache.setText(desc);
	}
	
}
