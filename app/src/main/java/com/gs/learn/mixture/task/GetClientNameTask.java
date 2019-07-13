package com.gs.learn.mixture.task;

import com.gs.learn.mixture.WifiShareActivity;
import com.gs.learn.mixture.util.GetClientName;

import android.os.AsyncTask;
import android.util.Log;

public class GetClientNameTask extends AsyncTask<String, Void, String> {
	private final static String TAG = "GetClientNameTask";
    
	@Override
	protected String doInBackground(String... params) {
		Log.d(TAG, "doInBackground ip="+params[0]);
		//jni方式获取主机名
		String info = WifiShareActivity.nameFromJNI(params[0]);
		//java方式获取主机名
//		try {
//			GetClientName client = new GetClientName(params[0]);
//			info = client.getRemoteInfo();
//		} catch (Exception e) {
//			e.printStackTrace();
//			info = e.getMessage();
//		}
		Log.d(TAG, "doInBackground info="+info);
		return info;
	}

	@Override
	protected void onPostExecute(String info) {
		mListener.onFindName(info);
	}

	private FindNameListener mListener;
	public void setFindNameListener(FindNameListener listener) {
		mListener = listener;
	}

	public static interface FindNameListener {
		public abstract void onFindName(String info);
	}

}
