package com.gs.learn.middle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/9/24.
 */
public class EditAutoActivity extends AppCompatActivity {

	private AutoCompleteTextView ac_text;
	private String[] hintArray = {"第一", "第一次", "第一次写代码", "第一次领工资", "第二", "第二个"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_auto);

		ac_text= (AutoCompleteTextView) findViewById(R.id.ac_text);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				this, R.layout.item_dropdown, hintArray);
		ac_text.setAdapter(adapter);
	}
	
}
