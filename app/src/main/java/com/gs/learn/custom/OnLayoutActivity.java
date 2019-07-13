package com.gs.learn.custom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

import com.gs.learn.custom.widget.OffsetLayout;
import com.gs.learn.R;
/**
 * Created by ouyangshen on 2016/10/14.
 */
public class OnLayoutActivity extends AppCompatActivity {

	private final static String TAG = "OnLayoutActivity";
	private OffsetLayout ol_content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_on_layout);
		ol_content = (OffsetLayout) findViewById(R.id.ol_content);

		ArrayAdapter<String> offsetAdapter = new ArrayAdapter<String>(this,
				R.layout.item_select, descArray);
		Spinner sp_offset = (Spinner) findViewById(R.id.sp_offset);
		sp_offset.setPrompt("请选择偏移大小");
		sp_offset.setAdapter(offsetAdapter);
		sp_offset.setOnItemSelectedListener(new OffsetSelectedListener());
		sp_offset.setSelection(0);
	}

	private String[] descArray={"无偏移", "向左偏移100", "向右偏移100", "向上偏移100", "向下偏移100"};
	private int[] offsetArray={0, -100, 100, -100, 100};
	class OffsetSelectedListener implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			int offset = offsetArray[arg2];
			if (arg2 == 0 || arg2 == 1 || arg2 == 2) {
				ol_content.setOffsetHorizontal(offset);
			} else if (arg2 == 3 || arg2 == 4) {
				ol_content.setOffsetVertical(offset);
			}
		}

		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}
	
}
