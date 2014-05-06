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
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author Administrator
 *
 */
public class FileDbTable {
	private String mTabblename;// 数据库表名
	private SQLiteDatabase mDb;
	public static final String ACTION_DATABASE_CHANGED = "ACTION_DATABASE_CHANGED";
	private static final String KEY_COLUMN_ID = "id";
	private static final String KEY_COLUMN_TIMESTAMP = "timestamp";
	private static final String KEY_COLUMN_FILENAME = "filename";
	private static final String KEY_COLUMN_TIMELENGTH = "timelength";
	private static final String KEY_COLUMN_CONFIG = "config";
	private static final String KEY_COLUMN_FILEINEDX = "fileindex"; // 文件序列号
	private static final String KEY_COLUMN_STATUS = "status"; // 状态: 0:表示还没有上传完成，1：表示已经蓝牙传输完成
	private static final String KEY_COLUMN_DEVICE = "device"; // 设备MAC地址，设备的唯一标识
	
	private Context mContext;
	
	public FileDbTable(Context context,String name,SQLiteDatabase db){
		mTabblename = name;
		mDb = db;
		this.mContext = context;
	}
	
	FileDbTable Create(String sql){
		mDb.execSQL(sql);
		return this;
	}
	
	public long insert(FileDbBean bean) {
		ContentValues values = new ContentValues();
		values.put(KEY_COLUMN_TIMESTAMP, bean.mTime);
		values.put(KEY_COLUMN_STATUS, bean.mStatus);
		values.put(KEY_COLUMN_FILENAME, bean.mFileName);
		values.put(KEY_COLUMN_CONFIG, bean.mConfigPar);
		values.put(KEY_COLUMN_TIMELENGTH, bean.mTimeLength);
		values.put(KEY_COLUMN_DEVICE, bean.mDeviceMac);
		values.put(KEY_COLUMN_FILEINEDX, bean.mFileIndex);
		long ret = mDb.insert(mTabblename, null, values);
		mContext.sendBroadcast(new Intent(ACTION_DATABASE_CHANGED));
		return ret;
	}
	
	public long update(FileDbBean bean, long id) {
		ContentValues values = new ContentValues();
		values.put(KEY_COLUMN_TIMESTAMP, bean.mTime);
		values.put(KEY_COLUMN_STATUS, bean.mStatus);
		values.put(KEY_COLUMN_CONFIG, bean.mConfigPar);
		values.put(KEY_COLUMN_FILENAME, bean.mFileName);
		values.put(KEY_COLUMN_TIMELENGTH, bean.mTimeLength);
		values.put(KEY_COLUMN_DEVICE, bean.mDeviceMac);
		values.put(KEY_COLUMN_FILEINEDX, bean.mFileIndex);
		
		int ret = mDb.update(mTabblename, values, KEY_COLUMN_ID + "=?", new String[] { String.valueOf(id) });
		mContext.sendBroadcast(new Intent(ACTION_DATABASE_CHANGED));
		return ret;
	}
	
	public void delete(long id) {
		mDb.delete(mTabblename, KEY_COLUMN_ID + "=?",
				new String[] { String.valueOf(id) });
		mContext.sendBroadcast(new Intent(ACTION_DATABASE_CHANGED));
	}
	public void delete(String address,int status) {
		mDb.delete(mTabblename, KEY_COLUMN_DEVICE + "=? and "+KEY_COLUMN_STATUS+"=?",
				new String[] { address,String.valueOf(status)});
		mContext.sendBroadcast(new Intent(ACTION_DATABASE_CHANGED));
	}
	public void delete(String filename) {
		mDb.delete(mTabblename, KEY_COLUMN_FILENAME + "=?",
				new String[] { filename});
		mContext.sendBroadcast(new Intent(ACTION_DATABASE_CHANGED));
	}
	public FileDbBean get(String deviceMac,int fileindex) {
		Cursor cursor = mDb.query(mTabblename, null, KEY_COLUMN_DEVICE
				+ "=? and " + KEY_COLUMN_FILEINEDX + "=?",
				new String[] { deviceMac,String.valueOf(fileindex) },
				null, null, null);
		if (cursor != null && cursor.moveToLast()){
			FileDbBean bean = new FileDbBean();
			bean.mId = cursor.getInt(cursor
					.getColumnIndex(KEY_COLUMN_ID));
			bean.mTime = cursor.getLong(cursor
					.getColumnIndex(KEY_COLUMN_TIMESTAMP));
			bean.mStatus = cursor.getInt(cursor
					.getColumnIndex(KEY_COLUMN_STATUS));
			bean.mFileName = cursor.getString(cursor
					.getColumnIndex(KEY_COLUMN_FILENAME));
			bean.mConfigPar = cursor.getString(cursor
					.getColumnIndex(KEY_COLUMN_CONFIG));
			bean.mTimeLength =  cursor.getLong(cursor
					.getColumnIndex(KEY_COLUMN_TIMELENGTH));
			bean.mFileIndex = cursor.getInt(cursor
					.getColumnIndex(KEY_COLUMN_FILEINEDX));
			bean.mDeviceMac = deviceMac;
			cursor.close();
			cursor = null;
			return bean;
		}
		return null;
	}
	
