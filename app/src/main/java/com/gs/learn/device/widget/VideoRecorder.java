package com.gs.learn.device.widget;

import java.util.Timer;
import java.util.TimerTask;

import com.gs.learn.R;
import com.gs.learn.device.util.MediaUtil;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.media.MediaRecorder;
import android.media.MediaRecorder.AudioEncoder;
import android.media.MediaRecorder.AudioSource;
import android.media.MediaRecorder.OnErrorListener;
import android.media.MediaRecorder.OnInfoListener;
import android.media.MediaRecorder.OutputFormat;
import android.media.MediaRecorder.VideoEncoder;
import android.media.MediaRecorder.VideoSource;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

public class VideoRecorder extends LinearLayout implements 
		OnErrorListener, OnInfoListener, OnCheckedChangeListener {
	private static final String TAG = "VideoRecorder";
	private SurfaceHolder mHolder;
	private Camera mCamera;
	private MediaRecorder mMediaRecorder;

	private SurfaceView sv_record;
	private ProgressBar pb_record;
	private CheckBox ck_record;
	private Timer mTimer; // 计时器
	private int mRecordMaxTime = 10; // 一次拍摄最长时间
	private int mTimeCount; // 时间计数
	private String mRecordFilePath;

	public VideoRecorder(Context context) {
		this(context, null);
	}

	public VideoRecorder(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public VideoRecorder(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		LayoutInflater.from(context).inflate(R.layout.video_recorder, this);
		sv_record = (SurfaceView) findViewById(R.id.sv_record);
		pb_record = (ProgressBar) findViewById(R.id.pb_record);
		pb_record.setMax(mRecordMaxTime);
		ck_record = (CheckBox) findViewById(R.id.ck_record);
		ck_record.setOnCheckedChangeListener(this);
		mHolder = sv_record.getHolder();
		mHolder.addCallback(mSurfaceCallback);
	}

	// 开始录像
	public void start() {
		mRecordFilePath = MediaUtil.getRecordFilePath("RecordVideo", ".mp4");
		try {
			initCamera();
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

	// 停止录像
	public void stop() {
		if (mOnRecordFinishListener != null) {
			mOnRecordFinishListener.onRecordFinish();
		}
		stopRecord();
		releaseRecord();
		freeCamera();
	}

	public String getRecordFilePath() {
		return mRecordFilePath;
	}

	private SurfaceHolder.Callback mSurfaceCallback = new SurfaceHolder.Callback() {
		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			initCamera();
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			freeCamera();
		}
	};

	private void initCamera() {
		if (mCamera != null) {
			freeCamera();
		}
		try {
			mCamera = Camera.open();
			mCamera.setDisplayOrientation(90);
			mCamera.setPreviewDisplay(mHolder);
			mCamera.startPreview();
			mCamera.unlock();
		} catch (Exception e) {
			e.printStackTrace();
			freeCamera();
		}
	}

	private void freeCamera() {
		if (mCamera != null) {
			mCamera.stopPreview();
			mCamera.lock();
			mCamera.release();
			mCamera = null;
		}
	}

	private void initRecord() {
		mMediaRecorder = new MediaRecorder();
		mMediaRecorder.setCamera(mCamera);
		mMediaRecorder.setOnErrorListener(this);
		mMediaRecorder.setOnInfoListener(this);
		mMediaRecorder.setPreviewDisplay(mHolder.getSurface());
		mMediaRecorder.setVideoSource(VideoSource.CAMERA); // 视频源
		mMediaRecorder.setAudioSource(AudioSource.MIC); // 音频源
		mMediaRecorder.setOutputFormat(OutputFormat.MPEG_4); // 视频输出格式
		mMediaRecorder.setAudioEncoder(AudioEncoder.AMR_NB); // 音频格式
		// 如果录像报错：MediaRecorder start failed: -19
		// 试试把setVideoSize和setVideoFrameRate注释掉，因为尺寸设置必须为摄像头所支持，否则报错
		// mMediaRecorder.setVideoSize(mWidth, mHeight); // 设置分辨率：
		// mMediaRecorder.setVideoFrameRate(16);
		// setVideoFrameRate与setVideoEncodingBitRate设置其一即可
		mMediaRecorder.setVideoEncodingBitRate(1 * 1024 * 512); // 设置帧频率
		mMediaRecorder.setOrientationHint(90); // 输出旋转90度，保持竖屏录制
		mMediaRecorder.setVideoEncoder(VideoEncoder.MPEG_4_SP); // 视频录制格式
		mMediaRecorder.setMaxDuration(mRecordMaxTime * 1000); // 设置录制时长
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
