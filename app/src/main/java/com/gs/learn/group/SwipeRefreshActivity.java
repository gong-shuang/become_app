package com.gs.learn.group;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.gs.learn.R;
/**
 * Created by ouyangshen on 2016/10/21.
 */
public class SwipeRefreshActivity extends AppCompatActivity implements OnRefreshListener {
	
	private TextView tv_simple;
	private SwipeRefreshLayout srl_simple;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_swipe_refresh);

		tv_simple = (TextView) findViewById(R.id.tv_simple);
		srl_simple = (SwipeRefreshLayout) findViewById(R.id.srl_simple);
		srl_simple.setOnRefreshListener(this);
		//旧版用下面的setColorScheme设置进度条颜色
		//srl_simple.setColorScheme(R.color.red, R.color.orange, R.color.green, R.color.blue);
		//新版用下面的setColorSchemeResources设置进度圆圈颜色
		srl_simple.setColorSchemeResources(
				R.color.red, R.color.orange, R.color.green, R.color.blue );
		//旧版v4包中无下面三个方法
//		srl_simple.setProgressBackgroundColorSchemeResource(R.color.black);
//		srl_simple.setProgressViewOffset(true, 0, 50);
//		srl_simple.setDistanceToTriggerSync(100);
	}

	@Override
	public void onRefresh() {
		tv_simple.setText("正在刷新");
		mHandler.postDelayed(mRefresh, 2000);
	}

	private Handler mHandler = new Handler();
	private Runnable mRefresh = new Runnable() {
		@Override
		public void run() {
			tv_simple.setText("刷新完成");
			srl_simple.setRefreshing(false);
		}
	};
	
}
