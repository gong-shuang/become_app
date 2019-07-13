package com.gs.learn.storage;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;
import com.gs.learn.R;
/**
 * Created by ouyangshen on 2016/10/1.
 */
public class LoginForgetActivity extends AppCompatActivity implements OnClickListener {

	private EditText et_password_first;
	private EditText et_password_second;
	private EditText et_verifycode;
	private String mVerifyCode;
	private String mPhone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_forget);
		et_password_first = (EditText) findViewById(R.id.et_password_first);
		et_password_second = (EditText) findViewById(R.id.et_password_second);
		et_verifycode = (EditText) findViewById(R.id.et_verifycode);
		findViewById(R.id.btn_verifycode).setOnClickListener(this);
		findViewById(R.id.btn_confirm).setOnClickListener(this);
		mPhone = getIntent().getStringExtra("phone");
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_verifycode) {
			if (mPhone==null || mPhone.length()<11) {
				Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
				return;
			}
			mVerifyCode = String.format("%06d", (int) (Math.random() * 1000000 % 1000000));
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("请记住验证码");
			builder.setMessage("手机号"+mPhone+"，本次验证码是"+mVerifyCode+"，请输入验证码");
			builder.setPositiveButton("好的", null);
			AlertDialog alert = builder.create();
			alert.show();
		} else if (v.getId() == R.id.btn_confirm) {
			String password_first = et_password_first.getText().toString();
			String password_second = et_password_second.getText().toString();
			if (password_first==null || password_first.length()<6 ||
					password_second==null || password_second.length()<6) {
				Toast.makeText(this, "请输入正确的新密码", Toast.LENGTH_SHORT).show();
				return;
			}
			if (password_first.equals(password_second) != true) {
				Toast.makeText(this, "两次输入的新密码不一致", Toast.LENGTH_SHORT).show();
				return;
			}
			if (et_verifycode.getText().toString().equals(mVerifyCode) != true) {
				Toast.makeText(this, "请输入正确的验证码", Toast.LENGTH_SHORT).show();
				return;
			} else {
				Toast.makeText(this, "密码修改成功", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent();
				intent.putExtra("new_password", password_first);
				setResult(Activity.RESULT_OK, intent);
				finish();
			}
		}
	}

}
