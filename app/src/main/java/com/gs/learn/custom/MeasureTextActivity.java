package com.gs.learn.custom;

import com.gs.learn.custom.util.MeasureUtil;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;
import com.gs.learn.R;
/**
 * Created by ouyangshen on 2016/10/14.
 */
public class MeasureTextActivity extends AppCompatActivity {

	private final static String TAG = "MeasureTextActivity";
	private TextView tv_desc, tv_text;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_measure_text);
		tv_desc = (TextView) findViewById(R.id.tv_desc);
		tv_text = (TextView) findViewById(R.id.tv_text);

		ArrayAdapter<String> sizeAdapter = new ArrayAdapter<String>(this,
				R.layout.item_select, descArray);
		Spinner sp_size = (Spinner) findViewById(R.id.sp_size);
		sp_size.setPrompt("请选择文字大小");
		sp_size.setAdapter(sizeAdapter);
		sp_size.setOnItemSelectedListener(new SizeSelectedListener());
		sp_size.setSelection(0);
	}

	private String[] descArray={"12sp", "15sp", "17sp", "20sp", "22sp", "25sp", "27sp", "30sp"};
	private int[] sizeArray={12, 15, 17, 20, 22, 25, 27, 30};
	class SizeSelectedListener implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			String text = tv_text.getText().toString();
			int textSize = sizeArray[arg2];
			tv_text.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
			int width = (int) MeasureUtil.getTextWidth(text, textSize);
			int height = (int) MeasureUtil.getTextHeight(text, textSize);
			String desc = String.format("下面文字的宽度是%s ,高度是%d", width, height);
			tv_desc.setText(desc);
		}

		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}
	
}
