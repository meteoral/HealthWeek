package com.bit_health.android.controllers.beans;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;


public class UserInfoBeanList extends JsonBase{
	public static final String USER_FLAG = "user";
	public static final int MAX_USERS = 100;
	public List<UserInfoBean> userbeanlist = new ArrayList<UserInfoBean>();
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
		String str = userbean.toString();
		
		UserInfoBean userbean1 = new UserInfoBean();
		userbean1.mId = "1";
		userbean1.mFullName = "mFullName";
		userbean1.mEmail = "mEmail";
		userbean1.mAddress = "mAddress111";
		userbean1.mMobile = "mMobile";
		userbean1.mLoginCount =2;
		userbean1.mBirthday = "mBirthday";
		userbean1.mGendar = 2;
		userbean1.mName = "mName";
		userbean1.mRegisterday = 3;
		userbean1.mIsPass = "mIsPass1";
		String str1 = userbean1.toString();
		
		JSONObject jobj = new JSONObject();
		try {
			jobj.put(UserInfoBean.RETURN_CODE, 0);
			jobj.put(USER_FLAG+0, str);
			jobj.put(USER_FLAG+1, str1);
			String str2 = jobj.toString();
			
			UserInfoBeanList userbeanlist = new UserInfoBeanList();
			userbeanlist.toUserInfoBeans(str2);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void toUserInfoBeans(String json) {
		if (userbeanlist.size() != 0) {
			userbeanlist.clear();
		}
		JSONObject jobj = super.toBean(json);
		try {
			for (int i = 0; i < MAX_USERS; i++) {
				UserInfoBean userinfo = new UserInfoBean();
				userinfo.toJson(jobj.getString(USER_FLAG + i));
				userbeanlist.add(userinfo);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
