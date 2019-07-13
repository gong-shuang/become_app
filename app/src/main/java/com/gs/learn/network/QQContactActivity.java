package com.gs.learn.network;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.gs.learn.MainApplication;
import com.gs.learn.network.adapter.FriendExpandAdapter;
import com.gs.learn.network.bean.Friend;
import com.gs.learn.network.bean.FriendGroup;
import com.gs.learn.network.task.QueryFriendTask;
import com.gs.learn.network.task.QueryFriendTask.OnQueryFriendListener;
import com.gs.learn.network.thread.ClientThread;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.Toast;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/11/11.
 */
public class QQContactActivity extends AppCompatActivity implements 
		OnClickListener, OnQueryFriendListener {
	private final static String TAG = "QQContactActivity";
	private static Context mContext;
	private static ExpandableListView elv_friend;
	private static ArrayList<FriendGroup> mGroupList;
	private static FriendGroup mGroupOnline = new FriendGroup();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_qq_contact);
		Toolbar tl_head = (Toolbar) findViewById(R.id.tl_head);
		tl_head.setTitle(getResources().getString(R.string.menu_second));
		setSupportActionBar(tl_head);
		tl_head.setNavigationOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});
		mContext = getApplicationContext();
		mGroupOnline.title = "在线好友";
		mGroupList = new ArrayList<FriendGroup>();
		elv_friend = (ExpandableListView) findViewById(R.id.elv_friend);
		findViewById(R.id.btn_refresh).setOnClickListener(this);
		QueryFriendTask queryTask = new QueryFriendTask(this);
		queryTask.setOnQueryFriendListener(this);
		queryTask.execute();
	}

	@Override
	public void onQueryFriend(String resp) {
		try {
			JSONObject obj = new JSONObject(resp);
	        JSONArray groupArray = obj.getJSONArray("group_list");
	        for (int i=0; i<groupArray.length(); i++) {
	        	JSONObject groupObj = groupArray.getJSONObject(i);
	        	FriendGroup group = new FriendGroup();
	        	group.title = groupObj.getString("title");
	        	JSONArray friendArray = groupObj.getJSONArray("friend_list");
		        for (int j=0; j<friendArray.length(); j++) {
		        	JSONObject friendObj = friendArray.getJSONObject(j);
		        	Friend friend = new Friend("", friendObj.getString("name"), "");
		        	group.friend_list.add(friend);
		        }
		        mGroupList.add(group);
	        }
			showAllFriend();
		} catch (JSONException e) {
			e.printStackTrace();
			Toast.makeText(this, "获取全部好友列表出错："+e.getMessage(), 
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	protected void onResume() {
		mHandler.postDelayed(mRefresh, 500);
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		MainApplication.getInstance().sendAction(ClientThread.LOGOUT, "", "");
		super.onDestroy();
	}

	private Handler mHandler = new Handler();
	private Runnable mRefresh = new Runnable() {
		@Override
		public void run() {
			MainApplication.getInstance().sendAction(ClientThread.GETLIST, "", "");
		}
	};

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_refresh) {
			mHandler.post(mRefresh);
		}
	}

	public static class GetListReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent != null) {
				Log.d(TAG, "onReceive");
				String content = intent.getStringExtra(ClientThread.CONTENT);
				if (mContext != null && content != null && content.length() > 0) {
					showFriendOnline(content);
				}
			}
		}
	}
	
	private static void showFriendOnline(String content) {
		int pos = content.indexOf(ClientThread.SPLIT_LINE);
		String head = content.substring(0, pos);
		String body = content.substring(pos + 1);
		String[] splitArray = head.split(ClientThread.SPLIT_ITEM);
		if (splitArray[0].equals(ClientThread.GETLIST)) {
			String[] bodyArray = body.split("\\|");
			ArrayList<Friend> friendList = new ArrayList<Friend>();
			for (int i = 0; i < bodyArray.length; i++) {
				String[] itemArray = bodyArray[i].split(ClientThread.SPLIT_ITEM);
				if (bodyArray[i].length() > 0 && itemArray != null && itemArray.length >= 3) {
					friendList.add(new Friend(itemArray[0], itemArray[1], itemArray[2]));
				}
			}
			mGroupOnline.friend_list = friendList;
			showAllFriend();
		} else {
			String hint = String.format("%s\n%s", splitArray[0], body);
			Toast.makeText(mContext, hint, Toast.LENGTH_SHORT).show();
		}
	}
	
	private static void showAllFriend() {
		ArrayList<FriendGroup> all_group = new ArrayList<FriendGroup>();
		all_group.add(mGroupOnline);
		all_group.addAll(mGroupList);
        FriendExpandAdapter adapter = new FriendExpandAdapter(mContext, all_group);
		elv_friend.setAdapter(adapter);
		elv_friend.setOnChildClickListener(adapter);
		elv_friend.setOnGroupClickListener(adapter);
		elv_friend.expandGroup(0);  //默认展开在线好友
	}

}
