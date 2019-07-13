package com.gs.learn.test;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/10/28.
 */
public class VersionActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_version);
		ImageView iv_icon = (ImageView) findViewById(R.id.iv_icon);
		TextView tv_desc = (TextView) findViewById(R.id.tv_desc);
		iv_icon.setImageResource(R.mipmap.ic_launcher);
		try {
			PackageInfo pi = getPackageManager().getPackageInfo(getPackageName(), 0);
			String desc = String.format("App名称为：%s\nApp版本号为：%d\nApp版本名称为：%s", 
					getResources().getString(R.string.app_name),
					pi.versionCode, pi.versionName);
			tv_desc.setText(desc);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
	}

}
