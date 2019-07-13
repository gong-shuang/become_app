package com.gs.learn.senior.database;

import java.util.ArrayList;

import com.gs.learn.senior.bean.CartInfo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class CartDBHelper extends SQLiteOpenHelper {
	private static final String TAG = "CartDBHelper";
	private static final String DB_NAME = "cart.db";
	private static final int DB_VERSION = 1;
	private static CartDBHelper mHelper = null;
	private SQLiteDatabase mDB = null;
	private static final String TABLE_NAME = "cart_info";

	private CartDBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	private CartDBHelper(Context context, int version) {
		super(context, DB_NAME, null, version);
	}

	public static CartDBHelper getInstance(Context context, int version) {
		if (version > 0 && mHelper == null) {
			mHelper = new CartDBHelper(context, version);
		} else if (mHelper == null) {
			mHelper = new CartDBHelper(context);
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
				+ "goods_id LONG NOT NULL," + "count INTEGER NOT NULL,"
				+ "update_time VARCHAR NOT NULL"
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

	public long insert(CartInfo info) {
		ArrayList<CartInfo> infoArray = new ArrayList<CartInfo>();
		infoArray.add(info);
		return insert(infoArray);
	}
	
	public long insert(ArrayList<CartInfo> infoArray) {
		long result = -1;
		for (int i = 0; i < infoArray.size(); i++) {
			CartInfo info = infoArray.get(i);
			Log.d(TAG, "goods_id="+info.goods_id+", count="+info.count);
			// 如果存在相同rowid的记录，则更新记录
			if (info.rowid > 0) {
				String condition = String.format("rowid='%d'", info.rowid);
				update(info, condition);
				result = info.rowid;
				continue;
			}
			// 不存在唯一性重复的记录，则插入新记录
			ContentValues cv = new ContentValues();
			cv.put("goods_id", info.goods_id);
			cv.put("count", info.count);
			cv.put("update_time", info.update_time);
			result = mDB.insert(TABLE_NAME, "", cv);
			// 添加成功后返回行号，失败后返回-1
			if (result == -1) {
				return result;
			}
		}
		return result;
	}

	public int update(CartInfo info, String condition) {
		ContentValues cv = new ContentValues();
		cv.put("goods_id", info.goods_id);
		cv.put("count", info.count);
		cv.put("update_time", info.update_time);
		int count = mDB.update(TABLE_NAME, cv, condition, null);
		return count;
	}

	public int update(CartInfo info) {
		return update(info, "rowid="+info.rowid);
	}

	public ArrayList<CartInfo> query(String condition) {
		String sql = String.format("select rowid,_id,goods_id,count,update_time" +
				" from %s where %s;", TABLE_NAME, condition);
		Log.d(TAG, "query sql: "+sql);
		ArrayList<CartInfo> infoArray = new ArrayList<CartInfo>();
		Cursor cursor = mDB.rawQuery(sql, null);
		if (cursor.moveToFirst()) {
			for (;; cursor.moveToNext()) {
				CartInfo info = new CartInfo();
				info.rowid = cursor.getLong(0);
				info.xuhao = cursor.getInt(1);
				info.goods_id = cursor.getLong(2);
				info.count = cursor.getInt(3);
				info.update_time = cursor.getString(4);
				infoArray.add(info);
				if (cursor.isLast() == true) {
					break;
				}
			}
		}
		cursor.close();
		return infoArray;
	}
	
	public CartInfo queryById(long rowid) {
		CartInfo info = null;
		ArrayList<CartInfo> infoArray = query(String.format("rowid='%d'", rowid));
		if (infoArray.size() > 0) {
			info = infoArray.get(0);
		}
		return info;
	}

	public CartInfo queryByGoodsId(long goods_id) {
		CartInfo info = null;
		ArrayList<CartInfo> infoArray = query(String.format("goods_id='%d'", goods_id));
		if (infoArray.size() > 0) {
			info = infoArray.get(0);
		}
		return info;
	}

}
