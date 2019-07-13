package com.gs.learn.custom.widget;

import com.gs.learn.R;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.TextView;

public class CustomDateDialog implements OnClickListener, OnDateChangedListener {
	private Dialog dialog;
	private View view;
	private TextView tv_title;
	private DatePicker dp_date;
	private Button btn_ok;

	public CustomDateDialog(Context context) {
		view = LayoutInflater.from(context).inflate(R.layout.dialog_date, null);
		dialog = new Dialog(context, R.style.CustomDateDialog);
		tv_title = (TextView) view.findViewById(R.id.tv_title);
		dp_date = (DatePicker) view.findViewById(R.id.dp_date);
		btn_ok = (Button) view.findViewById(R.id.btn_ok);
		btn_ok.setOnClickListener(this);
	}
	
	public void setTitle(String title) {
		tv_title.setText(title);
	}

	public void setDate(int year, int month, int day, OnDateSetListener listener) {
		dp_date.init(year, month, day, this);
		mDateSetListener = listener;
	}

	public void show() {
		dialog.getWindow().setContentView(view);
		dialog.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		dialog.show();
	}

	public void dismiss() {
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
	}

	public boolean isShowing() {
		if (dialog != null) {
			return dialog.isShowing();
		} else {
			return false;
		}
	}

	@Override
	public void onClick(View v) {
		dismiss();
        if (mDateSetListener != null) {
        	dp_date.clearFocus();
        	mDateSetListener.onDateSet(dp_date.getYear(),
        			dp_date.getMonth()+1, dp_date.getDayOfMonth());
        }
	}

	@Override
	public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		dp_date.init(year, monthOfYear, dayOfMonth, this);
	}

	private OnDateSetListener mDateSetListener;
    public interface OnDateSetListener {
        void onDateSet(int year, int monthOfYear, int dayOfMonth);
    }

}
