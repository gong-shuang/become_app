package com.gs.learn.mixture;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.webkit.DownloadListener;
import android.webkit.GeolocationPermissions.Callback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/12/11.
 */
public class WebBrowserActivity extends AppCompatActivity implements OnClickListener {
	private final static String TAG = "WebBrowserActivity";
	private EditText et_web_url;
	private WebView wv_web;
	private ProgressDialog m_pd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_browser);
		et_web_url = (EditText) findViewById(R.id.et_web_url);
		et_web_url.setText("news.qq.com/");
		wv_web = (WebView) findViewById(R.id.wv_web);
		findViewById(R.id.btn_web_go).setOnClickListener(this);
		findViewById(R.id.ib_back).setOnClickListener(this);
		findViewById(R.id.ib_forward).setOnClickListener(this);
		findViewById(R.id.ib_refresh).setOnClickListener(this);
		findViewById(R.id.ib_close).setOnClickListener(this);
		initWebViewSettings();
	}
	
	@SuppressLint("SetJavaScriptEnabled")
	private void initWebViewSettings() {
		WebSettings settings = wv_web.getSettings();
		settings.setLoadsImagesAutomatically(true);
		settings.setDefaultTextEncodingName("utf-8");
		settings.setJavaScriptEnabled(true);
		settings.setJavaScriptCanOpenWindowsAutomatically(false);
		settings.setSupportZoom(true);
		settings.setBuiltInZoomControls(true);
		settings.setUseWideViewPort(true);
		settings.setLoadWithOverviewMode(true);
		settings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_web_go) {
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(et_web_url.getWindowToken(), 0);
			String url = "http://" + et_web_url.getText().toString();
			Log.d(TAG, "url="+url);
			wv_web.loadUrl(url);
			wv_web.setWebViewClient(mWebViewClient);
			wv_web.setWebChromeClient(mWebChrome);
			wv_web.setDownloadListener(mDownloadListener);
		} else if (v.getId() == R.id.ib_back) {
			if (wv_web.canGoBack()) {
				wv_web.goBack();
			} else {
				Toast.makeText(this, "已经是最后一页了", Toast.LENGTH_SHORT).show();
			}
		} else if (v.getId() == R.id.ib_forward) {
			if (wv_web.canGoForward()) {
				wv_web.goForward();
			} else {
				Toast.makeText(this, "已经是最前一页了", Toast.LENGTH_SHORT).show();
			}
		} else if (v.getId() == R.id.ib_refresh) {
			//重新加载
			wv_web.reload();
			//停止加载
			//wv_web.stopLoading();
		} else if (v.getId() == R.id.ib_close) {
			finish();
		}
	}

	@Override
	public void onBackPressed() {
		if (wv_web.canGoBack()) {
			wv_web.goBack();
			return;
		} else {
			finish();
		}
	}

	private WebViewClient mWebViewClient = new WebViewClient() {
		@Override
		public void onReceivedSslError(WebView view,
				android.webkit.SslErrorHandler handler,
				android.net.http.SslError error) {
			handler.proceed();
		};

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
			Log.d(TAG, "onPageStarted:" + url);
			if (m_pd == null || m_pd.isShowing() == false) {
				m_pd = new ProgressDialog(WebBrowserActivity.this);
				m_pd.setTitle("稍等");
				m_pd.setMessage("页面加载中……");
				m_pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
				m_pd.show();
			}
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			Log.d(TAG, "onPageFinished:" + url);
			if (m_pd != null && m_pd.isShowing() == true) {
				m_pd.dismiss();
			}
		}

		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			super.onReceivedError(view, errorCode, description, failingUrl);
			Log.d(TAG, "onReceivedError: url=" + failingUrl+", errorCode="+errorCode+", description="+description);
			if (m_pd != null && m_pd.isShowing() == true) {
				m_pd.dismiss();
			}
			Toast.makeText(WebBrowserActivity.this, "页面加载失败，请稍候再试",Toast.LENGTH_LONG).show();
		}

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
	};

	private WebChromeClient mWebChrome = new WebChromeClient() {
		@Override
		public void onProgressChanged(WebView view, int progress) {
			if (m_pd != null && m_pd.isShowing() == true) {
				m_pd.setProgress(progress);
			}
		}

		@Override
		public void onGeolocationPermissionsShowPrompt(String origin, Callback callback) {
			callback.invoke(origin, true, false);
			super.onGeolocationPermissionsShowPrompt(origin, callback);
		}
	};

	private DownloadListener mDownloadListener = new DownloadListener() {
		@Override
		public void onDownloadStart(String url, String userAgent, String contentDisposition,
				String mimetype, long contentLength) {
			//此处操作文件下载
		}
	};

}
