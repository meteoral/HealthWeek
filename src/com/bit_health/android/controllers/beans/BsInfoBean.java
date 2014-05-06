package com.bit_health.android.controllers.beans;

import org.json.JSONException;
import org.json.JSONObject;


public class BsInfoBean extends BsBriefBean {
	public static final String SUGGEST_CONTENT = "suggest_content";
	public static final String DOCTOR_ID = "doctor_id";
	public static final String SUGGEST_TIME = "suggest_time";
	
	public String mSuggestContent = "";
	public int mDoctorId = 0;
	public int mSuggestTime = 0;
	
	public int mType; // 详细信息还是简要信息
	public String mData; // 缓存数据
	public boolean bIsNormal = true; // 体检是否正常
	
	public void parserJson(String json){
		mData = json; // 保存缓存数据
		super.parserJson(json);
		try {
			JSONObject jobj = new JSONObject(json);
			mSuggestContent = jobj.optString(SUGGEST_CONTENT);
			mDoctorId = jobj.optInt(DOCTOR_ID);
			mSuggestTime = jobj.optInt(SUGGEST_TIME);
			
			if (mLimosis_value_ab.equalsIgnoreCase("正常")
					&& mBefore_lunch_value_ab.equalsIgnoreCase("正常")
					&& mAfter_lunch_value_ab.equalsIgnoreCase("正常")
					&& mBefore_sleep_value_ab.equalsIgnoreCase("正常")) {
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
