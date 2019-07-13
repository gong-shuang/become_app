package com.gs.learn.mixture;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/12/11.
 */
public class WebSpanActivity extends AppCompatActivity {
	private final static String TAG = "WebSpanActivity";
	private TextView tv_spannable;
	private WebView wv_spannable;
	private String mText = "为人民服务";
	private String mKey = "人民";
	private int mBeginPos, mEndPos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_span);
		tv_spannable = (TextView) findViewById(R.id.tv_spannable);
		wv_spannable = (WebView) findViewById(R.id.wv_spannable);
		tv_spannable.setText(mText);
		mBeginPos = mText.indexOf(mKey);
		mEndPos = mBeginPos + mKey.length();
		initSpannableSpinner();
		initWebViewSettings();
	}

	private void initSpannableSpinner() {
		ArrayAdapter<String> spannableAdapter = new ArrayAdapter<String>(this,
				R.layout.item_select, spannableArray);
		Spinner sp_spannable = (Spinner) findViewById(R.id.sp_spannable);
		sp_spannable.setPrompt("请选择可变字符串样式");
		sp_spannable.setAdapter(spannableAdapter);
		sp_spannable.setOnItemSelectedListener(new SpannableSelectedListener());
		sp_spannable.setSelection(0);
	}

	private String[] spannableArray={
			"增大字号", "加粗字体", "前景红色", "背景绿色", "下划线", "表情图片", "超链接"
		};
	class SpannableSelectedListener implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			wv_spannable.setVisibility(View.GONE);
			SpannableString spanText = new SpannableString(mText);
			if (arg2 == 0) {
				spanText.setSpan(new RelativeSizeSpan(1.5f), mBeginPos, mEndPos, 
						Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			} else if (arg2 == 1) {
				spanText.setSpan(new StyleSpan(Typeface.BOLD), mBeginPos, mEndPos, 
						Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			} else if (arg2 == 2) {
				spanText.setSpan(new ForegroundColorSpan(Color.RED), mBeginPos, 
						mEndPos, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			} else if (arg2 == 3) {
				spanText.setSpan(new BackgroundColorSpan(Color.GREEN), mBeginPos, 
						mEndPos, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			} else if (arg2 == 4) {
				spanText.setSpan(new UnderlineSpan(), mBeginPos, mEndPos, 
						Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			} else if (arg2 == 5) {
				spanText.setSpan(new ImageSpan(WebSpanActivity.this, R.drawable.people),
						mBeginPos, mEndPos, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			} else if (arg2 == 6) {
				showUrlSpan();
				return;
			}
			tv_spannable.setText(spanText);
		}

		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}

	@SuppressLint("SetJavaScriptEnabled")
	private void initWebViewSettings() {
		WebSettings settings = wv_spannable.getSettings();
		//设置是否自动加载图片
		settings.setLoadsImagesAutomatically(true);
		//设置默认的文本编码
		settings.setDefaultTextEncodingName("utf-8");
		//设置是否支持Javascript
		settings.setJavaScriptEnabled(true);
		//设置是否允许js自动打开新窗口（window.open()）
		settings.setJavaScriptCanOpenWindowsAutomatically(false);
		
		// 设置是否支持缩放 
		settings.setSupportZoom(true);
		// 设置是否出现缩放工具 
		settings.setBuiltInZoomControls(true);
		//当容器超过页面大小时，是否放大页面大小到容器宽度
		settings.setUseWideViewPort(true);
		//当页面超过容器大小时，是否缩小页面尺寸到页面宽度
		settings.setLoadWithOverviewMode(true);
		//设置自适应屏幕。4.2.2及之前版本自适应时可能会出现表格错乱的情况
		settings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);

		//设置是否启用本地存储
		settings.setDomStorageEnabled(true);
		//优先使用缓存
		//settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		//设置是否使用缓存
		settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
		//设置是否启用app缓存
		settings.setAppCacheEnabled(true);
		//设置app缓存文件的路径
		settings.setAppCachePath("");
		//设置是否允许访问文件，如WebView访问sd卡的文件。
		//不过assets与res文件不受此限制，仍然可以通过“file:///android_asset”和“file:///android_res”访问
		settings.setAllowFileAccess(true);
		//设置是否启用数据库
		settings.setDatabaseEnabled(true);
	}

	private void showUrlSpan() {
		SpannableString spanText = new SpannableString(mText);
		//调用setMovementMethod方法之后，点击超链接才有反应
		tv_spannable.setMovementMethod(LinkMovementMethod.getInstance());
		Spannable sp = (Spannable) Html.fromHtml("<a href=\"\">"+mKey+"</a>");
		CharSequence text = sp.toString();
		URLSpan[] urls = sp.getSpans(0, text.length(), URLSpan.class);
		for (URLSpan url : urls) {
			MyURLSpan myURLSpan = new MyURLSpan(url.getURL());
			spanText.setSpan(myURLSpan, mBeginPos, mEndPos, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		tv_spannable.setText(spanText);
	}

	private class MyURLSpan extends URLSpan {
		public MyURLSpan(String url) {
			super(url);
		}

		@Override
		public void onClick(View widget) {
			wv_spannable.setVisibility(View.VISIBLE);
			wv_spannable.loadUrl("http://blog.csdn.net/aqi00");
			wv_spannable.requestFocus();
			wv_spannable.setWebViewClient(new WebViewClient());
			return;
		}
	}

}
