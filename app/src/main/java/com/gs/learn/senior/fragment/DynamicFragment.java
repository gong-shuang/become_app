package com.gs.learn.senior.fragment;

import com.gs.learn.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class DynamicFragment extends Fragment {
	private static final String TAG = "DynamicFragment";
	protected View mView;
	protected Context mContext;
	private int mPosition;
	private int mImageId;
	private String mDesc;

	public static DynamicFragment newInstance(int position, int image_id, String desc) {
		DynamicFragment fragment = new DynamicFragment();
		Bundle bundle = new Bundle();
		bundle.putInt("position", position);
		bundle.putInt("image_id", image_id);
		bundle.putString("desc", desc);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContext = getActivity();
		if (getArguments() != null) {
			mPosition = getArguments().getInt("position", 0);
			mImageId = getArguments().getInt("image_id", 0);
			mDesc = getArguments().getString("desc");
		}
		mView = inflater.inflate(R.layout.fragment_dynamic, container, false);
		ImageView iv_pic = (ImageView) mView.findViewById(R.id.iv_pic);
		TextView tv_desc = (TextView) mView.findViewById(R.id.tv_desc);
		iv_pic.setImageResource(mImageId);
		tv_desc.setText(mDesc);	
		Log.d(TAG, "onCreateView position="+mPosition);	
		return mView;
	}

	@Override
	@Deprecated
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		Log.d(TAG, "onAttach position="+mPosition);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate position="+mPosition);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestroy position="+mPosition);
	}
	
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		Log.d(TAG, "onDestroyView position="+mPosition);
	}
	
	@Override
	public void onDetach() {
		super.onDetach();
		Log.d(TAG, "onDetach position="+mPosition);
	}
	
	@Override
	public void onPause() {
		super.onPause();
		Log.d(TAG, "onPause position="+mPosition);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		Log.d(TAG, "onResume position="+mPosition);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		Log.d(TAG, "onStart position="+mPosition);
	}
	
	@Override
	public void onStop() {
		super.onStop();
		Log.d(TAG, "onStop position="+mPosition);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.d(TAG, "onActivityCreated position="+mPosition);
	}
	
}
