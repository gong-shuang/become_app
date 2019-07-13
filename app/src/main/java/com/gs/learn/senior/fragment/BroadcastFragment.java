package com.gs.learn.senior.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

import com.gs.learn.R;

public class BroadcastFragment extends Fragment {
	private static final String TAG = "BroadcastFragment";
	public final static String EVENT = "com.gs.learn.senior.fragment.BroadcastFragment";
	protected View mView;
	protected Context mContext;
	private int mPosition;
	private int mImageId;
	private String mDesc;
	private int mSeq = 0;
	private Spinner sp_bg;
	private boolean bFirst = true;

	public static BroadcastFragment newInstance(int position, int image_id, String desc) {
		BroadcastFragment fragment = new BroadcastFragment();
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
		mView = inflater.inflate(R.layout.fragment_broadcast, container, false);
		ImageView iv_pic = (ImageView) mView.findViewById(R.id.iv_pic);
		TextView tv_desc = (TextView) mView.findViewById(R.id.tv_desc);
		iv_pic.setImageResource(mImageId);
		tv_desc.setText(mDesc);
    	return mView;
	}
	
	private void initSpinner() {
		ArrayAdapter<String> dividerAdapter = new ArrayAdapter<String>(mContext,
				R.layout.item_select, mColorNameArray);
		sp_bg = (Spinner) mView.findViewById(R.id.sp_bg);
		sp_bg.setPrompt("请选择页面背景色");
		sp_bg.setAdapter(dividerAdapter);
		sp_bg.setSelection(mSeq);
		sp_bg.setOnItemSelectedListener(new ColorSelectedListener());
	}

	private String[] mColorNameArray = {"红色", "黄色", "绿色", "青色", "蓝色"};
	private int[] mColorIdArray= {Color.RED, Color.YELLOW, Color.GREEN, Color.CYAN, Color.BLUE};

	class ColorSelectedListener implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			if (!bFirst || mSeq!=arg2) {
		    	mSeq = arg2;
				Intent intent = new Intent(BroadcastFragment.this.EVENT);
				intent.putExtra("seq", arg2);
				intent.putExtra("color", mColorIdArray[arg2]);
				LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
			}
			bFirst = false;
		}

		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}
	
	@Override
	public void onStart() {
		super.onStart();
		initSpinner();
		bgChangeReceiver = new BgChangeReceiver();
		IntentFilter filter = new IntentFilter(BroadcastFragment.EVENT);
        LocalBroadcastManager.getInstance(mContext).registerReceiver(bgChangeReceiver, filter);
	}

	@Override
	public void onStop() {
        LocalBroadcastManager.getInstance(mContext).unregisterReceiver(bgChangeReceiver);
		super.onStop();
	}

    private BgChangeReceiver bgChangeReceiver;
    private class BgChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
            	mSeq = intent.getIntExtra("seq", 0);
            	sp_bg.setSelection(mSeq);
            }
        }
    }

}
