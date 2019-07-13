package com.gs.learn.performance;

import java.lang.ref.WeakReference;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.gs.learn.performance.util.Utils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/12/27.
 */
public class SchedulePoolActivity extends AppCompatActivity {
	private final static String TAG = "SchedulePoolActivity";
	private TextView tv_desc;
	private String mDesc = "";
	private boolean bFirst = true;
	private int ONCE = 0, RATE = 1, DELAY = 2;
	private ScheduledExecutorService mSinglePool;
	private ScheduledExecutorService mMultiPool;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_schedule_pool);
		tv_desc = (TextView) findViewById(R.id.tv_desc);
		initSpinner();
	}
	
	private void initSpinner() {
		ArrayAdapter<String> poolAdapter = new ArrayAdapter<String>(this,
				R.layout.item_select_performance, poolArray);
		Spinner sp_schedule_pool = (Spinner) findViewById(R.id.sp_schedule_pool);
		sp_schedule_pool.setPrompt("请选择定时器线程池类型");
		sp_schedule_pool.setAdapter(poolAdapter);
		sp_schedule_pool.setOnItemSelectedListener(new PoolSelectedListener());
		sp_schedule_pool.setSelection(0);
	}

	private String[] poolArray={
			"单线程定时器延迟一次", "单线程定时器固定速率", "单线程定时器固定延迟",
			"多线程定时器延迟一次", "多线程定时器固定速率", "多线程定时器固定延迟"
	};
	class PoolSelectedListener implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			if (bFirst) {
				bFirst = false;
				return;
			}
			ScheduledExecutorService poolService = (arg2<3)?mSinglePool:mMultiPool;
			startPool(poolService, arg2%3);
		}

		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}
	
	@Override
	protected void onStart() {
		mSinglePool = (ScheduledExecutorService) Executors.newSingleThreadScheduledExecutor();
		mMultiPool = (ScheduledExecutorService) Executors.newScheduledThreadPool(3);
		super.onStart();
	}
	
	@Override
	protected void onStop() {
		if (mSinglePool.isTerminated() != true) {
			mSinglePool.shutdown();
		}
		if (mMultiPool.isTerminated() != true) {
			mMultiPool.shutdown();
		}
		super.onStop();
	}

	private void startPool(ScheduledExecutorService pool, int type) {
		mDesc = "";
		for (int i=0; i<3; i++) {
			MyRunnable refresh = new MyRunnable(i);
			if (type == ONCE) {
				pool.schedule(refresh, 1, TimeUnit.SECONDS);
			} else if (type == RATE) {
				pool.scheduleAtFixedRate(refresh, 0, 3, TimeUnit.SECONDS);
			} else if (type == DELAY) {
				pool.scheduleWithFixedDelay(refresh, 0, 3, TimeUnit.SECONDS);
			}
		}
	}

	private Handler mMyHandler = new MyHandler(this);
	private static class MyHandler extends Handler {
		public static WeakReference<SchedulePoolActivity> mActivity;
		public MyHandler(SchedulePoolActivity activity) {
			mActivity = new WeakReference<SchedulePoolActivity>(activity);
		}

		@Override
		public void handleMessage(Message msg) {
			SchedulePoolActivity act = mActivity.get();
			if (act != null) {
				act.mDesc = String.format("%s\n%s 当前序号是%d", 
						act.mDesc, Utils.getNowTime(), msg.arg1);
				act.tv_desc.setText(act.mDesc);
			}
		}
	}

	private static class MyRunnable implements Runnable {
		private int mIndex;
		public MyRunnable(int index) {
			mIndex = index;
		}
		
		@Override
		public void run() {
			SchedulePoolActivity act = MyHandler.mActivity.get();
			if (act != null) {
				Message msg = act.mMyHandler.obtainMessage();
				msg.arg1 = mIndex;
				act.mMyHandler.sendMessage(msg);
			}
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	};

}
