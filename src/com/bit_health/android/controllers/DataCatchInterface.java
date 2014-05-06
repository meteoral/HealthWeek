/********************************************************
 * 类名：DataCatchInterface.java
 *
 * 作者：陈建平
 * 主要功能：
 * 创建日期：下午4:13:07
 ********************************************************/
package com.bit_health.android.controllers;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import com.bit_health.android.controllers.beans.JsonBase;
import com.bit_health.android.database.DatabaseBean;
import com.bit_health.android.database.RoleDatabase;
import com.bit_health.android.util.TimeFormatUtil;

/**
 * @author Administrator
 *
 */
public class DataCatchInterface {
//	public static final String TABLE_ALL = "all_data";
//	public static final String TABLE_ECG = "all_ecg_data";
//	public static final String TABLE_BS = "all_bs_data";
//	public static final String TABLE_BP = "all_bp_data";
//	public static final String TABLE_ABNORMAL_ONE_DAY = "abnormal_one_day";
//	public static final String TABLE_ABNORMAL_ONE_MONTH = "abnormal_one_month";
//	public static final String TABLE_ABNORMAL_ONE_YEAR = "abnormal_one_year";
	private static DataCatchInterface instance;
	private Context mContext;
	private DataCatchInterface(Context context){
		mContext = context.getApplicationContext();
		
	}
	public static DataCatchInterface getInstance(Context context){
		if(instance == null){
			instance = new DataCatchInterface(context);
		}
		return instance;
	}
	
	public static String getTableName(String measure, String error) {
		return measure + "_" + error;
	}
	
	public long updateData(String roleid, String table, JsonBase bean) {
		DatabaseBean dbBean = DatabaseBean.changeMesureToDbBean(bean);
		RoleDatabase dbTable = new RoleDatabase(mContext, roleid);
		long ret = -1;
		if (dbTable.CreateDb()) {
			ret = dbTable.openTable(table).updateFromSid(dbBean,
					dbBean.mServerId, dbBean.mMeasureType);
			dbTable.closeDb();
		}
		return ret;
	}

	public JsonBase getItemFromSid(String roleid, String table,String sid, String measure) {
		RoleDatabase dbTable = new RoleDatabase(mContext, roleid);
		if (dbTable.CreateDb()) {
			DatabaseBean dbBean = dbTable.openTable(table).getItemFromSid(sid,
					measure);
			if (dbBean != null) {
				dbTable.closeDb();
				return dbBean.changeDbToMeasure();
			}
			dbTable.closeDb();
		}
		return null;
		
	}
	
	public JsonBase getItemFromTime(String roleid, String table,long time, String measure) {
		RoleDatabase dbTable = new RoleDatabase(mContext, roleid);
		if (dbTable.CreateDb()) {
			DatabaseBean dbBean = dbTable.openTable(table).getItemFromTime(time,
					measure);
			if (dbBean != null) {
				dbTable.closeDb();
				return dbBean.changeDbToMeasure();
			}
			dbTable.closeDb();
		}
		return null;
		
	}

	public void insert(String roleid, String table, List<JsonBase> beans) {
		RoleDatabase dbTable = new RoleDatabase(mContext, roleid);
		if (dbTable.CreateDb()) {
			List<DatabaseBean> dbBeans = new ArrayList<DatabaseBean>();
			for (JsonBase bean : beans) {
				dbBeans.add(DatabaseBean.changeMesureToDbBean(bean));
			}
			dbTable.openTable(table).insert(dbBeans);
			dbTable.closeDb();
		}
	}
	
	public List<JsonBase> getItems(String roleid, String table, int begin,
			int count) {
		RoleDatabase dbTable = new RoleDatabase(mContext, roleid);
		List<JsonBase> beans = new ArrayList<JsonBase>();
		if (dbTable.CreateDb()) {
			List<DatabaseBean> dbBeans = dbTable.openTable(table).getItems(
					begin, count);
			if (dbBeans != null) {
				for (DatabaseBean dbBean : dbBeans) {
					beans.add(dbBean.changeDbToMeasure());
				}
			}
			dbTable.closeDb();
		}
		return beans;
	}
	public List<JsonBase> getItemsFromDate(String roleid, String table, int begin,
			int count,String dateBegin,String dateEnd) {
		RoleDatabase dbTable = new RoleDatabase(mContext, roleid);
		List<JsonBase> beans = new ArrayList<JsonBase>();
		if (dbTable.CreateDb()) {
			long lBegin = TimeFormatUtil.strToDateLong(dateBegin);
			long lEnd = TimeFormatUtil.strToDateLong(dateEnd);
			List<DatabaseBean> dbBeans = dbTable.openTable(table).getItemsFromDate(
					begin, count,lBegin,lEnd);
			if (dbBeans != null) {
				for (DatabaseBean dbBean : dbBeans) {
					beans.add(dbBean.changeDbToMeasure());
				}
			}
			dbTable.closeDb();
		}
		return beans;
	}
}
