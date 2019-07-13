package com.gs.learn.middle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import com.gs.learn.R;
/**
 * Created by ouyangshen on 2016/9/24.
 */
public class RadioHorizontalActivity extends AppCompatActivity implements OnCheckedChangeListener {

	private final static String TAG = "RadioHorizontalActivity";
	private TextView tv_sex;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_radio_horizontal);
		tv_sex = (TextView) findViewById(R.id.tv_sex);
		RadioGroup rg_sex = (RadioGroup) findViewById(R.id.rg_sex);
		rg_sex.setOnCheckedChangeListener(this);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		if (checkedId == R.id.rb_male) {
			tv_sex.setText("哇哦，你是个帅气的男孩");
		} else if (checkedId == R.id.rb_female) {
			tv_sex.setText("哇哦，你是个漂亮的女孩");
		}
	}
	
}
