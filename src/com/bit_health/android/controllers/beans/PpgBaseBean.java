package com.bit_health.android.controllers.beans;

import org.json.JSONException;
import org.json.JSONObject;

public class PpgBaseBean extends JsonBase {
	public static final String _ID = "_id";
	public static final String PPG_PULSERATE = "PulseRate";
	public static final String PPG_TIMESTAMP = "TimeStamp";
	public static final String PPG_AbnormalData = "AbnormalData";
	public static final String PPG_STATUS = "Status";
	
	public String mId = "";
	public int mPulserate = 0;
	public String mTimeStamp = "";
	public int  mFlagError = -1; // 1计算失败  0计算成功
	public int  mStatus = 0; // 0正在分析中  1分析结束
	public String toJsonString(String key) {
		return super.toJsonString(key, toString());
	}
	@Override
	public String toString(){
		JSONObject jobj = new JSONObject();
		try {
			jobj.put(_ID, mId);
			jobj.put(PPG_PULSERATE, mPulserate);
			jobj.put(PPG_TIMESTAMP, mTimeStamp);
			
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
			mPulserate = jobj.optInt(PPG_PULSERATE);
			mTimeStamp = jobj.optString(PPG_TIMESTAMP);
			mFlagError = jobj.optInt(PPG_AbnormalData);
			mStatus = jobj.optInt(PPG_STATUS);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
