package com.bit_health.android.controllers.beans;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class PpgInfoBean extends PpgBriefBean implements Serializable{
	private static final long serialVersionUID = 100001L;// 序列化时需要定义一个long ID
	public static final String PPG_K1 = "K1";
	public static final String PPG_K = "K";
	public static final String PPG_CO = "CO";
	public static final String PPG_Pm = "Pm";
	public static final String PPG_AC = "AC";
	public static final String PPG_SV = "SV";
	public static final String PPG_V = "V";
	public static final String PPG_TPR = "TPR";
	public static final String PPG_SI = "SI";
	public static final String PPG_CI = "CI";
	public static final String PPG_SPI = "SPI";
	public static final String PPG_SPO = "SPO";
	public static final String PPG_SPO1 = "SPO1";
	public static final String PPG_PWTT = "PWTT";
	public static final String PPG_SlowPulse = "SlowPulse";
	public static final String PPG_SlowTime = "SlowTime";
	public static final String PPG_QuickPulse = "QuickPulse";
	public static final String PPG_QuickTime = "QuickTime";
	public static final String PPG_PRLevel = "PRLevel";
	public static final String PPG_KLevel = "KLevel";
	public static final String PPG_SVLevel = "SVLevel";
	public static final String PPG_COLevel = "COLevel";
	public static final String PPG_ACLevel = "ACLevel";
	public static final String PPG_SILevel = "SILevel";
	public static final String PPG_VLevel = "VLevel";
	public static final String PPG_TPRLevel = "TPRLevel";
	public static final String PPG_SPOLevel = "SPOLevel";
	public static final String PPG_CILevel = "CILevel";
	public static final String PPG_SPILevel = "SPILevel";
	public static final String PPG_PWTTLevel = "PWTTLevel";
	public static final String PPG_PPGResult = "PPGResult";
	public static final String PPG_TimeLength = "TimeLength";
	public static final String PPG_ImageNum = "ImageNum";
	public static final String PPG_DeviceCode = "DeviceCode";
	public static final String PPG_Count = "iCount";
	public static final String PPG_EVENT_ID = "EventId";
	public static final String PPG_IMAGE_C0UNT = "ppg_img_count";
	public double mK1 = 0;
	public double mK = 0;
	public double mCO = 0;
	public double mPm = 0;
	public double mAC = 0;
	public double mV = 0;
	public double mTpr = 0;
	public double mSI = 0;
	public double mCI = 0;
	public double mSV = 0;
	public double mSPI = 0;
	public int mSPO = 0;
	public int mSPO1 = 0;
	public double mPWTT = 0;
	public int mSlowPulse = 0;
	public int mSlowTime = 0;
	public int mQuickPulse = 0;
	public int mQuickTime = 0;
	public int mPRLevel = 0;
	public int mKLevel = 0;
	public int mSVLevel = 0;
	public int mCOLevel = 0;
	public int mACLevel = 0;
	public int mSILevel = 0;
	public int mVLevel = 0;
	public int mTPRLevel = 0;
	public int mSPOLevel = 0;
	public int mCILevel = 0;
	public int mSPILevel = 0;
	public int mPWTTLevel = 0;
	public int mPPGResult = 0;
	public int mTimeLength = 0;
	public int mImageNum = 0;
	public String mEventId = "";
	public String mDeviceCode = "";
	public int miCount = 0;
	public int mImageCount=0;
	
	public int mType; // 详细信息还是简要信息
	public String mData;// 缓存数据
	public boolean bIsNormal = true; // 体检是否正常

	public void parserJson(String json) {
		mData = json; // 保存缓存数据
		super.parserJson(json);
		try {
			JSONObject jobj = new JSONObject(json);
			mK1 = jobj.optDouble(PPG_K1);
			mK = jobj.optDouble(PPG_K);
			mCO = jobj.optDouble(PPG_CO);
			mPm = jobj.optDouble(PPG_Pm);
			mAC = jobj.optDouble(PPG_AC);
			mV = jobj.optDouble(PPG_V);
			mTpr = jobj.optDouble(PPG_TPR);
			mSI = jobj.optDouble(PPG_SI);
			mCI = jobj.optDouble(PPG_CI);
			mSV = jobj.optDouble(PPG_SV);
			mSPI = jobj.optDouble(PPG_SPI);
			mSPO = jobj.optInt(PPG_SPO);
			mSPO1 = jobj.optInt(PPG_SPO1);
			mPWTT = jobj.optDouble(PPG_PWTT);
			mSlowPulse = jobj.optInt(PPG_SlowPulse);
			mSlowTime = jobj.optInt(PPG_SlowTime);
			mQuickPulse = jobj.optInt(PPG_QuickPulse);
			mQuickTime = jobj.optInt(PPG_QuickTime);
			mPRLevel = jobj.optInt(PPG_PRLevel);
			mKLevel = jobj.optInt(PPG_KLevel);
			mSVLevel = jobj.optInt(PPG_SVLevel);
			mCOLevel = jobj.optInt(PPG_COLevel);
			mACLevel = jobj.optInt(PPG_ACLevel);
			mSILevel = jobj.optInt(PPG_SILevel);
			mVLevel = jobj.optInt(PPG_VLevel);
			mTPRLevel = jobj.optInt(PPG_TPRLevel);
			mSPOLevel = jobj.optInt(PPG_SPOLevel);
			mCILevel = jobj.optInt(PPG_CILevel);
			mSPILevel = jobj.optInt(PPG_SPILevel);
			mPWTTLevel = jobj.optInt(PPG_PWTTLevel);
			mPPGResult = jobj.optInt(PPG_PPGResult);
			mTimeLength = jobj.optInt(PPG_TimeLength);
			mImageNum = jobj.optInt(PPG_ImageNum);
			miCount = jobj.optInt(PPG_Count);
			mEventId = jobj.optString(PPG_EVENT_ID);
			mImageCount = jobj.optInt(PPG_IMAGE_C0UNT);
			mDeviceCode = jobj.optString(PPG_DeviceCode);
			
			if (mPPGResult > 0) {
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
