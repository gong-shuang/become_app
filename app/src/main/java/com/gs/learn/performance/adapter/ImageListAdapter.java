package com.gs.learn.performance.adapter;

import com.gs.learn.R;
import com.gs.learn.performance.cache.ImageCache;
import com.gs.learn.performance.cache.ImageCacheConfig;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageListAdapter extends BaseAdapter {
	private final static String TAG = "ImageListAdapter";
	private LayoutInflater mInflater;
	private Context mContext;
	private String[] mUrlList;
	private ImageCache mCache;

	public ImageListAdapter(Context context, String[] urlList) {
		mInflater = LayoutInflater.from(context);
		mContext = context;
		mUrlList = urlList;
		mCache = ImageCache.getInstance(mContext);
	}

	@Override
	public int getCount() {
		return mUrlList.length;
	}

	@Override
	public Object getItem(int arg0) {
		return mUrlList[arg0];
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_image, null);
			holder.tv_seq = (TextView) convertView.findViewById(R.id.tv_seq);
			holder.iv_scene = (ImageView) convertView.findViewById(R.id.iv_scene);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.tv_seq.setText(""+(position+1));
		mCache.initConfig(new ImageCacheConfig.Builder().build())
				.show(mUrlList[position], holder.iv_scene);
		return convertView;
	}

	public final class ViewHolder {
		public TextView tv_seq;
		public ImageView iv_scene;
	}

}
