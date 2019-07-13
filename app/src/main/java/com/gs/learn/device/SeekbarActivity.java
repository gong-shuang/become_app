package com.gs.learn.device;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import com.gs.learn.R;
/**
 * Created by ouyangshen on 2016/11/4.
 */
public class SeekbarActivity extends AppCompatActivity implements OnSeekBarChangeListener {
	private SeekBar sb_progress;
	private TextView tv_progress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_seekbar);
		tv_progress = (TextView) findViewById(R.id.tv_progress);
		sb_progress = (SeekBar) findViewById(R.id.sb_progress);
		sb_progress.setOnSeekBarChangeListener(this);
		sb_progress.setProgress(50);
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		String desc = "当前进度为："+seekBar.getProgress()+", 最大进度为"+seekBar.getMax();
		tv_progress.setText(desc);
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
	}
	
}
