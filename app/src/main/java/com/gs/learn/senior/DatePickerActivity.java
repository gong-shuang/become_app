package com.gs.learn.senior;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.TextView;
import com.gs.learn.R;
/**
 * Created by ouyangshen on 2016/10/7.
 */
public class DatePickerActivity extends AppCompatActivity implements 
		OnClickListener, OnDateSetListener {

	private TextView tv_date;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_date_picker);
		tv_date = (TextView) findViewById(R.id.tv_date);
		findViewById(R.id.btn_date).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_date) {
			Calendar calendar = Calendar.getInstance();
			Log.d("cccc", calendar.get(Calendar.YEAR)+":"+
					calendar.get(Calendar.MONTH)+":"+
					calendar.get(Calendar.DAY_OF_MONTH)+"");
			DatePickerDialog dialog = new DatePickerDialog(this, this, 
					calendar.get(Calendar.YEAR), 
					calendar.get(Calendar.MONTH), 
					calendar.get(Calendar.DAY_OF_MONTH));
			dialog.show();
		}
	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		String desc = String.format("您选择的日期是%d年%d月%d日", 
				year, monthOfYear+1, dayOfMonth);
		tv_date.setText(desc);				
	}
	
}
