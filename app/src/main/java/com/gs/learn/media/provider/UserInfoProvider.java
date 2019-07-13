package com.gs.learn.media.provider;

import com.gs.learn.media.database.UserDBHelper;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class UserInfoProvider extends ContentProvider {
	private final static String TAG = "UserInfoProvider";
	public static final int USER_INFO = 1;
	public static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	static {
		uriMatcher.addURI(UserInfoContent.AUTHORITIES, "/user", USER_INFO);
	}
	private UserDBHelper userDB;

	//删除数据
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int count = 0;
		if (uriMatcher.match(uri) == USER_INFO) {
			SQLiteDatabase db = userDB.getWritableDatabase();
			count = db.delete(UserInfoContent.TABLE_NAME, selection, selectionArgs);
			db.close();
		}
		return count;
	}

	//插入数据
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		Uri newUri = uri;
		if (uriMatcher.match(uri) == USER_INFO) {
			SQLiteDatabase db = userDB.getWritableDatabase();
			// 向指定的表插入数据，得到返回的Id
			long rowId = db.insert(UserInfoContent.TABLE_NAME, null, values);
			if (rowId > 0) {// 判断插入是否执行成功
				// 如果添加成功，利用新添加的Id和生成新的地址
				newUri = ContentUris.withAppendedId( UserInfoContent.CONTENT_URI, rowId);
				// 通知监听器，数据已经改变
				getContext().getContentResolver().notifyChange(newUri, null);
			}
			db.close();
		}
		return uri;
	}

	//创建ContentProvider时调用的回调函数
	@Override
	public boolean onCreate() {
		userDB = UserDBHelper.getInstance(getContext(), 1);
		return false;
	}

	//查询数据库
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		Cursor cursor = null;
		if (uriMatcher.match(uri) == USER_INFO) {
			SQLiteDatabase db = userDB.getReadableDatabase();
			// 执行查询
			cursor = db.query(UserInfoContent.TABLE_NAME, 
					projection, selection, selectionArgs, null, null, sortOrder);
			// 设置监听
			cursor.setNotificationUri(getContext().getContentResolver(), uri);
		}
		return cursor;

	}

	//数据访问类型，暂未实现
	@Override
	public String getType(Uri uri) {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	//更新数据，暂未实现
	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		throw new UnsupportedOperationException("Not yet implemented");
	}

}
