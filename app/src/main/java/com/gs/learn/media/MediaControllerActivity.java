package com.gs.learn.media;

import java.util.Map;

import com.aqi00.lib.dialog.FileSelectFragment;
import com.aqi00.lib.dialog.FileSelectFragment.FileSelectCallbacks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/12/4.
 */
public class MediaControllerActivity extends AppCompatActivity implements
		OnClickListener, FileSelectCallbacks {
	private static final String TAG = "MediaControllerActivity";
	private LinearLayout ll_play;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_media_controller);
		findViewById(R.id.btn_open).setOnClickListener(this);
		ll_play = (LinearLayout) findViewById(R.id.ll_play);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_open) {
			FileSelectFragment.show(this, new String[] { "mp4" }, null);
		}
	}

	@Override
	public void onConfirmSelect(String absolutePath, String fileName, Map<String, Object> map_param) {
		String file_path = absolutePath + "/" + fileName;
		Log.d(TAG, "file_path=" + file_path);
		VideoView vv_play = new VideoView(this);
		vv_play.setVideoPath(file_path);
		vv_play.requestFocus();
		MediaController mc_play = new MediaController(this);
		mc_play.setAnchorView(vv_play);
		mc_play.setKeepScreenOn(true);
		vv_play.setMediaController(mc_play);
		ll_play.addView(vv_play);
		vv_play.start();
	}

	@Override
	public boolean isFileValid(String absolutePath, String fileName, Map<String, Object> map_param) {
		return true;
	}

}
