package com.gs.learn.senior.fragment;

import com.gs.learn.R;

import android.content.Context;
import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class StaticFragment extends Fragment implements OnClickListener {
	private static final String TAG = "StaticFragment";
	protected View mView;
	protected Context mContext;
	private TextView tv_adv;
	private ImageView iv_adv;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContext = getActivity();
		mView = inflater.inflate(R.layout.fragment_static, container, false);
		tv_adv = (TextView) mView.findViewById(R.id.tv_adv);
		iv_adv = (ImageView) mView.findViewById(R.id.iv_adv);
		tv_adv.setOnClickListener(this);
		iv_adv.setOnClickListener(this);
		Log.d(TAG, "onCreateView");
		return mView;
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.tv_adv) {
			Toast.makeText(mContext, "您点击了广告文本", Toast.LENGTH_LONG).show();
		} else if (v.getId() == R.id.iv_adv) {
			Toast.makeText(mContext, "您点击了广告图片", Toast.LENGTH_LONG).show();
		}
	}

	@Override
	@Deprecated
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		Log.d(TAG, "onAttach");
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate");
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestroy");
	}
	
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		Log.d(TAG, "onDestroyView");
	}
	
	@Override
	public void onDetach() {
		super.onDetach();
		Log.d(TAG, "onDetach");
	}
	
	@Override
	public void onPause() {
		super.onPause();
		Log.d(TAG, "onPause");
	}
	
	@Override
	public void onResume() {
		super.onResume();
		Log.d(TAG, "onResume");
	}
	
	@Override
	public void onStart() {
		super.onStart();
		Log.d(TAG, "onStart");
	}
	
	@Override
	public void onStop() {
		super.onStop();
		Log.d(TAG, "onStop");
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.d(TAG, "onActivityCreated");
	}
	
}
