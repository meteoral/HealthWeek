package com.bit_health.android.controllers.beans;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

public class PpgScoreBean extends JsonBase {
	public static final String PPG_SCORE_RESULT = "result";
	public static final String PPG_SCORE = "score";
	public static final String PPG_SCORE_START = "start_time";
	public static final String PPG_SCORE_UPDATE_TIME = "update_time";
	public static final String PPG_SCORE_USERID = "user_id";
	
	public static final String PPG_SCORE_SVLevel = "SVLevel";
	public static final String PPG_SCORE_COLevel = "COLevel";
	public static final String PPG_SCORE_ACLevel = "ACLevel";
	public static final String PPG_SCORE_VLevel = "VLevel";
	public static final String PPG_SCORE_TPRLevel = "TPRLevel";
	public static final String PPG_SCORE_SPOLevel = "SPOLevel";
	public static final String PPG_SCORE_PRLevel = "PRLevel";
	public static final String PPG_SCORE_CILevel = "CILevel";
	public static final String PPG_SCORE_SPILevel = "SPILevel";
	
	public static final String PPG_SCORE_Effective_Num = "Effective_Num";
	
	public int mScore;
	public String mStartTm;
	public String mUptateTm;
	public String mUserId;
	public int mSVLevel;
	public int mCOLevel;
	public int mACLevel;
	public int mVLevel;
	public int mTPRLevel;
	public int mSPOLevel;
	public int mPRLevel;
	public int mCILevel;
	public int mSPILevel;
	public int mEffectiveNum;
	
	public void parserJson(String json) throws JSONException{
		super.toBean(json);
		parser(json);
	}
	
	public void parser(String json) throws JSONException{
		JSONObject jobj = new JSONObject(json);
		String body = jobj.optString(PPG_SCORE_RESULT);
		if(!TextUtils.isEmpty(body)){
			jobj = new JSONObject(body);
			mScore = jobj.optInt(PPG_SCORE, -1);
			mStartTm = jobj.optString(PPG_SCORE_START);
			mUptateTm = jobj.optString(PPG_SCORE_UPDATE_TIME);
			mUserId = jobj.optString(PPG_SCORE_USERID);
			mSVLevel = jobj.optInt(PPG_SCORE_SVLevel);
			mCOLevel = jobj.optInt(PPG_SCORE_COLevel);
			mACLevel = jobj.optInt(PPG_SCORE_ACLevel);
			mVLevel = jobj.optInt(PPG_SCORE_VLevel);
			mTPRLevel = jobj.optInt(PPG_SCORE_TPRLevel);
			mSPOLevel = jobj.optInt(PPG_SCORE_SPOLevel);
			mPRLevel = jobj.optInt(PPG_SCORE_PRLevel);
			mCILevel = jobj.optInt(PPG_SCORE_CILevel);
			mSPILevel = jobj.optInt(PPG_SCORE_SPILevel);
			mEffectiveNum = jobj.optInt(PPG_SCORE_Effective_Num);
		}
	}
	
	public String toJson() throws JSONException {
		JSONObject jobj = new JSONObject();
		jobj.put(PPG_SCORE, mScore);
		jobj.put(PPG_SCORE_START, mStartTm);
		jobj.put(PPG_SCORE_UPDATE_TIME, mUptateTm);
		jobj.put(PPG_SCORE_USERID, mUserId);
		jobj.put(PPG_SCORE_SVLevel, mSVLevel);
		jobj.put(PPG_SCORE_COLevel, mCOLevel);
		jobj.put(PPG_SCORE_ACLevel, mACLevel);
		jobj.put(PPG_SCORE_VLevel, mVLevel);
		jobj.put(PPG_SCORE_TPRLevel, mTPRLevel);
		jobj.put(PPG_SCORE_SPOLevel, mSPOLevel);
		jobj.put(PPG_SCORE_PRLevel, mPRLevel);
		jobj.put(PPG_SCORE_CILevel,mCILevel);
		jobj.put(PPG_SCORE_SPILevel, mSPILevel);
		jobj.put(PPG_SCORE_Effective_Num, mEffectiveNum);
		JSONObject newobj = new JSONObject();
		newobj.put(PPG_SCORE_RESULT, jobj.toString());
		return newobj.toString();
	}
}
