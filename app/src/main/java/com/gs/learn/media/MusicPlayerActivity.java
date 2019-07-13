package com.gs.learn.media;

import java.util.Map;

import com.aqi00.lib.dialog.FileSelectFragment;
import com.aqi00.lib.dialog.FileSelectFragment.FileSelectCallbacks;
import com.gs.learn.MainApplication;
import com.gs.learn.media.adapter.MusicListAdapter;
import com.gs.learn.media.bean.MusicInfo;
import com.gs.learn.media.util.AlbumLoader;
import com.gs.learn.media.widget.AudioController;
import com.gs.learn.media.widget.VolumeDialog;
import com.gs.learn.media.widget.VolumeDialog.VolumeAdjustListener;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/12/4.
 */
public class MusicPlayerActivity extends AppCompatActivity implements 
		OnClickListener, FileSelectCallbacks, VolumeAdjustListener {
	private static final String TAG = "MusicPlayerActivity";
	private ListView lv_music;
	private TextView tv_song;
	private AudioController ac_play;
	private AudioManager mAudioMgr;
	private VolumeDialog dialog;
	private MainApplication app;
	private Handler mHandler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_music_player);
		findViewById(R.id.btn_open).setOnClickListener(this);
		lv_music = (ListView) findViewById(R.id.lv_music);
		tv_song = (TextView) findViewById(R.id.tv_song);
		initList();
		ac_play = (AudioController) findViewById(R.id.ac_play);
		mAudioMgr = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		app = MainApplication.getInstance();
	}
	
	private void initList() {
		AlbumLoader loader = AlbumLoader.getInstance(getContentResolver());
		MusicListAdapter adapter = new MusicListAdapter(this, loader.getMusicList());
		lv_music.setAdapter(adapter);
		lv_music.setOnItemClickListener(adapter);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		initController();
	}

	@Override
	protected void onStop() {
		super.onStop();
		mHandler.removeCallbacksAndMessages(null);
	}

	private void initController() {
		if (app.mSong != null) {
			tv_song.setText(app.mSong+"正在播放");
		} else {
			tv_song.setText("当前暂无歌曲播放");
		}
		mHandler.postDelayed(mRefreshCtrl, 100);
	}

	//刷新进度条
	private Runnable mRefreshCtrl = new Runnable() {
		@Override
		public void run() {
			ac_play.setCurrentTime(app.mMediaPlayer.getCurrentPosition(), 0);
			if (app.mMediaPlayer.getCurrentPosition() >= app.mMediaPlayer.getDuration()) {
				ac_play.setCurrentTime(0, 0);
			}
			mHandler.postDelayed(this, 500);
		}
	};

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_open) {
			String[] audioExs = new String[]{"mp3", "wav", "mid", "ogg", "amr", "acc", "pcm"};
			FileSelectFragment.show(this, audioExs, null);
		}
	}

	@Override
	public void onConfirmSelect(String absolutePath, String fileName, Map<String, Object> map_param) {
		Log.d(TAG, "onConfirmSelect absolutePath=" + absolutePath + ". fileName=" + fileName);
		String file_path = "";
		if (absolutePath != null && fileName != null) {
			file_path = absolutePath + "/" + fileName;
		}
		MusicInfo music = new MusicInfo();
		music.setTitle(fileName);
		music.setArtist("未知");
		music.setUrl(file_path);
		Intent intent = new Intent(this, MusicDetailActivity.class);
		intent.putExtra("music", music);
		startActivity(intent);
		startActivity(intent);
	}

	@Override
	public boolean isFileValid(String absolutePath, String fileName, Map<String, Object> map_param) {
		return true;
	}

	//音量调节对话框
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
			showVolumeDialog(AudioManager.ADJUST_RAISE);
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
			showVolumeDialog(AudioManager.ADJUST_LOWER);
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
		}
		return false;
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
	}

}
