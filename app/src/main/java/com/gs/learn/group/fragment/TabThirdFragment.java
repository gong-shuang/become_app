package com.gs.learn.group.fragment;

import com.gs.learn.R;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TabThirdFragment extends Fragment {
	private static final String TAG = "TabThirdFragment";
	protected View mView;
	protected Context mContext;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mContext = getActivity();
		mView = inflater.inflate(R.layout.fragment_tab_third, container, false);

		String desc = String.format("我是%s页面，来自%s", 
				"购物车", getArguments().getString("tag"));
		TextView tv_third = (TextView) mView.findViewById(R.id.tv_third);
		tv_third.setText(desc);
		
		return mView;
	}
	
}
