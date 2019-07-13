package com.gs.learn.network.thread;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import com.gs.learn.network.SocketActivity;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class MessageTransmit implements Runnable {
	private static final String TAG = "MessageTransmit";
	// 以下为Socket服务器的ip和端口，根据实际情况修改
	private static final String SOCKET_IP = "192.168.0.212";
	private static final int SOCKET_PORT = 51000;

	private Socket mSocket;
	private BufferedReader mReader = null;
	private OutputStream mWriter = null;

	@Override
	public void run() {
		mSocket = new Socket();
		try {
			mSocket.connect(new InetSocketAddress(SOCKET_IP, SOCKET_PORT), 3000);
			mReader = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
			mWriter = mSocket.getOutputStream();
			// 启动一条子线程来读取服务器的返回数据
			new RecvThread().start();
			Looper.prepare();
			Looper.loop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 定义接收UI线程的Handler对象，App向后台服务器发送消息
	public Handler mRecvHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Log.d(TAG, "handleMessage: "+msg.obj);
			// 换行符相当于回车键，表示我写好了发出去吧
			String send_msg = msg.obj.toString()+"\n";
			try {
				mWriter.write(send_msg.getBytes("utf8"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	// 定义消息接收子线程，App从后台服务器接收消息
	private class RecvThread extends Thread {
		@Override
		public void run() {
			try {
				String content = null;
				while ((content = mReader.readLine()) != null) {
					// 读取到来自服务器的数据
					Message msg = Message.obtain();
					msg.obj = content;
					SocketActivity.mHandler.sendMessage(msg);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
