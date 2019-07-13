package com.gs.learn.event;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/11/23.
 */
public class DrawerLayoutActivity extends AppCompatActivity implements OnClickListener {
	private final static String TAG = "DrawerLayoutActivity";
	private DrawerLayout dl_layout;
	private Button btn_drawer_left;
	private Button btn_drawer_right;
	private TextView tv_drawer_center;
	private ListView lv_drawer_left;
	private ListView lv_drawer_right;
	private String[] titleArray = { "首页", "新闻", "娱乐", "博客", "论坛" };
	private String[] settingArray = { "我的", "设置", "关于" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drawer_layout);
		dl_layout = (DrawerLayout) findViewById(R.id.dl_layout);
		dl_layout.addDrawerListener(new SlidingListener());

		btn_drawer_left = (Button) findViewById(R.id.btn_drawer_left);
		btn_drawer_right = (Button) findViewById(R.id.btn_drawer_right);
		tv_drawer_center = (TextView) findViewById(R.id.tv_drawer_center);
		btn_drawer_left.setOnClickListener(this);
		btn_drawer_right.setOnClickListener(this);

		lv_drawer_left = (ListView) findViewById(R.id.lv_drawer_left);
		ArrayAdapter<String> left_adapter = new ArrayAdapter<String>(this,
				R.layout.item_select, titleArray);
		lv_drawer_left.setAdapter(left_adapter);
		lv_drawer_left.setOnItemClickListener(new LeftListListener());

		lv_drawer_right = (ListView) findViewById(R.id.lv_drawer_right);
		ArrayAdapter<String> right_adapter = new ArrayAdapter<String>(this,
				R.layout.item_select, settingArray);
		lv_drawer_right.setAdapter(right_adapter);
		lv_drawer_right.setOnItemClickListener(new RightListListener());
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_drawer_left) {
			if (dl_layout.isDrawerOpen(lv_drawer_left)) {
				dl_layout.closeDrawer(lv_drawer_left);
			} else {
				dl_layout.openDrawer(lv_drawer_left);
			}
		} else if (v.getId() == R.id.btn_drawer_right) {
			if (dl_layout.isDrawerOpen(lv_drawer_right)) {
				dl_layout.closeDrawer(lv_drawer_right);
			} else {
				dl_layout.openDrawer(lv_drawer_right);
			}
		}
	}

	private class LeftListListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			String text = titleArray[position];
			tv_drawer_center.setText("这里是" + text + "页面");
			dl_layout.closeDrawers();
		}
	}

	private class RightListListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			String text = settingArray[position];
			tv_drawer_center.setText("这里是" + text + "页面");
			dl_layout.closeDrawers();
		}
	}

	private class SlidingListener implements DrawerListener {
		@Override
		public void onDrawerSlide(View paramView, float paramFloat) {
		}

		@Override
		public void onDrawerOpened(View paramView) {
			if (paramView.getId() == R.id.lv_drawer_left) {
				btn_drawer_left.setText("关闭左边侧滑");
			} else {
				btn_drawer_right.setText("关闭右边侧滑");
			}
		}

		@Override
		public void onDrawerClosed(View paramView) {
			if (paramView.getId() == R.id.lv_drawer_left) {
				btn_drawer_left.setText("打开左边侧滑");
			} else {
				btn_drawer_right.setText("打开右边侧滑");
			}
		}

		@Override
		public void onDrawerStateChanged(int paramInt) {
		}
	}

}
