package com.gs.learn.middle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/9/24.
 */
public class EditJumpActivity extends AppCompatActivity implements OnClickListener {

	private EditText et_username;
	private EditText et_password;
	private Button btn_login;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_jump);
		
		et_username= (EditText) findViewById(R.id.et_username);
		et_password= (EditText) findViewById(R.id.et_password);
		btn_login = (Button) findViewById(R.id.btn_login);
		et_username.addTextChangedListener(new JumpTextWatcher(et_username, et_password));
		et_password.addTextChangedListener(new JumpTextWatcher(et_password, btn_login));
		btn_login.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_login) {
			Toast.makeText(this, "这个登录按钮啥事也没做", Toast.LENGTH_SHORT).show();
		}
	}

	private class JumpTextWatcher implements TextWatcher {
		 
		private EditText mThisView = null;
		private View mNextView = null;
		 
		public JumpTextWatcher(EditText vThis, View vNext) {
			super();
			mThisView = vThis;
			if (vNext != null) {
				mNextView = vNext;
			}
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
		}

		@Override
		public void afterTextChanged(Editable s) {
			String str = s.toString();
			//发现输入回车符或换行符
			if (str.indexOf("\r") >= 0 || str.indexOf("\n") >= 0) {
				//去掉回车符和换行符
				mThisView.setText(str.replace("\r", "").replace("\n", ""));
				if (mNextView != null) {
					//让下一个视图获得焦点，即将光标移到下个视图
					mNextView.requestFocus();
					if (mNextView instanceof EditText) {
						EditText et = (EditText)mNextView;
						//让光标自动移到编辑框内部的文本末尾
						//方式一：直接调用EditText的setSelection方法
						et.setSelection(et.getText().length());
						//方式二：调用Selection类的setSelection方法
						//Editable edit = et.getText();
						//Selection.setSelection(edit, edit.length());
					}
				}
			}
		}
	 }
}
