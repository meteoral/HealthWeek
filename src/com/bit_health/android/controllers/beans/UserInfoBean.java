package com.bit_health.android.controllers.beans;
import org.json.JSONException;
import org.json.JSONObject;

public class UserInfoBean extends JsonBase{
	public static final String USER_DETAIL = "user_detail";
	public static final String USER_ADDRESS = "Address";
	public static final String USER_EMAIL = "E_mail";
	public static final String USER_MOBILE = "Mobile";
	public static final String USER_ID = "_id";
	public static final String USER_ISPASS = "is_pass";
	public static final String USER_LOGIN_COUNT = "login_count";
	public static final String USER_BIRTHDAY = "user_birthday";
	public static final String USER_FULLNAME = "user_fullname";
	public static final String USER_GENDER = "user_gender";
	public static final String USER_NAME = "user_name";
	public static final String USER_REGISTER = "user_register";
	
	public String mId = "";
	public String mFullName = "";
	public String mEmail = "";
	public String mAddress = "";
	public String mMobile = "";
	public int mLoginCount = 0;
	public String mBirthday = "";
	public int mGendar = 0;
	public String mName = "";
	public long mRegisterday = 0;
	public String mIsPass = "";
	
	
	/***************************************************
	 * 功能介绍�?
	 * 入参�?
	 * 出参:
	 * 返回�?
	 **************************************************/
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		UserInfoBean userbean = new UserInfoBean();
		userbean.mId = "1";
		userbean.mFullName = "mFullName";
		userbean.mEmail = "mEmail";
		userbean.mAddress = "mAddress";
		userbean.mMobile = "mMobile";
		userbean.mLoginCount =2;
		userbean.mBirthday = "mBirthday";
		userbean.mGendar = 1;
		userbean.mName = "mName";
		userbean.mRegisterday = 3;
		userbean.mIsPass = "mIsPass";
		String str = userbean.toJsonString();
		System.out.print(str);
		
		UserInfoBean userbean1 = new UserInfoBean();
		userbean1.toJsonBean(str);
		System.out.print(str);
	}

	public String toJsonString() {
		return super.toJsonString(USER_DETAIL, toString());
	}
	public String toString(){
		JSONObject jobj = new JSONObject();
		try {
			jobj.put(USER_ADDRESS, mAddress);
			jobj.put(USER_EMAIL, mEmail);
			jobj.put(USER_MOBILE, mMobile);
			jobj.put(USER_ID, mId);
			jobj.put(USER_ISPASS, mIsPass);
			jobj.put(USER_BIRTHDAY, mBirthday);
			jobj.put(USER_FULLNAME, mFullName);
			jobj.put(USER_GENDER, mGendar);
			jobj.put(USER_NAME, mName);
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
	public void toJson(String json){
		try {
			JSONObject jobj = new JSONObject(json);
			mAddress = jobj.getString(USER_ADDRESS);
			mEmail = jobj.getString(USER_EMAIL);
			mMobile = jobj.getString(USER_MOBILE);
			mId = jobj.getString(USER_ID);
			mIsPass = jobj.getString(USER_ISPASS);
			mBirthday = jobj.getString(USER_BIRTHDAY);
			mFullName = jobj.getString(USER_FULLNAME);
			mGendar = jobj.getInt(USER_GENDER);
			mName = jobj.getString(USER_NAME);
			mAddress = jobj.getString(USER_ADDRESS);
			mLoginCount = jobj.getInt(USER_LOGIN_COUNT);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
