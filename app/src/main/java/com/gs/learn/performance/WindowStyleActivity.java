package com.gs.learn.performance;

import com.gs.learn.performance.widget.CustomDialog;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/12/27.
 */
public class WindowStyleActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wnidow_style);
		initSpinner();
	}
	
	private void initSpinner() {
		ArrayAdapter<String> backgroundAdapter = new ArrayAdapter<String>(this,
				R.layout.item_select_performance, backgroundArray);
		Spinner sp_background = (Spinner) findViewById(R.id.sp_background);
		sp_background.setPrompt("请选择窗口背景风格样式");
		sp_background.setAdapter(backgroundAdapter);
		sp_background.setOnItemSelectedListener(new BackgroundSelectedListener());
		sp_background.setSelection(0);
	}

	private String[] backgroundArray={
			"不显示对话框", "android:windowBackground风格",
			"android:background风格", "android:windowFrame风格"
	};
	private int[] styleArray = {0, R.style.CustomWindowBackground,
			R.style.CustomBackground, R.style.CustomFrame};
	private int mStyle;
	class BackgroundSelectedListener implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			if (arg2 > 0) {
				mStyle = styleArray[arg2];
				mHandler.postDelayed(mDialog, 500);
			}
		}

		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}
	
	private Handler mHandler = new Handler();
	private Runnable mDialog = new Runnable() {
		@Override
		public void run() {
			CustomDialog dialog = new CustomDialog(WindowStyleActivity.this, mStyle);
			dialog.show();
		}
	};
	
}
