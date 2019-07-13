package com.gs.learn.device;

import com.gs.learn.R;
import com.gs.learn.device.widget.AudioPlayer;
import com.gs.learn.device.widget.AudioRecorder;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

/**
 * Created by ouyangshen on 2016/11/4.
 */
public class AudioActivity extends AppCompatActivity implements AudioRecorder.OnRecordFinishListener {
	private static final String TAG = "AudioActivity";
	private AudioRecorder ar_music;
	private AudioPlayer ap_music;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_audio);

		ar_music = (AudioRecorder) findViewById(R.id.ar_music);
		ar_music.setOnRecordFinishListener(this);
		ap_music = (AudioPlayer) findViewById(R.id.ap_music);
	}

	@Override
	public void onRecordFinish() {
		mHandler.postDelayed(mPreplay, 1000);
	}
	
	private Handler mHandler = new Handler();
	private Runnable mPreplay = new Runnable() {
		@Override
		public void run() {
			ap_music.init(ar_music.getRecordFilePath());
		}
	};
	
}
