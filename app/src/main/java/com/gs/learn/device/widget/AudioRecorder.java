package com.gs.learn.device.widget;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.media.MediaRecorder;
import android.media.MediaRecorder.AudioEncoder;
import android.media.MediaRecorder.AudioSource;
import android.media.MediaRecorder.OnErrorListener;
import android.media.MediaRecorder.OnInfoListener;
import android.media.MediaRecorder.OutputFormat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.gs.learn.R;
import com.gs.learn.device.util.MediaUtil;

public class AudioRecorder extends LinearLayout implements OnErrorListener,
		OnInfoListener, OnCheckedChangeListener {
	private static final String TAG = "AudioRecorder";
	private MediaRecorder mMediaRecorder;

	private ProgressBar pb_record;
	private CheckBox ck_record;
	private Timer mTimer; // 计时器
	private int mRecordMaxTime = 10; // 一次录音最长时间
	private int mTimeCount; // 时间计数
	private String mRecordFilePath;

	public AudioRecorder(Context context) {
		this(context, null);
	}

	public AudioRecorder(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public AudioRecorder(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		LayoutInflater.from(context).inflate(R.layout.audio_recorder, this);
		pb_record = (ProgressBar) findViewById(R.id.pb_record);
		pb_record.setMax(mRecordMaxTime);
		ck_record = (CheckBox) findViewById(R.id.ck_record);
		ck_record.setOnCheckedChangeListener(this);
	}

	// 开始录音
	public void start() {
		mRecordFilePath = MediaUtil.getRecordFilePath("RecordAudio", ".amr");
		try {
			initRecord();
			mTimeCount = 0;
			mTimer = new Timer();
			mTimer.schedule(new TimerTask() {
				@Override
				public void run() {
					pb_record.setProgress(mTimeCount++);
				}
			}, 0, 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 停止录音
	public void stop() {
		if (mOnRecordFinishListener != null) {
			mOnRecordFinishListener.onRecordFinish();
		}
		stopRecord();
		releaseRecord();
	}

	public String getRecordFilePath() {
		return mRecordFilePath;
	}

	private void initRecord() {
		mMediaRecorder = new MediaRecorder();
		mMediaRecorder.setOnErrorListener(this);
		mMediaRecorder.setOnInfoListener(this);
		mMediaRecorder.setAudioSource(AudioSource.MIC); // 音频源
		mMediaRecorder.setOutputFormat(OutputFormat.AMR_NB);
		mMediaRecorder.setAudioEncoder(AudioEncoder.AMR_NB); // 音频格式
		// mMediaRecorder.setAudioSamplingRate(8); //音频的采样率。可选
		// mMediaRecorder.setAudioChannels(2); //音频的声道数。可选
		// mMediaRecorder.setAudioEncodingBitRate(1024); //音频每秒录制的字节数。可选
		mMediaRecorder.setMaxDuration(10 * 1000); // 设置录制时长
		// mMediaRecorder.setMaxFileSize(1024*1024*10);
		// setMaxFileSize与setMaxDuration设置其一即可
		mMediaRecorder.setOutputFile(mRecordFilePath);
		try {
			mMediaRecorder.prepare();
			mMediaRecorder.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void stopRecord() {
		pb_record.setProgress(0);
		if (mTimer != null) {
			mTimer.cancel();
		}
		if (mMediaRecorder != null) {
			mMediaRecorder.setOnErrorListener(null);
			mMediaRecorder.setPreviewDisplay(null);
			try {
				mMediaRecorder.stop();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void releaseRecord() {
		if (mMediaRecorder != null) {
			mMediaRecorder.setOnErrorListener(null);
			mMediaRecorder.release();
			mMediaRecorder = null;
		}
	}

	private OnRecordFinishListener mOnRecordFinishListener; // 录制完成回调接口

	public interface OnRecordFinishListener {
		public void onRecordFinish();
	}

	public void setOnRecordFinishListener(OnRecordFinishListener listener) {
		mOnRecordFinishListener = listener;
	}

	@Override
	public void onError(MediaRecorder mr, int what, int extra) {
		if (mr != null) {
			mr.reset();
		}
	}

	@Override
	public void onInfo(MediaRecorder mr, int what, int extra) {
		if (what == MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED
				|| what == MediaRecorder.MEDIA_RECORDER_INFO_MAX_FILESIZE_REACHED) {
			ck_record.setChecked(false);
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (buttonView.getId() == R.id.ck_record) {
			if (isChecked == true) {
				ck_record.setText("停止录制");
				start();
			} else {
				ck_record.setText("开始录制");
				stop();
			}
		}
	}

}
