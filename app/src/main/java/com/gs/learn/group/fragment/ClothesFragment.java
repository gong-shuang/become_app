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

public class ClothesFragment extends Fragment implements OnRefreshListener {
	private static final String TAG = "ClothesFragment";
	protected View mView;
	protected Context mContext;
	private SwipeRefreshLayout srl_clothes;
	private RecyclerView rv_clothes;
	private StaggeredAdapter mAdapter;
	private ArrayList<GoodsInfo> mAllArray;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mContext = getActivity();
		mView = inflater.inflate(R.layout.fragment_clothes, container, false);
		
		srl_clothes = (SwipeRefreshLayout) mView.findViewById(R.id.srl_clothes);
		srl_clothes.setOnRefreshListener(this);
		srl_clothes.setColorSchemeResources(
				R.color.red, R.color.orange, R.color.green, R.color.blue );
		
		rv_clothes = (RecyclerView) mView.findViewById(R.id.rv_clothes);
		StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(3, LinearLayout.VERTICAL);
		rv_clothes.setLayoutManager(manager);
		mAllArray = GoodsInfo.getDefaultStag();
		mAdapter = new StaggeredAdapter(mContext, mAllArray);
		mAdapter.setOnItemClickListener(mAdapter);
		mAdapter.setOnItemLongClickListener(mAdapter);
		rv_clothes.setAdapter(mAdapter);
		rv_clothes.setItemAnimator(new DefaultItemAnimator());
		rv_clothes.addItemDecoration(new SpacesItemDecoration(3));
		
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
			srl_clothes.setRefreshing(false);
			for (int i=mAllArray.size()-1, count=0; count<5; count++) {
				GoodsInfo item = mAllArray.get(i);
				mAllArray.remove(i);
				mAllArray.add(0, item);
			}
			mAdapter.notifyDataSetChanged();
			rv_clothes.scrollToPosition(0);
		}
	};
	
}
