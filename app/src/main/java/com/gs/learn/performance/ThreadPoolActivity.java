package com.gs.learn.performance;

import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
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
public class ThreadPoolActivity extends AppCompatActivity {
	private final static String TAG = "ThreadPoolActivity";
	private TextView tv_desc;
	private String mDesc = "";
	private boolean bFirst = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_thread_pool);
		tv_desc = (TextView) findViewById(R.id.tv_desc);
		initSpinner();
	}
	
	private void initSpinner() {
		ArrayAdapter<String> poolAdapter = new ArrayAdapter<String>(this,
				R.layout.item_select_performance, poolArray);
		Spinner sp_thread_pool = (Spinner) findViewById(R.id.sp_thread_pool);
		sp_thread_pool.setPrompt("请选择普通线程池类型");
		sp_thread_pool.setAdapter(poolAdapter);
		sp_thread_pool.setOnItemSelectedListener(new PoolSelectedListener());
		sp_thread_pool.setSelection(0);
	}

	private String[] poolArray={
			"单线程线程池", "多线程线程池", "无限制线程池", "自定义线程池"
	};
	class PoolSelectedListener implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			if (bFirst) {
				bFirst = false;
				return;
			}
			if (arg2 == 0) {
				ExecutorService pool = Executors.newSingleThreadExecutor();
				startPool(pool);
			} else if (arg2 == 1) {
				ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);
				startPool(pool);
			} else if (arg2 == 2) {
				ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newCachedThreadPool();
				startPool(pool);
			} else if (arg2 == 3) {
				ThreadPoolExecutor pool = new ThreadPoolExecutor(
						2, 5, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(19));
				startPool(pool);
			}
		}

		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}
	
	private void startPool(ExecutorService pool) {
		mDesc = "";
		for (int i=0; i<20; i++) {
			MyRunnable refresh = new MyRunnable(i);
			pool.execute(refresh);
		}
	}

	private Handler mMyHandler = new MyHandler(this);
	private static class MyHandler extends Handler {
		public static WeakReference<ThreadPoolActivity> mActivity;

		public MyHandler(ThreadPoolActivity activity) {
			mActivity = new WeakReference<ThreadPoolActivity>(activity);
		}

		@Override
		public void handleMessage(Message msg) {
			ThreadPoolActivity act = mActivity.get();
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
			ThreadPoolActivity act = MyHandler.mActivity.get();
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
