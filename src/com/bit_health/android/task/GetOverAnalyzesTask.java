package com.bit_health.android.task;

import java.util.Date;
import java.util.List;

import android.content.Context;
import android.content.Intent;

import com.bit_health.android.configuration.AnalyzeCath;
import com.bit_health.android.configuration.AndroidConfiguration;
import com.bit_health.android.configuration.RoleCatchInfo;
import com.bit_health.android.constants.BusinessConst;
import com.bit_health.android.controllers.BusinessException;
import com.bit_health.android.controllers.IBusinessCallback;
import com.bit_health.android.controllers.IServerBusiness;
import com.bit_health.android.controllers.beans.BpScoreBean;
import com.bit_health.android.controllers.beans.BsScoreBean;
import com.bit_health.android.controllers.beans.EcgScoreBean;
import com.bit_health.android.controllers.beans.JsonBase;
import com.bit_health.android.controllers.beans.OverScoreBean;
import com.bit_health.android.controllers.beans.PpgScoreBean;
import com.bit_health.android.controllers.beans.RoleInfoBean;

public class GetOverAnalyzesTask implements Runnable {
	private static final long UPATE_TIME_INTERVAL =60*60 * 1000; // 一个小时
	public static final String KEY_CONFIG_UPDATE_TIME = GetOverAnalyzesTask.class
			.getSimpleName() + "_updatetime";
	private Context mContext;

	public GetOverAnalyzesTask(Context context, IBusinessCallback callback) {
		this.mContext = context.getApplicationContext();
	}

	/***************************************************
	 * 功能介绍：获取整体分析的数据
	 **************************************************/
	private void getOverAnalyze(String userid) {
		try {
			JsonBase overScore = IServerBusiness.getInstance(mContext)
					.getAnalyzeResult(userid, BusinessConst.SCORE_TYPE_OVERALL);
			AnalyzeCath.getInstance(mContext).saveOverall((OverScoreBean) overScore);// 写到缓存
			
			JsonBase ecgScore = IServerBusiness.getInstance(mContext)
					.getAnalyzeResult(userid, BusinessConst.SCORE_TYPE_ECG);
			AnalyzeCath.getInstance(mContext).saveEcg(
					(EcgScoreBean) ecgScore);// 写到缓存
			
			JsonBase ppgScore = IServerBusiness.getInstance(mContext)
					.getAnalyzeResult(userid, BusinessConst.SCORE_TYPE_PPG);
			AnalyzeCath.getInstance(mContext).savePpg(
					(PpgScoreBean) ppgScore);// 写到缓存
			
			JsonBase bsScore = IServerBusiness.getInstance(mContext)
					.getAnalyzeResult(userid, BusinessConst.SCORE_TYPE_BS);
			AnalyzeCath.getInstance(mContext).saveBs(
					(BsScoreBean) bsScore);// 写到缓存
			
			JsonBase bpScore = IServerBusiness.getInstance(mContext)
					.getAnalyzeResult(userid, BusinessConst.SCORE_TYPE_BP);
			AnalyzeCath.getInstance(mContext).saveBp(
					(BpScoreBean) bpScore);// 写到缓存
			AndroidConfiguration.getInstance(mContext).setLastUpdateTime(
					KEY_CONFIG_UPDATE_TIME, new Date().getTime());
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		long timeLast = AndroidConfiguration.getInstance(mContext)
				.getLastUpdateTime(KEY_CONFIG_UPDATE_TIME);
		long nowTime = new Date().getTime();
		if (timeLast == -1 || (nowTime - timeLast) >= UPATE_TIME_INTERVAL) {
			List<RoleInfoBean> roles = RoleCatchInfo.getInstance(mContext)
					.getRoles();
			for (RoleInfoBean role : roles) {
				getOverAnalyze(role.mId);
			}
			mContext.sendBroadcast(new Intent(
					RoleInfoBean.ACTION_ROLEINFO_CHANGED));
		}

	}
}
