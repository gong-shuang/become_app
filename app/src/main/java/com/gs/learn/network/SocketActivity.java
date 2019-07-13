package com.gs.learn.network;

import com.gs.learn.network.thread.MessageTransmit;
import com.gs.learn.network.util.DateUtil;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/11/11.
 */
public class SocketActivity extends AppCompatActivity implements OnClickListener {
	private final static String TAG = "SocketActivity";
	private EditText et_socket;
	private static TextView tv_socket;
	private MessageTransmit mTransmit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_socket);
		et_socket = (EditText) findViewById(R.id.et_socket);
		tv_socket = (TextView) findViewById(R.id.tv_socket);
		findViewById(R.id.btn_socket).setOnClickListener(this);
		mTransmit = new MessageTransmit();
		new Thread(mTransmit).start();
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_socket) {
			Message msg = Message.obtain();
			msg.obj = et_socket.getText().toString();
			mTransmit.mRecvHandler.sendMessage(msg);
		}
	}

	public static Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Log.d(TAG, "handleMessage: "+msg.obj);
			if (tv_socket != null) {
				String desc = String.format("%s 收到服务器的应答消息：%s", 
						DateUtil.getNowTime(), msg.obj.toString());
				tv_socket.setText(desc);
			}
		}
	};
	
}
