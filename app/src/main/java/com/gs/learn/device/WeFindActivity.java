package com.gs.learn.device;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import com.gs.learn.R;
/**
 * Created by ouyangshen on 2016/11/4.
 */
public class WeFindActivity extends AppCompatActivity implements OnClickListener {
	private final static String TAG = "WeFindActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_we_find);
		Toolbar tl_head = (Toolbar) findViewById(R.id.tl_head);
		tl_head.setTitle(getResources().getString(R.string.menu_third));
		setSupportActionBar(tl_head);
		tl_head.setNavigationOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});
		findViewById(R.id.tv_scan).setOnClickListener(this);
		findViewById(R.id.tv_shake).setOnClickListener(this);
		findViewById(R.id.tv_smell).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.tv_scan) {
			Intent intent = new Intent(this, FindScanActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.tv_shake) {
			Intent intent = new Intent(this, FindShakeActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.tv_smell) {
			Intent intent = new Intent(this, FindSmellActivity.class);
			startActivity(intent);
		}
	}

}
