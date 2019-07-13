package com.gs.learn.senior;

import java.util.Calendar;

import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TimePicker;
import android.widget.TextView;
import com.gs.learn.R;
/**
 * Created by ouyangshen on 2016/10/7.
 */
public class TimePickerActivity extends AppCompatActivity implements
		OnClickListener, OnTimeSetListener {

	private TextView tv_time;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_time_picker);
		tv_time = (TextView) findViewById(R.id.tv_time);
		findViewById(R.id.btn_time).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_time) {
			Calendar calendar = Calendar.getInstance();
			TimePickerDialog dialog = new TimePickerDialog(this, this,
					calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
			dialog.show();
		}
	}

	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		String desc = String.format("您选择的时间是%d时%d分", hourOfDay, minute);
		tv_time.setText(desc);
	}

}
