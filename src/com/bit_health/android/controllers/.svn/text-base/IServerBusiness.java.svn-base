package com.bit_health.android.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.TextUtils;
import android.util.Pair;

import com.bit_health.android.configuration.AndroidConfiguration;
import com.bit_health.android.configuration.RoleCatchInfo;
import com.bit_health.android.constants.BusinessConst;
import com.bit_health.android.controllers.beans.BpInfoBean;
import com.bit_health.android.controllers.beans.BpScoreBean;
import com.bit_health.android.controllers.beans.BsInfoBean;
import com.bit_health.android.controllers.beans.BsScoreBean;
import com.bit_health.android.controllers.beans.CheckVersionBean;
import com.bit_health.android.controllers.beans.EcgInfoBean;
import com.bit_health.android.controllers.beans.EcgScoreBean;
import com.bit_health.android.controllers.beans.JsonBase;
import com.bit_health.android.controllers.beans.MeasureInfoList;
import com.bit_health.android.controllers.beans.OverScoreBean;
import com.bit_health.android.controllers.beans.PpgInfoBean;
import com.bit_health.android.controllers.beans.PpgScoreBean;
import com.bit_health.android.controllers.beans.RoleInfoBean;
import com.bit_health.android.controllers.beans.RoleInfoBeanList;
import com.bit_health.android.controllers.beans.upload.UploadBpBean;
import com.bit_health.android.controllers.beans.upload.UploadBsBean;
import com.bit_health.android.controllers.beans.upload.UploadDeviceFileBean;
import com.bit_health.android.controllers.net.HttpCommunication;
import com.bit_health.android.task.CheckVersionTask;
import com.bit_health.android.util.EncryptionUtil;
import com.bit_health.android.util.FileCatchConfigUtil;

/**********************************************************************
 * 类名：IServerBusiness
 * 
 * @author 陈建平 主要功能：后台服务接口层 创建日期：2013.12.18
 **********************************************************************/
public class IServerBusiness {

	static private IServerBusiness instance;
	private Context mContext;

	private IServerBusiness(Context context) {
		mContext = context.getApplicationContext();
	}

	/***************************************************
	 * 功能介绍：获取类的唯一操作实例
	 * 入参：         android应用上下文，使用ApplicationContext
	 * 出参:    无
	 * 返回值:  IServerBusiness类的对象
	 **************************************************/
	public static IServerBusiness getInstance(Context context) {
		if (instance == null) {
			instance = new IServerBusiness(context);
		}
		return instance;
	}
	
