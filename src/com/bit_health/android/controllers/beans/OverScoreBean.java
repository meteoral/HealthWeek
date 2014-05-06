package com.bit_health.android.controllers.beans;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

public class OverScoreBean extends JsonBase {
	public static final String OVER_SCORE_RESULT = "result";
	public static final String OVER_SCORE = "score";
	public static final String OVER_SCORE_START = "start_time";
	public static final String OVER_SCORE_UPDATE_TIME = "update_time";
	public static final String OVER_SCORE_USERID = "user_id";
	public static final String OVER_SCORE_Effective_Num = "Effective_Num";
	
	public int mScore;
	public String mStartTm;
	public String mUptateTm;// 分析时间
	public String mUserId;
	public int mEffectiveNum;
	
	public void parserJson(String json) throws JSONException{
		super.toBean(json);
		parser(json);
	}
	
	public void parser(String json) throws JSONException{
		JSONObject jobj = new JSONObject(json);
		String body = jobj.optString(OVER_SCORE_RESULT);
		if(!TextUtils.isEmpty(body)){
			jobj = new JSONObject(body);
			mScore = jobj.optInt(OVER_SCORE, -1);
			mStartTm = jobj.optString(OVER_SCORE_START);
			mUptateTm = jobj.optString(OVER_SCORE_UPDATE_TIME);
			mUserId = jobj.optString(OVER_SCORE_USERID);
			mEffectiveNum = jobj.optInt(OVER_SCORE_Effective_Num);
		}
	}
	
	public String toJson() throws JSONException {
		JSONObject jobj = new JSONObject();
		jobj.put(OVER_SCORE, mScore);
		jobj.put(OVER_SCORE_START, mStartTm);
		jobj.put(OVER_SCORE_UPDATE_TIME, mUptateTm);
		jobj.put(OVER_SCORE_USERID, mUserId);
		jobj.put(OVER_SCORE_Effective_Num, mEffectiveNum);
		
		JSONObject newobj = new JSONObject();
		newobj.put(OVER_SCORE_RESULT, jobj.toString());
		return newobj.toString();
	}
}
