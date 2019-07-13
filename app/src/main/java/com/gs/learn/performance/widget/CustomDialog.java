package com.gs.learn.performance.widget;

import com.gs.learn.R;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TextView;

public class CustomDialog implements OnClickListener {
	private Dialog dialog;
	private View view;
	private TextView tv_title;
	private TextView tv_message;
	private Button btn_ok;

	public CustomDialog(Context context, int style) {
		view = LayoutInflater.from(context).inflate(R.layout.dialog_background, null);
		dialog = new Dialog(context, style);
		tv_title = (TextView) view.findViewById(R.id.tv_title);
		tv_message = (TextView) view.findViewById(R.id.tv_message);
		btn_ok = (Button) view.findViewById(R.id.btn_ok);
		btn_ok.setOnClickListener(this);
	}
	
	public void setTitle(String title) {
		tv_title.setText(title);
	}

	public void setMessage(String message) {
		tv_message.setText(message);
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
	}

}
