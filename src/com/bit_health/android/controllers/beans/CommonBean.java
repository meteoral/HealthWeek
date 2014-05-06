package com.bit_health.android.controllers.beans;

import org.json.JSONException;
import org.json.JSONObject;

public class CommonBean extends JsonBase {
	public static final String DOCTOR_SUGGEST = "doctor_suggest";
	public static final String ABNORMAL_COUNT = "abnormal_count";
	
	public String getDoctorSuggest(String json) {
		JSONObject jobj = super.toBean(json);
		try {
			return jobj.getString(DOCTOR_SUGGEST);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
	public int getAbnormalCount(String json) {
		JSONObject jobj = super.toBean(json);
		try {
			return jobj.getInt(ABNORMAL_COUNT);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
}
