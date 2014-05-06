package com.bit_health.android.controllers.beans;

import org.json.JSONException;
import org.json.JSONObject;

public class RoleInfoBean extends JsonBase {
	public static final String ACTION_ROLEINFO_CHANGED = "ACTION_ROLEINFO_CHANGED";
	public static final String USER_DETAIL = "user_detail";
	public static final String USER_ADDRESS = "role_address";
	public static final String USER_EMAIL = "role_email";
	public static final String USER_MOBILE = "role_mobile";
	public static final String USER_ID = "role_id";
	public static final String USER_LOGIN_COUNT = "role_count";
	public static final String USER_BIRTHDAY = "role_birthday";
	public static final String USER_FULLNAME = "role_fullname";
	public static final String USER_GENDER = "role_gender";
	public static final String USER_NAME = "role_name";
	public static final String USER_IMAGE = "role_avatar";
	public static final String USER_REGISTER = "role_register";
	public static final String USER_BE_CARE = "role_care";
	public static final String USER_WEIGHT = "role_weight";
	public static final String USER_HEIGHT = "role_height";
	public static final String USER_PASSWORD = "role_password";
	
	public static final String USER_YES_ABNUM = "yes_abnum";
	public static final String USER_MON_ABNUM = "mon_abnum";
	public static final String USER_3MON_ABNUM = "3mon_abnum";
	public static final String USER_PHY_CON = "phy_condition";
	public static final String USER_DOCTOR_SUG = "doc_suggest";
	
	
	public String mId = "";
	public String mFullName = "";
	public String mEmail = "";
	public String mAddress = "";
	public String mMobile = "";
	public int mLoginCount = 0;
	public String mBirthday = "";
	public String mGendar = "";
	public String mName = "";
	public String mPassWord = "";
	public String mImage = "";
	public String mRegisterday = "";
	public String mIsPass = "";
	public String mHeight = "";
	public String mWeight = "";
	
	/*********个人健康状况*****************/
	public int   mYesAbNumber = 0; // 近两天内异常次数
	public int   mMonthAbNumber = 0; // 近一个月内异常次数
	public int   m3MonthAbNumber = 0; // 近三个月内异常次数
	public String mPhyCondition = ""; // 身体状况描述
	public String mSuggested = ""; // 医生建议

	// 仅本地使用
	public boolean mbCare = false;

	/***************************************************
	 * 功能介绍�? 入参�? 出参: 返回�?
	 **************************************************/
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		RoleInfoBean userbean = new RoleInfoBean();
		userbean.mId = "1";
		userbean.mFullName = "mFullName";
		userbean.mEmail = "mEmail";
		userbean.mAddress = "mAddress";
		userbean.mMobile = "mMobile";
		userbean.mLoginCount = 2;
		userbean.mBirthday = "mBirthday";
		userbean.mGendar = "";
		userbean.mName = "mName";
		userbean.mRegisterday = "";
		userbean.mIsPass = "mIsPass";

		String str = userbean.toJsonString();
		System.out.print(str);

		RoleInfoBean userbean1 = new RoleInfoBean();
		userbean1.toJsonBean(str);
		System.out.print(str);
	}

	public String toJsonString() {
		return super.toJsonString(USER_DETAIL, toString());
	}

	public String toString() {
		JSONObject jobj = new JSONObject();
		try {
			jobj.put(USER_ADDRESS, mAddress);
			jobj.put(USER_EMAIL, mEmail);
			jobj.put(USER_MOBILE, mMobile);
			jobj.put(USER_ID, mId);
			jobj.put(USER_BIRTHDAY, mBirthday);
			jobj.put(USER_FULLNAME, mFullName);
			jobj.put(USER_GENDER, mGendar);
			jobj.put(USER_NAME, mName);
			jobj.put(USER_WEIGHT, mWeight);
			jobj.put(USER_HEIGHT, mHeight);
			return jobj.toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void toJsonBean(String json) {
		JSONObject jobj = super.toBean(json);
		try {
			toJson(jobj.getString(USER_DETAIL));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void toJson(String json) {
		try {
			JSONObject jobj = new JSONObject(json);
			mAddress = jobj.optString(USER_ADDRESS, "");
			mEmail = jobj.optString(USER_EMAIL, "");
			mMobile = jobj.optString(USER_MOBILE, "");
			mId = jobj.getString(USER_ID);
			mBirthday = jobj.optString(USER_BIRTHDAY, "");
			mFullName = jobj.getString(USER_FULLNAME);
			mGendar = jobj.optString(USER_GENDER, "");
			mName = jobj.getString(USER_NAME);
			mImage = jobj.optString(USER_IMAGE);
			mHeight = jobj.optString(USER_HEIGHT, "");
			mWeight = jobj.optString(USER_WEIGHT, "");
			mRegisterday = jobj.optString(USER_REGISTER, "");
			mLoginCount = jobj.optInt(USER_LOGIN_COUNT, 0);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public RoleInfoBean clone() {
		RoleInfoBean bean = new RoleInfoBean();
		bean.mId = mId;
		bean.mFullName = mFullName;
		bean.mEmail = mEmail;
		bean.mAddress = mAddress;
		bean.mMobile = mMobile;
		bean.mLoginCount = mLoginCount;
		bean.mBirthday = mBirthday;
		bean.mGendar = mGendar;
		bean.mName = mName;
		bean.mPassWord = mPassWord;
		bean.mImage = mImage;
		bean.mRegisterday = mRegisterday;
		bean.mIsPass = mIsPass;
		bean.mHeight = mHeight;
		bean.mWeight = mWeight;

		return bean;
	}
}
