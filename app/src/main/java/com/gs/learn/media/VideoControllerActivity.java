package com.gs.learn.media;

import java.util.Map;

import com.aqi00.lib.dialog.FileSelectFragment;
import com.aqi00.lib.dialog.FileSelectFragment.FileSelectCallbacks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.MediaController;
import android.widget.VideoView;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/12/4.
 */
public class VideoControllerActivity extends AppCompatActivity implements
		OnClickListener, FileSelectCallbacks {
	private static final String TAG = "VideoControllerActivity";
	private VideoView vv_play;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video_controller);
		findViewById(R.id.btn_open).setOnClickListener(this);
		vv_play = (VideoView) findViewById(R.id.vv_play);
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
		vv_play.setVideoPath(file_path);
		vv_play.requestFocus();
		//媒体控制条代码开始
		MediaController mc_play = new MediaController(this);
		vv_play.setMediaController(mc_play);
		mc_play.setMediaPlayer(vv_play);
		//媒体控制条代码结束
		vv_play.start();
	}

	@Override
	public boolean isFileValid(String absolutePath, String fileName, Map<String, Object> map_param) {
		return true;
	}

}
