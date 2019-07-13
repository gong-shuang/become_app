package com.gs.learn.mixture;

import com.gs.learn.mixture.util.AssetsUtil;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/12/11.
 */
public class AssetsTextActivity extends AppCompatActivity {
	private String mFilePath = "file/libai.txt";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_assets_text);
		TextView tv_text_path = (TextView) findViewById(R.id.tv_text_path);
		TextView tv_assets_text = (TextView) findViewById(R.id.tv_assets_text);
		tv_text_path.setText("下面文字来源于资产文件"+mFilePath);
		tv_assets_text.setText(AssetsUtil.getTxtFromAssets(this, mFilePath));
	}
	
}
