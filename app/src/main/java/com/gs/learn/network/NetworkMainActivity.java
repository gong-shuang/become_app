package com.gs.learn.network;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;

import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/11/11.
 */
public class NetworkMainActivity extends AppCompatActivity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_network);

		findViewById(R.id.btn_message).setOnClickListener(this);
		findViewById(R.id.btn_progress_dialog).setOnClickListener(this);
		findViewById(R.id.btn_progress_text).setOnClickListener(this);
		findViewById(R.id.btn_progress_circle).setOnClickListener(this);
		findViewById(R.id.btn_async_task).setOnClickListener(this);
		findViewById(R.id.btn_intent_service).setOnClickListener(this);
		findViewById(R.id.btn_connect).setOnClickListener(this);
		findViewById(R.id.btn_json).setOnClickListener(this);
		findViewById(R.id.btn_http_request).setOnClickListener(this);
		findViewById(R.id.btn_http_image).setOnClickListener(this);
		findViewById(R.id.btn_download_image).setOnClickListener(this);
		findViewById(R.id.btn_download_apk).setOnClickListener(this);
		findViewById(R.id.btn_file_save).setOnClickListener(this);
		findViewById(R.id.btn_file_select).setOnClickListener(this);
		findViewById(R.id.btn_upload_http).setOnClickListener(this);
		findViewById(R.id.btn_upload_ftp).setOnClickListener(this);
		findViewById(R.id.btn_net_address).setOnClickListener(this);
		findViewById(R.id.btn_socket).setOnClickListener(this);
		findViewById(R.id.btn_fold_list).setOnClickListener(this);
		findViewById(R.id.btn_qqchat).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_message) {
			Intent intent = new Intent(this, MessageActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_progress_dialog) {
			Intent intent = new Intent(this, ProgressDialogActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_progress_text) {
			Intent intent = new Intent(this, ProgressTextActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_progress_circle) {
			Intent intent = new Intent(this, ProgressCircleActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_async_task) {
			Intent intent = new Intent(this, AsyncTaskActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_intent_service) {
			Intent intent = new Intent(this, IntentServiceActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_connect) {
			Intent intent = new Intent(this, ConnectActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_json) {
			Intent intent = new Intent(this, JsonActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_http_request) {
			Intent intent = new Intent(this, HttpRequestActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_http_image) {
			Intent intent = new Intent(this, HttpImageActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_download_image) {
			Intent intent = new Intent(this, DownloadImageActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_download_apk) {
			Intent intent = new Intent(this, DownloadApkActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_file_save) {
			Intent intent = new Intent(this, FileSaveActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_file_select) {
			Intent intent = new Intent(this, FileSelectActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_upload_http) {
			Intent intent = new Intent(this, UploadHttpActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_upload_ftp) {
			Intent intent = new Intent(this, UploadFtpActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_net_address) {
			Intent intent = new Intent(this, NetAddressActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_socket) {
			Intent intent = new Intent(this, SocketActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_fold_list) {
			Intent intent = new Intent(this, FoldListActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_qqchat) {
			Intent intent = new Intent(this, QQLoginActivity.class);
			startActivity(intent);
		}
	}

}
