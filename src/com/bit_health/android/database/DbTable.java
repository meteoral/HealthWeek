/********************************************************
 * 类名：DbTable.java
 *
 * 作者：陈建平
 * 主要功能：
 * 创建日期：下午4:44:29
 ********************************************************/
package com.bit_health.android.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author Administrator
 *
 */
public class DbTable {
	private String mTabblename;
	private SQLiteDatabase mDb;
	private static final String KEY_COLUMN_ID = "id";
	private static final String KEY_COLUMN_SID = "sid";
	private static final String KEY_COLUMN_TIMESTAMP = "timestamp";
	private static final String KEY_COLUMN_MEASURETYPE = "measuretype";
	private static final String KEY_COLUMN_STATUS = "status";
	private static final String KEY_COLUMN_DATA = "data";
	private static final String KEY_COLUMN_DATATYPE = "datatype"; //详细还是简要信息
	
	public DbTable(String name,SQLiteDatabase db){
		mTabblename = name;
		mDb = db;
	}
	
	DbTable Create(String sql){
		mDb.execSQL(sql);
		return this;
	}
	
	public long insert(DatabaseBean bean) {
		ContentValues values = new ContentValues();
		values.put(KEY_COLUMN_SID, bean.mServerId);
		values.put(KEY_COLUMN_TIMESTAMP, bean.mTime);
		values.put(KEY_COLUMN_MEASURETYPE, bean.mMeasureType);
		values.put(KEY_COLUMN_STATUS, bean.mStatus);
		values.put(KEY_COLUMN_DATATYPE, bean.mDataType);
		values.put(KEY_COLUMN_DATA, bean.mData);
		return mDb.insert(mTabblename, null, values);
	}
	
	public long updateFromSid(DatabaseBean bean, String sid,String measure) {
		ContentValues values = new ContentValues();
		values.put(KEY_COLUMN_SID, bean.mServerId);
		values.put(KEY_COLUMN_TIMESTAMP, bean.mTime);
		values.put(KEY_COLUMN_MEASURETYPE, bean.mMeasureType);
		values.put(KEY_COLUMN_STATUS, bean.mStatus);
		values.put(KEY_COLUMN_DATATYPE, bean.mDataType);
		values.put(KEY_COLUMN_DATA, bean.mData);
		return mDb.update(mTabblename, values, KEY_COLUMN_SID + "=? and "
				+ KEY_COLUMN_MEASURETYPE + "=?", new String[] { sid, measure });
	}
	public DatabaseBean getItemFromSid(String sid,String measure) {
		String[] args = {sid,measure};
		Cursor cursor = mDb.query(mTabblename, null, KEY_COLUMN_SID + "=? and "
				+ KEY_COLUMN_MEASURETYPE + "=?", args, null, null, null);
		if(cursor != null && cursor.moveToFirst()){
			DatabaseBean bean = new DatabaseBean();
			bean.mId = cursor.getInt(cursor.getColumnIndex(KEY_COLUMN_ID));
			bean.mData = cursor.getString(cursor.getColumnIndex(KEY_COLUMN_DATA));
			bean.mDataType = cursor.getInt(cursor.getColumnIndex(KEY_COLUMN_DATATYPE));
			bean.mMeasureType = cursor.getString(cursor.getColumnIndex(KEY_COLUMN_MEASURETYPE));
			bean.mServerId = cursor.getString(cursor.getColumnIndex(KEY_COLUMN_SID));
			bean.mStatus = cursor.getInt(cursor.getColumnIndex(KEY_COLUMN_STATUS));
			bean.mTime = cursor.getLong(cursor.getColumnIndex(KEY_COLUMN_TIMESTAMP));
			cursor.close();
			cursor = null;
			return bean;
		}
		return null;
	}
	
