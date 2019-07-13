package com.gs.learn.custom;

import com.gs.learn.custom.util.SharedUtil;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;
import com.gs.learn.R;
/**
 * Created by ouyangshen on 2016/10/14.
 */
public class MobileConfigActivity extends AppCompatActivity implements OnClickListener {

	private EditText et_config_month;
	private EditText et_config_day;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mobile_config);
		et_config_month = (EditText) findViewById(R.id.et_config_month);
		et_config_day = (EditText) findViewById(R.id.et_config_day);
		findViewById(R.id.btn_config_save).setOnClickListener(this);
		
		String limit_month = SharedUtil.getIntance(this).readShared("limit_month", "1024");
		String limit_day = SharedUtil.getIntance(this).readShared("limit_day", "30");
		et_config_month.setText(limit_month);
		et_config_day.setText(limit_day);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_config_save) {
			String limit_month = et_config_month.getText().toString();
			String limit_day = et_config_day.getText().toString();
			SharedUtil.getIntance(this).writeShared("limit_month", limit_month);
			SharedUtil.getIntance(this).writeShared("limit_day", limit_day);
			Toast.makeText(this, "成功保存配置", Toast.LENGTH_SHORT).show();
			finish();
		}
	}


}
