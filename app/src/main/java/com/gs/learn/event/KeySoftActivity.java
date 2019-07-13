package com.gs.learn.event;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.TextView;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/11/23.
 */
public class KeySoftActivity extends AppCompatActivity implements OnKeyListener {
	private EditText et_soft;
	private TextView tv_result;
	private String desc = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_key_soft);
		et_soft = (EditText) findViewById(R.id.et_soft);
		tv_result = (TextView) findViewById(R.id.tv_result);
		et_soft.setOnKeyListener(this);
	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		if (event.getAction()==KeyEvent.ACTION_DOWN) {
			desc = String.format("%s输入的软按键编码是%d,动作是按下", desc, keyCode);
			if (keyCode == KeyEvent.KEYCODE_ENTER) {
				desc = String.format("%s, 按键为回车键", desc);
			} else if (keyCode == KeyEvent.KEYCODE_DEL) {
				desc = String.format("%s, 按键为删除键", desc);
			} else if (keyCode == KeyEvent.KEYCODE_SEARCH) {
				desc = String.format("%s, 按键为搜索键", desc);
			} else if (keyCode == KeyEvent.KEYCODE_BACK) {
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
			return true;
		} else {
			//返回true表示处理完了不再输入该字符，返回false表示给你输入该字符吧
			return false;
		}
	}

	private Handler mHandler = new Handler();
	private Runnable mFinish = new Runnable() {
		@Override
		public void run() {
			finish();
		}
	};

}