	public DatabaseBean getItemFromTime(long time,String measure) {
		String[] args = {String.valueOf(time),measure};
		Cursor cursor = mDb.query(mTabblename, null, KEY_COLUMN_TIMESTAMP + "=? and "
				+ KEY_COLUMN_MEASURETYPE + "=?", args, null, null, null);
		if(cursor != null && cursor.moveToFirst()){
			DatabaseBean bean = new DatabaseBean();
			bean.mId = cursor.getInt(cursor.getColumnIndex(KEY_COLUMN_ID));
			bean.mData = cursor.getString(cursor.getColumnIndex(KEY_COLUMN_DATA));
			bean.mDataType = cursor.getInt(cursor.getColumnIndex(KEY_COLUMN_DATATYPE));
			bean.mMeasureType = cursor.getString(cursor.getColumnIndex(KEY_COLUMN_MEASURETYPE));
			bean.mServerId = cursor.getString(cursor.getColumnIndex(KEY_COLUMN_SID));
			bean.mStatus = cursor.getInt(cursor.getColumnIndex(KEY_COLUMN_STATUS));
			bean.mTime = cursor.getLong(cursor.getColumnIndex(KEY_COLUMN_TIMESTAMP));
			cursor.close();
			cursor = null;
			return bean;
		}
		return null;
	}
	public void insert(List<DatabaseBean> beans) {
//		mDb.beginTransaction();
		for (DatabaseBean bean : beans) {
			if (getItemFromSid(bean.mServerId, bean.mMeasureType) == null) {
				long id = insert(bean);
				if (id >= 0) {
					bean.mId = id;
				}
			}
		}
//		mDb.endTransaction();
	}
	
	public List<DatabaseBean> getItems(int begin, int count) {
		Cursor cursor = mDb.query(mTabblename, null, null, null, null, null,
				KEY_COLUMN_TIMESTAMP);
		List<DatabaseBean> beans = new ArrayList<DatabaseBean>();
		int pos = 0;
		if (cursor != null && cursor.moveToLast()){
			do {
				if (pos >= begin) {
					DatabaseBean bean = new DatabaseBean();
					bean.mId = cursor.getInt(cursor
							.getColumnIndex(KEY_COLUMN_ID));
					bean.mData = cursor.getString(cursor
							.getColumnIndex(KEY_COLUMN_DATA));
					bean.mDataType = cursor.getInt(cursor
							.getColumnIndex(KEY_COLUMN_DATATYPE));
					bean.mMeasureType = cursor.getString(cursor
							.getColumnIndex(KEY_COLUMN_MEASURETYPE));
					bean.mServerId = cursor.getString(cursor
							.getColumnIndex(KEY_COLUMN_SID));
					bean.mStatus = cursor.getInt(cursor
							.getColumnIndex(KEY_COLUMN_STATUS));
					bean.mTime = cursor.getLong(cursor
							.getColumnIndex(KEY_COLUMN_TIMESTAMP));
					beans.add(bean);
				}
				pos++;
			} while (cursor.moveToPrevious() && (pos - begin) < count);
			cursor.close();
			cursor = null;
		}
		return beans;
	}
	public List<DatabaseBean> getItemsFromDate(int begin, int count,long dateBegin,long dateEnd) {
		String[] args = {""+dateBegin,""+dateEnd};
		Cursor cursor = mDb.query(mTabblename, null, KEY_COLUMN_TIMESTAMP
				+ ">? and " + KEY_COLUMN_TIMESTAMP + "<?", args, null, null,
				KEY_COLUMN_TIMESTAMP);
		List<DatabaseBean> beans = new ArrayList<DatabaseBean>();
		int pos = 0;
		if (cursor != null && cursor.moveToLast()) {
			do {
				if (pos >= begin){
					DatabaseBean bean = new DatabaseBean();
					bean.mId = cursor.getInt(cursor
							.getColumnIndex(KEY_COLUMN_ID));
					bean.mData = cursor.getString(cursor
							.getColumnIndex(KEY_COLUMN_DATA));
					bean.mDataType = cursor.getInt(cursor
							.getColumnIndex(KEY_COLUMN_DATATYPE));
					bean.mMeasureType = cursor.getString(cursor
							.getColumnIndex(KEY_COLUMN_MEASURETYPE));
					bean.mServerId = cursor.getString(cursor
							.getColumnIndex(KEY_COLUMN_SID));
					bean.mStatus = cursor.getInt(cursor
							.getColumnIndex(KEY_COLUMN_STATUS));
					bean.mTime = cursor.getLong(cursor
							.getColumnIndex(KEY_COLUMN_TIMESTAMP));
					beans.add(bean);
				}
				pos++;
			} while (cursor.moveToPrevious() && (pos - begin) < count);
			cursor.close();
			cursor = null;
		}
		return beans;
	}
}
