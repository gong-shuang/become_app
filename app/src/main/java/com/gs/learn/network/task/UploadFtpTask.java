package com.gs.learn.network.task;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.gs.learn.network.util.FTPUtil;

public class UploadFtpTask extends AsyncTask<String, Void, String> {
	private final static String TAG = "UploadFtpTask";
    private Context mContext;

	public UploadFtpTask(Context context) {
		super();
		mContext = context;
	}

	@Override
	protected String doInBackground(String... params) {
        String ftp_ip = params[0];
        String ftp_port = params[1];
        String ftp_username = params[2];
        String ftp_password = params[3];
        String ftp_dir = params[4];
        String filepath = params[5];
        String filename = params[6];
        String desc = String.format("ip=%s,port=%s,username=%s,password=%s,dir=%s,file=%s%s", 
        		ftp_ip, ftp_port, ftp_username, ftp_password, ftp_dir, filepath, filename);
        Log.d(TAG, "ftp info: "+desc);
		FTPUtil.setUser(ftp_ip, ftp_port, ftp_username, ftp_password);
		String result = FTPUtil.upload(ftp_dir, filepath, filename);
        Log.d(TAG, "upload result="+result);
		return result;
	}

	@Override
	protected void onPostExecute(String result) {
		mListener.onUploadFinish(result);
	}

	private OnUploadFtpListener mListener;
	public void setOnUploadFtpListener(OnUploadFtpListener listener) {
		mListener = listener;
	}

	public static interface OnUploadFtpListener {
		public abstract void onUploadFinish(String result);
	}

}
