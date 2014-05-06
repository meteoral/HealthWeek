/********************************************************
 * 类名：DababaseBean.java
 *
 * 作者：陈建平
 * 主要功能：
 * 创建日期：上午10:14:32
 ********************************************************/
package com.bit_health.android.database;
import com.bit_health.android.constants.BusinessConst;
import com.bit_health.android.controllers.beans.BpInfoBean;
import com.bit_health.android.controllers.beans.BsInfoBean;
import com.bit_health.android.controllers.beans.EcgInfoBean;
import com.bit_health.android.controllers.beans.JsonBase;
import com.bit_health.android.controllers.beans.PpgInfoBean;
import com.bit_health.android.util.TimeFormatUtil;

/**
 * @author Administrator
 *
 */
public class DatabaseBean {
	public long mId = -1;;
	public String mServerId;
	public long mTime;
	public int mDataType; // 详细还是简要?
	public int mStatus = 1; // 分析状态，ECG有用,0正在分析中  1分析结束
	public String mMeasureType;
	public String mData;
	static public DatabaseBean changeMesureToDbBean(JsonBase bean){
		DatabaseBean dbBean = new DatabaseBean();
		if (bean instanceof EcgInfoBean) {
			EcgInfoBean ecgInfo = (EcgInfoBean) bean;
			dbBean.mDataType = ecgInfo.mType;
			dbBean.mStatus = ecgInfo.mStatus;
			dbBean.mServerId = ecgInfo.mId;
			dbBean.mData = ecgInfo.mData;
			// +1 如果脉搏和心电时间一致，心电优先显示
			dbBean.mTime = TimeFormatUtil.strToDateLong(ecgInfo.mTimeStamp)+1;
			dbBean.mMeasureType = BusinessConst.ECG_MESURE;
		}
		if (bean instanceof PpgInfoBean) {
			PpgInfoBean ecgInfo = (PpgInfoBean) bean;
			dbBean.mDataType = ecgInfo.mType;
			dbBean.mStatus = ecgInfo.mStatus;
			dbBean.mServerId = ecgInfo.mId;
			dbBean.mData = ecgInfo.mData;
			dbBean.mTime = TimeFormatUtil.strToDateLong(ecgInfo.mTimeStamp);
			dbBean.mMeasureType = BusinessConst.PPG_MESURE;
		}
		if (bean instanceof BpInfoBean) {
			BpInfoBean bpInfo = (BpInfoBean) bean;
			dbBean.mDataType = bpInfo.mType;
			dbBean.mServerId = bpInfo.mId;
			dbBean.mData = bpInfo.mData;
			dbBean.mTime = TimeFormatUtil.strToDateLongEx(bpInfo.mTimeStamp);
			dbBean.mMeasureType = BusinessConst.BP_MESURE;
		}
		if (bean instanceof BsInfoBean) {
			BsInfoBean bsInfo = (BsInfoBean) bean;
			dbBean.mDataType = bsInfo.mType;
			dbBean.mServerId = bsInfo.mId;
			dbBean.mData = bsInfo.mData;
			dbBean.mTime = TimeFormatUtil.strToDateLongEx(bsInfo.mTimeStamp);
			dbBean.mMeasureType = BusinessConst.BS_MESURE;
		}
		return dbBean;
	}
	
	
	public JsonBase changeDbToMeasure(){
		JsonBase infoBean = null;
		if(mMeasureType.equals(BusinessConst.ECG_MESURE)){
			infoBean = new EcgInfoBean();
			((EcgInfoBean)infoBean).mType = mDataType;
			((EcgInfoBean)infoBean).parserJson(mData);
		}
		if(mMeasureType.equals(BusinessConst.PPG_MESURE)){
			infoBean = new PpgInfoBean();
			((PpgInfoBean)infoBean).mType = mDataType;
			((PpgInfoBean)infoBean).parserJson(mData);
		}
		if (mMeasureType.equals(BusinessConst.BP_MESURE)) {
			infoBean = new BpInfoBean();
			((BpInfoBean)infoBean).mType = mDataType;
			((BpInfoBean)infoBean).parserJson(mData);
		}
		if (mMeasureType.equals(BusinessConst.BS_MESURE)) {
			infoBean = new BsInfoBean();
			((BsInfoBean)infoBean).mType = mDataType;
			((BsInfoBean)infoBean).parserJson(mData);
		}
		return infoBean;
	}
}
