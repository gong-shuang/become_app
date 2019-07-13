package com.gs.learn.network.task;

import com.gs.learn.network.http.HttpUploadUtil;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class UploadHttpTask extends AsyncTask<String, Void, String> {
	private final static String TAG = "UploadHttpTask";
	private Context mContext;

	public UploadHttpTask(Context context) {
		super();
		mContext = context;
	}

	@Override
	protected String doInBackground(String... params) {
		String uploadUrl = params[0];
		String filePath = params[1];
		Log.d(TAG, "uploadUrl=" + uploadUrl + ", filePath=" + filePath);
		String result = HttpUploadUtil.upload(uploadUrl, filePath);
		Log.d(TAG, "upload result=" + result);
		return result;
	}

	@Override
	protected void onPostExecute(String result) {
		mListener.onUploadFinish(result);
	}

	private OnUploadHttpListener mListener;
	public void setOnUploadHttpListener(OnUploadHttpListener listener) {
		mListener = listener;
	}

	public static interface OnUploadHttpListener {
		public abstract void onUploadFinish(String result);
	}

}
