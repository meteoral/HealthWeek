/********************************************************
 * 类名：IBusinessCallback
 *
 * 作者：陈建平
 * 主要功能：服务器接口调用结果回调函数
 * 创建日期：上午9:44:04
 ********************************************************/
package com.bit_health.android.controllers;

import java.util.List;

import com.bit_health.android.controllers.beans.CheckVersionBean;
import com.bit_health.android.controllers.beans.JsonBase;
import com.bit_health.android.controllers.beans.RoleInfoBean;

public interface IBusinessCallback {
	void callback(int retCode, String errorMsg);

	void updateHeadback(int retCode, String errorMsg);

	void getRoleInfoCallback(int retCode, String errorMsg, RoleInfoBean beans);

	void getRoleInfoListCallback(int retCode, String errorMsg,
			List<RoleInfoBean> beans);

	void getDoctorSuggestCallback(int retCode, String errorMsg,
			String doctorSuggest);

	void getEcgImageUrlCallback(int retCode, String errorMsg, String imageUrl);

	void checkVersionCallback(int retCode, String errorMsg,
			CheckVersionBean bean);

	void getPpgHrImageUrlCallback(int retCode, String errorMsg, String imageUrl);

	void getPpgImageUrlCallback(int retCode, String errorMsg, String imageUrl);

	void getEcgPRCallback(int retCode, String errorMsg, int[] pr);

	void getEcgHrImageUrlCallback(int retCode, String errorMsg, String imageUrl);

	void getABEcgImageUrlCallback(int retCode, String errorMsg, String imageUrl);

	void getAbnormalMeasuresCallback(int retCode, String errorMsg, int abCount);

	void getMeasuresCallback(int retCode, String errorMsg, List<JsonBase> beans);
	
	void getAnalyzeResultCallback(int retCode, String errorMsg, JsonBase bean);

	void getMeasureDetailCallback(int retCode, String errorMsg, JsonBase bean);

	void registerCallback(int retCode, String errorMsg, String retMsg);

	void addroleCallback(int retCode, String errorMsg, String roleid);
}
