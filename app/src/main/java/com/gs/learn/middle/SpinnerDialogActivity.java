package com.gs.learn.middle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import com.gs.learn.R;
/**
 * Created by ouyangshen on 2016/9/24.
 */
public class SpinnerDialogActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_spinner_dialog);

		ArrayAdapter<String> starAdapter = new ArrayAdapter<String>(this,
				R.layout.item_select, starArray);
		starAdapter.setDropDownViewResource(R.layout.item_dropdown);
		
		Spinner sp = (Spinner) findViewById(R.id.sp_dialog);
		sp.setPrompt("请选择行星");
		sp.setAdapter(starAdapter);
		sp.setSelection(0);
		sp.setOnItemSelectedListener(new MySelectedListener());
	}

	private String[] starArray = {"水星", "金星", "地球", "火星", "木星", "土星"};
	class MySelectedListener implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			Toast.makeText(SpinnerDialogActivity.this, "您选择的是"+starArray[arg2], Toast.LENGTH_LONG).show();
		}

		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}
	
}
