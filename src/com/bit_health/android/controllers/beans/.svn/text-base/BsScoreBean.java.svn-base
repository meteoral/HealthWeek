package com.bit_health.android.controllers.beans;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

public class BsScoreBean extends JsonBase {
	public static final String BS_SCORE_RESULT = "result";
	public static final String BS_SCORE = "score";
	public static final String BS_SCORE_START = "start_time";
	public static final String BS_SCORE_UPDATE_TIME = "update_time";
	public static final String BS_SCORE_USERID = "user_id";
	public static final String BS_SCORE_Diabetes = "Diabetes";
	public static final String BS_SCORE_Hyperglycemia = "Hyperglycemia";
	public static final String BS_SCORE_Hypoglycemia = "Hypoglycemia";
	public static final String BS_SCORE_Effective_Num = "Effective_Num";
	
	public int mScore;
	public String mStartTm;
	public String mUptateTm;
	public String mUserId;
	public int mDiabetes;
	public int mHyperglycemia;
	public int mHypoglycemia;
	public int mEffectiveNum;
	
	public void parserJson(String json) throws JSONException{
		super.toBean(json);
		parser(json);
	}
	
	public void parser(String json) throws JSONException{
		JSONObject jobj = new JSONObject(json);
		String body = jobj.optString(BS_SCORE_RESULT);
		if(!TextUtils.isEmpty(body)){
			jobj = new JSONObject(body);
			mScore = jobj.optInt(BS_SCORE, -1);
			mStartTm = jobj.optString(BS_SCORE_START);
			mUptateTm = jobj.optString(BS_SCORE_UPDATE_TIME);
			mUserId = jobj.optString(BS_SCORE_USERID);
			mDiabetes = jobj.optInt(BS_SCORE_Diabetes);
			mHyperglycemia = jobj.optInt(BS_SCORE_Hyperglycemia);
			mHypoglycemia = jobj.optInt(BS_SCORE_Hypoglycemia);
			mEffectiveNum = jobj.optInt(BS_SCORE_Effective_Num);
		}
	}
	
	public String toJson() throws JSONException {
		JSONObject jobj = new JSONObject();
		jobj.put(BS_SCORE, mScore);
		jobj.put(BS_SCORE_START, mStartTm);
		jobj.put(BS_SCORE_UPDATE_TIME, mUptateTm);
		jobj.put(BS_SCORE_USERID, mUserId);
		jobj.put(BS_SCORE_Diabetes, mDiabetes);
		jobj.put(BS_SCORE_Hyperglycemia, mHyperglycemia);
		jobj.put(BS_SCORE_Hypoglycemia, mHypoglycemia);
		jobj.put(BS_SCORE_Effective_Num, mEffectiveNum);
		JSONObject newobj = new JSONObject();
		newobj.put(BS_SCORE_RESULT, jobj.toString());
		return newobj.toString();
	}
}
