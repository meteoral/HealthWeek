package com.bit_health.android.controllers.beans;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

public class BpScoreBean extends JsonBase {
	public static final String BP_SCORE_RESULT = "result";
	public static final String BP_SCORE = "score";
	public static final String BP_SCORE_START = "start_time";
	public static final String BP_SCORE_UPDATE_TIME = "update_time";
	public static final String BP_SCORE_USERID = "user_id";
	public static final String BP_SCORE_ISH = "ISH";
	public static final String BP_SCORE_Mild_hypertension = "Mild_hypertension";
	public static final String BP_SCORE_Moderate_hypertension = "Moderate_hypertension";
	public static final String BP_SCORE_Highly_hypertension = "Highly_hypertension";
	public static final String BP_SCORE_Hypotension = "Hypotension";
	public static final String BP_SCORE_Effective_Num = "Effective_Num";
	
	public int mScore;
	public String mStartTm;
	public String mUptateTm;
	public String mUserId;
	public int mISH;
	public int mMild_hypertension;
	public int mModerate_hypertension;
	public int mHighly_hypertension;
	public int mHypotension;
	public int mEffectiveNum;
	
	public void parserJson(String json) throws JSONException {
		super.toBean(json);
		parser(json);
	}
	
	public void parser(String json) throws JSONException {
		JSONObject jobj = new JSONObject(json);
		String body = jobj.optString(BP_SCORE_RESULT);
		if(!TextUtils.isEmpty(body)){
			jobj = new JSONObject(body);
			mScore = jobj.optInt(BP_SCORE, -1);
			mStartTm = jobj.optString(BP_SCORE_START);
			mUptateTm = jobj.optString(BP_SCORE_UPDATE_TIME);
			mUserId = jobj.optString(BP_SCORE_USERID);
			mISH = jobj.optInt(BP_SCORE_ISH);
			mMild_hypertension = jobj.optInt(BP_SCORE_Mild_hypertension);
			mModerate_hypertension = jobj
					.optInt(BP_SCORE_Moderate_hypertension);
			mHighly_hypertension = jobj.optInt(BP_SCORE_Highly_hypertension);
			mHypotension = jobj.optInt(BP_SCORE_Hypotension);
			mEffectiveNum = jobj.optInt(BP_SCORE_Effective_Num);
		}
	}
	
	public String toJson() throws JSONException {
		JSONObject jobj = new JSONObject();
		jobj.put(BP_SCORE, mScore);
		jobj.put(BP_SCORE_START, mStartTm);
		jobj.put(BP_SCORE_UPDATE_TIME, mUptateTm);
		jobj.put(BP_SCORE_USERID, mUserId);
		jobj.put(BP_SCORE_ISH, mISH);
		jobj.put(BP_SCORE_Mild_hypertension, mMild_hypertension);
		jobj.put(BP_SCORE_Moderate_hypertension, mModerate_hypertension);
		jobj.put(BP_SCORE_Highly_hypertension, mHighly_hypertension);
		jobj.put(BP_SCORE_Hypotension, mHypotension);
		jobj.put(BP_SCORE_Effective_Num, mEffectiveNum);
		JSONObject newobj = new JSONObject();
		newobj.put(BP_SCORE_RESULT, jobj.toString());
		return newobj.toString();
	}
}
