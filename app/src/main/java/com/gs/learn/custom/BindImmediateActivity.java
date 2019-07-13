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

import com.gs.learn.custom.service.BindImmediateService;
import com.gs.learn.custom.util.DateUtil;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/10/14.
 */
public class BindImmediateActivity extends AppCompatActivity implements OnClickListener {

	private static final String TAG = "BindImmediateActivity";
	private static TextView tv_immediate;
	private Intent mIntent;
	private static String mDesc = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bind_immediate);
		tv_immediate = (TextView) findViewById(R.id.tv_immediate);
		findViewById(R.id.btn_start_bind).setOnClickListener(this);
		findViewById(R.id.btn_unbind).setOnClickListener(this);
		mIntent = new Intent(this, BindImmediateService.class);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_start_bind) {
			boolean bindFlag = bindService(mIntent, mFirstConn, Context.BIND_AUTO_CREATE);
			Log.d(TAG, "bindFlag="+bindFlag);
		} else if (v.getId() == R.id.btn_unbind) {
			if (mBindService != null) {
				unbindService(mFirstConn);
				mBindService = null;
			}
		}
	}

	public static void showText(String desc) {
		if (tv_immediate != null) {
			mDesc = String.format("%s%s %s\n", mDesc, DateUtil.getNowDateTime("HH:mm:ss"), desc);
			tv_immediate.setText(mDesc);
		}
	}
	
	private BindImmediateService mBindService;
    private ServiceConnection mFirstConn = new ServiceConnection() {
        //获取服务对象时的操作
        public void onServiceConnected(ComponentName name, IBinder service) {
        	//如果服务运行于另外一个进程，则不能直接强制转换类型，
        	//否则会报错“java.lang.ClassCastException: android.os.BinderProxy cannot be cast to...”
        	mBindService = ((BindImmediateService.LocalBinder) service).getService();
        	Log.d(TAG, "onServiceConnected");
        }

        //无法获取到服务对象时的操作
        public void onServiceDisconnected(ComponentName name) {
        	mBindService = null;
        	Log.d(TAG, "onServiceDisconnected");
        }

    };

}
