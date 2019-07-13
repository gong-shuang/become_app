package com.gs.learn.media.task;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import android.media.AudioRecord;
import android.media.AudioRecord.OnRecordPositionUpdateListener;
import android.media.MediaRecorder.AudioSource;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

public class AudioRecordTask extends AsyncTask<String, Integer, Void> {
	private final static String TAG = "AudioRecordTask";
	private Handler mHandler = new Handler();
	private int mRecordTime = 0;
	
	@Override
	protected Void doInBackground(String... arg0) {
		File recordFile = new File(arg0[0]);
		int frequence = Integer.parseInt(arg0[1]);
		int channelConfig = Integer.parseInt(arg0[2]);
		int audioFormat = Integer.parseInt(arg0[3]);
		try {
			// 开通输出流到指定的文件
			DataOutputStream dos = new DataOutputStream(
					new BufferedOutputStream(new FileOutputStream(recordFile)));
			// 根据定义好的几个配置，来获取合适的缓冲大小
			int bsize = AudioRecord.getMinBufferSize(frequence, channelConfig, audioFormat);
			AudioRecord record = new AudioRecord(AudioSource.MIC, 
					frequence, channelConfig, audioFormat, bsize);
			// 定义缓冲区
			short[] buffer = new short[bsize];
			//record.setNotificationMarkerPosition(1000);
			record.setPositionNotificationPeriod(1000);
			record.setRecordPositionUpdateListener(new RecordUpdateListener());
			record.startRecording();

			while (isCancelled() != true) {
				int bufferReadResult = record.read(buffer, 0, buffer.length);
				// 循环将buffer中的音频数据写入到OutputStream中
				for (int i = 0; i < bufferReadResult; i++) {
					dos.writeShort(buffer[i]);
				}
			}
			record.stop();
			dos.close();
			Log.d(TAG, "file_path="+arg0[0]+", length=" + recordFile.length());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPreExecute() {
		mRecordTime = 0;
		mHandler.postDelayed(mRecordRun, 1000);
	}

	@Override
	protected void onPostExecute(Void result) {
		if (mListener != null) {
			mListener.onRecordFinish();
		}
		mHandler.removeCallbacks(mRecordRun);
	}

	private class RecordUpdateListener implements OnRecordPositionUpdateListener {

		@Override
		public void onMarkerReached(AudioRecord recorder) {
		}

		@Override
		public void onPeriodicNotification(AudioRecord recorder) {
			if (mListener != null) {
				mListener.onRecordUpdate(mRecordTime);
			}
		}
		
	}
	
	private Runnable mRecordRun = new Runnable() {
		@Override
		public void run() {
			mRecordTime++;
			mHandler.postDelayed(this, 1000);
		}
	};

	private OnRecordListener mListener;
	public void setOnRecordListener(OnRecordListener listener) {
		mListener = listener;
	}

	public static interface OnRecordListener {
		public abstract void onRecordFinish();
		public abstract void onRecordUpdate(int duration);
	}

}
