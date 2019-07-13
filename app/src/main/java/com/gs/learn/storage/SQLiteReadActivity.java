package com.gs.learn.storage;

import java.util.ArrayList;

import com.gs.learn.storage.bean.UserInfo;
import com.gs.learn.storage.database.UserDBHelper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;
import com.gs.learn.R;
/**
 * Created by ouyangshen on 2016/10/1.
 */
public class SQLiteReadActivity extends AppCompatActivity implements OnClickListener {

	private UserDBHelper mHelper;
	private TextView tv_sqlite;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sqlite_read);
		tv_sqlite = (TextView) findViewById(R.id.tv_sqlite);
		findViewById(R.id.btn_delete).setOnClickListener(this);
	}
	
	private void readSQLite() {
		if (mHelper == null) {
			showToast("数据库连接为空");
			return;
		}
		ArrayList<UserInfo> userArray = mHelper.query("1=1");
		String desc = String.format("数据库查询到%d条记录，详情如下：", userArray.size());
		for (int i=0; i<userArray.size(); i++) {
			UserInfo info = userArray.get(i);
			desc = String.format("%s\n第%d条记录信息如下：", desc, i+1);
			desc = String.format("%s\n　姓名为%s", desc, info.name);
			desc = String.format("%s\n　年龄为%d", desc, info.age);
			desc = String.format("%s\n　身高为%d", desc, info.height);
			desc = String.format("%s\n　体重为%f", desc, info.weight);
			desc = String.format("%s\n　婚否为%b", desc, info.married);
			desc = String.format("%s\n　更新时间为%s", desc, info.update_time);
		}
		if (userArray==null || userArray.size()<=0) {
			desc = "数据库查询到的记录为空";
		}
		tv_sqlite.setText(desc);
	}

	@Override
	protected void onStart() {
		super.onStart();
		mHelper = UserDBHelper.getInstance(this, 2);
		mHelper.openReadLink();
		readSQLite();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		mHelper.closeLink();
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_delete) {
			//删除所有记录
			mHelper.closeLink();
			mHelper.openWriteLink();
			mHelper.deleteAll();
			//重新读取数据库
			mHelper.closeLink();
			mHelper.openReadLink();
			readSQLite();
		}
	}

	private void showToast(String desc) {
		Toast.makeText(this, desc, Toast.LENGTH_SHORT).show();
	}
	
}
