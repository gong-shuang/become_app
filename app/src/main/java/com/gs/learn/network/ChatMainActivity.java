package com.gs.learn.network;

import java.util.HashMap;
import java.util.Map;

import com.aqi00.lib.dialog.FileSelectFragment;
import com.aqi00.lib.dialog.FileSelectFragment.FileSelectCallbacks;
import com.gs.learn.MainApplication;
import com.gs.learn.R;
import com.gs.learn.network.task.UploadHttpTask;
import com.gs.learn.network.task.UploadHttpTask.OnUploadHttpListener;
import com.gs.learn.network.thread.ClientThread;
import com.gs.learn.network.util.DateUtil;
import com.gs.learn.network.util.MetricsUtil;
import com.gs.learn.network.util.Utils;
import com.gs.learn.network.widget.TextProgressCircle;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.DownloadManager.Query;
import android.app.DownloadManager.Request;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;

/**
 * Created by ouyangshen on 2016/11/11.
 */
public class ChatMainActivity extends AppCompatActivity implements
		OnClickListener, FileSelectCallbacks, OnUploadHttpListener {
	private static final String TAG = "ChatMainActivity";
	private static Context mContext;
	private static Activity mActivity;
	private TextView tv_other;
	private EditText et_input;
	private static TextView tv_show;
	private static ScrollView sv_chat;
	private static LinearLayout ll_show;
	private String mOtherId;
	private static int dip_margin;
	private static int TYPE_PHOTO = 0;
	private static int TYPE_SOUND = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat_main);
		mContext = getApplicationContext();
		mActivity = this;
		tv_other = (TextView) findViewById(R.id.tv_other);
		et_input = (EditText) findViewById(R.id.et_input);
		tv_show = (TextView) findViewById(R.id.tv_show);
		sv_chat = (ScrollView) findViewById(R.id.sv_chat);
		ll_show = (LinearLayout) findViewById(R.id.ll_show);
		findViewById(R.id.ib_photo).setOnClickListener(this);
		findViewById(R.id.ib_sound).setOnClickListener(this);
		findViewById(R.id.btn_send).setOnClickListener(this);

		dip_margin = MetricsUtil.dip2px(mContext, 5);
		Bundle bundle = getIntent().getExtras();
		mOtherId = bundle.getString("otherId", "");
		String desc = String.format("与%s聊天", bundle.getString("otherName", ""));
		tv_other.setText(desc);
		mDownloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.ib_photo) {
			String[] photoExt = new String[]{"jpg", "png"};
			HashMap<String, Object> map_param = new HashMap<String, Object>();
			map_param.put("type", TYPE_PHOTO);
			FileSelectFragment.show(this, photoExt, map_param);
		} else if (v.getId() == R.id.ib_sound) {
			String[] soundExt = new String[]{"amr", "aac", "mp3", "wav", "mid", "ogg"};
			HashMap<String, Object> map_param = new HashMap<String, Object>();
			map_param.put("type", TYPE_SOUND);
			FileSelectFragment.show(this, soundExt, map_param);
		} else if (v.getId() == R.id.btn_send) {
			String body = et_input.getText().toString();
			String append = String.format("%s %s\n%s", 
					MainApplication.getInstance().getNickName(),
					DateUtil.formatTime(DateUtil.getNowDateTime("")), body);
			appendMsg(Build.SERIAL, append);
			MainApplication.getInstance().sendAction(ClientThread.SENDMSG, mOtherId, body);
			et_input.setText("");
		}
	}

	private static void appendMsg(String deviceId, String append) {
		int gravity = deviceId.equals(Build.SERIAL) ? Gravity.RIGHT : Gravity.LEFT;
		int bg_color = deviceId.equals(Build.SERIAL) ? 0xffccccff : 0xffffcccc;
		LinearLayout ll_append = new LinearLayout(mContext);
		LinearLayout.LayoutParams ll_params = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		ll_params.setMargins(dip_margin, dip_margin, dip_margin, dip_margin);
		ll_append.setLayoutParams(ll_params);
		ll_append.setGravity(gravity);
		ll_append.setOrientation(LinearLayout.VERTICAL);

		TextView tv_append = getTextView(tv_show.getText().toString() + append, gravity);
		tv_append.setBackgroundColor(bg_color);
		ll_append.addView(tv_append);

		ll_show.addView(ll_append);
		mHandler.postDelayed(mScroll, 100);
	}

	private static TextView getTextView(String content, int gravity) {
		TextView tv = new TextView(mContext);
		tv.setText(content);
		tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		tv.setTextColor(Color.BLACK);
		LinearLayout.LayoutParams tv_params = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		tv.setLayoutParams(tv_params);
		tv.setGravity(gravity);
		return tv;
	}
	
	private static Runnable mScroll = new Runnable() {
		@Override
		public void run() {
			sv_chat.fullScroll(ScrollView.FOCUS_DOWN);
		}
	};

	private String mUploadUrl = ClientThread.REQUEST_URL + "/uploadServlet";
	private String mFileName;
	private static String mFilePath;
	private int mType;
	
	@Override
	public void onConfirmSelect(String absolutePath, String fileName, Map<String, Object> map_param) {
		mFileName = fileName;
		mFilePath = String.format("%s/%s", absolutePath, fileName);
		Log.d(TAG, "select path="+mFilePath);
		mType = (int) map_param.get("type");
		UploadHttpTask uploadTask = new UploadHttpTask(this);
		uploadTask.setOnUploadHttpListener(this);
		uploadTask.execute(new String[]{mUploadUrl, mFilePath});
//		String url = "http://img1.gtimg.com/fj/pics/hv1/229/130/1835/119354254.png";
//		MainApplication.getInstance().sendAction(ClientThread.SENDPHOTO, mOtherId, url);
//		String url = "http://mp3.haoduoge.com/s/2016-11-14/1479089459.mp3";
//		MainApplication.getInstance().sendAction(ClientThread.SENDSOUND, mOtherId, url);
	}

	@Override
	public boolean isFileValid(String absolutePath, String fileName, Map<String, Object> map_param) {
		return true;
	}

	@Override
	public void onUploadFinish(String result) {
		Log.d(TAG, "upload result="+result);
		if (result.equals("SUCC")  == true) {
			String downloadUrl = mUploadUrl.substring(0, mUploadUrl.lastIndexOf("/")+1) + mFileName;
			String title = String.format("%s %s", 
					MainApplication.getInstance().getNickName(), 
					DateUtil.formatTime(DateUtil.getNowDateTime("")));
			if (mType == TYPE_PHOTO) {
				showMedia(ClientThread.RECVPHOTO, Build.SERIAL, title, mFilePath);
				MainApplication.getInstance().sendAction(ClientThread.SENDPHOTO, mOtherId, downloadUrl);
			} else if (mType == TYPE_SOUND) {
				showMedia(ClientThread.RECVSOUND, Build.SERIAL, title, mFilePath);
				MainApplication.getInstance().sendAction(ClientThread.SENDSOUND, mOtherId, downloadUrl);
			}
		} else {
			Toast.makeText(this, "上传失败："+result, Toast.LENGTH_SHORT).show();
		}
	}

	public static class RecvMsgReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent != null) {
				Log.d(TAG, "onReceive");
				String content = intent.getStringExtra(ClientThread.CONTENT);
				if (mContext != null && content != null && content.length() > 0) {
					int pos = content.indexOf(ClientThread.SPLIT_LINE);
					String head = content.substring(0, pos);
					String body = content.substring(pos + 1);
					String[] splitArray = head.split(ClientThread.SPLIT_ITEM);
					String action = splitArray[0];
					if (action.equals(ClientThread.RECVMSG)) {
						String append = String.format("%s %s\n%s",
								splitArray[2], DateUtil.formatTime(splitArray[3]), body);
						appendMsg(splitArray[1], append);
					} else if (action.equals(ClientThread.RECVPHOTO)
							|| action.equals(ClientThread.RECVSOUND)) {
						String title = String.format("%s %s", splitArray[2],
								DateUtil.formatTime(splitArray[3]));
						showMedia(action, splitArray[1], title, body);
					} else {
						String hint = String.format("%s\n%s", splitArray[0], body);
						Toast.makeText(mContext, hint, Toast.LENGTH_SHORT).show();
					}
				}
			}
		}
	}

	private static int mBeginViewId = 0x7F24FFF0;
	private static DownloadManager mDownloadManager;
	private static long mDownloadId = 0;

	private static void showMedia(String action, String deviceId, String title, String url) {
		Log.d(TAG, "showMedia action="+action+", url="+url);
		boolean bLocal = (url.indexOf("http://")<0)?true:false;
		int gravity = deviceId.equals(Build.SERIAL) ? Gravity.RIGHT : Gravity.LEFT;
		gravity = gravity | Gravity.CENTER_VERTICAL;
		int bg_color = deviceId.equals(Build.SERIAL) ? 0xffccccff : 0xffffcccc;
		int type = TYPE_PHOTO;
		LinearLayout ll_append = new LinearLayout(mContext);
		LinearLayout.LayoutParams ll_params = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		ll_params.setMargins(dip_margin, dip_margin, dip_margin, dip_margin);
		ll_append.setLayoutParams(ll_params);
		ll_append.setGravity(gravity);
		ll_append.setOrientation(LinearLayout.VERTICAL);

		LinearLayout ll_content = new LinearLayout(mContext);
		LinearLayout.LayoutParams content_params = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		ll_content.setLayoutParams(content_params);
		ll_content.setBackgroundColor(bg_color);
		ll_content.setGravity(gravity);
		ll_content.setOrientation(LinearLayout.VERTICAL);
		
		TextView tv_title = getTextView(title, gravity);
		ll_content.addView(tv_title);

		RelativeLayout rl_content = new RelativeLayout(mContext);
		rl_content.setLayoutParams(content_params);
		rl_content.setId(mBeginViewId++);
		ImageView iv_append = new ImageView(mContext);
		LinearLayout.LayoutParams iv_params = new LinearLayout.LayoutParams(
				300, LayoutParams.WRAP_CONTENT);
		iv_append.setId(mBeginViewId++);
		iv_append.setLayoutParams(iv_params);
		iv_append.setScaleType(ScaleType.FIT_CENTER);
		if (action.equals(ClientThread.RECVPHOTO)) {
			iv_append.setImageResource(R.drawable.default_photo);
		} else if (action.equals(ClientThread.RECVSOUND)) {
			iv_append.setImageResource(R.drawable.default_sound);
			type = TYPE_SOUND;
		}
		if (bLocal == true && action.equals(ClientThread.RECVPHOTO)) {
			iv_append.setImageURI(Uri.parse(mFilePath));
		}
		rl_content.addView(iv_append);
		TextProgressCircle tpc_progress = new TextProgressCircle(mContext);
		if (bLocal != true) {
			RelativeLayout.LayoutParams tpc_params = new RelativeLayout.LayoutParams(200, 200);
			tpc_params.addRule(RelativeLayout.CENTER_IN_PARENT, rl_content.getId());
			tpc_progress.setId(mBeginViewId++);
			tpc_progress.setLayoutParams(tpc_params);
			tpc_progress.setBackgroundColor(0x99ffffff);
			tpc_progress.setVisibility(View.GONE);
			rl_content.addView(tpc_progress);
		}
		ll_content.addView(rl_content);

		TextView tv_detail = getTextView("", gravity);
		tv_detail.setId(mBeginViewId++);
		ll_content.addView(tv_detail);

		ll_append.addView(ll_content);
		ll_show.addView(ll_append);

		mHandler.postDelayed(mScroll, 100);
		if (bLocal != true) {
			downloadFile(url);
			RefreshRunnable refresh = new RefreshRunnable(new int[] { 
					type, rl_content.getId(), iv_append.getId(), tpc_progress.getId(), tv_detail.getId() });
			mHandler.postDelayed(refresh, 100);
		}
	}
	
	private static void downloadFile(String url) {
		String filename = url.substring(url.lastIndexOf("/") + 1);
		Uri uri = Uri.parse(url);
		Request down = new Request(uri);
		down.setAllowedNetworkTypes(Request.NETWORK_MOBILE | Request.NETWORK_WIFI);
		down.setNotificationVisibility(Request.VISIBILITY_HIDDEN);
		down.setVisibleInDownloadsUi(false);
		down.setDestinationInExternalFilesDir(mContext, Environment.DIRECTORY_DCIM, filename);
		mDownloadId = mDownloadManager.enqueue(down);
	}

	private static Handler mHandler = new Handler();

	private static class RefreshRunnable implements Runnable {
		private String mMediaPath;
		private int mType;
		private RelativeLayout rl_content;
		private ImageView iv_append;
		private TextProgressCircle tpc_progress;
		private TextView tv_detail;

		public RefreshRunnable(int[] resIds) {
			mType = resIds[0];
			rl_content = (RelativeLayout) mActivity.findViewById(resIds[1]);
			iv_append = (ImageView) mActivity.findViewById(resIds[2]);
			tpc_progress = (TextProgressCircle) mActivity.findViewById(resIds[3]);
			tv_detail = (TextView) mActivity.findViewById(resIds[4]);
		}

		@Override
		public void run() {
			boolean bFinish = false;
			Query down_query = new Query();
			down_query.setFilterById(mDownloadId);
			Cursor cursor = mDownloadManager.query(down_query);
			if (cursor.moveToFirst()) {
				for (;; cursor.moveToNext()) {
					int nameIdx = cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME);
					int totalSizeIdx = cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES);
					int nowSizeIdx = cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR);
					int progress = (int) (100 * cursor.getLong(nowSizeIdx) / cursor.getLong(totalSizeIdx));
					if (cursor.getString(nameIdx) == null) {
						break;
					}
					tpc_progress.setVisibility(View.VISIBLE);
					tpc_progress.setProgress(progress, 30);
					mMediaPath = cursor.getString(nameIdx);
					if (progress == 100) {
						bFinish = true;
					}
					if (cursor.isLast() == true) {
						break;
					}
				}
			}
			cursor.close();
			if (bFinish != true) {
				mHandler.postDelayed(this, 100);
			} else {
				mHandler.postDelayed(mScroll, 100);
				tpc_progress.setVisibility(View.GONE);
				if (mType == TYPE_PHOTO) {
					iv_append.setImageURI(Uri.parse(mMediaPath));
					tv_detail.setText(Utils.getFileSize(mMediaPath));
				} else if (mType == TYPE_SOUND) {
					final MediaPlayer player = new MediaPlayer();
					player.setAudioStreamType(AudioManager.STREAM_MUSIC);
					try {
						player.setDataSource(mMediaPath);
						player.prepare();
						tv_detail.setText((player.getDuration() / 1000) + "秒");
						rl_content.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								player.start();
							}
						});
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

}
