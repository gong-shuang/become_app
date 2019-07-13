package com.gs.learn.network.task;

import com.gs.learn.network.http.HttpRequestUtil;
import com.gs.learn.network.http.tool.HttpReqData;
import com.gs.learn.network.http.tool.HttpRespData;
import com.gs.learn.network.thread.ClientThread;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class QueryFriendTask extends AsyncTask<Void, Void, String> {
	private final static String TAG = "QueryFriendTask";
    private Context mContext;
    private String mQueryUrl = ClientThread.REQUEST_URL + "/queryFriend";

	public QueryFriendTask(Context context) {
		super();
		mContext = context;
	}

	@Override
	protected String doInBackground(Void... params) {
        Log.d(TAG, "query url="+mQueryUrl);
        HttpReqData req_data = new HttpReqData(mQueryUrl);
        HttpRespData resp_data = HttpRequestUtil.postData(req_data);
        Log.d(TAG, "result="+resp_data.content);
        return resp_data.content;
	}

	@Override
	protected void onPostExecute(String resp) {
		mListener.onQueryFriend(resp);
	}

	private OnQueryFriendListener mListener;
	public void setOnQueryFriendListener(OnQueryFriendListener listener) {
		mListener = listener;
	}

	public static interface OnQueryFriendListener {
		public abstract void onQueryFriend(String resp);
	}

}
