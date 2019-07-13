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
import com.gs.learn.network.task.UploadHttpTask;
import com.gs.learn.network.task.UploadHttpTask.OnUploadHttpListener;
import com.gs.learn.network.thread.ClientThread;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/11/11.
 */
public class UploadHttpActivity extends AppCompatActivity implements
		OnClickListener, FileSelectCallbacks, OnUploadHttpListener {
	private EditText et_http_url;
	private TextView tv_file_path;
	private String mFileName;

	@Override
	protected void onCreate(Bundle selectdInstanceState) {
		super.onCreate(selectdInstanceState);
		setContentView(R.layout.activity_upload_http);
		et_http_url = (EditText) findViewById(R.id.et_http_url);
		et_http_url.setText(ClientThread.REQUEST_URL + "/uploadServlet");
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
		UploadHttpTask uploadTask = new UploadHttpTask(this);
		uploadTask.setOnUploadHttpListener(this);
		uploadTask.execute(new String[]{et_http_url.getText().toString(), path});
	}

	@Override
	public boolean isFileValid(String absolutePath, String fileName, Map<String, Object> map_param) {
		return true;
	}

	@Override
	public void onUploadFinish(String result) {
		String desc = String.format("%s\n上传结果为：%s", 
				tv_file_path.getText().toString(), result);
		if (result.equals("SUCC")  == true) {
			String uploadUrl = et_http_url.getText().toString();
			desc = String.format("%s\n预计下载地址为：%s%s", desc, 
					uploadUrl.substring(0, uploadUrl.lastIndexOf("/")+1), mFileName);
		}
		tv_file_path.setText(desc);
	}

}
