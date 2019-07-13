package com.gs.learn.media.task;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;

import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.AudioTrack.OnPlaybackPositionUpdateListener;
import android.os.AsyncTask;
import android.os.Handler;

public class AudioPlayTask extends AsyncTask<String, Integer, Void> {
	private final static String TAG = "AudioPlayTask";
	private Handler mHandler = new Handler();
	private int mPlayTime = 0;
	
	@Override
	protected Void doInBackground(String... arg0) {
		File recordFile = new File(arg0[0]);
		int frequence = Integer.parseInt(arg0[1]);
		int channelConfig = Integer.parseInt(arg0[2]);
		int audioFormat = Integer.parseInt(arg0[3]);
		try {
			// 定义输入流，将音频写入到AudioTrack类中，实现播放
			DataInputStream dis = new DataInputStream(
					new BufferedInputStream(new FileInputStream(recordFile)));
			int bsize = AudioTrack.getMinBufferSize(frequence, channelConfig, audioFormat);
			short[] buffer = new short[bsize / 4];
			AudioTrack track = new AudioTrack(AudioManager.STREAM_MUSIC,
					frequence, channelConfig, audioFormat, bsize, AudioTrack.MODE_STREAM);
			//track.setNotificationMarkerPosition(1000);
			track.setPositionNotificationPeriod(1000);
			track.setPlaybackPositionUpdateListener(new PlaybackUpdateListener());
			track.play();
			// 由于AudioTrack播放的是流，所以，我们需要一边播放一边读取
			while (isCancelled() != true && dis.available() > 0) {
				int i = 0;
				while (dis.available() > 0 && i < buffer.length) {
					buffer[i] = dis.readShort();
					i++;
				}
				// 然后将数据写入到AudioTrack中
				track.write(buffer, 0, buffer.length);
			}
			track.stop();
			dis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPreExecute() {
		mPlayTime = 0;
		mHandler.postDelayed(mPlayRun, 1000);
	}

	@Override
	protected void onPostExecute(Void result) {
		if (mListener != null) {
			mListener.onPlayFinish();
		}
		mHandler.removeCallbacks(mPlayRun);
	}

	private class PlaybackUpdateListener implements OnPlaybackPositionUpdateListener {

		@Override
		public void onMarkerReached(AudioTrack track) {
		}

		@Override
		public void onPeriodicNotification(AudioTrack track) {
			if (mListener != null) {
				mListener.onPlayUpdate(mPlayTime);
			}
		}

	}

	private Runnable mPlayRun = new Runnable() {
		@Override
		public void run() {
			mPlayTime++;
			mHandler.postDelayed(this, 1000);
		}
	};

	private OnPlayListener mListener;
	public void setOnPlayListener(OnPlayListener listener) {
		mListener = listener;
	}

	public static interface OnPlayListener {
		public abstract void onPlayFinish();
		public abstract void onPlayUpdate(int duration);
	}

}
