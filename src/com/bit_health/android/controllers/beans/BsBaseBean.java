package com.bit_health.android.controllers.beans;

import org.json.JSONException;
import org.json.JSONObject;

public class BsBaseBean extends JsonBase {
	public static final String _ID = "_id";
	public static final String BS_DATE = "date";
	public static final String BS_LIMOSIS = "limosis_value";
	public static final String BS_BEFORE_LUNCH = "before_lunch_value";
	public static final String BS_AFTER_LUCH = "after_lunch_value";
	public static final String BS_BEFORE_SLEEP = "before_sleep_value";
	public static final String BS_ANALYSIS = "analysis_result";
	
	public static final String BS_LIMOSIS_AB = "limosis_value_ab";
	public static final String BS_BEFORE_LUNCH_AB = "before_lunch_value_ab";
	public static final String BS_AFTER_LUNCH_AB = "after_lunch_value_ab";
	public static final String BS_BEFORE_SLEEP_AB = "before_sleep_value_ab";
	
	
	public String mId = "";
	public String mTimeStamp = "";
	public String mLimosis = "";
	public String mBLunch = "";
	public String mALunch = "";
	public String mBSleep = "";
	public String mAnalysisResult = "";
	public String mLimosis_value_ab = ""; //空腹时状态
	public String mBefore_lunch_value_ab = ""; // 午饭前时状态
	public String mAfter_lunch_value_ab = ""; // 午饭后时状态
	public String mBefore_sleep_value_ab = ""; //睡前时状态
	
	public String toJsonString(String key) {
		return super.toJsonString(key, toString());
	}
	@Override
	public String toString(){
		JSONObject jobj = new JSONObject();
		try {
			jobj.put(_ID, mId);
			jobj.put(BS_DATE, mTimeStamp);
			jobj.put(BS_LIMOSIS, mLimosis);
			jobj.put(BS_BEFORE_LUNCH, mBLunch);
			jobj.put(BS_AFTER_LUCH, mALunch);
			jobj.put(BS_BEFORE_SLEEP, mBSleep);
			jobj.put(BS_ANALYSIS, mAnalysisResult);
			return jobj.toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public void toJsonBean(String json,String key) {
		JSONObject jobj = super.toBean(json);
		try {
			parserJson(jobj.getString(key));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void parserJson(String json) {
		try {
			JSONObject jobj = new JSONObject(json);
			mId = jobj.getString(_ID);
			mTimeStamp = jobj.optString(BS_DATE);
			mLimosis = jobj.optString(BS_LIMOSIS);
			mBLunch = jobj.optString(BS_BEFORE_LUNCH);
			mALunch = jobj.optString(BS_AFTER_LUCH);
			mBSleep = jobj.optString(BS_BEFORE_SLEEP);
			
			mLimosis_value_ab = jobj.optString(BS_LIMOSIS_AB);
			mBefore_lunch_value_ab = jobj.optString(BS_BEFORE_LUNCH_AB);
			mAfter_lunch_value_ab = jobj.optString(BS_AFTER_LUNCH_AB);
			mBefore_sleep_value_ab = jobj.optString(BS_BEFORE_SLEEP_AB);
			
			mAnalysisResult = jobj.optString(BS_ANALYSIS);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
