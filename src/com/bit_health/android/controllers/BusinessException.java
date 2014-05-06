package com.bit_health.android.controllers;

import android.content.Context;

import com.siat.healthweek.R;

public class BusinessException extends Exception {

	private static final long serialVersionUID = 1L;
	/* 设备网络错误 */
	public static final int DEVICE_CONNECT_ERROR = -2; // 网络错误
	/* json解析错误 */
	public static final int JSON_ERROR = -3; // json解析错误
	/* 服务器内部错误 */
	public static final int SERVER_INTERNAL_ERROR = -4;

	/* 本地内部错误 */
	public static final int LOCAL_PARAM_ERROR = -5;

	/* 服务器内部错误 */
	public static final int ERROR_SERVER_BUSY = -1; // 系统繁忙
	public static final int ERROR_ACCOUNT_INVALID = 90001; // 不存在的用户或用户未激活
	public static final int ERROR_ACCOUNT_PASSWORD = 90002; // 密码不正确
	public static final int ERROR_USER_ID_INVALID = 90003; // 无效用户ID
	public static final int ERROR_ROLE_ID_INVALID = 90004; // 无效角色ID
	public static final int ERROR_MEASURE_ID_INVALID = 90005; // 无效数据记录ID
	public static final int ERROR_DATA_TYPE_INVALID = 90006; // 无效的数据类型
	public static final int ERROR_BEGIN_DATE_INVALID = 90007; // 开始时间不合法
	public static final int ERROR_END_DATE_INVALID = 90008; // 结束时间不合法
	public static final int ERROR_SESSION_INVALID = 90009; // SESSION失效，请重新登录家庭账户
	public static final int ERROR_COOKIE_INVALID = 90010; // 角色失效，请重新选择角色
	public static final int ERROR_GET_PARAMS_INVALID = 90011; // GET请求参数错误
	public static final int ERROR_POST_PARAMS_INVALID = 90012; // POST请求参数错误
	public static final int ERROR_NOANY_ROLE = 90013; // 该用户无创建任何角色
	public static final int ERROR_POSITION_INVALID = 90014; // 无效偏离值
	public static final int ERROR_NUMBLE_INVALID = 90015; // 没有获取到数据
	public static final int ERROR_FULLNAME_EMPTY = 90016; // 角色全名不能为空
	public static final int ERROR_ROLENAME_EMPTY = 90017; // 角色名不能为空
	public static final int ERROR_PASSWORD_EMPTY = 90018; // 密码不能为空
	public static final int ERROR_GENDER_EMPTY = 90019; // 性别不能为空
	public static final int ERROR_BIRTHDAY_EMPTY = 90020; // 生日不能为空
	public static final int ERROR_MOBILE_EMPTY = 90021; // 手机号码不能为空
	public static final int ERROR_EMAIL_EMPTY = 90022; // 电子邮箱不能为空
	public static final int ERROR_ADDRESS_EMPTY = 90023; // 地址不能为空
	public static final int ERROR_ROLE_LOGIN = 90024; // 该角色并未登录
	public static final int ERROR_ACCOUNTNAME_EMPTY = 90025; // 用户名不能为空
	public static final int ERROR_USERID_EMPTY = 90026; // 用户ID不能为空
	public static final int ERROR_ROLEID_EMPTY = 90027; // 角色ID不能为空
	public static final int ERROR_BEGIN_DATE_EMPTY = 90028; // 开始时间不能为空
	public static final int ERROR_END_DATE_EMPTY = 90029; // 结束时间不能为空
	public static final int ERROR_EMAILORMOBILE_EMPTY = 90030; // 电子邮箱或手机号码不能为空
	public static final int ERROR_CAREROLE_EMPTY = 90031; // 关注角色ID不能为空
	public static final int ERROR_ACCOUNT_ACTIVITY = 90032; // 该用户未激活
	public static final int ERROR_WEIGHT_EMPTY = 90033; // 体重不能为空
	public static final int ERROR_HEIGHT_EMPTY = 90034; // 身高不能为空
	public static final int ERROR_CALEOPREATE_EMPTY = 90035; // 关注操作不能为空
	public static final int ERROR_NOTFIND_RECORD = 90036; // 该记录不存在
	public static final int ERROR_OLDPWD_EMPTY = 90037; // 旧密码不能为空
	public static final int ERROR_NEWPWD_EMPTY = 90038; // 新密码不能为空
	public static final int ERROR_NOTFIND_RT = 90039; // 缺少result_type参数
	public static final int ERROR_NEED_GET = 91001; // 需要GET请求
	public static final int ERROR_NEED_POST = 91002; // 需要POST请求
	public static final int ERROR_LOGOUT = 92001; // 注销失败
	public static final int ERROR_DEL_ROLE = 92002; // 删除角色失败
	public static final int ERROR_MODIFY_ROLE = 92003; // 修改角色失败
	public static final int ERROR_ADD_ROLE = 93001; // 新增角色失败
	public static final int ERROR_ADD_BPMEASURE = 93002; // 新增血压测量失败
	public static final int ERROR_ADD_BSMEASURE = 93003; // 新增血压测量失败
	public static final int ERROR_ADD_ECGMEASURE = 93004; // 新增心电测量失败
	public static final int ERROR_INPUT_EMAILMOBILE = 93005; // 请输入电子邮箱或手机号码
	public static final int ERROR_EMAILORMOBILE_EXSIST = 93006; // 电子邮箱或手机号码已被注册
	public static final int ERROR_EMAIL_SEND = 93007; // 验证邮件发送失败
	public static final int ERROR_SMS_SEND = 93008; // 验证短信发送失败
	public static final int ERROR_NOTSURPORT_EMAIL = 93010; // 暂不支持该邮箱
	public static final int ERROR_CARE_OPREATE = 93011; // 关注操作失败
	public static final int ERROR_OLDPWD = 93012; // 旧密码错误
	public static final int ERROR_MODIFY_PWD = 93013; // 修改密码失败
	public static final int ERROR_MODIFY_HEADER = 93014; // 修改头像失败
	public static final int ERROR_DATEFORMAT_INVALID = 93015; // 无效日期格式
	public int retCode;

