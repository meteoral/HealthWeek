package com.bit_health.android.controllers.beans;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

public class EcgScoreBean extends JsonBase {
	public static final String ECG_SCORE_RESULT = "result";
	public static final String ECG_SCORE = "score";
	public static final String ECG_SCORE_START = "start_time";
	public static final String ECG_SCORE_UPDATE_TIME = "update_time";
	public static final String ECG_SCORE_USERID = "user_id";
	public static final String ECG_SCORE_Polycardia = "Polycardia";
	public static final String ECG_SCORE_Bradycardia = "Bradycardia";
	public static final String ECG_SCORE_VT = "VT";
	public static final String ECG_SCORE_Bigeminy_Num = "Bigeminy_Num";
	public static final String ECG_SCORE_Trigeminy_Num = "Trigeminy_Num";
	public static final String ECG_SCORE_Wide_Num = "Wide_Num";
	public static final String ECG_SCORE_Arrest_Num = "Arrest_Num";
	public static final String ECG_SCORE_Missed_Num = "Missed_Num";
	public static final String ECG_SCORE_PVB_Num = "PVB_Num";
	public static final String ECG_SCORE_APB_Num = "APB_Num";
	public static final String ECG_SCORE_Insert_PVBnum = "Insert_PVBnum";
	public static final String ECG_SCORE_Arrhythmia = "Arrhythmia";
	public static final String ECG_SCORE_Effective_Num = "Effective_Num";
	
	public int mScore;
	public String mStartTm;
	public String mUptateTm;
	public String mUserId;
	public int mPolycardia;
	public int mBradycardia;
	public int mVT;
	public int mBigeminyNum;
	public int mTrigeminyNum;
	public int mWideNum;
	public int mArrestNum;
	public int mMissedNum;
	public int mPVBNum;
	public int mAPBNum;
	public int mInsertPVBnum;
	public int mArrhythmia;
	public int mEffectiveNum;
	
	public void parserJson(String json) throws JSONException{
		super.toBean(json);
		parser(json);
	}
	
	public void parser(String json) throws JSONException{
		JSONObject jobj = new JSONObject(json);
		String body = jobj.optString(ECG_SCORE_RESULT);
		if(!TextUtils.isEmpty(body)){
			jobj = new JSONObject(body);
			mScore = jobj.optInt(ECG_SCORE, -1);
			mStartTm = jobj.optString(ECG_SCORE_START);
			mUptateTm = jobj.optString(ECG_SCORE_UPDATE_TIME);
			mUserId = jobj.optString(ECG_SCORE_USERID);
			mPolycardia = jobj.optInt(ECG_SCORE_Polycardia);
			mBradycardia = jobj.optInt(ECG_SCORE_Bradycardia);
			mVT = jobj.optInt(ECG_SCORE_VT);
			mBigeminyNum = jobj.optInt(ECG_SCORE_Bigeminy_Num);
			mTrigeminyNum = jobj.optInt(ECG_SCORE_Trigeminy_Num);
			mWideNum = jobj.optInt(ECG_SCORE_Wide_Num);
			mArrestNum = jobj.optInt(ECG_SCORE_Arrest_Num);
			mMissedNum = jobj.optInt(ECG_SCORE_Missed_Num);
			mPVBNum = jobj.optInt(ECG_SCORE_PVB_Num);
			mAPBNum = jobj.optInt(ECG_SCORE_APB_Num);
			mInsertPVBnum = jobj.optInt(ECG_SCORE_Insert_PVBnum);
			mArrhythmia = jobj.optInt(ECG_SCORE_Arrhythmia);
			mEffectiveNum = jobj.optInt(ECG_SCORE_Effective_Num);
		}
	}
	
	public String toJson() throws JSONException {
		JSONObject jobj = new JSONObject();
		jobj.put(ECG_SCORE, mScore);
		jobj.put(ECG_SCORE_START, mStartTm);
		jobj.put(ECG_SCORE_UPDATE_TIME, mUptateTm);
		jobj.put(ECG_SCORE_USERID, mUserId);
		jobj.put(ECG_SCORE_Polycardia, mPolycardia);
		jobj.put(ECG_SCORE_Polycardia, mPolycardia);
		jobj.put(ECG_SCORE_Bradycardia, mBradycardia);
		jobj.put(ECG_SCORE_VT, mVT);
		jobj.put(ECG_SCORE_Bigeminy_Num, mBigeminyNum);
		jobj.put(ECG_SCORE_Trigeminy_Num, mTrigeminyNum);
		jobj.put(ECG_SCORE_Wide_Num, mWideNum);
		jobj.put(ECG_SCORE_Arrest_Num,mArrestNum);
		jobj.put(ECG_SCORE_Missed_Num, mMissedNum);
		jobj.put(ECG_SCORE_PVB_Num,mPVBNum);
		jobj.put(ECG_SCORE_APB_Num, mAPBNum);
		jobj.put(ECG_SCORE_Insert_PVBnum, mInsertPVBnum);
		jobj.put(ECG_SCORE_Arrhythmia, mArrhythmia);
		jobj.put(ECG_SCORE_Effective_Num, mEffectiveNum);
		JSONObject newobj = new JSONObject();
		newobj.put(ECG_SCORE_RESULT, jobj.toString());
		return newobj.toString();
	}
}
