package com.gs.learn.group;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TabHost;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/10/21.
 */
public class TabHostActivity extends TabActivity implements OnClickListener {
	private static final String TAG = "TabHostActivity";
	private Bundle mBundle = new Bundle();
	private TabHost tab_host;
	private LinearLayout ll_first, ll_second, ll_third;
	private String FIRST_TAG = "first";
	private String SECOND_TAG = "second";
	private String THIRD_TAG = "third";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab_host);
		mBundle.putString("tag", TAG);
		ll_first = (LinearLayout) findViewById(R.id.ll_first);
		ll_second = (LinearLayout) findViewById(R.id.ll_second);
		ll_third = (LinearLayout) findViewById(R.id.ll_third);
		ll_first.setOnClickListener(this);
		ll_second.setOnClickListener(this);
		ll_third.setOnClickListener(this);
		tab_host = getTabHost();
		tab_host.addTab(getNewTab(FIRST_TAG, R.string.menu_first,
				R.drawable.tab_first_selector, TabFirstActivity.class));
		tab_host.addTab(getNewTab(SECOND_TAG, R.string.menu_second,
				R.drawable.tab_second_selector, TabSecondActivity.class));
		tab_host.addTab(getNewTab(THIRD_TAG, R.string.menu_third,
				R.drawable.tab_third_selector, TabThirdActivity.class));
		tab_host.setCurrentTabByTag(FIRST_TAG);
		changeContainerView(ll_first);
	}

	private TabHost.TabSpec getNewTab(String spec, int label, int icon, Class<?> cls) {
		Intent intent = new Intent(this, cls).putExtras(mBundle);
		return tab_host.newTabSpec(spec).setContent(intent)
				.setIndicator(getString(label), getResources().getDrawable(icon));
	}

	@Override
	public void onClick(View v) {
		if (v.getId()==R.id.ll_first || v.getId()==R.id.ll_second || v.getId()==R.id.ll_third) {
			changeContainerView(v);
		}
	}

	private void changeContainerView(View v) {
		ll_first.setSelected(false);
		ll_second.setSelected(false);
		ll_third.setSelected(false);
		v.setSelected(true);
		if (v == ll_first) {
			tab_host.setCurrentTabByTag(FIRST_TAG);
		} else if (v == ll_second) {
			tab_host.setCurrentTabByTag(SECOND_TAG);
		} else if (v == ll_third) {
			tab_host.setCurrentTabByTag(THIRD_TAG);
		}
	}

}