	public BusinessException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BusinessException(String detailMessage) {
		super(detailMessage);
		// TODO Auto-generated constructor stub
	}

	public BusinessException(String detailMessage, int ret) {
		super(detailMessage);
		retCode = ret;
		// TODO Auto-generated constructor stub
	}

	public static String getErrorMessage(Context context, int ret) {
		String errorMsg;
		switch (ret) {
		case DEVICE_CONNECT_ERROR:
			errorMsg = context.getString(R.string.string_network_error);
			break;
		case ERROR_SERVER_BUSY:
			errorMsg = context.getString(R.string.string_serverbusy_error);
			break;
		case ERROR_ACCOUNT_INVALID:
			errorMsg = context.getString(R.string.string_accountinvalid_error);
			break;
		case ERROR_ACCOUNT_PASSWORD:
			errorMsg = context.getString(R.string.string_password_error);
			break;
		case ERROR_SESSION_INVALID:
			errorMsg = context.getString(R.string.string_session_error);
			break;
		case ERROR_COOKIE_INVALID:
			errorMsg = context.getString(R.string.string_cookie_error);
			break;
		case ERROR_FULLNAME_EMPTY:
			errorMsg = context.getString(R.string.string_fullname_empty);
			break;
		case ERROR_ROLENAME_EMPTY:
			errorMsg = context.getString(R.string.string_rolename_empty);
			break;
		case ERROR_PASSWORD_EMPTY:
			errorMsg = context.getString(R.string.string_password_empty);
			break;
		case ERROR_GENDER_EMPTY:
			errorMsg = context.getString(R.string.string_gender_empty);
			break;
		case ERROR_BIRTHDAY_EMPTY:
			errorMsg = context.getString(R.string.string_birthday_empty);
			break;
		case ERROR_MOBILE_EMPTY:
			errorMsg = context.getString(R.string.string_mobile_empty);
			break;
		case ERROR_EMAIL_EMPTY:
			errorMsg = context.getString(R.string.string_email_empty);
			break;
		case ERROR_ADDRESS_EMPTY:
			errorMsg = context.getString(R.string.string_address_empty);
			break;
		case ERROR_ACCOUNTNAME_EMPTY:
			errorMsg = context.getString(R.string.string_accountname_empty);
			break;
		case ERROR_EMAILORMOBILE_EMPTY:
		case ERROR_INPUT_EMAILMOBILE:
			errorMsg = context.getString(R.string.string_emailormobile_empty);
			break;
		case ERROR_ACCOUNT_ACTIVITY:
			errorMsg = context.getString(R.string.string_account_activity);
			break;
		case ERROR_WEIGHT_EMPTY:
			errorMsg = context.getString(R.string.string_weight_empty);
			break;
		case ERROR_HEIGHT_EMPTY:
			errorMsg = context.getString(R.string.string_height_empty);
			break;
		case ERROR_NOTFIND_RECORD:
			errorMsg = context.getString(R.string.string_record_notfind);
			break;
		case ERROR_OLDPWD_EMPTY:
			errorMsg = context.getString(R.string.string_oldpwd_empty);
			break;
		case ERROR_NEWPWD_EMPTY:
			errorMsg = context.getString(R.string.string_newpwd_empty);
			break;
		case ERROR_EMAILORMOBILE_EXSIST:
			errorMsg = context.getString(R.string.string_emailormobile_exsist);
			break;
		case ERROR_NOTSURPORT_EMAIL:
			errorMsg = context.getString(R.string.string_surport_email);
			break;
		case ERROR_OLDPWD:
			errorMsg = context.getString(R.string.string_oldpwd_error);
			break;
		default:
			errorMsg = context.getString(R.string.string_system_inner_error);
			android.util.Log.e("BusinessException", "operate error code = "
					+ ret);
			break;
		}
		return errorMsg;
	}

}
