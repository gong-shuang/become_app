package com.gs.learn.media.adapter;

import java.util.ArrayList;

import com.gs.learn.media.MusicDetailActivity;
import com.gs.learn.R;
import com.gs.learn.media.bean.MusicInfo;
import com.gs.learn.media.util.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MusicListAdapter extends BaseAdapter implements OnItemClickListener {
	private ArrayList<MusicInfo> mMusicList;
	private LayoutInflater mInflater;
	private Context mContext;

	public MusicListAdapter(Context context, ArrayList<MusicInfo> musicList) {
		mInflater = LayoutInflater.from(context);
		mContext = context;
		mMusicList = musicList;
	}

	@Override
	public int getCount() {
		return mMusicList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return mMusicList.get(arg0);
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
			convertView = mInflater.inflate(R.layout.item_music, null);
			holder.iv_format = (ImageView) convertView.findViewById(R.id.iv_format);
			holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
			holder.tv_artist = (TextView) convertView.findViewById(R.id.tv_artist);
			holder.tv_duration = (TextView) convertView.findViewById(R.id.tv_duration);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		MusicInfo item = mMusicList.get(position);
		//这里要补充不同音频图片的设置
		holder.iv_format.setImageResource(getAudioImage(item.getUrl()));
		holder.tv_title.setText(item.getTitle());
		holder.tv_artist.setText(item.getArtist());
		holder.tv_duration.setText(Utils.formatDuration(item.getDuration()));
		return convertView;
	}
	
	private int getAudioImage(String path) {
		int icon_id = R.drawable.icon_other;
		String extendName = Utils.getExtendName(path);
		if (extendName.equals("mp3")) {
			icon_id = R.drawable.icon_mp3;
		} else if (extendName.equals("wav")) {
			icon_id = R.drawable.icon_wav;
		} else if (extendName.equals("mid")) {
			icon_id = R.drawable.icon_mid;
		} else if (extendName.equals("ogg")) {
			icon_id = R.drawable.icon_ogg;
		}
		return icon_id;
	}

	public final class ViewHolder {
		public ImageView iv_format;
		public TextView tv_title;
		public TextView tv_artist;
		public TextView tv_duration;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		MusicInfo item = mMusicList.get(position);
		Intent intent = new Intent(mContext, MusicDetailActivity.class);
		intent.putExtra("music", item);
		mContext.startActivity(intent);
	}

}
