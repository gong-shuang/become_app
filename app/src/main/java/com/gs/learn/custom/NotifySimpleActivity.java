package com.gs.learn.custom;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import com.gs.learn.R;
/**
 * Created by ouyangshen on 2016/10/14.
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class NotifySimpleActivity extends AppCompatActivity implements OnClickListener {

	private EditText et_title;
	private EditText et_message;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notify_simple);
		et_title = (EditText) findViewById(R.id.et_title);
		et_message = (EditText) findViewById(R.id.et_message);
		findViewById(R.id.btn_send_simple).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_send_simple) {
			String title = et_title.getText().toString();
			String message = et_message.getText().toString();
			sendSimpleNotify(title, message);
		}
	}

	private void sendSimpleNotify(String title, String message) {
		Intent clickIntent = new Intent(this, CustomMainActivity.class);
		PendingIntent contentIntent = PendingIntent.getActivity(this,
				R.string.app_name, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);

		Notification.Builder builder = new Notification.Builder(this);
		builder.setContentIntent(contentIntent)
				.setAutoCancel(true)
				.setSmallIcon(R.drawable.ic_app)
				//.setSubText("这里是副本")
				.setTicker("提示消息来啦")
				.setWhen(System.currentTimeMillis())
				.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_app))
				.setContentTitle(title)
				.setContentText(message);
		Notification notify = builder.build();

		NotificationManager notifyMgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		notifyMgr.notify(R.string.app_name, notify);
	}

}