	/***************************************************
	 * 功能介绍：账户登录
	 * 入参：         accountname 账户名称
	 *          password    账户密码
	 * 出参:    无
	 * 返回值:  无
	 * 异常：        外部捕获BusinessException
	 **************************************************/
	public void Login(String accountname, String password)
			throws BusinessException {
		String url = AndroidConfiguration.getInstance(mContext)
				.getBitHealthHost() + "api/user_info.php?type=login";
		Map<String, String> mapPar = new HashMap<String, String>();
		mapPar.put("user_name", accountname);
		mapPar.put("user_password", EncryptionUtil.getEncryptMd5_32(password));
		try {
			// response:服务器端返回的json数据
			String response = HttpCommunication.postForm(url, mapPar, null, "",null);
			if (response == null) {
				// 网络没有连接
				throw new BusinessException(BusinessException.getErrorMessage(
						mContext, BusinessException.DEVICE_CONNECT_ERROR),
						BusinessException.DEVICE_CONNECT_ERROR);
			}
			JsonBase jBase = new JsonBase();
			JSONObject jObject = jBase.toBean(response);
			if (jBase.mRcode == BusinessConst.RT_OK) {// 登录帐号和密码都正确的情况
				try {
					AndroidConfiguration.getInstance(mContext)
							.setAccountSession(jObject.getString("session_id"));
					AndroidConfiguration.getInstance(mContext).setUserId(
							jObject.getString("user_id"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new BusinessException(
							BusinessException.getErrorMessage(mContext,
									BusinessException.JSON_ERROR),
							BusinessException.JSON_ERROR);
				}
			} else {// 登录帐号不存在，或者密码不正确的情况
				throw new BusinessException(BusinessException.getErrorMessage(
						mContext, jBase.mRcode),
						jBase.mRcode);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BusinessException(BusinessException.getErrorMessage(
					mContext, BusinessException.DEVICE_CONNECT_ERROR),
					BusinessException.DEVICE_CONNECT_ERROR);
		}
	}
	
	/***************************************************
	 * 功能介绍：忘记密码
	 * 入参：         name 用户名或者角色名
	 *          forget_type    忘记密码类型（用户：user；角色：role）
	 * 出参:    无
	 * 返回值:  无
	 * 异常：        外部捕获BusinessException
	 **************************************************/
	public void fortgetPassword(String name, String forgettype)
			throws BusinessException {
		String url = AndroidConfiguration.getInstance(mContext)
				.getBitHealthHost() + "api/user_update.php?type=forget";
		Map<String, String> mapPar = new HashMap<String, String>();
		mapPar.put("forget_type", forgettype);
		mapPar.put("name", name);
		if(BusinessConst.FORGET_TYPE_ROLE.equals(forgettype)){
			mapPar.put("session_id", AndroidConfiguration.getInstance(mContext)
					.getAccountSession());
		}
		try {
			String response = HttpCommunication.postForm(url, mapPar, null, "",null);
			if (response == null) {
				// 网络错误
				throw new BusinessException(BusinessException.getErrorMessage(
						mContext, BusinessException.DEVICE_CONNECT_ERROR),
						BusinessException.DEVICE_CONNECT_ERROR);
			}
			JsonBase jBase = new JsonBase();
			jBase.toBean(response);
			if (jBase.mRcode != BusinessConst.RT_OK) {
				throw new BusinessException(BusinessException.getErrorMessage(
						mContext, jBase.mRcode),
						jBase.mRcode);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BusinessException(BusinessException.getErrorMessage(
					mContext, BusinessException.DEVICE_CONNECT_ERROR),
					BusinessException.DEVICE_CONNECT_ERROR);
		}
	}
	
	/***************************************************
	 * 功能介绍：更新密码
	 * 入参：         name 用户名或者角色名
	 *          forget_type    忘记密码类型（用户：user；角色：role）
	 *          vcode 验证码
	 *          newPassword 新密码
	 * 出参:    无
	 * 返回值:  无
	 * 异常：        外部捕获BusinessException
	 **************************************************/
	public void updatePassword(String name, String forgettype,String vcode,String newPassword)
			throws BusinessException {
		String url = AndroidConfiguration.getInstance(mContext)
				.getBitHealthHost() + "api/user_update.php?type=forget_update";
		Map<String, String> mapPar = new HashMap<String, String>();
		mapPar.put("forget_type", forgettype);
		mapPar.put("name", name);
		mapPar.put("vcode", vcode);
		mapPar.put("new_password", EncryptionUtil.getEncryptMd5_32(newPassword));
		if(BusinessConst.FORGET_TYPE_ROLE.equals(forgettype)){
			mapPar.put("session_id", AndroidConfiguration.getInstance(mContext)
					.getAccountSession());
		}
		try {
			String response = HttpCommunication.postForm(url, mapPar, null, "",
					null);
			if (response == null) {
				// 网络错误
				throw new BusinessException(BusinessException.getErrorMessage(
						mContext, BusinessException.DEVICE_CONNECT_ERROR),
						BusinessException.DEVICE_CONNECT_ERROR);
			}
			JsonBase jBase = new JsonBase();
			jBase.toBean(response);
			if (jBase.mRcode != BusinessConst.RT_OK) {
				throw new BusinessException(BusinessException.getErrorMessage(
						mContext, jBase.mRcode),
						jBase.mRcode);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BusinessException(BusinessException.getErrorMessage(
					mContext, BusinessException.DEVICE_CONNECT_ERROR),
					BusinessException.DEVICE_CONNECT_ERROR);
		}
	}
	
	/***************************************************
	 * 功能介绍：账户注册
	 * 入参：         accountname 账户名称
	 *          password    账户密码
	 * 出参:    无
	 * 返回值:  无
	 * 异常：        外部捕获BusinessException
	 **************************************************/
	public String register(String accountname, String password)
			throws BusinessException {
		String url = AndroidConfiguration.getInstance(mContext)
				.getBitHealthHost() + "api/user_update.php?type=register";
		Map<String, String> mapPar = new HashMap<String, String>();
		mapPar.put("user_name", accountname);
		mapPar.put("user_password", EncryptionUtil.getEncryptMd5_32(password));
		String retMsg = "";
		try {
			String response = HttpCommunication.postForm(url, mapPar, null, "",null);
			if (response == null) {
				// 网络错误
				throw new BusinessException(BusinessException.getErrorMessage(
						mContext, BusinessException.DEVICE_CONNECT_ERROR),
						BusinessException.DEVICE_CONNECT_ERROR);
			}
			JsonBase jBase = new JsonBase();
			JSONObject jobj = jBase.toBean(response);
			if (jBase.mRcode != BusinessConst.RT_OK) {
				throw new BusinessException(BusinessException.getErrorMessage(
						mContext, jBase.mRcode),
						jBase.mRcode);
			} else{
				retMsg = jobj.optString("email_host", "");
			}
			return retMsg;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BusinessException(BusinessException.getErrorMessage(
					mContext, BusinessException.DEVICE_CONNECT_ERROR),
					BusinessException.DEVICE_CONNECT_ERROR);
		}
	}
	/***************************************************
	 * 功能介绍：角色登录
	 * 入参：         roleId      角色ID
	 *          password    角色密码，可以为空
	 * 出参:    无
	 * 返回值:  无
	 * 异常：        外部捕获BusinessException
	 **************************************************/
	public void roleLogin(String roleId, String password)
			throws BusinessException {
		String url = AndroidConfiguration.getInstance(mContext)
				.getBitHealthHost() + "api/user_info.php?type=role_login";
		Map<String, String> mapPar = new HashMap<String, String>();
		mapPar.put("role_id", roleId);
		if (TextUtils.isEmpty(password)) {
			String enPassword = AndroidConfiguration.getInstance(mContext)
					.getmAccountPwd();
			mapPar.put("role_password", enPassword);
		} else {
			mapPar.put("role_password", EncryptionUtil.getEncryptMd5_32(password));
		}
		mapPar.put("session_id", AndroidConfiguration.getInstance(mContext)
				.getAccountSession());
		try {
			String response = HttpCommunication.postForm(url, mapPar, null, "",null);
			if (response == null) {
				throw new BusinessException(BusinessException.getErrorMessage(
						mContext, BusinessException.DEVICE_CONNECT_ERROR),
						BusinessException.DEVICE_CONNECT_ERROR);
			}
			JsonBase jBase = new JsonBase();
			JSONObject jObject = jBase.toBean(response);
			if (jBase.mRcode == BusinessConst.RT_OK) {
				try {
					AndroidConfiguration.getInstance(mContext).setRoleSession(
							jObject.getString("user_cookie"));
					AndroidConfiguration.getInstance(mContext)
					.setRoleId(roleId);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new BusinessException(
							BusinessException.getErrorMessage(mContext,
									BusinessException.JSON_ERROR),
							BusinessException.JSON_ERROR);
				}
			} else {
				throw new BusinessException(BusinessException.getErrorMessage(
						mContext, jBase.mRcode),
						jBase.mRcode);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BusinessException(BusinessException.getErrorMessage(
					mContext, BusinessException.DEVICE_CONNECT_ERROR),
					BusinessException.DEVICE_CONNECT_ERROR);
		}
	}

	/***************************************************
	 * 功能介绍：账户登出
	 * 入参：         无
	 * 出参:    无
	 * 返回值:  无
	 * 异常：        外部捕获BusinessException
	 **************************************************/
	public void Logout() throws BusinessException {
		String url = AndroidConfiguration.getInstance(mContext)
				.getBitHealthHost() + "api/user_info.php?type=logout";
		Map<String, String> mapPar = new HashMap<String, String>();
		mapPar.put("session_id", AndroidConfiguration.getInstance(mContext)
				.getAccountSession());
		mapPar.put("user_id", AndroidConfiguration.getInstance(mContext)
				.getUserId());
		try {
			String response = HttpCommunication.postForm(url, mapPar, null, "",null);
			if (response == null) {
				throw new BusinessException(BusinessException.getErrorMessage(
						mContext, BusinessException.DEVICE_CONNECT_ERROR),
						BusinessException.DEVICE_CONNECT_ERROR);
			}
			JsonBase jBase = new JsonBase();
			jBase.toBean(response);
			if (jBase.mRcode == BusinessConst.RT_OK) {
				AndroidConfiguration.getInstance(mContext)
						.setAccountSession("");

			} else {
				throw new BusinessException(BusinessException.getErrorMessage(
						mContext, jBase.mRcode),
						jBase.mRcode);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BusinessException(BusinessException.getErrorMessage(
					mContext, BusinessException.DEVICE_CONNECT_ERROR),
					BusinessException.DEVICE_CONNECT_ERROR);
		}
	}

	/***************************************************
	 * 功能介绍：增加角色
	 * 入参：         RoleInfoBean bean 角色信息
	 * 出参:    无
	 * 返回值:   roleid角色ID
	 * 异常：        外部捕获BusinessException
	 **************************************************/
	public String addRole(RoleInfoBean bean) throws BusinessException {
		String url = AndroidConfiguration.getInstance(mContext)
				.getBitHealthHost() + "api/user_update.php?type=add";
		Map<String, String> mapPar = new HashMap<String, String>();
		mapPar.put(RoleInfoBean.USER_FULLNAME, bean.mFullName);
		mapPar.put(RoleInfoBean.USER_NAME, bean.mName);
		mapPar.put(RoleInfoBean.USER_PASSWORD, bean.mPassWord);
		mapPar.put(RoleInfoBean.USER_GENDER, bean.mGendar);
		mapPar.put(RoleInfoBean.USER_BIRTHDAY, bean.mBirthday);
		mapPar.put(RoleInfoBean.USER_MOBILE, bean.mMobile);
		mapPar.put(RoleInfoBean.USER_EMAIL, bean.mEmail);
		mapPar.put(RoleInfoBean.USER_ADDRESS, bean.mAddress);
		mapPar.put(RoleInfoBean.USER_WEIGHT, bean.mWeight);
		mapPar.put(RoleInfoBean.USER_HEIGHT, bean.mHeight);
		mapPar.put("user_id", AndroidConfiguration.getInstance(mContext).getUserId());
		mapPar.put("session_id", AndroidConfiguration.getInstance(mContext)
				.getAccountSession());
		try {
			String response = HttpCommunication.postForm(url, mapPar, null, "",null);
			if (response == null) {
				throw new BusinessException(BusinessException.getErrorMessage(
						mContext, BusinessException.DEVICE_CONNECT_ERROR),
						BusinessException.DEVICE_CONNECT_ERROR);
			}
			JsonBase jBase = new JsonBase();
			JSONObject jobj = jBase.toBean(response);
			if (jBase.mRcode == BusinessConst.RT_OK) {
				// add any code?
				try {
					return jobj.getString("role_id");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					throw new BusinessException(BusinessException.getErrorMessage(
							mContext, BusinessException.JSON_ERROR),
							BusinessException.JSON_ERROR);
				}
			} else {
				throw new BusinessException(BusinessException.getErrorMessage(
						mContext, jBase.mRcode),
						jBase.mRcode);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BusinessException(BusinessException.getErrorMessage(
					mContext, BusinessException.DEVICE_CONNECT_ERROR),
					BusinessException.DEVICE_CONNECT_ERROR);
		}
	}

	/***************************************************
	 * 功能介绍：删除角色
	 * 入参：        String  roleId 角色id
	 * 出参:    无
	 * 返回值:  无
	 * 异常：        外部捕获BusinessException
	 **************************************************/
	public void deleteRole(String roleId) throws BusinessException {
		if(roleId.equalsIgnoreCase(AndroidConfiguration.getInstance(mContext).getRoleId())){
			throw new BusinessException("Business error code = "
					+ BusinessException.LOCAL_PARAM_ERROR,BusinessException.LOCAL_PARAM_ERROR);
		}
		String url = AndroidConfiguration.getInstance(mContext)
				.getBitHealthHost() + "api/user_update.php?type=delete";
		Map<String, String> mapPar = new HashMap<String, String>();
		mapPar.put("role_id", roleId);
		mapPar.put("session_id", AndroidConfiguration.getInstance(mContext)
				.getAccountSession());
		try {
			String response = HttpCommunication.postForm(url, mapPar, null, "",null);
			if (response == null) {
				throw new BusinessException(BusinessException.getErrorMessage(
						mContext, BusinessException.DEVICE_CONNECT_ERROR),
						BusinessException.DEVICE_CONNECT_ERROR);
			}
			JsonBase jBase = new JsonBase();
			jBase.toBean(response);
			if (jBase.mRcode == BusinessConst.RT_OK) {
				// add any code?
				RoleCatchInfo.getInstance(mContext).deleteRole(roleId);
			} else {
				throw new BusinessException(BusinessException.getErrorMessage(
						mContext, jBase.mRcode),
						jBase.mRcode);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BusinessException(BusinessException.getErrorMessage(
					mContext, BusinessException.DEVICE_CONNECT_ERROR),
					BusinessException.DEVICE_CONNECT_ERROR);
		}
	}
	/***************************************************
	 * 功能介绍：获取角色信息
	 * 入参：        String roleId 角色ID
	 * 出参:    无
	 * 返回值:  RoleInfoBean 角色详细信息
	 * 异常：        外部捕获BusinessException
	 **************************************************/
	public RoleInfoBean getRoleInfo(String roleId) throws BusinessException {
		String url = AndroidConfiguration.getInstance(mContext)
				.getBitHealthHost() + "api/user_info.php?type=detail";
		Map<String, String> mapPar = new HashMap<String, String>();
		mapPar.put("role_id", roleId);
		mapPar.put("session_id", AndroidConfiguration.getInstance(mContext)
				.getAccountSession());
		try {
			String response = HttpCommunication.postForm(url, mapPar, null, "",null);
			if (response == null) {
				throw new BusinessException(BusinessException.getErrorMessage(
						mContext, BusinessException.DEVICE_CONNECT_ERROR),
						BusinessException.DEVICE_CONNECT_ERROR);
			}
			RoleInfoBean roleInfo = new RoleInfoBean();
			roleInfo.toJsonBean(response);
			if (roleInfo.mRcode == BusinessConst.RT_OK) {
				return roleInfo;
			} else {
				throw new BusinessException(BusinessException.getErrorMessage(
						mContext, roleInfo.mRcode),
						roleInfo.mRcode);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BusinessException(BusinessException.getErrorMessage(
					mContext, BusinessException.DEVICE_CONNECT_ERROR),
					BusinessException.DEVICE_CONNECT_ERROR);
		}
	}

	/***************************************************
	 * 功能介绍：更新角色信息
	 * 入参：        RoleInfoBean bean 角色信息
	 * 出参:    无
	 * 返回值:  无
	 * 异常：        外部捕获BusinessException
	 **************************************************/
	public void updateRoleInfo(RoleInfoBean bean) throws BusinessException {
		String url = AndroidConfiguration.getInstance(mContext)
				.getBitHealthHost() + "api/user_update.php?type=change";
		Map<String, String> mapPar = new HashMap<String, String>();
		mapPar.put(RoleInfoBean.USER_ID, bean.mId);
		mapPar.put("role_cookie", AndroidConfiguration.getInstance(mContext)
				.getRoleSession());
		mapPar.put(RoleInfoBean.USER_FULLNAME, bean.mFullName);
		mapPar.put(RoleInfoBean.USER_NAME, bean.mName);
		mapPar.put(RoleInfoBean.USER_GENDER, bean.mGendar);
		mapPar.put(RoleInfoBean.USER_BIRTHDAY, bean.mBirthday);
		mapPar.put(RoleInfoBean.USER_WEIGHT, bean.mWeight);
		mapPar.put(RoleInfoBean.USER_HEIGHT, bean.mHeight);
		mapPar.put(RoleInfoBean.USER_MOBILE, bean.mMobile);
		mapPar.put(RoleInfoBean.USER_EMAIL, bean.mEmail);
		mapPar.put(RoleInfoBean.USER_ADDRESS, bean.mAddress);
		try {
			String response = HttpCommunication.postForm(url, mapPar, null, "",null);
			if (response == null) {
				throw new BusinessException(BusinessException.getErrorMessage(
						mContext, BusinessException.DEVICE_CONNECT_ERROR),
						BusinessException.DEVICE_CONNECT_ERROR);
			}
			JsonBase jBase = new JsonBase();
			jBase.toBean(response);
			if (jBase.mRcode != BusinessConst.RT_OK) {
				throw new BusinessException(BusinessException.getErrorMessage(
						mContext, jBase.mRcode),
						jBase.mRcode);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BusinessException(BusinessException.getErrorMessage(
					mContext, BusinessException.DEVICE_CONNECT_ERROR),
					BusinessException.DEVICE_CONNECT_ERROR);
		}
	}
	
	/***************************************************
	 * 功能介绍：更新角色的头像
	 * 入参：        localpath  本地头像文件
	 * 出参:    无
	 * 返回值:  无
	 * 异常：        外部捕获BusinessException
	 **************************************************/
	public void updateRoleHead(String localpath) throws BusinessException {
		Map<String, String> mapPar = new HashMap<String, String>();
		Map<String, Pair<String, String>> mapFile =  new HashMap<String, Pair<String, String>>();
		String url = AndroidConfiguration.getInstance(mContext)
				.getBitHealthHost() + "/api/user_update.php?type=role_img";
		String session = AndroidConfiguration.getInstance(mContext).getRoleSession();
		String roleid = AndroidConfiguration.getInstance(mContext).getRoleId();
		mapPar.put("role_id", roleid);
		mapPar.put("role_cookie", session);
		mapFile.put("role_img", new Pair<String, String>(roleid+".png", localpath));
		try {
			String str = HttpCommunication.postForm(url, mapPar, mapFile,"",null);
			if (str != null) {
				JsonBase jBase = new JsonBase();
				jBase.toBean(str);
				if (jBase.mRcode != BusinessConst.RT_OK) {
					throw new BusinessException(BusinessException.getErrorMessage(
							mContext, jBase.mRcode),
							jBase.mRcode);
				} 
			} else {
				throw new BusinessException(BusinessException.getErrorMessage(
						mContext, BusinessException.DEVICE_CONNECT_ERROR),
						BusinessException.DEVICE_CONNECT_ERROR);
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(BusinessException.getErrorMessage(
					mContext, BusinessException.DEVICE_CONNECT_ERROR),
					BusinessException.DEVICE_CONNECT_ERROR);
		}
	}

	/***************************************************
	 * 功能介绍：关注/取消关注
	 * 入参：        roleid 被关注的角色ID
	 *         bCared  关注或取消关注
	 * 出参:    无
	 * 返回值:  无
	 * 异常：        外部捕获BusinessException
	 **************************************************/
	public void careRole(String roleid,boolean bCared) throws BusinessException {
		String url = AndroidConfiguration.getInstance(mContext)
				.getBitHealthHost() + "api/user_update.php?type=follow";
		Map<String, String> mapPar = new HashMap<String, String>();
		mapPar.put("role_cookie", AndroidConfiguration.getInstance(mContext)
				.getRoleSession());
		mapPar.put("role_id", AndroidConfiguration.getInstance(mContext)
				.getRoleId());
		mapPar.put("role_follow_id", roleid);
		mapPar.put("role_follow", bCared?"1":"0");
		try {
			String response = HttpCommunication.postForm(url, mapPar, null, "",null);
			if (response == null) {
				throw new BusinessException(BusinessException.getErrorMessage(
						mContext, BusinessException.DEVICE_CONNECT_ERROR),
						BusinessException.DEVICE_CONNECT_ERROR);
			}
			JsonBase jBase = new JsonBase();
			jBase.toBean(response);
			if (jBase.mRcode != BusinessConst.RT_OK) {
				throw new BusinessException(BusinessException.getErrorMessage(
						mContext, jBase.mRcode),
						jBase.mRcode);
			} else {
				RoleCatchInfo.getInstance(mContext).updateCare(roleid, bCared);
				RoleCatchInfo.getInstance(mContext).commit();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BusinessException(BusinessException.getErrorMessage(
					mContext, BusinessException.DEVICE_CONNECT_ERROR),
					BusinessException.DEVICE_CONNECT_ERROR);
		}
	}
	/***************************************************
	 * 功能介绍：获取角色列表
	 * 入参：       无
	 * 出参:    无
	 * 返回值:  List<RoleInfoBean> 角色列表
	 * 异常：        外部捕获BusinessException
	 **************************************************/
	public List<RoleInfoBean> getRoleInfoList() throws BusinessException {
		String url = AndroidConfiguration.getInstance(mContext)
				.getBitHealthHost() + "api/user_info.php?type=list";
		Map<String, String> mapPar = new HashMap<String, String>();
		mapPar.put("session_id", AndroidConfiguration.getInstance(mContext)
				.getAccountSession());
		mapPar.put("user_id", AndroidConfiguration.getInstance(mContext)
				.getUserId());
		try {
			String response = HttpCommunication.postForm(url, mapPar, null, "",null);
			if (response == null) {
				throw new BusinessException("Http error!",-2);
			}
			RoleInfoBeanList roleInfoList = new RoleInfoBeanList();
			roleInfoList.toUserInfoBeans(response);
			if (roleInfoList.mRcode == BusinessConst.RT_OK) {
				return roleInfoList.mRolebeanlist;
			} else {
				throw new BusinessException(BusinessException.getErrorMessage(
						mContext, roleInfoList.mRcode),
						roleInfoList.mRcode);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BusinessException(BusinessException.getErrorMessage(
					mContext, BusinessException.DEVICE_CONNECT_ERROR),
					BusinessException.DEVICE_CONNECT_ERROR);
		}
	}
	/***************************************************
	 * 功能介绍：获取关注角色列表
	 * 入参：       无
	 * 出参:    无
	 * 返回值:  无
	 * 异常：        外部捕获BusinessException
	 **************************************************/
	public void getCareRoleList() throws BusinessException {
		String url = AndroidConfiguration.getInstance(mContext)
				.getBitHealthHost() + "api/user_info.php?type=follow_list";
		Map<String, String> mapPar = new HashMap<String, String>();
		mapPar.put("role_cookie", AndroidConfiguration.getInstance(mContext).getRoleSession());
		mapPar.put("role_id", AndroidConfiguration.getInstance(mContext).getRoleId());
		try {
			String response = HttpCommunication.postForm(url, mapPar, null, "",null);
			if (response == null) {
				throw new BusinessException(BusinessException.getErrorMessage(
						mContext, BusinessException.DEVICE_CONNECT_ERROR),
						BusinessException.DEVICE_CONNECT_ERROR);
			}
			JsonBase jBase = new JsonBase();
			JSONObject jObj = jBase.toBean(response);
			if (jBase.mRcode == BusinessConst.RT_OK) {
				jObj = jObj.optJSONObject("follow_role");
				RoleCatchInfo.getInstance(mContext).clearCare();
				if(jObj != null){
					for (int i = 0; i < RoleInfoBeanList.MAX_USERS; i++) {
						String careid = jObj.optString("role"+i,null);
						if(TextUtils.isEmpty(careid)){
							break;
						}
						RoleCatchInfo.getInstance(mContext).updateCare(careid, true);
					}
				}
				RoleCatchInfo.getInstance(mContext).commit();
			} else {
				throw new BusinessException(BusinessException.getErrorMessage(
						mContext, jBase.mRcode),
						jBase.mRcode);
			}
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BusinessException(BusinessException.getErrorMessage(
					mContext, BusinessException.DEVICE_CONNECT_ERROR),
					BusinessException.DEVICE_CONNECT_ERROR);
		}
	}
	

	/***************************************************
	 * 功能介绍：获取医生建议
	 * 入参：       roleId 角色ID
	 *         type 测量类型 ECG/PPG/BP/BGM 
	 *         eventId 测试事件ID
	 * 出参:    无
	 * 返回值:  String 医生建议
	 * 异常：        外部捕获BusinessException
	 **************************************************/
	public String getDoctorSuggest(String roleId, String type, int eventId)
			throws BusinessException {
		String url = AndroidConfiguration.getInstance(mContext)
				.getBitHealthHost() + "api/doctor_suggest.php";
		Map<String, String> mapPar = new HashMap<String, String>();
		mapPar.put("session_id", AndroidConfiguration.getInstance(mContext)
				.getAccountSession());
		mapPar.put("role_id", roleId);
		if (TextUtils.isEmpty(type)) {
			mapPar.put("suggest_type", type);
		}
		if (eventId != -1) {
			mapPar.put("event_id", "" + eventId);
		}
		try {
			String response = HttpCommunication.postForm(url, mapPar, null, "",null);
			if (response == null) {
				throw new BusinessException(BusinessException.getErrorMessage(
						mContext, BusinessException.DEVICE_CONNECT_ERROR),
						BusinessException.DEVICE_CONNECT_ERROR);
			}
			JsonBase jBase = new JsonBase();
			JSONObject jObj = jBase.toBean(response);
			if (jBase.mRcode == BusinessConst.RT_OK) {
				try {
					return jObj.getString("doctor_suggest");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new BusinessException(
							BusinessException.getErrorMessage(mContext,
									BusinessException.JSON_ERROR),
							BusinessException.JSON_ERROR);
				}
			} else {
				throw new BusinessException(BusinessException.getErrorMessage(
						mContext, jBase.mRcode),
						jBase.mRcode);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BusinessException(BusinessException.getErrorMessage(
					mContext, BusinessException.DEVICE_CONNECT_ERROR),
					BusinessException.DEVICE_CONNECT_ERROR);
		}
	}
	
	/***************************************************
	 * 功能介绍：获取心电图URL
	 * 入参：       eventId 心电记录id
	 *         pageid 页面ID
	 * 出参:    无
	 * 返回值:  String url
	 * 异常：        外部捕获BusinessException
	 **************************************************/
	public String getEcgImageUrl(String eventId,int pageid)
			throws BusinessException {
		String url = AndroidConfiguration.getInstance(mContext)
				.getBitHealthHost() + "api/img_info.php";
		Map<String, String> mapPar = new HashMap<String, String>();
		mapPar.put("type", "ecg_img");
		mapPar.put("sid", AndroidConfiguration.getInstance(mContext)
				.getAccountSession());
		mapPar.put("eid", eventId);
		mapPar.put("page", ""+pageid);
		try {
			String response = HttpCommunication.get(url, mapPar);
			if (response == null) {
				throw new BusinessException(BusinessException.getErrorMessage(
						mContext, BusinessException.DEVICE_CONNECT_ERROR),
						BusinessException.DEVICE_CONNECT_ERROR);
			}
			JsonBase jBase = new JsonBase();
			JSONObject jObj = jBase.toBean(response);
			if (jBase.mRcode == BusinessConst.RT_OK) {
				try {
					return jObj.getString("img_url");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new BusinessException(
							BusinessException.getErrorMessage(mContext,
									BusinessException.JSON_ERROR),
							BusinessException.JSON_ERROR);
				}
			} else {
				throw new BusinessException(BusinessException.getErrorMessage(
						mContext, jBase.mRcode),
						jBase.mRcode);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BusinessException(BusinessException.getErrorMessage(
					mContext, BusinessException.DEVICE_CONNECT_ERROR),
					BusinessException.DEVICE_CONNECT_ERROR);
		}
	}
	/***************************************************
	 * 功能介绍：检查是否有新的版本下载
	 * 入参：       无
	 * 出参:    无
	 * 返回值:  CheckVersionBean 
	 * 异常：        外部捕获BusinessException
	 **************************************************/
	@SuppressWarnings("deprecation")
	public CheckVersionBean checkAppVersion() throws BusinessException {
		String url = AndroidConfiguration.getInstance(mContext)
				.getBitHealthHost() + "api/app_update.php";
		Map<String, String> mapPar = new HashMap<String, String>();
		mapPar.put("type", "check");
		PackageManager packageManager = mContext.getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo;
		try {
			packInfo = packageManager.getPackageInfo(
					mContext.getPackageName(), 0);			
			mapPar.put("version", "" + packInfo.versionCode);
		} catch (NameNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		mapPar.put("device_type", "android");
		mapPar.put("sdk_version", android.os.Build.VERSION.SDK);
		try {
			String response = HttpCommunication.get(url, mapPar);			
			if (response == null) {
				throw new BusinessException(BusinessException.getErrorMessage(
						mContext, BusinessException.DEVICE_CONNECT_ERROR),
						BusinessException.DEVICE_CONNECT_ERROR);
			}
			CheckVersionBean bean = new CheckVersionBean();
			bean.parserJson(response);
			if (bean.mRcode == BusinessConst.RT_OK) {
				// 设置最后更新时间
				if (bean.mIsForce != 1) {
					AndroidConfiguration.getInstance(mContext)
							.setLastUpdateTime(
									CheckVersionTask.KEY_CONFIG_UPDATE_TIME,
									new Date().getTime());
				}
				return bean;
			} else {
				throw new BusinessException(BusinessException.getErrorMessage(
						mContext, bean.mRcode),
						bean.mRcode);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BusinessException(BusinessException.getErrorMessage(
					mContext, BusinessException.DEVICE_CONNECT_ERROR),
					BusinessException.DEVICE_CONNECT_ERROR);
		}
	}
	
	/***************************************************
	 * 功能介绍：获取脉搏图URL
	 * 入参：       eventId 心电记录id
	 *         pageid 页面ID
	 * 出参:    无
	 * 返回值:  String url
	 * 异常：        外部捕获BusinessException
	 **************************************************/
	public String getPpgImageUrl(String eventId,int pageid)
			throws BusinessException {
		String url = AndroidConfiguration.getInstance(mContext)
				.getBitHealthHost() + "api/img_info.php";
		Map<String, String> mapPar = new HashMap<String, String>();
		mapPar.put("type", "ppg_img");
		mapPar.put("sid", AndroidConfiguration.getInstance(mContext)
				.getAccountSession());
		mapPar.put("eid", eventId);
		mapPar.put("page", ""+pageid);
		try {
			String response = HttpCommunication.get(url, mapPar);
			if (response == null) {
				throw new BusinessException(BusinessException.getErrorMessage(
						mContext, BusinessException.DEVICE_CONNECT_ERROR),
						BusinessException.DEVICE_CONNECT_ERROR);
			}
			JsonBase jBase = new JsonBase();
			JSONObject jObj = jBase.toBean(response);
			if (jBase.mRcode == BusinessConst.RT_OK) {
				try {
					return jObj.getString("img_url");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new BusinessException(
							BusinessException.getErrorMessage(mContext,
									BusinessException.JSON_ERROR),
							BusinessException.JSON_ERROR);
				}
			} else {
				throw new BusinessException(BusinessException.getErrorMessage(
						mContext, jBase.mRcode),
						jBase.mRcode);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BusinessException(BusinessException.getErrorMessage(
					mContext, BusinessException.DEVICE_CONNECT_ERROR),
					BusinessException.DEVICE_CONNECT_ERROR);
		}
	}
	/***************************************************
	 * 功能介绍：获取异常心电图URL
	 * 入参：       eventId 心电记录id
	 *         symptom 异常参数
	 * 出参:    无
	 * 返回值:  String url
	 * 异常：        外部捕获BusinessException
	 **************************************************/
	public String getABEcgImageUrl(String eventId,String symptom)
			throws BusinessException {
		String url = AndroidConfiguration.getInstance(mContext)
				.getBitHealthHost() + "api/img_info.php";
		Map<String, String> mapPar = new HashMap<String, String>();
		mapPar.put("type", "ecg_ab_img");
		mapPar.put("sid", AndroidConfiguration.getInstance(mContext)
				.getAccountSession());
		mapPar.put("eid", eventId);
		mapPar.put("symptom", symptom);
		try {
			String response = HttpCommunication.get(url, mapPar);
			if (response == null) {
				throw new BusinessException(BusinessException.getErrorMessage(
						mContext, BusinessException.DEVICE_CONNECT_ERROR),
						BusinessException.DEVICE_CONNECT_ERROR);
			}
			JsonBase jBase = new JsonBase();
			JSONObject jObj = jBase.toBean(response);
			if (jBase.mRcode == BusinessConst.RT_OK) {
				try {
					return jObj.getString("img_url");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new BusinessException(
							BusinessException.getErrorMessage(mContext,
									BusinessException.JSON_ERROR),
							BusinessException.JSON_ERROR);
				}
			} else {
				throw new BusinessException(BusinessException.getErrorMessage(
						mContext, jBase.mRcode),
						jBase.mRcode);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BusinessException(BusinessException.getErrorMessage(
					mContext, BusinessException.DEVICE_CONNECT_ERROR),
					BusinessException.DEVICE_CONNECT_ERROR);
		}
	}
	/***************************************************
	 * 功能介绍：获取瞬时心电图URL
	 * 入参：       eventId 心电记录id
	 *         pageid 页面ID
	 * 出参:    无
	 * 返回值:  String url
	 * 异常：        外部捕获BusinessException
	 **************************************************/
	public String getEcgHrImageUrl(String eventId,int pageid)
			throws BusinessException {
		String url = AndroidConfiguration.getInstance(mContext)
				.getBitHealthHost() + "api/img_info.php";
		Map<String, String> mapPar = new HashMap<String, String>();
		mapPar.put("type", "ecg_hr_img");
		mapPar.put("sid", AndroidConfiguration.getInstance(mContext)
				.getAccountSession());
		mapPar.put("eid", eventId);
		mapPar.put("page", ""+pageid);
		try {
			String response = HttpCommunication.get(url, mapPar);
			if (response == null) {
				throw new BusinessException(BusinessException.getErrorMessage(
						mContext, BusinessException.DEVICE_CONNECT_ERROR),
						BusinessException.DEVICE_CONNECT_ERROR);
			}
			JsonBase jBase = new JsonBase();
			JSONObject jObj = jBase.toBean(response);
			if (jBase.mRcode == BusinessConst.RT_OK) {
				try {
					return jObj.getString("img_url");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new BusinessException(
							BusinessException.getErrorMessage(mContext,
									BusinessException.JSON_ERROR),
							BusinessException.JSON_ERROR);
				}
			} else {
				throw new BusinessException(BusinessException.getErrorMessage(
						mContext, jBase.mRcode),
						jBase.mRcode);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BusinessException(BusinessException.getErrorMessage(
					mContext, BusinessException.DEVICE_CONNECT_ERROR),
					BusinessException.DEVICE_CONNECT_ERROR);
		}
	}
	
	/***************************************************
	 * 功能介绍：获取瞬时脉搏图URL
	 * 入参：       eventId 心电记录id
	 * 出参:    无
	 * 返回值:  String url
	 * 异常：        外部捕获BusinessException
	 **************************************************/
	public String getPpgHrImageUrl(String eventId)
			throws BusinessException {
		String url = AndroidConfiguration.getInstance(mContext)
				.getBitHealthHost() + "api/img_info.php";
		Map<String, String> mapPar = new HashMap<String, String>();
		mapPar.put("type", "ppg_hr_img");
		mapPar.put("sid", AndroidConfiguration.getInstance(mContext)
				.getAccountSession());
		mapPar.put("eid", eventId);
		try {
			String response = HttpCommunication.get(url, mapPar);
			if (response == null) {
				throw new BusinessException(BusinessException.getErrorMessage(
						mContext, BusinessException.DEVICE_CONNECT_ERROR),
						BusinessException.DEVICE_CONNECT_ERROR);
			}
			JsonBase jBase = new JsonBase();
			JSONObject jObj = jBase.toBean(response);
			if (jBase.mRcode == BusinessConst.RT_OK) {
				try {
					return jObj.getString("img_url");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new BusinessException(
							BusinessException.getErrorMessage(mContext,
									BusinessException.JSON_ERROR),
							BusinessException.JSON_ERROR);
				}
			} else {
				throw new BusinessException(BusinessException.getErrorMessage(
						mContext, jBase.mRcode),
						jBase.mRcode);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BusinessException(BusinessException.getErrorMessage(
					mContext, BusinessException.DEVICE_CONNECT_ERROR),
					BusinessException.DEVICE_CONNECT_ERROR);
		}
	}
	/***************************************************
	 * 功能介绍：获取指定时间段异常数据数量
	 * 入参：       roleId 角色ID
	 *         type 测量类型 ECG/PPG/BP/BGM 
	 *         tBegin 开始时间
	 *         tEnd   结束时间
	 * 出参:    无
	 * 返回值:  int 数量
	 * 异常：        外部捕获BusinessException
	 **************************************************/
	public int getAbnormalMeasures(String roleId, String tBegin, String tEnd,
			String type) throws BusinessException {
		String url = AndroidConfiguration.getInstance(mContext)
				.getBitHealthHost() + "api/abnormal_info.php?type=count";
		Map<String, String> mapPar = new HashMap<String, String>();
		mapPar.put("session_id", AndroidConfiguration.getInstance(mContext)
				.getAccountSession());
		mapPar.put("role_id", roleId);
		mapPar.put("time_begin", tBegin);
		mapPar.put("time_end", tEnd);
		mapPar.put("measure_type", type);
		try {
			String response = HttpCommunication.postForm(url, mapPar, null, "",null);
			if (response == null) {
				throw new BusinessException(BusinessException.getErrorMessage(
						mContext, BusinessException.DEVICE_CONNECT_ERROR),
						BusinessException.DEVICE_CONNECT_ERROR);
			}
			JsonBase jBase = new JsonBase();
			JSONObject jObj = jBase.toBean(response);
			if (jBase.mRcode == BusinessConst.RT_OK) {
				try {
					return jObj.getInt("abnormal_count");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new BusinessException(
							BusinessException.getErrorMessage(mContext,
									BusinessException.JSON_ERROR),
							BusinessException.JSON_ERROR);
				}
			} else {
				throw new BusinessException(BusinessException.getErrorMessage(
						mContext, jBase.mRcode),
						jBase.mRcode);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BusinessException(BusinessException.getErrorMessage(
					mContext, BusinessException.DEVICE_CONNECT_ERROR),
					BusinessException.DEVICE_CONNECT_ERROR);
		}
	}
	
	/***************************************************
	 * 功能介绍：获取分析结果
	 * 入参：       roleId 角色ID
	 *         type   类型bs/bp/ecg/ppg/count
	 * 出参:    无
	 * 返回值:  JsonBase 分析结果
	 * 异常：        外部捕获BusinessException
	 **************************************************/
	public JsonBase getAnalyzeResult(String roleid, String type) throws BusinessException {
		String url = AndroidConfiguration.getInstance(mContext)
				.getBitHealthHost() + "api/score_api.php?type=info";
		Map<String, String> mapPar = new HashMap<String, String>();
		mapPar.put("session_id", AndroidConfiguration.getInstance(mContext)
				.getAccountSession());
		mapPar.put("role_id", roleid);
		mapPar.put("m_type", type);
		try {
			String response = HttpCommunication.postForm(url, mapPar, null, "",null);
			if (response == null) {
				throw new BusinessException(BusinessException.getErrorMessage(
						mContext, BusinessException.DEVICE_CONNECT_ERROR),
						BusinessException.DEVICE_CONNECT_ERROR);
			}

			JsonBase jbase = new JsonBase();
			jbase.toBean(response);
			if (jbase.mRcode == BusinessConst.RT_OK) {
				if (BusinessConst.SCORE_TYPE_BP.equals(type)) {
					// BP数据类型
					BpScoreBean bean = new BpScoreBean();
					try {
						bean.parserJson(response);
						return bean;
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						throw new BusinessException(
								BusinessException.getErrorMessage(mContext,
										BusinessException.JSON_ERROR),
								BusinessException.JSON_ERROR);
					}

				} else if (BusinessConst.SCORE_TYPE_BS.equals(type)) {
					// BS数据类型
					BsScoreBean bean = new BsScoreBean();
					try {
						bean.parserJson(response);
						return bean;
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						throw new BusinessException(
								BusinessException.getErrorMessage(mContext,
										BusinessException.JSON_ERROR),
								BusinessException.JSON_ERROR);
					}
				}else if (BusinessConst.SCORE_TYPE_ECG.equals(type)) {
					// ECG数据类型
					EcgScoreBean bean = new EcgScoreBean();
					try {
						bean.parserJson(response);
						return bean;
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						throw new BusinessException(
								BusinessException.getErrorMessage(mContext,
										BusinessException.JSON_ERROR),
								BusinessException.JSON_ERROR);
					}
				}else if (BusinessConst.SCORE_TYPE_PPG.equals(type)) {
					// PPG数据类型
					PpgScoreBean bean = new PpgScoreBean();
					try {
						bean.parserJson(response);
						return bean;
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						throw new BusinessException(
								BusinessException.getErrorMessage(mContext,
										BusinessException.JSON_ERROR),
								BusinessException.JSON_ERROR);
					}
				} else if (BusinessConst.SCORE_TYPE_OVERALL.equals(type)) {
					// OVERALL数据类型
					OverScoreBean bean = new OverScoreBean();
					try {
						bean.parserJson(response);
						return bean;
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						throw new BusinessException(
								BusinessException.getErrorMessage(mContext,
										BusinessException.JSON_ERROR),
								BusinessException.JSON_ERROR);
					}
				}else {
					throw new BusinessException(BusinessException.getErrorMessage(
							mContext, BusinessException.SERVER_INTERNAL_ERROR),BusinessException.SERVER_INTERNAL_ERROR);
				}
			} else {
				throw new BusinessException(BusinessException.getErrorMessage(
						mContext, jbase.mRcode),
						jbase.mRcode);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BusinessException(BusinessException.getErrorMessage(
					mContext, BusinessException.DEVICE_CONNECT_ERROR),
					BusinessException.DEVICE_CONNECT_ERROR);
		}
	}
	

	/***************************************************
	 * 功能介绍：获取各种测量信息列表
	 * 入参：       roleId 角色ID
	 *         pos 其实位置从0开始
	 *         count 数量
	 *         type   测量类型PPG/ECG/BP
	 * 出参:    无
	 * 返回值:  List<JsonBase> 各种测量信息列表
	 * 异常：        外部捕获BusinessException
	 **************************************************/
	public List<JsonBase> getMeasures(String roleid, int pos, int count, String error,
			String type) throws BusinessException {
		String url = AndroidConfiguration.getInstance(mContext)
				.getBitHealthHost() + "api/measure_info.php?type=list";
		Map<String, String> mapPar = new HashMap<String, String>();
		mapPar.put("session_id", AndroidConfiguration.getInstance(mContext)
				.getAccountSession());
		mapPar.put("offset", "" + pos);
		mapPar.put("num", "" + count);
		mapPar.put("measure_type", type);
		mapPar.put("role_id", roleid);
		mapPar.put("result_type", error);
		try {
			String response = HttpCommunication.postForm(url, mapPar, null, "",null);
			if (response == null) {
				throw new BusinessException(BusinessException.getErrorMessage(
						mContext, BusinessException.DEVICE_CONNECT_ERROR),
						BusinessException.DEVICE_CONNECT_ERROR);
			}

			MeasureInfoList infoList = new MeasureInfoList(type);
			infoList.toMeasureInfoListBean(response);
			if (infoList.mRcode == BusinessConst.RT_OK) {
				return infoList.mMeasureList;
			} else {
				throw new BusinessException(BusinessException.getErrorMessage(
						mContext, infoList.mRcode),
						infoList.mRcode);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BusinessException(BusinessException.getErrorMessage(
					mContext, BusinessException.DEVICE_CONNECT_ERROR),
					BusinessException.DEVICE_CONNECT_ERROR);
		}
	}
	
	/***************************************************
	 * 功能介绍：根据日期获取各种测量信息列表
	 * 入参：       roleId 角色ID
	 *         pos 其实位置从0开始
	 *         count 数量
	 *         begin 开始日期
	 *         end   结束日期
	 *         type   测量类型PPG/ECG/BP/BS/ALL
	 * 出参:    无
	 * 返回值:  List<JsonBase> 各种测量信息列表
	 * 异常：        外部捕获BusinessException
	 **************************************************/
	public List<JsonBase> getMeasuresFromDate(String roleid, int pos, int count, String begin,String end,String error,
			String type) throws BusinessException {
		String url = AndroidConfiguration.getInstance(mContext)
				.getBitHealthHost() + "api/measure_info.php?type=list";
		Map<String, String> mapPar = new HashMap<String, String>();
		mapPar.put("session_id", AndroidConfiguration.getInstance(mContext)
				.getAccountSession());
		mapPar.put("offset", "" + pos);
		mapPar.put("num", "" + count);
		mapPar.put("time_begin", "" + begin);
		mapPar.put("time_end", end);
		mapPar.put("measure_type", type);
		mapPar.put("role_id", roleid);
		mapPar.put("result_type", error);
		try {
			String response = HttpCommunication.postForm(url, mapPar, null, "",null);
			if (response == null) {
				throw new BusinessException(BusinessException.getErrorMessage(
						mContext, BusinessException.DEVICE_CONNECT_ERROR),
						BusinessException.DEVICE_CONNECT_ERROR);
			}

			MeasureInfoList infoList = new MeasureInfoList(type);
			infoList.toMeasureInfoListBean(response);
			if (infoList.mRcode == BusinessConst.RT_OK) {
				return infoList.mMeasureList;
			} else {
				throw new BusinessException(BusinessException.getErrorMessage(
						mContext, infoList.mRcode),
						infoList.mRcode);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BusinessException(BusinessException.getErrorMessage(
					mContext, BusinessException.DEVICE_CONNECT_ERROR),
					BusinessException.DEVICE_CONNECT_ERROR);
		}
	}

	/***************************************************
	 * 功能介绍：获取详细测量数据信息
	 * 入参：       date 测量日期（格式为YYYY-MM-DD）
	 *         roleid 角色ID
	 *         type 类型PPG/ECG/BP/BS
	 * 出参:    无
	 * 返回值:  JsonBase 测量详细信息
	 * 异常：        外部捕获BusinessException
	 **************************************************/
	public JsonBase getMeasureDetail(String roleid,String date, String type)
			throws BusinessException {
		String url = AndroidConfiguration.getInstance(mContext)
				.getBitHealthHost() + "api/measure_info.php?type=date_detail";
		Map<String, String> mapPar = new HashMap<String, String>();
		mapPar.put("session_id", AndroidConfiguration.getInstance(mContext)
				.getAccountSession());
		mapPar.put("role_id", roleid);
		mapPar.put("date", date);
		mapPar.put("measure_type", type);
		try {
			String response = HttpCommunication.postForm(url, mapPar, null, "",null);
			if (response == null) {
				throw new BusinessException(BusinessException.getErrorMessage(
						mContext, BusinessException.DEVICE_CONNECT_ERROR),
						BusinessException.DEVICE_CONNECT_ERROR);
			}

			JsonBase jBase = new JsonBase();
			JSONObject jObject = jBase.toBean(response);
			if (jBase.mRcode == BusinessConst.RT_OK) {
				if (BusinessConst.ECG_MESURE.equals(type)) {
					// Ecg数据类型
					EcgInfoBean bean = new EcgInfoBean();
					try {
						bean.parserJson(jObject
								.getString(MeasureInfoList.DATA_FLAG));
						bean.mType = BusinessConst.DATA_DETAIL;
						return bean;
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						throw new BusinessException(
								BusinessException.getErrorMessage(mContext,
										BusinessException.JSON_ERROR),
								BusinessException.JSON_ERROR);
					}

				} else if (BusinessConst.PPG_MESURE.equals(type)) {
					// ppg数据类型
					PpgInfoBean bean = new PpgInfoBean();
					try {
						bean.parserJson(jObject
								.getString(MeasureInfoList.DATA_FLAG));
						bean.mType = BusinessConst.DATA_DETAIL;
						return bean;
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						throw new BusinessException("JSONException : "
								+ e.getMessage(),-3);
					}
				} else if (BusinessConst.BP_MESURE.equals(type)) {
					// bp数据类型
					BpInfoBean bean = new BpInfoBean();
					try {
						bean.parserJson(jObject
								.getString(MeasureInfoList.DATA_FLAG));
						bean.mType = BusinessConst.DATA_DETAIL;
						return bean;
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						throw new BusinessException(
								BusinessException.getErrorMessage(mContext,
										BusinessException.JSON_ERROR),
								BusinessException.JSON_ERROR);
					}
				} else if (BusinessConst.BS_MESURE.equals(type)) {
					// bs数据类型
					BsInfoBean bean = new BsInfoBean();
					try {
						bean.parserJson(jObject
								.getString(MeasureInfoList.DATA_FLAG));
						bean.mType = BusinessConst.DATA_DETAIL;
						return bean;
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						throw new BusinessException(
								BusinessException.getErrorMessage(mContext,
										BusinessException.JSON_ERROR),
								BusinessException.JSON_ERROR);
					}
				} else {
					throw new BusinessException(BusinessException.getErrorMessage(
							mContext, BusinessException.SERVER_INTERNAL_ERROR),BusinessException.SERVER_INTERNAL_ERROR);
				}
			} else {
				throw new BusinessException(BusinessException.getErrorMessage(
						mContext, jBase.mRcode),
						jBase.mRcode);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BusinessException(BusinessException.getErrorMessage(
					mContext, BusinessException.DEVICE_CONNECT_ERROR),
					BusinessException.DEVICE_CONNECT_ERROR);
		}
	}
	

	/***************************************************
	 * 功能介绍：获取详细测量数据信息
	 * 入参：       id 测量ID
	 *         type 类型PPG/ECG/BP
	 * 出参:    无
	 * 返回值:  JsonBase 测量详细信息
	 * 异常：        外部捕获BusinessException
	 **************************************************/
	public JsonBase getMeasureDetail(String id, String type)
			throws BusinessException {
		String url = AndroidConfiguration.getInstance(mContext)
				.getBitHealthHost() + "api/measure_info.php?type=detail";
		Map<String, String> mapPar = new HashMap<String, String>();
		mapPar.put("session_id", AndroidConfiguration.getInstance(mContext)
				.getAccountSession());
		mapPar.put("measure_id", id);
		mapPar.put("measure_type", type);
		try {
			String response = HttpCommunication.postForm(url, mapPar, null, "",null);
			if (response == null) {
				throw new BusinessException(BusinessException.getErrorMessage(
						mContext, BusinessException.DEVICE_CONNECT_ERROR),
						BusinessException.DEVICE_CONNECT_ERROR);
			}

			JsonBase jBase = new JsonBase();
			JSONObject jObject = jBase.toBean(response);
			if (jBase.mRcode == BusinessConst.RT_OK) {
				if (BusinessConst.ECG_MESURE.equals(type)) {
					// Ecg数据类型
					EcgInfoBean bean = new EcgInfoBean();
					try {
						bean.parserJson(jObject
								.getString(MeasureInfoList.DATA_FLAG));
						bean.mType = BusinessConst.DATA_DETAIL;
						bean.mId = id;
						return bean;
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						throw new BusinessException(
								BusinessException.getErrorMessage(mContext,
										BusinessException.JSON_ERROR),
								BusinessException.JSON_ERROR);
					}

				} else if (BusinessConst.PPG_MESURE.equals(type)) {
					// ppg数据类型
					PpgInfoBean bean = new PpgInfoBean();
					try {
						bean.parserJson(jObject
								.getString(MeasureInfoList.DATA_FLAG));
						bean.mId = id;
						return bean;
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						throw new BusinessException(
								BusinessException.getErrorMessage(mContext,
										BusinessException.JSON_ERROR),
								BusinessException.JSON_ERROR);
					}
				} else if (BusinessConst.BP_MESURE.equals(type)) {
					// bp数据类型
					BpInfoBean bean = new BpInfoBean();
					try {
						bean.parserJson(jObject
								.getString(MeasureInfoList.DATA_FLAG));
						bean.mType = BusinessConst.DATA_DETAIL;
						bean.mId = id;
						return bean;
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						throw new BusinessException(
								BusinessException.getErrorMessage(mContext,
										BusinessException.JSON_ERROR),
								BusinessException.JSON_ERROR);
					}
				} else if (BusinessConst.BS_MESURE.equals(type)) {
					// bs数据类型
					BsInfoBean bean = new BsInfoBean();
					try {
						bean.parserJson(jObject
								.getString(MeasureInfoList.DATA_FLAG));
						bean.mType = BusinessConst.DATA_DETAIL;
						bean.mId = id;
						return bean;
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						throw new BusinessException(
								BusinessException.getErrorMessage(mContext,
										BusinessException.JSON_ERROR),
								BusinessException.JSON_ERROR);
					}
				} else {
					throw new BusinessException(BusinessException.getErrorMessage(
							mContext, BusinessException.SERVER_INTERNAL_ERROR),BusinessException.SERVER_INTERNAL_ERROR);
				}
			} else {
				throw new BusinessException(BusinessException.getErrorMessage(
						mContext, jBase.mRcode),
						jBase.mRcode);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BusinessException(BusinessException.getErrorMessage(
					mContext, BusinessException.DEVICE_CONNECT_ERROR),
					BusinessException.DEVICE_CONNECT_ERROR);
		}
	}

	/***************************************************
	 * 功能介绍：上传血压数据
	 * 入参：          roleid  角色ID
	 *          UploadBpBean 血压数据
	 * 出参:    无
	 * 返回值:  string id
	 * 异常：        外部捕获BusinessException
	 **************************************************/
	public String UploadBp(UploadBpBean bean)
			throws BusinessException {
		String url = AndroidConfiguration.getInstance(mContext)
				.getBitHealthHost() + "api/measure_add.php?type=bp";
		Map<String, String> mapPar = new HashMap<String, String>();
		mapPar.put("role_id", AndroidConfiguration.getInstance(mContext)
				.getRoleId());
		mapPar.put("role_cookie", AndroidConfiguration.getInstance(mContext).getRoleSession());
		mapPar.put("date", ""+bean.date);
		mapPar.put("wake_SBP", ""+bean.mWakeSBP);
		mapPar.put("wake_DBP", ""+bean.mWakeDBP);
		mapPar.put("wake_pulse_rate", ""+bean.mWakePulseRate);
		mapPar.put("wake_user_systolic", ""+bean.mWakeUserSystolic);
		mapPar.put("wake_user_diastolic", ""+bean.mWakeUserDiastolic);
		mapPar.put("sleep_SBP", ""+bean.mSleepSBP);
		mapPar.put("sleep_DBP", ""+bean.mSleepDBP);
		mapPar.put("sleep_pulse_rate", ""+bean.mSleepPulseRate);
		mapPar.put("sleep_user_systolic", ""+bean.mSleepUserSystolic);
		mapPar.put("sleep_user_diastolic", ""+bean.mSleepUserDiastolic);
		try {
			String response = HttpCommunication.postForm(url, mapPar, null, "",null);
			if (response == null) {
				throw new BusinessException(BusinessException.getErrorMessage(
						mContext, BusinessException.DEVICE_CONNECT_ERROR),
						BusinessException.DEVICE_CONNECT_ERROR);
			}

			JsonBase jBase = new JsonBase();
			JSONObject jObj = jBase.toBean(response);
			if (jBase.mRcode == BusinessConst.RT_OK) {
				return jObj.optString("bp_id");
			} else {
				throw new BusinessException(BusinessException.getErrorMessage(
						mContext, jBase.mRcode),
						jBase.mRcode);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BusinessException(BusinessException.getErrorMessage(
					mContext, BusinessException.DEVICE_CONNECT_ERROR),
					BusinessException.DEVICE_CONNECT_ERROR);
		}
	}
	/***************************************************
	 * 功能介绍：获取PR数据
	 * 入参：          eventId  ECG分析后的数据
	 * 出参:    无
	 * 返回值:  int[] pr数据
	 * 异常：        外部捕获BusinessException
	 **************************************************/
	public int[] getEcgAnlyseResult(String eventId,String roleid)
			throws BusinessException {
		String url = AndroidConfiguration.getInstance(mContext)
				.getBitHealthHost() + "api/measure_info.php?type=rr_date";
		Map<String, String> mapPar = new HashMap<String, String>();
		mapPar.put("role_id", roleid);
		mapPar.put("session_id", AndroidConfiguration.getInstance(mContext)
				.getAccountSession());
		mapPar.put("event_type", "0");
		mapPar.put("event_id", eventId);
		try {
			String response = HttpCommunication.postForm(url, mapPar,null,"",null);
			if (response == null) {
				throw new BusinessException(BusinessException.getErrorMessage(
						mContext, BusinessException.DEVICE_CONNECT_ERROR),
						BusinessException.DEVICE_CONNECT_ERROR);
			}
			JsonBase jBase = new JsonBase();
			JSONObject jObj = jBase.toBean(response);
			if (jBase.mRcode == BusinessConst.RT_OK) {
				String rrInterval;
				try {
					rrInterval = jObj
							.getString("result");
					String[] rrString = rrInterval.split("\\n");
					if (rrString.length > 0) {
						int[] RR = new int[rrString.length];
						for (int i = 0; i < RR.length; i++) {
							RR[i] = Integer.valueOf(rrString[i]);
						}
						return RR;
					}else{
						throw new BusinessException(BusinessException.getErrorMessage(
								mContext, jBase.mRcode),
								jBase.mRcode);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new BusinessException(
							BusinessException.getErrorMessage(mContext,
									BusinessException.JSON_ERROR),
							BusinessException.JSON_ERROR);
				}
				
			} else {
				throw new BusinessException(BusinessException.getErrorMessage(
						mContext, jBase.mRcode),
						jBase.mRcode);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BusinessException(BusinessException.getErrorMessage(
					mContext, BusinessException.DEVICE_CONNECT_ERROR),
					BusinessException.DEVICE_CONNECT_ERROR);
		}
	}
	

	/***************************************************
	 * 功能介绍：上传血糖数据
	 * 入参：          roleid  角色ID
	 *          UploadBpBean 血糖数据
	 * 出参:    无
	 * 返回值:  无
	 * 异常：        外部捕获BusinessException
	 **************************************************/
	public String UploadBs(UploadBsBean bean)
			throws BusinessException {
		String url = AndroidConfiguration.getInstance(mContext)
				.getBitHealthHost() + "api/measure_add.php?type=bs";
		Map<String, String> mapPar = new HashMap<String, String>();
		mapPar.put("role_id", AndroidConfiguration.getInstance(mContext)
				.getRoleId());
		mapPar.put("role_cookie", AndroidConfiguration.getInstance(mContext).getRoleSession());
		mapPar.put("limosis_value", ""+bean.mLimosisValue);
		mapPar.put("before_lunch_value", ""+bean.mBLunchValue);
		mapPar.put("after_lunch_value", ""+bean.mALunchValue);
		mapPar.put("before_sleep_value", ""+bean.mBSleepValue);
		mapPar.put("date", bean.date);
		try {
			String response = HttpCommunication.postForm(url, mapPar, null, "",null);
			if (response == null) {
				throw new BusinessException(BusinessException.getErrorMessage(
						mContext, BusinessException.DEVICE_CONNECT_ERROR),
						BusinessException.DEVICE_CONNECT_ERROR);
			}

			JsonBase jBase = new JsonBase();
			JSONObject jObj = jBase.toBean(response);
			if (jBase.mRcode == BusinessConst.RT_OK) {
				return jObj.optString("bs_id");
			} else {
				throw new BusinessException(BusinessException.getErrorMessage(
						mContext, jBase.mRcode),
						jBase.mRcode);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BusinessException(BusinessException.getErrorMessage(
					mContext, BusinessException.DEVICE_CONNECT_ERROR),
					BusinessException.DEVICE_CONNECT_ERROR);
		}
	}
	/***************************************************
	 * 功能介绍：下载文件
	 * 入参：          url  网络地址
	 *          filePath 存放在本地的地址
	 *          IProgressCallback 回调
	 * 出参:    文件本地地址
	 * 返回值:  无
	 **************************************************/
	public boolean DownLoadFile(String url,String filePath,
			IProgressCallback callback){
		String path = null;
		if(TextUtils.isEmpty(filePath)){
			String []strTemp = url.split("/");
			path = FileCatchConfigUtil.getCatchPath();
			if(TextUtils.isEmpty(path)){
				return false;
			}
			if (strTemp.length == 0) {
				return false;
			} else {
				path = path + strTemp[strTemp.length - 1];
			}
		}else{
			path = filePath;
		}
		
		
		File f = new File(path);
		FileOutputStream out = null;
		try {
			f.createNewFile();
			out = new FileOutputStream(f);
			if(!HttpCommunication.downloadFile(url, out, callback)){
				if(out != null){
					out.close();
					out = null;
				}
				if(callback != null){
					callback.downloadError("");
				}
				f.delete();
				return false;
			}
			if(out != null){
				out.close();
				out = null;
			}
			if(callback != null){
				callback.downloadSuccess(path);
			}
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(out != null){
			try {
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			out = null;
		}
		if(callback != null){
			callback.downloadError("");
		}
		return false;
	}
	/***************************************************
	 * 功能介绍：文件方式上传测量数据(新)
	 * 入参：          roleid  角色ID
	 *          UploadDeviceFileBean 测量文件
	 * 出参:    无
	 * 返回值:  无
	 **************************************************/
	public boolean UploadDeviceFile2(UploadDeviceFileBean bean,
			IProgressCallback callback){
		String url = AndroidConfiguration.getInstance(mContext)
				.getBitHealthHost() + "/api/measure_add.php?type=ecg";
		String session = AndroidConfiguration.getInstance(mContext).getRoleSession();
		String roleid = AndroidConfiguration.getInstance(mContext).getRoleId();
		bean.mapPar.put("role_id", roleid);
		bean.mapPar.put("role_cookie", session);
		try {
			String str = HttpCommunication.postForm(url, bean.mapPar, bean.mapFile,"",callback);
			if (str != null) {
				JsonBase jBase = new JsonBase();
				JSONObject jObj = jBase.toBean(str);
				if (jBase.mRcode == BusinessConst.RT_OK) {
					if(callback != null){
						String ecgid = jObj.optString("ecg_id");
						String ppgid = jObj.optString("ppg_id");
						callback.uploadSuccess("上传成功",ecgid,ppgid);
					}
					return true;
				} else {
					if(callback != null){
						callback.uploadError(BusinessException.getErrorMessage(
								mContext, jBase.mRcode));
					}
					System.out.println(str);
					return false;
				}
			} else {
				if(callback != null){
					callback.uploadError(BusinessException.getErrorMessage(
							mContext, BusinessException.DEVICE_CONNECT_ERROR));
				}
				// result = mContext.getString(R.string.U_string4);
				// updateProgress(0,100,result);
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
			if(callback != null){
				callback.uploadError("上传失败:"+e.getMessage());
			}
		}
		return false;

	
	}

	/***************************************************
	 * 功能介绍：文件方式上传测量数据
	 * 入参：          roleid  角色ID
	 *          UploadDeviceFileBean 测量文件
	 * 出参:    无
	 * 返回值:  无
	 **************************************************/
	public void UploadDeviceFile(UploadDeviceFileBean bean,
			IProgressCallback callback){
		String url = AndroidConfiguration.getInstance(mContext)
				.getBitHealthHost() + "/save_data/vytv.php";
		String session = AndroidConfiguration.getInstance(mContext).getRoleSession();
		try {
			String str = HttpCommunication.postForm(url, bean.mapPar, bean.mapFile,
					session,callback);
			if (str != null) {
				if (str.startsWith("1")) {
					// ECG数据发送成功

					// result = mContext.getString(R.string.U_string1);
					// eventID = str.split("\\s")[1];
					//
					// int number = 0;
					// while (!analyzeFlag && (number < 4)) {
					// Thread.sleep(500);
					// 获取血流参数等
					// // getResult();
					// number++;
					// }
					if(callback != null){
						callback.notifyProgressChanged(0,100,"上传成功");
					}
				} else if (str.startsWith("0")) {
					// if (str.length() > 1) {
					// char[] error = new char[str.length() - 1];
					// str.getChars(1, str.length(), error, 0);
					// // result = "数据发送失败:/r/n" + error.toString();
					// result = mContext.getString(R.string.U_string2)
					// + error.toString();
					// updateProgress(0,100,result);
					// } else {
					// result = mContext.getString(R.string.U_string2);
					// updateProgress(100,100,result);
					// }
					if(callback != null){
						callback.notifyProgressChanged(0,100,"上传失败");
					}
				} else {
					// result = mContext.getString(R.string.U_string3);
					// updateProgress(0,100,result);
					if(callback != null){
						callback.notifyProgressChanged(0,100,"上传失败");
					}
				}
			} else {
				if(callback != null){
					callback.notifyProgressChanged(0,100,"上传失败");
				}
				// result = mContext.getString(R.string.U_string4);
				// updateProgress(0,100,result);
			}
		} catch (IOException e) {
			System.out.println("EcgHistoryActivity--uploadThread--IOException");
			e.printStackTrace();
			if(callback != null){
				callback.notifyProgressChanged(0,100,"上传失败:"+e.getMessage());
			}
			
		}

	}
}
