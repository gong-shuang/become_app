package com.gs.learn.storage;

import com.gs.learn.R;
import com.gs.learn.common.util.DateUtil;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

/**
 * Created by ouyangshen on 2016/10/1.
 */
public class MenuContextActivity extends AppCompatActivity implements OnClickListener {

	private TextView tv_context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_context);
		tv_context = (TextView) findViewById(R.id.tv_context);
		findViewById(R.id.btn_context).setOnClickListener(this);

		setRandomTime();
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_context) {
		    registerForContextMenu(v);
		    openContextMenu(v);
		    unregisterForContextMenu(v);
		}
	}

	@Override
	protected void onResume() {
		registerForContextMenu(tv_context);
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		unregisterForContextMenu(tv_context);
		super.onPause();
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		getMenuInflater().inflate(R.menu.menu_option, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.menu_change_time) {
			setRandomTime();
		} else if (id == R.id.menu_change_color) {
			tv_context.setTextColor(getRandomColor());
		} else if (id == R.id.menu_change_bg) {
			tv_context.setBackgroundColor(getRandomColor());
		}
		return true;
	}
	
	private void setRandomTime() {
		String desc = DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss") + " 这里是菜单显示文本";
		tv_context.setText(desc);
	}

	private int[] mColorArray = {
			Color.BLACK, Color.WHITE, Color.RED, Color.YELLOW, Color.GREEN, 
			Color.BLUE, Color.CYAN, Color.MAGENTA, Color.GRAY, Color.DKGRAY
	};
	private int getRandomColor() {
		int random = (int) (Math.random()*10 % 10);
		return mColorArray[random];
	}
	
}
