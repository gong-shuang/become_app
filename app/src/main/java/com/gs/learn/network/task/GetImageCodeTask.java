package com.gs.learn.network.task;

import com.gs.learn.network.http.HttpRequestUtil;
import com.gs.learn.network.http.tool.HttpReqData;
import com.gs.learn.network.http.tool.HttpRespData;
import com.gs.learn.network.util.BitmapUtil;
import com.gs.learn.network.util.DateUtil;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class GetImageCodeTask extends AsyncTask<Void, Void, String> {
	private final static String TAG = "GetImageCodeTask";
    private Context mContext;
    private String mImageCodeUrl = "http://220.160.54.47:82/JSPORTLET/radomImage?x=";

	public GetImageCodeTask(Context context) {
		super();
		mContext = context;
	}

	@Override
	protected String doInBackground(Void... params) {
        String url = mImageCodeUrl + DateUtil.getNowDateTime(null);
        Log.d(TAG, "image url="+url);
        HttpReqData req_data = new HttpReqData(url);
        HttpRespData resp_data = HttpRequestUtil.getImage(req_data);
        String path = BitmapUtil.getCachePath(mContext) + DateUtil.getNowDateTime(null) + ".jpg";
        BitmapUtil.saveBitmap(path, resp_data.bitmap, "jpg", 80);
        Log.d(TAG, "image path="+path);
		return path;
	}

	@Override
	protected void onPostExecute(String path) {
		mListener.onGetCode(path);
	}

	private OnImageCodeListener mListener;
	public void setOnImageCodeListener(OnImageCodeListener listener) {
		mListener = listener;
	}

	public static interface OnImageCodeListener {
		public abstract void onGetCode(String path);
	}

}
