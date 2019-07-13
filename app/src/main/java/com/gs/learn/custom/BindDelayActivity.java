package com.gs.learn.custom;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.gs.learn.custom.service.BindDelayService;
import com.gs.learn.custom.util.DateUtil;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/10/14.
 */
public class BindDelayActivity extends AppCompatActivity implements OnClickListener {

	private static final String TAG = "BindDelayActivity";
	private static TextView tv_delay;
	private Intent mIntent;
	private static String mDesc = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bind_delay);
		tv_delay = (TextView) findViewById(R.id.tv_delay);
		findViewById(R.id.btn_start).setOnClickListener(this);
		findViewById(R.id.btn_bind).setOnClickListener(this);
		findViewById(R.id.btn_unbind).setOnClickListener(this);
		findViewById(R.id.btn_stop).setOnClickListener(this);
		mIntent = new Intent(this, BindDelayService.class);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_start) {
			startService(mIntent);
		} else if (v.getId() == R.id.btn_bind) {
			boolean bindFlag = bindService(mIntent, mFirstConn, Context.BIND_AUTO_CREATE);
			Log.d(TAG, "bindFlag="+bindFlag);
		} else if (v.getId() == R.id.btn_unbind) {
			if (mBindService != null) {
				unbindService(mFirstConn);
				mBindService = null;
			}
		} else if (v.getId() == R.id.btn_stop) {
			stopService(mIntent);
		}
	}

	public static void showText(String desc) {
		if (tv_delay != null) {
			mDesc = String.format("%s%s %s\n", mDesc, DateUtil.getNowDateTime("HH:mm:ss"), desc);
			tv_delay.setText(mDesc);
		}
	}
	
	private BindDelayService mBindService;
    private ServiceConnection mFirstConn = new ServiceConnection() {
        /** 获取服务对象时的操作 */
        public void onServiceConnected(ComponentName name, IBinder service) {
        	//如果服务运行于另外一个进程，则不能直接强制转换类型，
        	//否则会报错“java.lang.ClassCastException: android.os.BinderProxy cannot be cast to...”
        	mBindService = ((BindDelayService.LocalBinder) service).getService();
        	Log.d(TAG, "onServiceConnected");
        }

        /** 无法获取到服务对象时的操作 */
        public void onServiceDisconnected(ComponentName name) {
        	mBindService = null;
        	Log.d(TAG, "onServiceDisconnected");
        }

    };

}
