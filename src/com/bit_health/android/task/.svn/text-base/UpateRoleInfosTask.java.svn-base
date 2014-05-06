package com.bit_health.android.task;

import java.util.Date;
import java.util.List;

import android.content.Context;

import com.bit_health.android.configuration.AndroidConfiguration;
import com.bit_health.android.constants.BusinessConst;
import com.bit_health.android.controllers.BusinessException;
import com.bit_health.android.controllers.IBusinessCallback;
import com.bit_health.android.controllers.IServerBusiness;
import com.bit_health.android.controllers.beans.RoleInfoBean;
import com.bit_health.android.util.TimeFormatUtil;

public class UpateRoleInfosTask implements Runnable {
	private IBusinessCallback mCallback;
	private static final long UPATE_TIME_INTERVAL = 5 * 60 * 1000; // 5分钟
	public static final String KEY_CONFIG_UPDATE_TIME = UpateRoleInfosTask.class
			.getSimpleName() + "_updatetime";
	private Context mContext;

	public UpateRoleInfosTask(Context context, IBusinessCallback callback) {
		this.mCallback = callback;
		this.mContext = context.getApplicationContext();
	}

	/***************************************************
	 * 功能介绍：获取角色的总体健康信息 入参： bean 角色信息 出参: 无 返回值: 无
	 **************************************************/
	private void getAbnormalMeasures(RoleInfoBean bean) {
		String nowTime = TimeFormatUtil.getNowTime();
		String yestoday = TimeFormatUtil.getPreDaysTime(1);
		String preOneMonth = TimeFormatUtil.getPreMonthTime(1);
		String preThreeMonth = TimeFormatUtil.getPreMonthTime(3);
		try {
			bean.mYesAbNumber = IServerBusiness.getInstance(mContext)
					.getAbnormalMeasures(bean.mId, yestoday, nowTime,
							BusinessConst.ALL_MESURE);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			bean.mMonthAbNumber = IServerBusiness.getInstance(mContext)
					.getAbnormalMeasures(bean.mId, preOneMonth, nowTime,
							BusinessConst.ALL_MESURE);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			bean.m3MonthAbNumber = IServerBusiness.getInstance(mContext)
					.getAbnormalMeasures(bean.mId, preThreeMonth, nowTime,
							BusinessConst.ALL_MESURE);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			long timeLast = AndroidConfiguration.getInstance(mContext)
					.getLastUpdateTime(KEY_CONFIG_UPDATE_TIME);
			long nowTime = new Date().getTime();
			if (timeLast == -1 || (nowTime - timeLast) >= UPATE_TIME_INTERVAL) {
				List<RoleInfoBean> beans = IServerBusiness
						.getInstance(mContext).getRoleInfoList();
				for (RoleInfoBean bean : beans) {
					getAbnormalMeasures(bean);
				}
				mCallback.getRoleInfoListCallback(0, "ok", beans);
				AndroidConfiguration.getInstance(mContext).setLastUpdateTime(
						KEY_CONFIG_UPDATE_TIME, new Date().getTime());
			}
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mCallback.getRoleInfoListCallback(e.retCode, e.getMessage(), null);
		}
	}
}
