package com.bit_health.android.controllers.beans;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;


public class RoleInfoBeanList extends JsonBase{
	public static final String USER_FLAG = "user";
	public static final int MAX_USERS = 100;
	public List<RoleInfoBean> mRolebeanlist = new ArrayList<RoleInfoBean>();
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		RoleInfoBean userbean = new RoleInfoBean();
		userbean.mId = "1";
		userbean.mFullName = "mFullName";
		userbean.mEmail = "mEmail";
		userbean.mAddress = "mAddress";
		userbean.mMobile = "mMobile";
		userbean.mLoginCount =2;
		userbean.mBirthday = "mBirthday";
		userbean.mGendar = "";
		userbean.mName = "mName";
		userbean.mRegisterday = "";
		userbean.mIsPass = "mIsPass";
		String str = userbean.toString();
		
		RoleInfoBean userbean1 = new RoleInfoBean();
		userbean1.mId = "1";
		userbean1.mFullName = "mFullName";
		userbean1.mEmail = "mEmail";
		userbean1.mAddress = "mAddress111";
		userbean1.mMobile = "mMobile";
		userbean1.mLoginCount =2;
		userbean1.mBirthday = "mBirthday";
		userbean1.mGendar = "";
		userbean1.mName = "mName";
		userbean1.mRegisterday = "";
		userbean1.mIsPass = "mIsPass1";
		String str1 = userbean1.toString();
		
		JSONObject jobj = new JSONObject();
		try {
			jobj.put(RoleInfoBean.RETURN_CODE, 0);
			jobj.put(USER_FLAG+0, str);
			jobj.put(USER_FLAG+1, str1);
			String str2 = jobj.toString();
			
			RoleInfoBeanList userbeanlist = new RoleInfoBeanList();
			userbeanlist.toUserInfoBeans(str2);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void toUserInfoBeans(String json) {
		if (mRolebeanlist.size() != 0) {
			mRolebeanlist.clear();
		}
		JSONObject jobj = super.toBean(json);
		try {
			if (jobj != null) {
				for (int i = 0; i < MAX_USERS; i++) {
					RoleInfoBean userinfo = new RoleInfoBean();
					userinfo.toJson(jobj.getString(USER_FLAG + i));
					mRolebeanlist.add(userinfo);
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
