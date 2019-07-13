package com.gs.learn.media;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/12/4.
 */
public class ContentObserverActivity extends AppCompatActivity implements OnClickListener {
	private static final String TAG = "ContentObserverActivity";
	private static TextView tv_check_flow;
	private static ProgressDialog pd;
	private static String mCheckResult;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_content_observer);
		tv_check_flow = (TextView) findViewById(R.id.tv_check_flow);
		tv_check_flow.setOnClickListener(this);
		findViewById(R.id.btn_check_flow).setOnClickListener(this);
		initSmsObserver();
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_check_flow) {
			pd = ProgressDialog.show(this, "请稍候", "正在进行流量校准");
			//查询数据流量，移动号码的查询方式为发送短信内容“18”给“10086”
			//电信和联通号码的短信查询方式请咨询当地运营商客服热线
			//sendSmsManual("10086", "18"); 
			sendSmsAuto("10086", "18");
		} else if (v.getId() == R.id.tv_check_flow) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("收到流量校准短信");
			builder.setMessage(mCheckResult);
			builder.setPositiveButton("确定", null);
			builder.create().show();
		}
	}

	public void sendSmsManual(String phoneNumber, String message) {
		Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phoneNumber));
		intent.putExtra("sms_body", message);
		startActivity(intent);
	}

	//短信发送广播，如需处理可注册该事件的BroadcastReceiver
	private String SENT_SMS_ACTION = "com.gs.learn.media.SENT_SMS_ACTION";
	//短信接收广播，如需处理可注册该事件的BroadcastReceiver
	private String DELIVERED_SMS_ACTION = "com.gs.learn.media.DELIVERED_SMS_ACTION";
	public void sendSmsAuto(String phoneNumber, String message) {
		Intent sentIntent = new Intent(SENT_SMS_ACTION);
		sentIntent.putExtra("phone", phoneNumber);
		sentIntent.putExtra("message", message);
		PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, sentIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		
		Intent deliverIntent = new Intent(DELIVERED_SMS_ACTION);
		deliverIntent.putExtra("phone", phoneNumber);
		deliverIntent.putExtra("message", message);
		PendingIntent deliverPI = PendingIntent.getBroadcast(this, 1, deliverIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		
		SmsManager smsManager = SmsManager.getDefault();
		//要确保打开发送短信的完全权限，不是那种还需提示的不完整权限
		smsManager.sendTextMessage(phoneNumber, null, message, sentPI, deliverPI);
	}

	private Handler mHandler = new Handler();
	private SmsGetObserver mObserver;
	private static Uri mSmsUri;
	private static String[] mSmsColumn;
	
	@TargetApi(Build.VERSION_CODES.KITKAT)
	private void initSmsObserver() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			mSmsUri = Telephony.Sms.Inbox.CONTENT_URI;
			mSmsColumn = new String[] { 
					Telephony.Sms.ADDRESS, Telephony.Sms.BODY, Telephony.Sms.DATE };
		} else {
			mSmsUri = Uri.parse("content://sms/inbox");
			mSmsColumn = new String[] { "address", "body", "date" };
		}
		//Android5.0之后似乎无法单独观察某个信箱，只能监控整个短信
		mSmsUri = Uri.parse("content://sms");
		mObserver = new SmsGetObserver(this, mHandler);
		getContentResolver().registerContentObserver(mSmsUri, true, mObserver);
	}
	
	@Override
	protected void onDestroy() {
		getContentResolver().unregisterContentObserver(mObserver);
		super.onDestroy();
	};
	
	private static class SmsGetObserver extends ContentObserver {
		private Context mContext;
		
		public SmsGetObserver(Context context, Handler handler) {
			super(handler);
			mContext = context;
		}

		@Override
		public void onChange(boolean selfChange) {
			String sender = "";
			String content = "";
			String selection = String.format("address='10086' and date>%d", System.currentTimeMillis()-1000*60*60);
			Cursor cursor = mContext.getContentResolver().query(
					mSmsUri, mSmsColumn, selection, null, " date desc");
			while(cursor.moveToNext()){
				sender = cursor.getString(0);
				content = cursor.getString(1);
				break;
			}
			cursor.close();
			if (pd != null && pd.isShowing() == true) {
				pd.dismiss();
			}
			//回调短信监听方法
			mCheckResult = String.format("发送号码：%s\n短信内容：%s", sender, content);
			Log.d(TAG, "result="+mCheckResult);
			String flow = String.format("流量校准结果如下：\n\t总流量为：%s\n\t已使用：%s\n\t剩余：%s", 
					findFlow(content, "总流量为", "MB"), 
					findFlow(content, "已使用", "MB"), findFlow(content, "剩余", "MB"));
			tv_check_flow.setText(flow);
	        
			super.onChange(selfChange);
		}
	}
	
	private static String findFlow(String sms, String begin, String end) {
		int begin_pos = sms.indexOf(begin);
		if (begin_pos < 0) {
			return "未获取";
		}
		String sub_sms = sms.substring(begin_pos);
		int end_pos = sub_sms.indexOf(end);
		if (end_pos < 0) {
			return "未获取";
		}
		String flow_desc = sub_sms.substring(begin.length(), end_pos+end.length());
		return flow_desc;
	}

}
