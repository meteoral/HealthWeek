package com.bit_health.android.controllers.beans;

import org.json.JSONException;
import org.json.JSONObject;

public class BpInfoBean extends BpBriefBean {
	public static final String BP_ABNORMAL = "abnormal";
	public static final String BP_PP = "PP";
	public static final String BP_MAP = "MAP";
	public static final String BP_RPP = "RPP";
	public static final String BP_SBPLEVEL = "SBPLevel";
	public static final String BP_DBPLEVEL = "DBPLevel";
	public static final String BP_PPLEVEL = "PPLevel";
	public static final String BP_PRLEVEL = "PRLevel";
	public static final String BP_MAPLEVEL = "MAPLevel";
	public static final String BP_HYPER = "Hyper";

	public String mAbnormal = "";
	public int mPP = 0;
	public int mMap = 0;
	public int mRpp = 0;
	public int mSBPLevel = 0;
	public int mDBPLevel = 0;
	public int mPPLevel = 0;
	public int mPRLevel = 0;
	public int mMAPLevel = 0;
	public int mHyper = 0;
	
	public int mType; // 详细信息还是简要信息
	public String mData; // 缓存数据
	
	public boolean bIsNormal = true; // 体检是否正常
	
	public void parserJson(String json){
		mData = json; // 保存缓存数据
		super.parserJson(json);
		try {
			JSONObject jobj = new JSONObject(json);
			mAbnormal = jobj.optString(BP_ABNORMAL);
			mPP = jobj.optInt(BP_PP);
			mMap = jobj.optInt(BP_MAP);
			mRpp = jobj.optInt(BP_RPP);
			mSBPLevel = jobj.optInt(BP_SBPLEVEL);
			mPPLevel = jobj.optInt(BP_PPLEVEL);
			mPRLevel = jobj.optInt(BP_PRLEVEL);
			mMAPLevel = jobj.optInt(BP_MAPLEVEL);
			mHyper = jobj.optInt(BP_HYPER);
			// 检测是否正常
			if(mAbnormal.equalsIgnoreCase("正常")){
				bIsNormal = true;
			}else{
				bIsNormal = false;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
