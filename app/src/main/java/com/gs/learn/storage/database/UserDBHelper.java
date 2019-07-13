package com.gs.learn.storage.database;

import java.util.ArrayList;

import com.gs.learn.storage.bean.UserInfo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class UserDBHelper extends SQLiteOpenHelper {
	private static final String TAG = "UserDBHelper";
	private static final String DB_NAME = "user.db";
	private static final int DB_VERSION = 1;
	private static UserDBHelper mHelper = null;
	private SQLiteDatabase mDB = null;
	private static final String TABLE_NAME = "user_info";

	private UserDBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	private UserDBHelper(Context context, int version) {
		super(context, DB_NAME, null, version);
	}

	public static UserDBHelper getInstance(Context context, int version) {
		if (version > 0 && mHelper == null) {
			mHelper = new UserDBHelper(context, version);
		} else if (mHelper == null) {
			mHelper = new UserDBHelper(context);
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
				+ "_id INTEGER PRIMARY KEY  AUTOINCREMENT NOT NULL,"
				+ "name VARCHAR NOT NULL," + "age INTEGER NOT NULL,"
				+ "height LONG NOT NULL," + "weight FLOAT NOT NULL,"
				+ "married INTEGER NOT NULL," + "update_time VARCHAR NOT NULL"
				//演示数据库升级时要先把下面这行注释
				+ ",phone VARCHAR" + ",password VARCHAR"
				+ ");";
		Log.d(TAG, "create_sql:" + create_sql);
		db.execSQL(create_sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d(TAG, "onUpgrade oldVersion="+oldVersion+", newVersion="+newVersion);
		if (newVersion > 1) {
			//Android的ALTER命令不支持一次添加多列，只能分多次添加
			String alter_sql = "ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + "phone VARCHAR;";
			Log.d(TAG, "alter_sql:" + alter_sql);
			db.execSQL(alter_sql);
			alter_sql = "ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + "password VARCHAR;";
			Log.d(TAG, "alter_sql:" + alter_sql);
			db.execSQL(alter_sql);
		}
	}

	public int delete(String condition) {
		int count = mDB.delete(TABLE_NAME, condition, null);
		return count;
	}

	public int deleteAll() {
		int count = mDB.delete(TABLE_NAME, "1=1", null);
		return count;
	}

	public long insert(UserInfo info) {
		ArrayList<UserInfo> infoArray = new ArrayList<UserInfo>();
		infoArray.add(info);
		return insert(infoArray);
	}
	
	public long insert(ArrayList<UserInfo> infoArray) {
		long result = -1;
		for (int i = 0; i < infoArray.size(); i++) {
			UserInfo info = infoArray.get(i);
			ArrayList<UserInfo> tempArray = new ArrayList<UserInfo>();
			// 如果存在同名记录，则更新记录
			// 注意条件语句的等号后面要用单引号括起来
			if (info.name!=null && info.name.length()>0) {
				String condition = String.format("name='%s'", info.name);
				tempArray = query(condition);
				if (tempArray.size() > 0) {
					update(info, condition);
					result = tempArray.get(0).rowid;
					continue;
				}
			}
			// 如果存在同样的手机号码，则更新记录
			if (info.phone!=null && info.phone.length()>0) {
				String condition = String.format("phone='%s'", info.phone);
				tempArray = query(condition);
				if (tempArray.size() > 0) {
					update(info, condition);
					result = tempArray.get(0).rowid;
					continue;
				}
			}
			// 不存在唯一性重复的记录，则插入新记录
			ContentValues cv = new ContentValues();
			cv.put("name", info.name);
			cv.put("age", info.age);
			cv.put("height", info.height);
			cv.put("weight", info.weight);
			cv.put("married", info.married);
			cv.put("update_time", info.update_time);
			cv.put("phone", info.phone);
			cv.put("password", info.password);
			result = mDB.insert(TABLE_NAME, "", cv);
			// 添加成功后返回行号，失败后返回-1
			if (result == -1) {
				return result;
			}
		}
		return result;
	}

	public int update(UserInfo info, String condition) {
		ContentValues cv = new ContentValues();
		cv.put("name", info.name);
		cv.put("age", info.age);
		cv.put("height", info.height);
		cv.put("weight", info.weight);
		cv.put("married", info.married);
		cv.put("update_time", info.update_time);
		cv.put("phone", info.phone);
		cv.put("password", info.password);
		int count = mDB.update(TABLE_NAME, cv, condition, null);
		return count;
	}

	public int update(UserInfo info) {
		return update(info, "rowid="+info.rowid);
	}

	public ArrayList<UserInfo> query(String condition) {
		String sql = String.format("select rowid,_id,name,age,height,weight,married,update_time," +
				"phone,password from %s where %s;", TABLE_NAME, condition);
		Log.d(TAG, "query sql: "+sql);
		ArrayList<UserInfo> infoArray = new ArrayList<UserInfo>();
		Cursor cursor = mDB.rawQuery(sql, null);
		if (cursor.moveToFirst()) {
			for (;; cursor.moveToNext()) {
				UserInfo info = new UserInfo();
				info.rowid = cursor.getLong(0);
				info.xuhao = cursor.getInt(1);
				info.name = cursor.getString(2);
				info.age = cursor.getInt(3);
				info.height = cursor.getLong(4);
				info.weight = cursor.getFloat(5);
				//SQLite没有布尔型，用0表示false，用1表示true
				info.married = (cursor.getInt(6)==0)?false:true;
				info.update_time = cursor.getString(7);
				info.phone = cursor.getString(8);
				info.password = cursor.getString(9);
				infoArray.add(info);
				if (cursor.isLast() == true) {
					break;
				}
			}
		}
		cursor.close();
		return infoArray;
	}
	
	public UserInfo queryByPhone(String phone) {
		UserInfo info = null;
		ArrayList<UserInfo> infoArray = query(String.format("phone='%s'", phone));
		if (infoArray.size() > 0) {
			info = infoArray.get(0);
		}
		return info;
	}
	
}
