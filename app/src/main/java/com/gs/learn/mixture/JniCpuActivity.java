package com.gs.learn.mixture;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/12/11.
 */
public class JniCpuActivity extends AppCompatActivity implements OnClickListener {
	private TextView tv_cpu_build;
	private TextView tv_cpu_jni;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jni_cpu);
		tv_cpu_build = (TextView) findViewById(R.id.tv_cpu_build);
		tv_cpu_jni = (TextView) findViewById(R.id.tv_cpu_jni);
		findViewById(R.id.btn_cpu).setOnClickListener(this);
		tv_cpu_build.setText("Build类获得的CPU指令集为"+Build.CPU_ABI);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_cpu) {
			String desc = cpuFromJNI(1, 0.5f, 99.9, true);
			tv_cpu_jni.setText(desc);
		}
	}

	public native String cpuFromJNI(int i1, float f1, double d1, boolean b1);
	public native String unimplementedCpuFromJNI(int i1, float f1, double d1, boolean b1);
	static {
		System.loadLibrary("jni_mix");
	}
    
}
