package com.bit_health.android.task;

import java.util.Date;

import android.content.Context;

import com.bit_health.android.configuration.AndroidConfiguration;
import com.bit_health.android.controllers.BusinessException;
import com.bit_health.android.controllers.IBusinessCallback;
import com.bit_health.android.controllers.IServerBusiness;
import com.bit_health.android.controllers.beans.CheckVersionBean;

public class CheckVersionTask implements Runnable {
	private IBusinessCallback mCallback;
	private static final long UPATE_TIME_INTERVAL = 12 * 60 * 60 * 1000; // 12个小时
	public static final String KEY_CONFIG_UPDATE_TIME = CheckVersionTask.class
			.getSimpleName() + "_updatetime";
	private Context mContext;

	public CheckVersionTask(Context context, IBusinessCallback callback) {
		this.mCallback = callback;
		this.mContext = context.getApplicationContext();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			long timeLast = AndroidConfiguration.getInstance(mContext)
					.getLastUpdateTime(KEY_CONFIG_UPDATE_TIME);
			long nowTime = new Date().getTime();
			if (timeLast == -1 || (nowTime - timeLast) >= UPATE_TIME_INTERVAL) {
				CheckVersionBean bean = IServerBusiness.getInstance(mContext)
						.checkAppVersion();
				mCallback.checkVersionCallback(0, "ok", bean);
			}
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mCallback.checkVersionCallback(e.retCode, e.getMessage(), null);
		}
	}
}
