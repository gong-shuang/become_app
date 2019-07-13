package com.gs.learn.network;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

import com.gs.learn.network.widget.TextProgressCircle;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/11/11.
 */
public class ProgressCircleActivity extends AppCompatActivity {
	
	private TextProgressCircle tpc_progress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_text_circle);
		tpc_progress = (TextProgressCircle) findViewById(R.id.tpc_progress);

		ArrayAdapter<String> progressAdapter = new ArrayAdapter<String>(this,
				R.layout.item_select, progressArray);
		Spinner sp_progress = (Spinner) findViewById(R.id.sp_progress);
		sp_progress.setPrompt("请选择进度值");
		sp_progress.setAdapter(progressAdapter);
		sp_progress.setOnItemSelectedListener(new DividerSelectedListener());
		sp_progress.setSelection(0);
	}

	private String[] progressArray={
			"0", "10", "20", "30", "40", "50",
			"60", "70", "80", "90", "100"
	};
	class DividerSelectedListener implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			int progress = Integer.parseInt(progressArray[arg2]);
			tpc_progress.setProgress(progress, -1);
		}

		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}
	
}