	public FileDbBean get(String deviceMac,int status,int fileindex) {
		Cursor cursor = mDb.query(mTabblename, null, KEY_COLUMN_DEVICE
				+ "=? and " + KEY_COLUMN_STATUS + "=? and "
				+ KEY_COLUMN_FILEINEDX + "=?",
				new String[] { deviceMac, String.valueOf(status),  String.valueOf(fileindex) },
				null, null, null);
		if (cursor != null && cursor.moveToLast()){
			FileDbBean bean = new FileDbBean();
			bean.mId = cursor.getInt(cursor
					.getColumnIndex(KEY_COLUMN_ID));
			bean.mTime = cursor.getLong(cursor
					.getColumnIndex(KEY_COLUMN_TIMESTAMP));
			bean.mStatus = cursor.getInt(cursor
					.getColumnIndex(KEY_COLUMN_STATUS));
			bean.mFileName = cursor.getString(cursor
					.getColumnIndex(KEY_COLUMN_FILENAME));
			bean.mConfigPar = cursor.getString(cursor
					.getColumnIndex(KEY_COLUMN_CONFIG));
			bean.mTimeLength =  cursor.getLong(cursor
					.getColumnIndex(KEY_COLUMN_TIMELENGTH));
			bean.mFileIndex = cursor.getInt(cursor
					.getColumnIndex(KEY_COLUMN_FILEINEDX));
			bean.mDeviceMac = deviceMac;
			cursor.close();
			cursor = null;
			return bean;
		}
		return null;
	}
	public FileDbBean get(int id) {
		Cursor cursor = mDb.query(mTabblename, null, KEY_COLUMN_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null);
		if (cursor != null && cursor.moveToLast()){
			FileDbBean bean = new FileDbBean();
			bean.mId = cursor.getInt(cursor
					.getColumnIndex(KEY_COLUMN_ID));
			bean.mTime = cursor.getLong(cursor
					.getColumnIndex(KEY_COLUMN_TIMESTAMP));
			bean.mStatus = cursor.getInt(cursor
					.getColumnIndex(KEY_COLUMN_STATUS));
			bean.mFileName = cursor.getString(cursor
					.getColumnIndex(KEY_COLUMN_FILENAME));
			bean.mConfigPar = cursor.getString(cursor
					.getColumnIndex(KEY_COLUMN_CONFIG));
			bean.mTimeLength =  cursor.getLong(cursor
					.getColumnIndex(KEY_COLUMN_TIMELENGTH));
			bean.mFileIndex = cursor.getInt(cursor
					.getColumnIndex(KEY_COLUMN_FILEINEDX));
			bean.mDeviceMac =cursor.getString(cursor
					.getColumnIndex(KEY_COLUMN_DEVICE));
			cursor.close();
			cursor = null;
			return bean;
		}
		return null;
	}
	
	public List<FileDbBean> getFailUploadFiles() {
		Cursor cursor = mDb.query(mTabblename, null, KEY_COLUMN_STATUS + "=?",
				new String[] { String.valueOf(1) }, null, null,
				KEY_COLUMN_TIMESTAMP);
		List<FileDbBean> beans = new ArrayList<FileDbBean>();
		if (cursor != null && cursor.moveToLast()) {
			do {
				FileDbBean bean = new FileDbBean();
				bean.mId = cursor.getInt(cursor.getColumnIndex(KEY_COLUMN_ID));
				bean.mTime = cursor.getLong(cursor
						.getColumnIndex(KEY_COLUMN_TIMESTAMP));
				bean.mStatus = cursor.getInt(cursor
						.getColumnIndex(KEY_COLUMN_STATUS));
				bean.mFileName = cursor.getString(cursor
						.getColumnIndex(KEY_COLUMN_FILENAME));
				bean.mConfigPar = cursor.getString(cursor
						.getColumnIndex(KEY_COLUMN_CONFIG));
				bean.mTimeLength =  cursor.getLong(cursor
						.getColumnIndex(KEY_COLUMN_TIMELENGTH));
				bean.mFileIndex = cursor.getInt(cursor
						.getColumnIndex(KEY_COLUMN_FILEINEDX));
				bean.mDeviceMac = cursor.getString(cursor
						.getColumnIndex(KEY_COLUMN_DEVICE));
				beans.add(bean);
			} while (cursor.moveToPrevious());
			cursor.close();
			cursor = null;
		}
		return beans;
	}
}
