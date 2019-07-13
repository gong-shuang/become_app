package com.gs.learn.custom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ProgressBar;
import com.gs.learn.R;
/**
 * Created by ouyangshen on 2016/10/14.
 */
public class NotifyProgressActivity extends AppCompatActivity implements OnClickListener {

	private ProgressBar pb_progress;
	private EditText et_progress;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notify_progress);
		pb_progress = (ProgressBar) findViewById(R.id.pb_progress);
		et_progress = (EditText) findViewById(R.id.et_progress);
		findViewById(R.id.btn_progress).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_progress) {
			int progress = Integer.parseInt(et_progress.getText().toString());
			pb_progress.setProgress(progress);
		}
	}

}
