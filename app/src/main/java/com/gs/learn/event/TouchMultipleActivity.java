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
public class TouchMultipleActivity extends AppCompatActivity {
	private TextView tv_touch_main;
	private TextView tv_touch_secondary;
	private boolean bSecondaryPressed = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_touch_multiple);
		tv_touch_main = (TextView) findViewById(R.id.tv_touch_main);
		tv_touch_secondary = (TextView) findViewById(R.id.tv_touch_secondary);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int seconds = (int) (event.getEventTime() / 1000);
		int hour = seconds / 3600;
		int minute = seconds % 3600 / 60;
		int second = seconds % 60;
		Date date = new Date(event.getEventTime());
		String desc_main = String.format("主要动作发生时间：开机距离现在%02d:%02d:%02d\n%s", 
				hour, minute, second, "主要动作名称是：");
		String desc_secondary = "";
		int action = event.getAction() & MotionEvent.ACTION_MASK;
		if (action == event.ACTION_DOWN) {
			desc_main = String.format("%s按下", desc_main);
		} else if (action == event.ACTION_MOVE) {
			desc_main = String.format("%s移动", desc_main);
			if (bSecondaryPressed == true) {
				desc_secondary = String.format("%s次要动作名称是：移动", desc_secondary);
			}
		} else if (action == event.ACTION_UP) {
			desc_main = String.format("%s提起", desc_main);
		} else if (action == event.ACTION_CANCEL) {
			desc_main = String.format("%s取消", desc_main);
		} else if (action == event.ACTION_POINTER_DOWN) {
			bSecondaryPressed = true;
			desc_secondary = String.format("%s次要动作名称是：按下", desc_secondary);
		} else if (action == event.ACTION_POINTER_UP) {
			bSecondaryPressed = false;
			desc_secondary = String.format("%s次要动作名称是：提起", desc_secondary);
		}
		desc_main = String.format("%s\n主要动作发生位置是：横坐标%f，纵坐标%f", 
				desc_main, event.getX(), event.getY());
		tv_touch_main.setText(desc_main);
		if (bSecondaryPressed==true || desc_secondary.length()>0) {
			desc_secondary = String.format("%s\n次要动作发生位置是：横坐标%f，纵坐标%f", 
					desc_secondary, event.getX(1), event.getY(1));
			tv_touch_secondary.setText(desc_secondary);
		}
		return super.onTouchEvent(event);
	}

}
