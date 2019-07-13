package com.gs.learn.mixture;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/12/11.
 */
public class WebLocalActivity extends AppCompatActivity {
	private String mFilePath = "file:///android_asset/html/index.html";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_local);
		TextView tv_web_path = (TextView) findViewById(R.id.tv_web_path);
		WebView wv_assets_web = (WebView) findViewById(R.id.wv_assets_web);
		tv_web_path.setText("下面网页来源于资产文件"+mFilePath);
		wv_assets_web.loadUrl(mFilePath);
		wv_assets_web.setWebViewClient(new WebViewClient());
	}
	
}
