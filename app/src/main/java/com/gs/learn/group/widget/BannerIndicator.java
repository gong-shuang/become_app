package com.gs.learn.group.widget;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.gs.learn.R;

public class BannerIndicator extends RelativeLayout implements View.OnClickListener {
	private static final String TAG = "BannerIndicator";
	private Context mContext;
	private ViewPager mPager;
	private PagerIndicator mIndicator;
	private List<ImageView> mViewList = new ArrayList<ImageView>();
	private LayoutInflater mInflater;

	public BannerIndicator(Context context) {
		this(context, null);
	}

	public BannerIndicator(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		init();
	}

	public void setImage(ArrayList<Integer> imageList) {
		for (int i = 0; i < imageList.size(); i++) {
			Integer imageID = ((Integer) imageList.get(i)).intValue();
			ImageView iv = new ImageView(mContext);
			iv.setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			iv.setScaleType(ImageView.ScaleType.FIT_XY);
			iv.setImageResource(imageID);
			iv.setOnClickListener(this);
			mViewList.add(iv);
		}
		mPager.setAdapter(new ImageAdapater());
		mPager.addOnPageChangeListener(new BannerChangeListener());
		mPager.setCurrentItem(0);
		mIndicator.setCount(imageList.size(), 30);
	}

	private void init() {
		mInflater = ((Activity) mContext).getLayoutInflater();
		View view = mInflater.inflate(R.layout.banner_indicator, null);
		mPager = (ViewPager) view.findViewById(R.id.vp_banner);
		mIndicator = (PagerIndicator) view.findViewById(R.id.pi_banner);
		addView(view);
	}

	private class ImageAdapater extends PagerAdapter {
		@Override
		public int getCount() {
			return mViewList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(mViewList.get(position));
		}
		
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(mViewList.get(position));
			return mViewList.get(position);
		}
	}

	@Override
	public void onClick(View v) {
		int position = mPager.getCurrentItem();
		mListener.onBannerClick(position);
	}

	public void setOnBannerListener(BannerClickListener listener) {
		mListener = listener;
	}

	private BannerClickListener mListener;
	public interface BannerClickListener {
		public void onBannerClick(int position);
	}

	private class BannerChangeListener implements ViewPager.OnPageChangeListener {
		@Override
		public void onPageScrollStateChanged(int arg0) {
		}

		@Override
		public void onPageScrolled(int seq, float ratio, int offset) {
			mIndicator.setCurrent(seq, ratio);
		}

		@Override
		public void onPageSelected(int seq) {
			mIndicator.setCurrent(seq, 0);
		}
	}
}

