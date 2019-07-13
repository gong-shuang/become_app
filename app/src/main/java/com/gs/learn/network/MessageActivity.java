package com.gs.learn.network;

import com.gs.learn.network.util.DateUtil;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/11/11.
 */
public class MessageActivity extends AppCompatActivity implements OnClickListener {
	
	private TextView tv_message;
	private boolean bPlay = false;
	private int BEGIN = 0, SCROLL = 1, END = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);
		tv_message = (TextView) findViewById(R.id.tv_message);
		tv_message.setGravity(Gravity.LEFT|Gravity.BOTTOM);
		tv_message.setLines(8);
		tv_message.setMaxLines(8);
		tv_message.setMovementMethod(new ScrollingMovementMethod());
		findViewById(R.id.btn_start_message).setOnClickListener(this);
		findViewById(R.id.btn_stop_message).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_start_message) {
			if (bPlay != true) {
				bPlay = true;
				new PlayThread().start();
			}
		} else if (v.getId() == R.id.btn_stop_message) {
			bPlay = false;
		}
	}

	private String[] mNewsArray = {
			"中国航天员在天宫二号泡茶，羡煞歪果仁",
			"美国大选顺利闭幕，特朗普高票当选",
			"越南撤销日本获得订单的核电站计划",
			"上海建成卓越全球城市，新市镇轨交全覆盖",
			"土耳其老人怀抱受伤山羊错跑入医院急诊室",
	};
	private class PlayThread extends Thread {
		@Override
		public void run() {
			mHandler.sendEmptyMessage(BEGIN);
			while (bPlay == true) {
				try {
					sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Message message = Message.obtain();
				message.what = SCROLL;
				message.obj = mNewsArray[(int) (Math.random()*30%5)];
				mHandler.sendMessage(message);
			}
			bPlay = true;
			try {
				sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			mHandler.sendEmptyMessage(END);
			bPlay = false;
		}
	}
	
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			String desc = tv_message.getText().toString();
			if (msg.what == BEGIN) {
				desc = String.format("%s\n%s %s", desc, DateUtil.getNowTime(), "下面开始播放新闻");
			} else if (msg.what == SCROLL) {
				desc = String.format("%s\n%s %s", desc, DateUtil.getNowTime(), (String) msg.obj);
			} else if (msg.what == END) {
				desc = String.format("%s\n%s %s", desc, DateUtil.getNowTime(), "新闻播放结束，谢谢观看");
			}
			tv_message.setText(desc);
		};
	};
	
}
