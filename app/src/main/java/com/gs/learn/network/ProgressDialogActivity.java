package com.gs.learn.network;

import com.gs.learn.network.util.DateUtil;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/11/11.
 */
public class ProgressDialogActivity extends AppCompatActivity {
	private final static String TAG = "ProgressDialogActivity";
	private ProgressDialog mProgressDialog;
	private TextView tv_result;
	private String mStyleDesc;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_progress_dialog);
		tv_result = (TextView) findViewById(R.id.tv_result);
		
		ArrayAdapter<String> styleAdapter = new ArrayAdapter<String>(this,
				R.layout.item_select, descArray);
		Spinner sp_style = (Spinner) findViewById(R.id.sp_style);
		sp_style.setPrompt("请选择对话框样式");
		sp_style.setAdapter(styleAdapter);
		sp_style.setOnItemSelectedListener(new StyleSelectedListener());
		sp_style.setSelection(0);
	}

	private String[] descArray={"圆圈进度", "水平进度条"};
	private int[] styleArray={ProgressDialog.STYLE_SPINNER, ProgressDialog.STYLE_HORIZONTAL};
	class StyleSelectedListener implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			if (mProgressDialog == null || mProgressDialog.isShowing() != true) {
				mStyleDesc = descArray[arg2];
				int style = styleArray[arg2];
				if (style == ProgressDialog.STYLE_SPINNER) {
					mProgressDialog = ProgressDialog.show(ProgressDialogActivity.this, 
							"请稍候", "正在努力加载页面");
					mHandler.postDelayed(mCloseDialog, 1500);
				} else {
					mProgressDialog = new ProgressDialog(ProgressDialogActivity.this);
					mProgressDialog.setTitle("请稍候");
					mProgressDialog.setMessage("正在努力加载页面");
					mProgressDialog.setMax(100);
					mProgressDialog.setProgressStyle(style);
					mProgressDialog.show();
					new RefreshThread().start();
				}
			}
		}

		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}
	
	private Runnable mCloseDialog = new Runnable() {
		@Override
		public void run() {
			if (mProgressDialog.isShowing() == true) {
				mProgressDialog.dismiss();
				tv_result.setText(DateUtil.getNowTime()+" "+mStyleDesc+"加载完成");
			}
		}
	};
	
	private class RefreshThread extends Thread {
		@Override
		public void run() {
			for (int i=0; i<10; i++) {
				Message message = Message.obtain();
				message.what = 0;
				message.arg1 = i*10;
				mHandler.sendMessage(message);
				try {
					sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			mHandler.sendEmptyMessage(1);
		}
	}
	
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				mProgressDialog.setProgress(msg.arg1);
			} else if (msg.what == 1) {
				post(mCloseDialog);
			}
		};
	};
	
}
