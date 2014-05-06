package com.bit_health.android.controllers.beans;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class EcgInfoBean extends EcgBriefBean implements Serializable{
	private static final long serialVersionUID = 100000L;// 序列化时需要定义一个long ID
	public static final String ECG_TIME_STAMP_END = "Time_stamp_end";
	public static final String ECG_IMAGE_NUM = "ImageNum";
	public static final String ECG_ICOUNT = "iCount";
	public static final String ECG_TIMELENGTH = "TimeLength";
	public static final String ECG_SDNN = "SDNN";
	public static final String ECG_SDNNLevel = "SDNNLevel";
	public static final String ECG_PNN50 = "PNN50";
	public static final String ECG_PNN50Level = "PNN50Level";
	public static final String ECG_HRVI = "HRVI";
	public static final String ECG_HRVILevel = "HRVILevel";
	public static final String ECG_RMSSD = "RMSSD";
	public static final String ECG_RMSSDLevel = "RMSSDLevel";
	public static final String ECG_TP = "TP";
	public static final String ECG_TPLevel = "TPLevel";
	public static final String ECG_VLF = "VLF";
	public static final String ECG_VLFLevel = "VLFLevel";
	public static final String ECG_LF = "LF";
	public static final String ECG_LFLevel = "LFLevel";
	public static final String ECG_HF = "HF";
	public static final String ECG_HFLevel = "HFLevel";
	public static final String ECG_LF_HF_Ratio = "LF_HF_Ratio";
	public static final String ECG_LHRLevel = "LHRLevel";
	public static final String ECG_SD1 = "SD1";
	public static final String ECG_SD2 = "SD2";
	public static final String ECG_HRLevel = "HRLevel";
	public static final String ECG_ECGResult = "ECGResult";
	public static final String ECG_AnalysisOk = "AnalysisOk";
	public static final String ECG_HeartNum = "HeartNum";
	public static final String ECG_Slowest_Beat = "Slowest_Beat";
	public static final String ECG_Slowest_Time = "Slowest_Time";
	public static final String ECG_Fastest_Beat = "Fastest_Beat";
	public static final String ECG_Fastest_Time = "Fastest_Time";
	public static final String ECG_Polycardia = "Polycardia";
	public static final String ECG_Bradycardia = "Bradycardia";
	public static final String ECG_Arrest_Num = "Arrest_Num";
	public static final String ECG_Missed_Num = "Missed_Num";
	public static final String ECG_Wide_Num = "Wide_Num";
	public static final String ECG_PVB_Num = "PVB_Num";
	public static final String ECG_APB_Num = "APB_Num";
	public static final String ECG_Insert_PVBnum = "Insert_PVBnum";
	public static final String ECG_VT = "VT";
	public static final String ECG_Bigeminy_Num = "Bigeminy_Num";
	public static final String ECG_Trigeminy_Num = "Trigeminy_Num";
	public static final String ECG_Arrhythmia = "Arrhythmia";
	public static final String ECG_AbECGNum = "AbECGNum";
	public static final String ECG_ResultPerHour = "ECG_ResultPerHour";
	public static final String ECG_DeviceCode = "DeviceCode";
	public static final String ECG_IMAGE_COUNT = "ecg_img_count";
	public static final String ECG_EventId = "EventId";
	public static final String ECG_HRIMGCOUNT = "ecg_hr_img_count";
	

	public String mTimeStampEnd = "";
	public int mImageNum = 0;
	public int miCount = 0;
	public int mTimeLength = 0;
	public double mSdnn = 0;
	public int mSdnnLevel = 0;
	public double mPnn50 = 0;
	public int mPnn50Level = 0;
	public double mHrvi = 0;
	public int mHRVILevel = 0;
	public double mRmssd = 0;
	public int mRmssdLevel = 0;
	public double mTp = 0;
	public int mTpLevel = 0;
	public double mVlf = 0;
	public int mVLFLevel = 0;
	public double mLf = 0;
	public int mLFLevel = 0;
	public double mHf = 0;
	public int mHfLevel = 0;
	public double mLfHfRatio = 0;
	public int mLHRLevel = 0;
	public double mSd1 = 0;
	public double mSd2 = 0;
	public int mHrLevel = 0;
	public int mEcgResult = 0;
	public int mAnalysisOk = 0;
	public int mHeartNum = 0;
	public int mSlowestBeat = 0;
	public int mSlowestTime = 0;
	public int mFastestBeat = 0;
	public int mFastestTime = 0;
	public int mPolycardia = 0;
	public int mBradycardia = 0;
	public int mArrestNum = 0;
	public int mMissedNum = 0;
	public int mWideNum = 0;
	public int mPVBNum = 0;
	public int mApbNum = 0;
	public int mInsertPVBnum = 0;
	public int mVT = 0;
	public int mBigeminyNum = 0;
	public int mTrigeminyNum = 0;
	public int mArrhythmia = 0;
	public int mAbECGNum = 0;
	public String mEventId = "";
	public int mEcgImgCount = 0;
	public int mHrImageCount = 0;
	public String mECGResultPerHour = "";
	public String mECGDeviceCode = "";
	public int mType; // 详细信息还是简要信息
	public String mData;// 缓存数据
	public boolean bIsNormal = true; // 体检是否正常
	public void parserJson(String json) {
		mData = json; // 保存缓存数据
		super.parserJson(json);
		try {
			JSONObject jobj = new JSONObject(json);
			mTimeStampEnd = jobj.optString(ECG_TIME_STAMP_END);
			mImageNum = jobj.optInt(ECG_IMAGE_NUM);
			miCount = jobj.optInt(ECG_ICOUNT);
			mTimeLength = jobj.optInt(ECG_TIMELENGTH);
			mSdnn = jobj.optDouble(ECG_SDNN);
			mSdnnLevel = jobj.optInt(ECG_SDNNLevel);
			mPnn50 = jobj.optDouble(ECG_PNN50);
			mPnn50Level = jobj.optInt(ECG_PNN50Level);
			mHrvi = jobj.optDouble(ECG_HRVI);
			mHRVILevel = jobj.optInt(ECG_HRVILevel);
			mRmssd = jobj.optDouble(ECG_RMSSD);
			mRmssdLevel = jobj.optInt(ECG_RMSSDLevel);
			mTp = jobj.optDouble(ECG_TP);
			mTpLevel = jobj.optInt(ECG_TPLevel);
			mVlf = jobj.optDouble(ECG_VLF);
			mVLFLevel = jobj.optInt(ECG_HRLevel);
			mLf = jobj.optDouble(ECG_LF);
			mLFLevel = jobj.optInt(ECG_LFLevel);
			mHf = jobj.optDouble(ECG_HF);
			mHfLevel = jobj.optInt(ECG_HFLevel);
			mSd1 = jobj.optDouble(ECG_SD1);
			mSd2 = jobj.optDouble(ECG_SD2);
			mHrLevel = jobj.optInt(ECG_HRLevel);
			mEcgResult = jobj.optInt(ECG_ECGResult);
			mAnalysisOk = jobj.optInt(ECG_AnalysisOk);
			mHeartNum = jobj.optInt(ECG_HeartNum);
			mSlowestBeat = jobj.optInt(ECG_Slowest_Beat);
			mSlowestTime = jobj.optInt(ECG_Slowest_Time);
			mFastestBeat = jobj.optInt(ECG_Fastest_Beat);
			mFastestTime = jobj.optInt(ECG_Fastest_Time);
			mPolycardia = jobj.optInt(ECG_Polycardia);
			mBradycardia = jobj.optInt(ECG_Bradycardia);
			mArrestNum = jobj.optInt(ECG_Arrest_Num);
			mMissedNum = jobj.optInt(ECG_Missed_Num);
			mWideNum = jobj.optInt(ECG_Wide_Num);
			mPVBNum = jobj.optInt(ECG_PVB_Num);
			mApbNum = jobj.optInt(ECG_APB_Num);
			mInsertPVBnum = jobj.optInt(ECG_Insert_PVBnum);
			mVT = jobj.optInt(ECG_VT);
			mBigeminyNum = jobj.optInt(ECG_Bigeminy_Num);
			mTrigeminyNum = jobj.optInt(ECG_Trigeminy_Num);
			mArrhythmia = jobj.optInt(ECG_Arrhythmia);
			mAbECGNum = jobj.optInt(ECG_AbECGNum);
			mEcgImgCount = jobj.optInt(ECG_IMAGE_COUNT);
			mHrImageCount = jobj.optInt(ECG_HRIMGCOUNT);
			mECGResultPerHour = jobj.optString(ECG_ResultPerHour);
			mECGDeviceCode = jobj.optString(ECG_DeviceCode);
			mEventId = jobj.optString(ECG_EventId);
			if (mAbECGNum > 0) {
				bIsNormal = false;
			} else {
				bIsNormal = true;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
