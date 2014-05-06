package com.bit_health.android.controllers.beans;

import org.json.JSONException;
import org.json.JSONObject;

public class CheckVersionBean extends JsonBase {
	private static final String STRING_CHECK_CODE = "check_code";
	private static final String STRING_UPDATE_INFO = "update_info";
	private static final String STRING_UPDATE_URL = "update_url";
	private static final String STRING_IS_FORCE_UPDATE = "is_force";

	private static final String STRING_RESULT = "result";

	public int mCheckCode = 1; // 0有新版本，1 已经是最新的版本
	public int mIsForce = 0; // 是否需要强制更新 0:不需要 1:需要
	public String mUrl = ""; // 版本地址
	public String mUpdateInfo = ""; // 版本更新的内容

	public void parserJson(String json) {
		JSONObject jobj = super.toBean(json);
		try {
			if (jobj != null) {
				jobj = jobj.getJSONObject(STRING_RESULT);
				if (jobj != null) {
					mCheckCode = jobj.getInt(STRING_CHECK_CODE);
					mIsForce = jobj.optInt(STRING_IS_FORCE_UPDATE);
					mUrl = jobj.optString(STRING_UPDATE_URL);
					mUpdateInfo = jobj.optString(STRING_UPDATE_INFO);
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
