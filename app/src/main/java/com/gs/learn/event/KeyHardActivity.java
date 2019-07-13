package com.gs.learn.event;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.TextView;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/11/23.
 */
public class KeyHardActivity extends AppCompatActivity {
	private TextView tv_result;
	private String desc = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_key_hard);
		tv_result = (TextView) findViewById(R.id.tv_result);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		desc = String.format("%s物理按键的编码是%d", desc, keyCode);
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			desc = String.format("%s, 按键为返回键", desc);
			mHandler.postDelayed(mFinish, 3000);
		} else if (keyCode == KeyEvent.KEYCODE_MENU) {
			desc = String.format("%s, 按键为菜单键", desc);
		} else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
			desc = String.format("%s, 按键为加大音量键", desc);
		} else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
			desc = String.format("%s, 按键为减小音量键", desc);
		}
		desc = desc + "\n";
		tv_result.setText(desc);
		//返回true表示不再响应系统动作，返回false表示继续响应系统动作
		return true;
	}
	
	private Handler mHandler = new Handler();
	private Runnable mFinish = new Runnable() {
		@Override
		public void run() {
			finish();
		}
	};

}
