package com.gs.learn.custom;

import java.util.Calendar;

import com.gs.learn.custom.widget.CustomDateDialog;
import com.gs.learn.custom.widget.CustomDateDialog.OnDateSetListener;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/10/14.
 */
public class DialogDateActivity extends AppCompatActivity implements
		OnClickListener, OnDateSetListener {

	private TextView tv_date;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dialog_date);
		tv_date = (TextView) findViewById(R.id.tv_date);
		findViewById(R.id.btn_date).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_date) {
			Calendar calendar = Calendar.getInstance();
			CustomDateDialog dialog = new CustomDateDialog(this);
			dialog.setDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
					calendar.get(Calendar.DAY_OF_MONTH), this);
			dialog.show();
		}
	}

	@Override
	public void onDateSet(int year, int month, int day) {
		String desc = String.format("您选择的日期是%d年%d月%d日", year, month, day);
		tv_date.setText(desc);
	}

}
