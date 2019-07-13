package com.gs.learn.network;

import com.gs.learn.MainApplication;
import com.gs.learn.network.thread.ClientThread;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/11/11.
 */
public class QQLoginActivity extends AppCompatActivity implements OnClickListener {
    private EditText et_name;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_qq_login);
		et_name = (EditText) findViewById(R.id.et_name);
        findViewById(R.id.btn_ok).setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_ok) {
			String nickName = et_name.getText().toString().trim();
			if (nickName.length() <= 0) {
				Toast.makeText(this, "请输入您的昵称", Toast.LENGTH_SHORT).show();
			} else {
				MainApplication.getInstance().setNickName(nickName);
				MainApplication.getInstance().sendAction(ClientThread.LOGIN, "", "");
				Intent intent = new Intent(this, QQChatActivity.class);
				startActivity(intent);
				finish();
			}
		}
	}
}
