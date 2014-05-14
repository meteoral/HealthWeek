package com.bit_health.android.configuration;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

/**
 * 统一管理配置参数的类
 *
 * @author 梁才学
 *
 */
public class AndroidConfiguration {

	static private AndroidConfiguration instance;

	public static final String FIRST_TIME = "FIRST_TIME";// 第一次进入本应用程序
	public static final String BIT_HEALTH_CONFIG = "BitHealthConfigFile";
//	private static final String DEFAULT_HOST = "10.2.2.130:8081";
	private static final String DEFAULT_HOST = "172.18.50.50";
//	private static final String DEFAULT_HOST = "210.75.252.106:4084";
	private Context mContext;
	private SharedPreferences mSharedPreferences;
	private Editor mEditor;

	private String mBitHealthHost;
	private String mAccountSession;
	private String mAccountPwd;

	private String mUserId;
	private String mRoleId;
	private String mRoleSession;


	private AndroidConfiguration(Context contex) {
		mContext = contex.getApplicationContext();
		mSharedPreferences = mContext
				.getSharedPreferences(BIT_HEALTH_CONFIG, 0);
		mEditor = mSharedPreferences.edit();
	}

	/**********************************************************
	 * 方法描述：
	 *
	 * @param context
	 *            这个context不能是Activity的，必须是全局的, 即 Application的
	 * @return
	 **********************************************************/
	static public AndroidConfiguration getInstance(Context context) {
		if (instance == null) {
			instance = new AndroidConfiguration(context);
		}
		return instance;
	}

	public String getBitHealthHost() {
		if (TextUtils.isEmpty(mBitHealthHost)) {
			mBitHealthHost = mSharedPreferences.getString("host",
					DEFAULT_HOST);
			mBitHealthHost = "http://" + mBitHealthHost + "/";
		}
		return mBitHealthHost;
	}

	public void setBitHealthHost(String host) {
		this.mBitHealthHost = host;
		mEditor.putString("host", this.mBitHealthHost);
		mEditor.commit();
	}
	public String getmAccountPwd() {
		if (TextUtils.isEmpty(mAccountPwd)) {
			mAccountPwd = mSharedPreferences.getString("account_password",
					null);
		}
		return mAccountPwd;
	}

	public void setmAccountPwd(String mAccountPwd) {
		this.mAccountPwd = mAccountPwd;
		mEditor.putString("account_password", this.mAccountPwd);
		mEditor.commit();
	}
	public String getAccountSession() {
		if (TextUtils.isEmpty(mAccountSession)) {
			mAccountSession = mSharedPreferences.getString("account_session",
					null);
		}
		return mAccountSession;
	}

	public void setAccountSession(String session) {
		mAccountSession = session;
		mEditor.putString("account_session", this.mAccountSession);
		mEditor.commit();
	}

	public String getUserId() {
		if (TextUtils.isEmpty(mUserId)) {
			mUserId = mSharedPreferences.getString("user_id", null);
		}
		return mUserId;
	}

	// 获取最后更新时间
	public long getLastUpdateTime(String taskName){
		return mSharedPreferences.getLong(taskName, -1);
	}

	// 设置最后更新时间
	public void setLastUpdateTime(String taskName,long time) {
		mEditor.putLong(taskName, time);
		mEditor.commit();
	}
	public void setUserId(String userId) {
		this.mUserId = userId;
		mEditor.putString("user_id", this.mUserId);
		mEditor.commit();
	}

	public String getRoleId() {
		if (TextUtils.isEmpty(mRoleId)) {
			mRoleId = mSharedPreferences.getString("role_id", null);
		}
		return mRoleId;
	}

	public void setRoleId(String roleId) {
		this.mRoleId = roleId;
		mEditor.putString("role_id", this.mRoleId);
		mEditor.commit();
	}
	public String getRoleSession() {
		if (TextUtils.isEmpty(mRoleSession)) {
			mRoleSession = mSharedPreferences.getString("role_session", null);
		}
		return mRoleSession;
	}

	public void setRoleSession(String roleSession) {
		this.mRoleSession = roleSession;
		mEditor.putString("role_session", this.mRoleSession);
		mEditor.commit();
	}

	public void clear() {
		mEditor.clear();
		mEditor.commit();
		mContext = null;
		instance = null;
		RoleCatchInfo.getInstance(mContext).clear();
	}
}
