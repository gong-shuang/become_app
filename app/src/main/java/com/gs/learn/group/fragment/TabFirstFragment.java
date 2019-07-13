package com.gs.learn.group.fragment;

import com.gs.learn.R;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TabFirstFragment extends Fragment {
	private static final String TAG = "TabFirstFragment";
	protected View mView;
	protected Context mContext;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mContext = getActivity();
		mView = inflater.inflate(R.layout.fragment_tab_first, container, false);

		String desc = String.format("我是%s页面，来自%s", 
				"首页", getArguments().getString("tag"));
		TextView tv_first = (TextView) mView.findViewById(R.id.tv_first);
		tv_first.setText(desc);
		
		return mView;
	}
	
}
