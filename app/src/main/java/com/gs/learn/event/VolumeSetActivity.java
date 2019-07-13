package com.gs.learn.event;

import com.gs.learn.event.widget.VolumeDialog;
import com.gs.learn.event.widget.VolumeDialog.VolumeAdjustListener;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.TextView;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/11/23.
 */
public class VolumeSetActivity extends AppCompatActivity implements VolumeAdjustListener {
	private TextView tv_volume;
	private VolumeDialog dialog;
	private AudioManager mAudioMgr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_volume_set);
		tv_volume = (TextView) findViewById(R.id.tv_volume);
		mAudioMgr = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_VOLUME_UP && event.getAction()==KeyEvent.ACTION_DOWN) {
			showVolumeDialog(AudioManager.ADJUST_RAISE);
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN && event.getAction()==KeyEvent.ACTION_DOWN) {
			showVolumeDialog(AudioManager.ADJUST_LOWER);
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			return false;
		} else {
			return false;
		}
	}
	
	private void showVolumeDialog(int direction) {
		if (dialog==null || dialog.isShowing()!=true) {
			dialog = new VolumeDialog(this);
			dialog.setVolumeAdjustListener(this);
			dialog.show();
		}
		dialog.adjustVolume(direction, true);
		onVolumeAdjust(mAudioMgr.getStreamVolume(AudioManager.STREAM_MUSIC));
	}

	@Override
	public void onVolumeAdjust(int volume) {
		tv_volume.setText("调节后的音乐音量大小为："+volume);
	}
	
}
