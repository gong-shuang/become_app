package com.gs.learn.middle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.gs.learn.R;
/**
 * Created by ouyangshen on 2016/9/24.
 */
public class SwitchIOSActivity extends AppCompatActivity implements OnCheckedChangeListener {

	private CheckBox ck_status;
	private TextView tv_result;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_switch_ios);
		ck_status = (CheckBox) findViewById(R.id.ck_status);
		tv_result = (TextView) findViewById(R.id.tv_result);
		ck_status.setOnCheckedChangeListener(this);
		refreshResult();
	}
	
	private void refreshResult() {
		String result = String.format("仿iOS开关的状态是%s", 
				(ck_status.isChecked())?"开":"关");
		tv_result.setText(result);
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		refreshResult();
	}

}
