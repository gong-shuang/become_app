package com.gs.learn.device;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.widget.Toast;

import com.gs.learn.common.zxing.camera.CameraManager;
import com.gs.learn.common.zxing.decoding.CaptureActivityHandler;
import com.gs.learn.common.zxing.decoding.InactivityTimer;
import com.gs.learn.common.zxing.view.ViewfinderView;
import com.google.zxing.Result;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/11/4.
 */
public class FindScanActivity extends Activity implements SurfaceHolder.Callback {
	private final static String TAG = "FindScanActivity";
	private CaptureActivityHandler mHandler;
	private ViewfinderView vv_finder;
	private boolean hasSurface = false;
	private InactivityTimer mTimer;
	private MediaPlayer mPlayer;
	private boolean bBeep;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_find_scan);
		CameraManager.init(getApplication(), CameraManager.QR_CODE);
		vv_finder = (ViewfinderView) findViewById(R.id.vv_finder);
		mTimer = new InactivityTimer(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		SurfaceView sv_scan = (SurfaceView) findViewById(R.id.sv_scan);
		SurfaceHolder surfaceHolder = sv_scan.getHolder();
		if (hasSurface) {
			initCamera(surfaceHolder);
		} else {
			surfaceHolder.addCallback(this);
		}
		bBeep = true;
		AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
		if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
			bBeep = false;
		}
		initBeepSound();
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (mHandler != null) {
			mHandler.quitSynchronously();
			mHandler = null;
		}
		CameraManager.get().closeDriver();
	}

	@Override
	protected void onDestroy() {
		mTimer.shutdown();
		super.onDestroy();
	}
	
	public void handleDecode(Result result, Bitmap barcode) {
		mTimer.onActivity();
		beepAndVibrate();
		String resultString = result.getText();
		if (resultString==null || resultString.length()<=0) {
			Toast.makeText(this, "Scan failed or result is null", Toast.LENGTH_SHORT).show();
		} else {
			String desc = String.format("barcode width=%d,height=%d",
					barcode.getWidth(), barcode.getHeight());
			Toast.makeText(this, desc, Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(this, ScanResultActivity.class);
			intent.putExtra("result", resultString);
			startActivity(intent);
		}
	}

	private void initCamera(SurfaceHolder surfaceHolder) {
		try {
			CameraManager.get().openDriver(surfaceHolder);
			if (mHandler == null) {
				mHandler = new CaptureActivityHandler(this, null, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;
	}

	public ViewfinderView getViewfinderView() {
		return vv_finder;
	}

	public Handler getHandler() {
		return mHandler;
	}

	public void drawViewfinder() {
		vv_finder.drawViewfinder();
	}

	private void initBeepSound() {
		if (bBeep && mPlayer == null) {
			setVolumeControlStream(AudioManager.STREAM_MUSIC);
			mPlayer = new MediaPlayer();
			mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mPlayer.setOnCompletionListener(beepListener);
			AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.beep);
			try {
				mPlayer.setDataSource(file.getFileDescriptor(),
						file.getStartOffset(), file.getLength());
				file.close();
				mPlayer.setVolume(0.1f, 0.1f);
				mPlayer.prepare();
			} catch (IOException e) {
				e.printStackTrace();
				mPlayer = null;
			}
		}
	}

	private static final long VIBRATE_DURATION = 200L;

	private void beepAndVibrate() {
		if (bBeep && mPlayer != null) {
			mPlayer.start();
		}
		Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
		vibrator.vibrate(VIBRATE_DURATION);
	}

	private final OnCompletionListener beepListener = new OnCompletionListener() {
		public void onCompletion(MediaPlayer mPlayer) {
			mPlayer.seekTo(0);
		}
	};

}