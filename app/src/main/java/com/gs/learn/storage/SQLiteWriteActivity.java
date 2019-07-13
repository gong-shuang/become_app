package com.gs.learn.storage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.gs.learn.storage.bean.UserInfo;
import com.gs.learn.storage.database.UserDBHelper;
import com.gs.learn.common.util.DateUtil;
import com.gs.learn.R;
/**
 * Created by ouyangshen on 2016/10/1.
 */
public class SQLiteWriteActivity extends AppCompatActivity implements OnClickListener {

	private UserDBHelper mHelper;
	private EditText et_name;
	private EditText et_age;
	private EditText et_height;
	private EditText et_weight;
	private boolean bMarried = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sqlite_write);
		et_name = (EditText) findViewById(R.id.et_name);
		et_age = (EditText) findViewById(R.id.et_age);
		et_height = (EditText) findViewById(R.id.et_height);
		et_weight = (EditText) findViewById(R.id.et_weight);
		findViewById(R.id.btn_save).setOnClickListener(this);

		ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(this,
				R.layout.item_select, typeArray);
		typeAdapter.setDropDownViewResource(R.layout.item_dropdown);
		Spinner sp_married = (Spinner) findViewById(R.id.sp_married);
		sp_married.setPrompt("请选择婚姻状况");
		sp_married.setAdapter(typeAdapter);
		sp_married.setSelection(0);
		sp_married.setOnItemSelectedListener(new TypeSelectedListener());
		
	}
	
	private String[] typeArray = {"未婚", "已婚"};
	class TypeSelectedListener implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			bMarried = (arg2==0)?false:true;
		}

		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		mHelper = UserDBHelper.getInstance(this, 2);
		mHelper.openWriteLink();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		mHelper.closeLink();
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_save) {
			String name = et_name.getText().toString();
			String age = et_age.getText().toString();
			String height = et_height.getText().toString();
			String weight = et_weight.getText().toString();
			if (name==null || name.length()<=0) {
				showToast("请先填写姓名");
				return;
			}
			if (age==null || age.length()<=0) {
				showToast("请先填写年龄");
				return;
			}
			if (height==null || height.length()<=0) {
				showToast("请先填写身高");
				return;
			}
			if (weight==null || weight.length()<=0) {
				showToast("请先填写体重");
				return;
			}
			
			UserInfo info = new UserInfo();
			info.name = name;
			info.age = Integer.parseInt(age);
			info.height = Long.parseLong(height);
			info.weight = Float.parseFloat(weight);
			info.married = bMarried;
			info.update_time = DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss");
			mHelper.insert(info);
			showToast("数据已写入SQLite数据库");
		}
	}
	
	private void showToast(String desc) {
		Toast.makeText(this, desc, Toast.LENGTH_SHORT).show();
	}
	
}
