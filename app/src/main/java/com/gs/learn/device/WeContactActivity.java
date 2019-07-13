package com.gs.learn.device;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import com.gs.learn.R;
/**
 * Created by ouyangshen on 2016/11/4.
 */
public class WeContactActivity extends AppCompatActivity {
	private final static String TAG = "WeContactActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_we_contact);
		Toolbar tl_head = (Toolbar) findViewById(R.id.tl_head);
		tl_head.setTitle(getResources().getString(R.string.menu_second));
		setSupportActionBar(tl_head);
		tl_head.setNavigationOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});
	}

}
