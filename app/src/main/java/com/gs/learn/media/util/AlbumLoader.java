package com.gs.learn.media.util;

import java.util.ArrayList;

import com.gs.learn.media.bean.MusicInfo;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore.Audio;
import android.util.Log;

public class AlbumLoader {
	private static final String TAG = "AlbumLoader";
	private static ArrayList<MusicInfo> musicList = new ArrayList<MusicInfo>();
	private static AlbumLoader mLoader;
	private static ContentResolver mResolver;
	private static Uri mAudioUri = Audio.Media.EXTERNAL_CONTENT_URI;
	private static String[] mContactColumn = new String[] {
		Audio.Media._ID,
		Audio.Media.TITLE,
		Audio.Media.ALBUM,
		Audio.Media.DURATION,
		Audio.Media.SIZE,
		Audio.Media.ARTIST,
		Audio.Media.DATA, };
	
	public static AlbumLoader getInstance(ContentResolver resolver){
		if(mLoader == null){
			mResolver = resolver;
			mLoader = new AlbumLoader();			
		}
		return mLoader;
	}
	
	private AlbumLoader(){		
		Cursor cursor = mResolver.query(mAudioUri, mContactColumn, null, null, null);
		if (cursor.moveToFirst()) {
			for (;; cursor.moveToNext()) {
				MusicInfo music = new MusicInfo();
				music.setId(cursor.getLong(0));
				music.setTitle(cursor.getString(1));
				music.setAlbum(cursor.getString(2));
				music.setDuration(cursor.getInt(3));
				music.setSize(cursor.getLong(4));
				music.setArtist(cursor.getString(5));
				music.setUrl(cursor.getString(6));
				Log.d(TAG, music.getTitle()+" "+music.getDuration());
				musicList.add(music);
				if (cursor.isLast() == true) {
					break;
				}
			}
		}
	}
	
	public ArrayList<MusicInfo> getMusicList(){
		return musicList;
	}
	
	public Uri getMusicUriById(long id){
		Uri uri = ContentUris.withAppendedId(mAudioUri, id);
		return uri;
	}	

}
