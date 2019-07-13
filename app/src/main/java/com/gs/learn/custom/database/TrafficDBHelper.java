package com.gs.learn.custom.database;

import java.util.ArrayList;

import com.gs.learn.custom.bean.AppInfo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TrafficDBHelper extends SQLiteOpenHelper {
	private static final String TAG = "TrafficDBHelper";
	private static final String DB_NAME = "traffic.db";
	private static final int DB_VERSION = 1;
	private static TrafficDBHelper mHelper = null;
	private SQLiteDatabase mDB = null;
	private static final String TABLE_NAME = "traffic_info";

	private TrafficDBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	private TrafficDBHelper(Context context, int version) {
		super(context, DB_NAME, null, version);
	}

	public static TrafficDBHelper getInstance(Context context, int version) {
		if (version > 0 && mHelper == null) {
			mHelper = new TrafficDBHelper(context, version);
		} else if (mHelper == null) {
			mHelper = new TrafficDBHelper(context);
		}
		return mHelper;
	}

	public SQLiteDatabase openReadLink() {
		if (mDB == null || mDB.isOpen() != true) {
			mDB = mHelper.getReadableDatabase();
		}
		return mDB;
	}

	public SQLiteDatabase openWriteLink() {
		if (mDB == null || mDB.isOpen() != true) {
			mDB = mHelper.getWritableDatabase();
		}
		return mDB;
	}

	public void closeLink() {
		if (mDB != null && mDB.isOpen() == true) {
			mDB.close();
			mDB = null;
		}
	}
	
	public String getDBName() {
		if (mHelper != null) {
			return mHelper.getDatabaseName();
		} else {
			return DB_NAME;
		}
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d(TAG, "onCreate");
		String drop_sql = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
		Log.d(TAG, "drop_sql:" + drop_sql);
		db.execSQL(drop_sql);
		String create_sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
				+ "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
				+ "month INTEGER NOT NULL," + "day INTEGER NOT NULL,"
				+ "uid INTEGER NOT NULL," + "label VARCHAR NOT NULL,"
				+ "package_name VARCHAR NOT NULL," + "icon_path VARCHAR NOT NULL,"
				+ "traffic LONG NOT NULL"
				+ ");";
		Log.d(TAG, "create_sql:" + create_sql);
		db.execSQL(create_sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
	
	public int delete(String condition) {
		int count = mDB.delete(TABLE_NAME, condition, null);
		return count;
	}

	public int deleteAll() {
		int count = mDB.delete(TABLE_NAME, "1=1", null);
		return count;
	}

	public long insert(AppInfo info) {
		ArrayList<AppInfo> infoArray = new ArrayList<AppInfo>();
		infoArray.add(info);
		return insert(infoArray);
	}
	
	public long insert(ArrayList<AppInfo> infoArray) {
		long result = -1;
		for (int i = 0; i < infoArray.size(); i++) {
			AppInfo info = infoArray.get(i);
			// 如果存在相同rowid的记录，则更新记录
			if (info.rowid > 0) {
				String condition = String.format("rowid='%d'", info.rowid);
				update(info, condition);
				result = info.rowid;
				continue;
			}
			// 如果存在同样日期的uid，则更新记录
			if (info.day>0 && info.uid>0) {
				String condition = String.format("day=%d and uid=%d", info.day, info.uid);
				ArrayList<AppInfo> tempArray = new ArrayList<AppInfo>();
				tempArray = query(condition);
				if (tempArray.size() > 0) {
					update(info, condition);
					result = tempArray.get(0).rowid;
					continue;
				}
			}
			// 不存在唯一性重复的记录，则插入新记录
			ContentValues cv = new ContentValues();
			cv.put("month", info.month);
			cv.put("day", info.day);
			cv.put("uid", info.uid);
			cv.put("label", info.label);
			cv.put("package_name", info.package_name);
			cv.put("icon_path", info.icon_path);
			cv.put("traffic", info.traffic);
			result = mDB.insert(TABLE_NAME, "", cv);
			// 添加成功后返回行号，失败后返回-1
			if (result == -1) {
				return result;
			}
		}
		return result;
	}

	public int update(AppInfo info, String condition) {
		ContentValues cv = new ContentValues();
		cv.put("month", info.month);
		cv.put("day", info.day);
		cv.put("uid", info.uid);
		cv.put("label", info.label);
		cv.put("package_name", info.package_name);
		cv.put("icon_path", info.icon_path);
		cv.put("traffic", info.traffic);
		int count = mDB.update(TABLE_NAME, cv, condition, null);
		return count;
	}

	public int update(AppInfo info) {
		return update(info, "rowid="+info.rowid);
	}

	public ArrayList<AppInfo> query(String condition) {
		String sql = String.format("select rowid,_id,month,day,uid,label,package_name,icon_path,traffic" +
				" from %s where %s;", TABLE_NAME, condition);
		Log.d(TAG, "query sql: "+sql);
		ArrayList<AppInfo> infoArray = new ArrayList<AppInfo>();
		Cursor cursor = mDB.rawQuery(sql, null);
		if (cursor.moveToFirst()) {
			for (;; cursor.moveToNext()) {
				AppInfo info = new AppInfo();
				info.rowid = cursor.getLong(0);
				info.xuhao = cursor.getInt(1);
				info.month = cursor.getInt(2);
				info.day = cursor.getInt(3);
				info.uid = cursor.getInt(4);
				info.label = cursor.getString(5);
				info.package_name = cursor.getString(6);
				info.icon_path = cursor.getString(7);
				info.traffic = cursor.getLong(8);
				infoArray.add(info);
				if (cursor.isLast() == true) {
					break;
				}
			}
		}
		cursor.close();
		return infoArray;
	}
	
	public AppInfo queryById(long rowid) {
		AppInfo info = null;
		ArrayList<AppInfo> infoArray = query(String.format("rowid='%d'", rowid));
		if (infoArray.size() > 0) {
			info = infoArray.get(0);
		}
		return info;
	}

}
