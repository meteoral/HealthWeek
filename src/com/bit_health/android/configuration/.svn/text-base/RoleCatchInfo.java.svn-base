/********************************************************
 * 类名：RoleCatchInfo.java
 *
 * 作者：陈建平
 * 主要功能：存放角色的缓存信息
 * 创建日期：下午2:10:38
 ********************************************************/
package com.bit_health.android.configuration;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

import com.bit_health.android.controllers.beans.RoleInfoBean;
import com.bit_health.android.controllers.beans.RoleInfoBeanList;
import com.bit_health.android.task.AndroidCustomTaskMgr;
import com.bit_health.android.task.GetHeadImageTask;
import com.bit_health.android.util.HeadImageUtil;

/**
 * @author Administrator
 *
 */
public class RoleCatchInfo {
	private static final String ROLE_CATCH_INFO = "role_cath_info";
	private SharedPreferences mSharedPreferences;
	private Context mContext;
	private List<RoleInfoBean> mRoles;
	private static RoleCatchInfo instance;
	private RoleCatchInfo(Context contex) {
		mContext = contex.getApplicationContext();
		mSharedPreferences = mContext
				.getSharedPreferences(ROLE_CATCH_INFO, 0);
		
	}
	
	static public RoleCatchInfo getInstance(Context contex){
		if(instance == null){
			instance = new RoleCatchInfo(contex);
		}
		return instance;
	}
	public List<String> getCareList(){
		List<String> carelist = new ArrayList<String>();
		if(mRoles != null){
			for (RoleInfoBean role : mRoles) {
				if(role.mbCare){
					carelist.add(role.mId);
				}
			}
		}
		return carelist;
	}
	
	public List<RoleInfoBean> getRoles(){
		if(mRoles == null){
			mRoles = new ArrayList<RoleInfoBean>();
			for (int i = 0; i < RoleInfoBeanList.MAX_USERS; i++) {
				String roleid = mSharedPreferences.getString(
						RoleInfoBean.USER_ID + i, null);
				if (roleid == null) {
					break;
				}
				RoleInfoBean bean = new RoleInfoBean();
				bean.mId = roleid;
				bean.mAddress = mSharedPreferences.getString(
						RoleInfoBean.USER_ADDRESS + i, "");
				bean.mBirthday = mSharedPreferences.getString(
						RoleInfoBean.USER_BIRTHDAY + i, "");
				bean.mEmail = mSharedPreferences.getString(
						RoleInfoBean.USER_EMAIL + i, "");
				bean.mFullName = mSharedPreferences.getString(
						RoleInfoBean.USER_FULLNAME + i, "");
				bean.mGendar = mSharedPreferences.getString(
						RoleInfoBean.USER_GENDER + i, "");
				bean.mLoginCount = mSharedPreferences.getInt(
						RoleInfoBean.USER_LOGIN_COUNT + i, 0);
				bean.mMobile = mSharedPreferences.getString(
						RoleInfoBean.USER_MOBILE + i, "");
				bean.mName = mSharedPreferences.getString(
						RoleInfoBean.USER_NAME + i, "");
				bean.mRegisterday = mSharedPreferences.getString(
						RoleInfoBean.USER_REGISTER + i, "");
				bean.mbCare = mSharedPreferences.getBoolean(
						RoleInfoBean.USER_BE_CARE + i, false);
				
				bean.mYesAbNumber = mSharedPreferences.getInt(
						RoleInfoBean.USER_YES_ABNUM + i, 0);
				bean.mMonthAbNumber = mSharedPreferences.getInt(
						RoleInfoBean.USER_MON_ABNUM + i, 0);
				bean.m3MonthAbNumber = mSharedPreferences.getInt(
						RoleInfoBean.USER_3MON_ABNUM + i, 0);
				bean.mPhyCondition = mSharedPreferences.getString(
						RoleInfoBean.USER_PHY_CON + i, "");
				bean.mSuggested = mSharedPreferences.getString(
						RoleInfoBean.USER_DOCTOR_SUG + i, "");
				
				mRoles.add(bean);
			}
		}
		return mRoles;
	}
	
	public RoleInfoBean getRole(String id){
		if(mRoles != null){
			for(RoleInfoBean bean:mRoles){
				if(bean.mId.equals(id)){
					return bean;
				}
			}
		}
		return null;
	}
	
	private void addUpdateTaskMgr(List<String> roleids){
		AndroidCustomTaskMgr mTaskMgr = AndroidCustomTaskMgr.getInstance(mContext);
		for(String roleid:roleids){
			mTaskMgr.addSheduleTask(
					new GetHeadImageTask(roleid, mContext),
					GetHeadImageTask.class.getSimpleName()+roleid);
		}
		
	}

