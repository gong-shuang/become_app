package com.gs.learn.event;

import java.util.Date;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.widget.TextView;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/11/23.
 */
public class TouchSingleActivity extends AppCompatActivity {
	private TextView tv_touch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_touch_single);
		tv_touch = (TextView) findViewById(R.id.tv_touch);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// 从开机到现在的毫秒数
		int seconds = (int) (event.getEventTime() / 1000);
		int hour = seconds / 3600;
		int minute = seconds % 3600 / 60;
		int second = seconds % 60;
		Date date = new Date(event.getEventTime());
		String desc = String.format("动作发生时间：开机距离现在%02d:%02d:%02d", 
				hour, minute, second);
		desc = String.format("%s\n动作名称是：", desc);
		int action = event.getAction();
		if (action == event.ACTION_DOWN) {
			desc = String.format("%s按下", desc);
		} else if (action == event.ACTION_MOVE) {
			desc = String.format("%s移动", desc);
		} else if (action == event.ACTION_UP) {
			desc = String.format("%s提起", desc);
		} else if (action == event.ACTION_CANCEL) {
			desc = String.format("%s取消", desc);
		}
		desc = String.format("%s\n动作发生位置是：横坐标%f，纵坐标%f", 
				desc, event.getX(), event.getY());
		tv_touch.setText(desc);
		return super.onTouchEvent(event);
	}

}
