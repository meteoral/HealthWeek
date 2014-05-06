package com.bit_health.android.controllers.beans;

import org.json.JSONException;
import org.json.JSONObject;

public class BpBaseBean extends JsonBase {
	public static final String _ID = "_id";
	public static final String BP_WAKE_SBP = "wake_SBP";
	public static final String BP_WAKE_DBP = "wake_DBP";
	public static final String BP_SLEEP_SBP = "sleep_SBP";
	public static final String BP_SLEEP_DBP = "sleep_DBP";
	public static final String BP_SLEEP_PR = "sleep_PR";
	public static final String BP_WAKE_PR = "wake_PR";
	public static final String BP_PR = "PR";
	public static final String BP_TIMEStAMP = "user_register";

	public String mId = "";
	public int mWakeSpb = 0;
	public int mWakeDpb = 0;
	public int mSleepSpb = 0;
	public int mSleepDpb = 0;
	public int mSleepPR = 0;
	public int mWakePR = 0;
	public int mPr = 0;
	public String mTimeStamp = "";
	
	public String toJsonString(String key) {
		return super.toJsonString(key, toString());
	}
	public String toString(){
		JSONObject jobj = new JSONObject();
		try {
			jobj.put(_ID, mId);
			jobj.put(BP_WAKE_SBP, mWakeSpb);
			jobj.put(BP_WAKE_DBP, mWakeDpb);
			jobj.put(BP_SLEEP_SBP, mSleepSpb);
			jobj.put(BP_SLEEP_DBP, mSleepDpb);
			jobj.put(BP_SLEEP_PR, mSleepPR);
			jobj.put(BP_WAKE_PR, mWakePR);
			jobj.put(BP_PR, mPr);
			jobj.put(BP_TIMEStAMP, mTimeStamp);
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
	public void parserJson(String json){
		try {
			JSONObject jobj = new JSONObject(json);
			mId = jobj.getString(_ID);
			mWakeSpb = jobj.optInt(BP_WAKE_SBP);
			mWakeDpb = jobj.optInt(BP_WAKE_DBP);
			mSleepSpb = jobj.optInt(BP_SLEEP_SBP);
			mSleepDpb = jobj.optInt(BP_SLEEP_DBP);
			mSleepPR = jobj.optInt(BP_SLEEP_PR);
			mWakePR = jobj.optInt(BP_WAKE_PR);
			mPr = jobj.optInt(BP_PR);
			mTimeStamp = jobj.optString(BP_TIMEStAMP);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
