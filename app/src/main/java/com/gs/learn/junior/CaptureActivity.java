package com.gs.learn.junior;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gs.learn.R;
import com.gs.learn.common.util.DateUtil;

/**
 * Created by ouyangshen on 2016/9/15.
 */
public class CaptureActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {
	private TextView tv_capture;
	private ImageView iv_capture;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_capture);
		tv_capture = (TextView) findViewById(R.id.tv_capture);
		iv_capture = (ImageView) findViewById(R.id.iv_capture);
		tv_capture.setDrawingCacheEnabled(true);
		Button btn_chat = (Button) findViewById(R.id.btn_chat);
		Button btn_capture = (Button) findViewById(R.id.btn_capture);
		btn_chat.setOnClickListener(this);
		btn_chat.setOnLongClickListener(this);
		btn_capture.setOnClickListener(this);
	}

	private String[] mChatStr = { "你吃饭了吗？", "今天天气真好呀。",
			"我中奖啦！", "我们去看电影吧。", "晚上干什么好呢？" };

	@Override
	public boolean onLongClick(View v) {
		if (v.getId() == R.id.btn_chat) {
			tv_capture.setText("");
		}
		return true;
	}
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_chat) {
			int random = (int)(Math.random()*10) % 5;
			String newStr = String.format("%s\n%s %s",
					tv_capture.getText().toString(), DateUtil.getNowTime(), mChatStr[random]);
			tv_capture.setText(newStr);
		} else if (v.getId() == R.id.btn_capture) {
			Bitmap bitmap = tv_capture.getDrawingCache();
			iv_capture.setImageBitmap(bitmap);
			// 注意这里在截图完毕后不能马上关闭绘图缓存，因为画面渲染需要时间，
			// 如果立即关闭缓存，渲染画面就会找不到位图对象，会报错
			// “java.lang.IllegalArgumentException: Cannot draw recycled bitmaps”。
			mHandler.postDelayed(mResetCache, 200);
		}
	}

	private Handler mHandler = new Handler();
	private Runnable mResetCache = new Runnable() {
		@Override
		public void run() {
			tv_capture.setDrawingCacheEnabled(false);
			tv_capture.setDrawingCacheEnabled(true);
		}
	};
}
