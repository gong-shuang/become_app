package com.gs.learn.network;

import java.util.Map;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.aqi00.lib.dialog.FileSelectFragment;
import com.aqi00.lib.dialog.FileSelectFragment.FileSelectCallbacks;
import com.gs.learn.network.task.UploadFtpTask;
import com.gs.learn.network.task.UploadFtpTask.OnUploadFtpListener;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/11/11.
 */
public class UploadFtpActivity extends AppCompatActivity implements
		OnClickListener, FileSelectCallbacks, OnUploadFtpListener {
	private EditText et_ftp_ip, et_ftp_port, et_ftp_username, et_ftp_password, et_ftp_dir;
	private TextView tv_file_path;
	private String mFileName;

	@Override
	protected void onCreate(Bundle selectdInstanceState) {
		super.onCreate(selectdInstanceState);
		setContentView(R.layout.activity_upload_ftp);
		et_ftp_ip = (EditText) findViewById(R.id.et_ftp_ip);
		et_ftp_port = (EditText) findViewById(R.id.et_ftp_port);
		et_ftp_username = (EditText) findViewById(R.id.et_ftp_username);
		et_ftp_password = (EditText) findViewById(R.id.et_ftp_password);
		et_ftp_dir = (EditText) findViewById(R.id.et_ftp_dir);
		tv_file_path = (TextView) findViewById(R.id.tv_file_path);
		findViewById(R.id.btn_file_select).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_file_select) {
			String[] fileExt = new String[] { "jpg", "png", "txt", "3gp", "mp4", "amr", "aac" };
			FileSelectFragment.show(this, fileExt, null);
		}
	}

	@Override
	public void onConfirmSelect(String absolutePath, String fileName, Map<String, Object> map_param) {
		mFileName = fileName;
		String path = String.format("%s/%s", absolutePath, fileName);
		tv_file_path.setText("上传文件的路径为：" + path);
		UploadFtpTask uploadTask = new UploadFtpTask(this);
		uploadTask.setOnUploadFtpListener(this);
		uploadTask.execute(new String[] { 
				et_ftp_ip.getText().toString(),
				et_ftp_port.getText().toString(),
				et_ftp_username.getText().toString(),
				et_ftp_password.getText().toString(),
				et_ftp_dir.getText().toString(),
				absolutePath+"/", fileName });
	}

	@Override
	public boolean isFileValid(String absolutePath, String fileName, Map<String, Object> map_param) {
		return true;
	}

	@Override
	public void onUploadFinish(String result) {
		String desc = String.format("%s\n上传结果为：%s", tv_file_path.getText().toString(), result);
		if (result.equals("SUCC") == true) {
			String downloadUrl = String.format("ftp://%s:%s@%s:%s%s/%s", 
					et_ftp_username.getText().toString(),
					et_ftp_password.getText().toString(),
					et_ftp_ip.getText().toString(),
					et_ftp_port.getText().toString(),
					et_ftp_dir.getText().toString(), mFileName);
			desc = String.format("%s\n预计下载地址为：%s", desc, downloadUrl);
		}
		tv_file_path.setText(desc);
	}

}
