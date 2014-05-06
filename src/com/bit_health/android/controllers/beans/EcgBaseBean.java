package com.bit_health.android.controllers.beans;

import org.json.JSONException;
import org.json.JSONObject;

public class EcgBaseBean extends JsonBase {
	public static final String _ID = "_id";
	public static final String ECG_HERTRATE = "HeartRate";
	public static final String ECG_TIMESTAMP = "TimeStamp";
	public static final String ECG_FLAG_ERROR = "flag_error";
	public static final String ECG_STATUS = "Status";
	
	public String mId = "";
	public int mHeartRate = 0;
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
			jobj.put(ECG_HERTRATE, mHeartRate);
			jobj.put(ECG_TIMESTAMP, mTimeStamp);
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
			mHeartRate = jobj.optInt(ECG_HERTRATE,-1);
			mTimeStamp = jobj.optString(ECG_TIMESTAMP,"");
			mFlagError = jobj.optInt(ECG_FLAG_ERROR,-1);
			mStatus = jobj.optInt(ECG_STATUS,0);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
