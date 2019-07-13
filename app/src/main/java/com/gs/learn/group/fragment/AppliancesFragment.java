package com.gs.learn.group.fragment;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.gs.learn.R;
import com.gs.learn.group.adapter.StaggeredAdapter;
import com.gs.learn.group.bean.GoodsInfo;
import com.gs.learn.group.widget.SpacesItemDecoration;

public class AppliancesFragment extends Fragment implements OnRefreshListener {
	private static final String TAG = "AppliancesFragment";
	protected View mView;
	protected Context mContext;
	private SwipeRefreshLayout srl_appliances;
	private RecyclerView rv_appliances;
	private StaggeredAdapter mAdapter;
	private ArrayList<GoodsInfo> mAllArray;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mContext = getActivity();
		mView = inflater.inflate(R.layout.fragment_appliances, container, false);
		
		srl_appliances = (SwipeRefreshLayout) mView.findViewById(R.id.srl_appliances);
		srl_appliances.setOnRefreshListener(this);
		srl_appliances.setColorSchemeResources(
				R.color.red, R.color.orange, R.color.green, R.color.blue );
		
		rv_appliances = (RecyclerView) mView.findViewById(R.id.rv_appliances);
		StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(3, LinearLayout.VERTICAL);
		rv_appliances.setLayoutManager(manager);
		mAllArray = GoodsInfo.getDefaultAppi();
		mAdapter = new StaggeredAdapter(mContext, mAllArray);
		mAdapter.setOnItemClickListener(mAdapter);
		mAdapter.setOnItemLongClickListener(mAdapter);
		rv_appliances.setAdapter(mAdapter);
		rv_appliances.setItemAnimator(new DefaultItemAnimator());
		rv_appliances.addItemDecoration(new SpacesItemDecoration(3));
		
		return mView;
	}

	@Override
	public void onRefresh() {
		mHandler.postDelayed(mRefresh, 2000);
	}

	private Handler mHandler = new Handler();
	private Runnable mRefresh = new Runnable() {
		@Override
		public void run() {
			srl_appliances.setRefreshing(false);
			for (int i=mAllArray.size()-1, count=0; count<5; count++) {
				GoodsInfo item = mAllArray.get(i);
				mAllArray.remove(i);
				mAllArray.add(0, item);
			}
			mAdapter.notifyDataSetChanged();
			rv_appliances.scrollToPosition(0);
		}
	};
	
}