	public void setRolesAllData(List<RoleInfoBean> beans) {
		List<String> updateImageRoles = new ArrayList<String>();
		if (mRoles == null) {
			mRoles = beans;
			for(RoleInfoBean role : mRoles){
				if(!TextUtils.isEmpty(role.mImage)){
					updateImageRoles.add(role.mId);
				}
			}
		} else {
			if (beans != this.mRoles) {
				for (RoleInfoBean bean : beans) {
					RoleInfoBean beanOrg = getRole(bean.mId);
					if (beanOrg != null && beanOrg.mbCare) {
						bean.mbCare = true;
					}
					if (beanOrg == null && !TextUtils.isEmpty(bean.mImage)) {
						updateImageRoles.add(bean.mId);
					} else if (beanOrg != null) {
						if(!TextUtils.isEmpty(bean.mImage) && !bean.mImage.equals(beanOrg.mImage)){
							updateImageRoles.add(bean.mId);
						}else if(TextUtils.isEmpty(bean.mImage) && !TextUtils.isEmpty(beanOrg.mImage)){
							HeadImageUtil.deleteHeadimage(beanOrg.mId);
						}
					}
				}
				mRoles.clear();
				mRoles.addAll(beans);
			}
		}
		commit();
		
		addUpdateTaskMgr(updateImageRoles);
	}
	public void setRoles(List<RoleInfoBean> beans) {
		List<String> updateImageRoles = new ArrayList<String>();
		if (mRoles == null) {
			mRoles = beans;
			for(RoleInfoBean role : mRoles){
				if(!TextUtils.isEmpty(role.mImage)){
					updateImageRoles.add(role.mId);
				}
			}
		} else {
			if (beans != this.mRoles) {
				for (RoleInfoBean bean : beans) {
					RoleInfoBean beanOrg = getRole(bean.mId);
					if (beanOrg != null && beanOrg.mbCare) {
						bean.mbCare = true;
					}
					if(beanOrg != null){
						bean.mYesAbNumber = beanOrg.mYesAbNumber;
						bean.mMonthAbNumber = beanOrg.mMonthAbNumber;
						bean.m3MonthAbNumber = beanOrg.m3MonthAbNumber;
						bean.mPhyCondition = beanOrg.mPhyCondition;
						bean.mSuggested = beanOrg.mSuggested;
					}
					if (beanOrg == null && !TextUtils.isEmpty(bean.mImage)) {
						updateImageRoles.add(bean.mId);
					} else if (beanOrg != null) {
						if(!TextUtils.isEmpty(bean.mImage) && !bean.mImage.equals(beanOrg.mImage)){
							updateImageRoles.add(bean.mId);
						}else if(TextUtils.isEmpty(bean.mImage) && !TextUtils.isEmpty(beanOrg.mImage)){
							HeadImageUtil.deleteHeadimage(beanOrg.mId);
						}
					}
				}
				mRoles.clear();
				mRoles.addAll(beans);
			}
		}
		commit();
		addUpdateTaskMgr(updateImageRoles);
	}
	public void addRole(RoleInfoBean role){
		if (mRoles == null) {
			mRoles = new ArrayList<RoleInfoBean>();
			mRoles.add(role);
		} else {
			RoleInfoBean beanOrg = getRole(role.mId);
			if(beanOrg != null){
				mRoles.remove(beanOrg);
			}
			mRoles.add(role);
		}
		commit();
	}
	public void deleteRole(String roleid){
		if (mRoles != null) {
			RoleInfoBean beanOrg = getRole(roleid);
			if (beanOrg != null) {
				mRoles.remove(beanOrg);
			}
			commit();
		}
	}
	
	public void clearCare(){
		if(mRoles != null){
			for(RoleInfoBean bean:mRoles){
				bean.mbCare = false;
			}
		}
	}
	public void updateCare(String roleid,boolean bCare){
		RoleInfoBean role = getRole(roleid);
		if(role != null){
			role.mbCare = bCare;
		}
	}
	public void commit(){
		Editor editor = mSharedPreferences.edit();
		for (int i = 0; i < mRoles.size(); i++) {
			editor.putString(RoleInfoBean.USER_ID + i, mRoles.get(i).mId);
			editor.putString(RoleInfoBean.USER_ADDRESS + i, mRoles.get(i).mAddress);
			editor.putString(RoleInfoBean.USER_BIRTHDAY + i, mRoles.get(i).mBirthday);
			editor.putString(RoleInfoBean.USER_EMAIL + i, mRoles.get(i).mEmail);
			editor.putString(RoleInfoBean.USER_FULLNAME + i, mRoles.get(i).mFullName);
			editor.putString(RoleInfoBean.USER_GENDER + i, mRoles.get(i).mGendar);
			editor.putInt(RoleInfoBean.USER_LOGIN_COUNT + i, mRoles.get(i).mLoginCount);
			editor.putString(RoleInfoBean.USER_MOBILE + i, mRoles.get(i).mMobile);
			editor.putString(RoleInfoBean.USER_NAME + i, mRoles.get(i).mName);
			editor.putString(RoleInfoBean.USER_REGISTER + i, mRoles.get(i).mRegisterday);
			editor.putBoolean(RoleInfoBean.USER_BE_CARE + i, mRoles.get(i).mbCare);
			
			editor.putInt(RoleInfoBean.USER_YES_ABNUM + i, mRoles.get(i).mYesAbNumber);
			editor.putInt(RoleInfoBean.USER_MON_ABNUM + i, mRoles.get(i).mMonthAbNumber);
			editor.putInt(RoleInfoBean.USER_3MON_ABNUM + i, mRoles.get(i).m3MonthAbNumber);
			editor.putString(RoleInfoBean.USER_PHY_CON + i, mRoles.get(i).mPhyCondition);
			editor.putString(RoleInfoBean.USER_DOCTOR_SUG + i, mRoles.get(i).mSuggested);
		}
		editor.commit();
	}
	public void clear(){
		Editor editor = mSharedPreferences.edit();
		editor.clear();
		editor.commit();
		mRoles.clear();
		mRoles = null;
		mContext = null;
		instance = null;
	}
}
