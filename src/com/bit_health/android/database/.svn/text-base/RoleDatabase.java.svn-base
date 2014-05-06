/********************************************************
 * 类名：RoleDatabase.java
 *
 * 作者：陈建平
 * 主要功能：
 * 创建日期：下午4:29:09
 ********************************************************/
package com.bit_health.android.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author Administrator
 *
 */
public class RoleDatabase {
	public final static String FILE_TABLE_NAME = "bl_upload_file";
	private Context mContext;
	private String  mName;
	private SQLiteDatabase mDb;
	public RoleDatabase(Context context,String name){
		mName = name;
		mContext =  context;
	}
	
	public boolean CreateDb() {
		mDb = mContext.openOrCreateDatabase(mName+".db", Context.MODE_PRIVATE,
				null);
		if (mDb == null) {
			return false;
		} else {
			return true;
		}
	}
	
	public DbTable openTable(String tablename) {
		String sql = "CREATE TABLE IF NOT EXISTS "
				+ tablename
				+ " (id INTEGER primary key autoincrement, sid VARCHAR(24), timestamp BIGINT,measuretype VARCHAR(24),datatype INTEGER, status INTEGER,data VARCHAR(2048))";
		mDb.execSQL(sql);
		DbTable table = new DbTable(tablename, mDb);
		return table.Create(sql);
	}
	
	public FileDbTable openFileTable(String tablename) {
		String sql = "CREATE TABLE IF NOT EXISTS "
				+ tablename
				+ " (id INTEGER primary key autoincrement, timestamp BIGINT,device VARCHAR(48), config VARCHAR(512),status INTEGER, timelength BIGINT,fileindex INTEGER,filename VARCHAR(256))";
		mDb.execSQL(sql);
		FileDbTable table = new FileDbTable(mContext,tablename, mDb);
		return table.Create(sql);
	}
	public void closeDb() {
		if(mDb != null){
			mDb.close();
			mDb = null;
		}
	}
}
