package com.gs.learn.media;

import java.util.ArrayList;

import com.gs.learn.media.bean.UserInfo;
import com.gs.learn.media.provider.UserInfoContent;
import com.gs.learn.media.util.Utils;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/12/4.
 */
public class ContentProviderActivity extends AppCompatActivity implements OnClickListener {
	private static final String TAG = "ContentProviderActivity";
	private EditText et_user_name;
	private EditText et_user_age;
	private EditText et_user_height;
	private EditText et_user_weight;
	private TextView tv_read_user;
	private String mUserCount = "";
	private String mUserResult = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_content_provider);
		et_user_name = (EditText) findViewById(R.id.et_user_name);
		et_user_age = (EditText) findViewById(R.id.et_user_age);
		et_user_height = (EditText) findViewById(R.id.et_user_height);
		et_user_weight = (EditText) findViewById(R.id.et_user_weight);
		tv_read_user = (TextView) findViewById(R.id.tv_read_user);
		et_user_name.setText("阿四");
		et_user_age.setText("25");
		et_user_height.setText("165");
		et_user_weight.setText("50");
		findViewById(R.id.btn_add_user).setOnClickListener(this);
		tv_read_user.setOnClickListener(this);
		showUserInfo();
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_add_user) {
			UserInfo user = new UserInfo();
			user.name = et_user_name.getText().toString().trim();
			user.age = Integer.parseInt(et_user_age.getText().toString().trim());
			user.height = Integer.parseInt(et_user_height.getText().toString().trim());
			user.weight = Float.parseFloat(et_user_weight.getText().toString().trim());
			addUser(getContentResolver(), user);
			showUserInfo();
		} else if (v.getId() == R.id.tv_read_user) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(mUserCount);
			builder.setMessage(mUserResult);
			builder.setPositiveButton("确定", null);
			builder.create().show();
		}
	}
	
	private void showUserInfo() {
		mUserResult = readAllUser(getContentResolver());
		String[] split = mUserResult.split("\n");
		int count = (mUserResult.indexOf("\n")<0)?0:split.length;
		mUserCount = String.format("当前共找到%d位用户信息", count);
		tv_read_user.setText(mUserCount);
	}

	private void addUser(ContentResolver resolver, UserInfo user) {
		ContentValues name = new ContentValues();
		name.put("name", user.name);
		name.put("age", user.age);
		name.put("height", user.height);
		name.put("weight", user.weight);
		name.put("married", false);
		name.put("update_time", Utils.getNowDateTime());
		resolver.insert(UserInfoContent.CONTENT_URI, name);
	}
	
	private String readAllUser(ContentResolver resolver) {
		ArrayList<UserInfo> userArray = new ArrayList<UserInfo>();
		Cursor cursor = resolver.query(UserInfoContent.CONTENT_URI, null, null, null, null);
		while (cursor.moveToNext()) {
			UserInfo user = new UserInfo();
			user.name = cursor.getString(cursor.getColumnIndex(UserInfoContent.USER_NAME));
			user.age = cursor.getInt(cursor.getColumnIndex(UserInfoContent.USER_AGE));
			user.height = cursor.getInt(cursor.getColumnIndex(UserInfoContent.USER_HEIGHT));
			user.weight = cursor.getFloat(cursor.getColumnIndex(UserInfoContent.USER_WEIGHT));
			userArray.add(user);
		}
		cursor.close();

		String result = "";
		for (int i=0; i<userArray.size(); i++) {
			UserInfo user = userArray.get(i);
			result = String.format("%s%s	年龄%d	身高%d	体重%f\n", result, 
					user.name, user.age, user.height, user.weight);
		}
		Log.d(TAG, "result="+result);
		return result;
	}
	
}
