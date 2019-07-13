package com.gs.learn.network.task;

import android.os.AsyncTask;

public class ProgressAsyncTask extends AsyncTask<String, Integer, String> {
	private String mBook;
	public ProgressAsyncTask(String title) {
		super();
		mBook = title;
	}

	@Override
	protected String doInBackground(String... params) {
		int ratio = 0;
		for (; ratio <= 100; ratio += 5) {
			// 睡眠200毫秒模拟网络通信处理
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			publishProgress(ratio);
			// if (ratio >= 50) {
			// cancel(false);
			// }
		}
		return params[0];
	}

	@Override
	protected void onPreExecute() {
		mListener.onBegin(mBook);
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		mListener.onUpdate(mBook, values[0], 0);
	}

	@Override
	protected void onPostExecute(String result) {
		mListener.onFinish(result);
	}

	@Override
	protected void onCancelled(String result) {
		mListener.onCancel(result);
	}

	private OnProgressListener mListener;
	public void setOnProgressListener(OnProgressListener listener) {
		mListener = listener;
	}

	public static interface OnProgressListener {
		public abstract void onFinish(String result);
		public abstract void onCancel(String result);
		public abstract void onUpdate(String request, int progress, int sub_progress);
		public abstract void onBegin(String request);
	}

}
