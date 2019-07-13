package com.gs.learn.mixture;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/12/11.
 */
public class JniSecretActivity extends AppCompatActivity implements OnClickListener {
	private EditText et_origin;
	private EditText et_encrypt;
	private TextView tv_cpu_build;
	private TextView tv_decrypt;
	private String mKey = "123456789abcdef"; //该算法要求密钥值长度为16位

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jni_secret);
		et_origin = (EditText) findViewById(R.id.et_origin);
		et_encrypt = (EditText) findViewById(R.id.et_encrypt);
		tv_decrypt = (TextView) findViewById(R.id.tv_decrypt);
		findViewById(R.id.btn_encrypt).setOnClickListener(this);
		findViewById(R.id.btn_decrypt).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_encrypt) {
			String des = encryptFromJNI(et_origin.getText().toString(), mKey);
			et_encrypt.setText(des);
		} else if (v.getId() == R.id.btn_decrypt) {
			String raw = decryptFromJNI(et_encrypt.getText().toString(), mKey);
			tv_decrypt.setText(raw);
		}
	}

	public native String encryptFromJNI(String raw, String key);
	public native String unimplementedEncryptFromJNI(String raw, String key);
	public native String decryptFromJNI(String des, String key);
	public native String unimplementedEecryptFromJNI(String des, String key);
	static {
		System.loadLibrary("jni_mix");
	}
    
}
