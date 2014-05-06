package com.bit_health.android.controllers.beans;


import org.json.JSONException;
import org.json.JSONObject;

public class JsonBase {
	public int mRcode = -2;
	public static String RETURN_CODE = "return_code";

	public String toJsonString(String key, String value) {
		JSONObject jObj = new JSONObject();
		try {
			jObj.put(RETURN_CODE, mRcode);
			jObj.put(key, value);
			return jObj.toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/****************************************************************
	 * 方法描述：
	 * @param json 服务器端返回的json数据
	 ****************************************************************/
	public JSONObject toBean(String json) {
		try {
			// 解析服务器端返回的json数据
			JSONObject jObj = new JSONObject(json);// 转换为JSONObject
			mRcode = jObj.getInt(RETURN_CODE);
			return jObj;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
