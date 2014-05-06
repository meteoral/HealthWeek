/********************************************************
 * 类名：InterfaceService.java
 *
 * 作者：陈建平
 * 主要功能：
 * 创建日期：上午9:49:23
 ********************************************************/
package com.bit_health.android.controllers;
import java.util.Date;
import java.util.List;

import com.bit_health.android.configuration.AndroidConfiguration;
import com.bit_health.android.configuration.RoleCatchInfo;
import com.bit_health.android.constants.BusinessConst;
import com.bit_health.android.controllers.beans.CheckVersionBean;
import com.bit_health.android.controllers.beans.JsonBase;
import com.bit_health.android.controllers.beans.RoleInfoBean;
import com.bit_health.android.controllers.beans.upload.UploadBpBean;
import com.bit_health.android.controllers.beans.upload.UploadBsBean;
import com.bit_health.android.task.UpateRoleInfosTask;
import com.bit_health.android.util.TimeFormatUtil;

import android.content.Context;

/**
 * @author Administrator
 *
 */
public class InterfaceService {
	private static InterfaceService service;
	private IServerBusiness instance;
	private Context mContext;
	
	private InterfaceService(Context context) {
		this.mContext = context.getApplicationContext();
		instance = IServerBusiness.getInstance(context);
	}

	/***************************************************
	 * 功能介绍：获取类的唯一操作实例
	 * 入参：         android应用上下文，使用ApplicationContext
	 * 出参:    无
	 * 返回值:  InterfaceService类的对象
	 **************************************************/
	public static InterfaceService getInstance(Context context) {
		if (service == null) {
			service = new InterfaceService(context);
		}
		return service;
	}
	
	/***************************************************
	 * 功能介绍：账户登录
	 * 入参：         accountname 账户名称
	 *          password    账户密码
	 *          iCallback   回调
	 * 出参:    无
	 * 返回值:  无
	 **************************************************/
	public void Login(final String accountname, final String password,final IBusinessCallback iCallback){
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					instance.Login(accountname, password);
					iCallback.callback(0, "ok");
				} catch (BusinessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					iCallback.callback(e.retCode, e.getMessage());
				}
			}
		}).start();
	
	}
	
	/***************************************************
	 * 功能介绍：账户注册
	 * 入参：         accountname 账户名称
	 *          password    账户密码
	 * 出参:    无
	 * 返回值:  无
	 **************************************************/
	public void register(final String accountname, final String password,final IBusinessCallback iCallback){
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					String retMsg = instance.register(accountname, password);
					iCallback.registerCallback(0, "ok",retMsg);
				} catch (BusinessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					iCallback.registerCallback(e.retCode, e.getMessage(),null);
				}
			}
		}).start();
	}
	
	/***************************************************
	 * 功能介绍：角色登录
	 * 入参：         roleId      角色ID
	 *          password    角色密码，可以为空
	 *          iCallback   回调
	 * 出参:    无
	 * 返回值:  无
	 **************************************************/
	public void roleLogin(final String roleId, final String password,final IBusinessCallback iCallback) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					instance.roleLogin(roleId, password);
					iCallback.callback(0, "ok");
				} catch (BusinessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					iCallback.callback(e.retCode, e.getMessage());
				}
			}
		}).start();
	}

	/***************************************************
	 * 功能介绍：账户登出
	 * 入参：         iCallback   回调
	 * 出参:    无
	 * 返回值:  无
	 **************************************************/
	public void Logout(final IBusinessCallback iCallback){
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					instance.Logout();
					iCallback.callback(0, "ok");
				} catch (BusinessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					iCallback.callback(e.retCode, e.getMessage());
				}
			}
		}).start();
	}

	/***************************************************
	 * 功能介绍：增加角色
	 * 入参：         RoleInfoBean bean 角色信息
	 *          iCallback   回调
	 * 出参:    无
	 * 返回值:  无
	 **************************************************/
	public void addRole(final RoleInfoBean bean,final IBusinessCallback iCallback){
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					String roleid = instance.addRole(bean);
					iCallback.addroleCallback(0, "ok",roleid);
				} catch (BusinessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					iCallback.addroleCallback(e.retCode, e.getMessage(),null);
				}
			}
		}).start();
	}
	
	/***************************************************
	 * 功能介绍：删除角色
	 * 入参：         String roleid 角色id
	 *          iCallback   回调
	 * 出参:    无
	 * 返回值:  无
	 **************************************************/
	public void deleteRole(final String roleid,final IBusinessCallback iCallback){
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					instance.deleteRole(roleid);
					iCallback.callback(0, "ok");
				} catch (BusinessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					iCallback.callback(e.retCode, e.getMessage());
				}
			}
		}).start();
	}

	/***************************************************
	 * 功能介绍：获取角色信息
	 * 入参：        String roleId 角色ID
	 *          iCallback   回调
	 * 出参:    无
	 * 返回值:  无
	 **************************************************/
	public void getRoleInfo(final String roleId,final IBusinessCallback iCallback){
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					RoleInfoBean beans = instance.getRoleInfo(roleId);
					iCallback.getRoleInfoCallback(0, "ok",beans);
				} catch (BusinessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					iCallback.getRoleInfoCallback(e.retCode, e.getMessage(),null);
				}
			}
		}).start();
	}

	/***************************************************
	 * 功能介绍：更新角色信息
	 * 入参：        RoleInfoBean bean 角色信息
	 *         iCallback   回调
	 * 出参:    无
	 * 返回值:  无
	 * 异常：        外部捕获BusinessException
	 **************************************************/
	public void updateRoleInfo(final RoleInfoBean bean,final IBusinessCallback iCallback){
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					instance.updateRoleInfo(bean);
					iCallback.callback(0, "ok");
				} catch (BusinessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					iCallback.callback(e.retCode, e.getMessage());
				}
			}
		}).start();
	}
	/***************************************************
	 * 功能介绍：更新角色信息
	 * 入参：        filePath  角色头像文件
	 *         iCallback   回调
	 * 出参:    无
	 * 返回值:  无
	 * 异常：        外部捕获BusinessException
	 **************************************************/
	public void updateRoleHead(final String filePath,final IBusinessCallback iCallback){
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					instance.updateRoleHead(filePath);
					iCallback.updateHeadback(0, "ok");
				} catch (BusinessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					iCallback.updateHeadback(e.retCode, e.getMessage());
				}
			}
		}).start();
	}

	/***************************************************
	 * 功能介绍：获取角色列表
	 * 入参：      iCallback   回调
	 * 出参:    无
	 * 返回值:  无
	 **************************************************/
	public void getRoleInfoList(final IBusinessCallback iCallback) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					List<RoleInfoBean> beans = instance.getRoleInfoList();
					for (RoleInfoBean bean : beans) {
						getAbnormalMeasures(bean);
					}
					iCallback.getRoleInfoListCallback(0, "ok",beans);
					AndroidConfiguration.getInstance(mContext).setLastUpdateTime(
							UpateRoleInfosTask.KEY_CONFIG_UPDATE_TIME, new Date().getTime());
				} catch (BusinessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					iCallback.getRoleInfoListCallback(e.retCode, e.getMessage(),null);
				}
			}
		}).start();
	}
	/***************************************************
	 * 功能介绍：更新角色信息列表，包括关注列表
	 * 入参：      iCallback   回调
	 * 出参:    无
	 * 返回值:  无
	 **************************************************/
	public void updateAllRoleInfoList(final IBusinessCallback iCallback){
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					List<RoleInfoBean> beans = instance.getRoleInfoList();
					RoleCatchInfo.getInstance(mContext).setRoles(beans);
					instance.getCareRoleList();
					iCallback.callback(0, "ok");
				} catch (BusinessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					iCallback.callback(e.retCode, e.getMessage());
				}
			}
		}).start();
	}
	/***************************************************
	 * 功能介绍：获取关注的角色列表
	 * 入参：      iCallback   回调
	 * 出参:    无
	 * 返回值:  无
	 **************************************************/
	public void getCareRoleList(final IBusinessCallback iCallback) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					instance.getCareRoleList();
					iCallback.callback(0, "ok");
				} catch (BusinessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					iCallback.callback(e.retCode, e.getMessage());
				}
			}
		}).start();
	}
	
	/***************************************************
	 * 功能介绍：关注/取消关注
	 * 入参：        roleid 被关注的角色ID
	 *         bCared  关注或取消关注
	 *          iCallback   回调
	 * 出参:    无
	 * 返回值:  无
	 **************************************************/
	public void careRole(final String roleid,final boolean bCared,final IBusinessCallback iCallback){

		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					instance.careRole(roleid, bCared);
					iCallback.callback(0, "ok");
				} catch (BusinessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					iCallback.callback(e.retCode, e.getMessage());
				}
			}
		}).start();
	}
	

	/***************************************************
	 * 功能介绍：获取医生建议
	 * 入参：       roleId 角色ID
	 *         type 测量类型 ECG/PPG/BP/BGM 
	 *         eventId 测试事件ID
	 *          iCallback   回调
	 * 出参:    无
	 * 返回值:  无
	 **************************************************/
	public void getDoctorSuggest(final String roleId, final String type,
			final int eventId, final IBusinessCallback iCallback) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					String message = instance.getDoctorSuggest(roleId, type,
							eventId);
					iCallback.getDoctorSuggestCallback(0, "ok", message);
				} catch (BusinessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					iCallback.getDoctorSuggestCallback(e.retCode, e.getMessage(),null);
				}
			}
		}).start();
	}
	/***************************************************
	 * 功能介绍：获取心电测试图片的 URL
	 * 入参：         pageid 页面ID
	 *          eventId 测试事件ID
	 *          iCallback   回调
	 * 出参:    无
	 * 返回值:  无
	 **************************************************/
	public void getEcgImageUrl(final String eventId, final int pageid,
			final IBusinessCallback iCallback) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					String url = instance.getEcgImageUrl(eventId, pageid);
					iCallback.getEcgImageUrlCallback(0, "ok", url);
				} catch (BusinessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					iCallback.getEcgImageUrlCallback(e.retCode, e.getMessage(),null);
				}
			}
		}).start();
	}
	/***************************************************
	 * 功能介绍：检查版本是否有最新更新
	 * 入参：         
	 * 出参:    无
	 * 返回值:  无
	 **************************************************/
	public void checkAppVersion(final IBusinessCallback iCallback) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					CheckVersionBean bean = instance.checkAppVersion();
					iCallback.checkVersionCallback(0, "ok", bean);
				} catch (BusinessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					iCallback.checkVersionCallback(e.retCode, e.getMessage(),null);
				}
			}
		}).start();
	}
	
	/***************************************************
	 * 功能介绍：获取脉搏图片的 URL
	 * 入参：         pageid 页面ID
	 *          eventId 测试事件ID
	 *          iCallback   回调
	 * 出参:    无
	 * 返回值:  无
	 **************************************************/
	public void getPpgImageUrl(final String eventId, final int pageid,
			final IBusinessCallback iCallback) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					String url = instance.getPpgImageUrl(eventId, pageid);
					iCallback.getPpgImageUrlCallback(0, "ok", url);
				} catch (BusinessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					iCallback.getPpgImageUrlCallback(e.retCode, e.getMessage(),null);
				}
			}
		}).start();
	}
	
	/***************************************************
	 * 功能介绍：获取瞬时脉率图片的 URL
	 * 入参：       
	 *          eventId 测试事件ID
	 *          iCallback   回调
	 * 出参:    无
	 * 返回值:  无
	 **************************************************/
	public void getPpgHrImageUrl(final String eventId,
			final IBusinessCallback iCallback) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					String url = instance.getPpgHrImageUrl(eventId);
					iCallback.getPpgHrImageUrlCallback(0, "ok", url);
				} catch (BusinessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					iCallback.getPpgHrImageUrlCallback(e.retCode, e.getMessage(),null);
				}
			}
		}).start();
	}

	/***************************************************
	 * 功能介绍：获取瞬时心率图片的 URL
	 * 入参：         pageid 页面ID
	 *          eventId 测试事件ID
	 *          iCallback   回调
	 * 出参:    无
	 * 返回值:  无
	 **************************************************/
	public void getEcgHrImageUrl(final String eventId, final int pageid,
			final IBusinessCallback iCallback) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					String url = instance.getEcgHrImageUrl(eventId, pageid);
					iCallback.getEcgImageUrlCallback(0, "ok", url);
				} catch (BusinessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					iCallback.getEcgImageUrlCallback(e.retCode, e.getMessage(),null);
				}
			}
		}).start();
	}
	/***************************************************
	 * 功能介绍：获取PR数据
	 * 入参：          eventId  ECG分析后的数据
	 * 出参:    无
	 * 返回值:  int[] pr数据
	 * 异常：        外部捕获BusinessException
	 **************************************************/
	public void getEcgAnlyseResult(final String eventId,final String roleid,
			final IBusinessCallback iCallback) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					int []PR  = instance.getEcgAnlyseResult(eventId,roleid);
					iCallback.getEcgPRCallback(0, "ok", PR);
				} catch (BusinessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					iCallback.getEcgPRCallback(e.retCode, e.getMessage(),null);
				}
			}
		}).start();
	}
	
	/***************************************************
	 * 功能介绍：获取异常心电测试图片的 URL
	 * 入参：         pageid 页面ID
	 *          symptom ECG异常参数，详见BusinessConst.java文件定义
	 *          iCallback   回调
	 * 出参:    无
	 * 返回值:  无
	 **************************************************/
	public void getABEcgImageUrl(final String eventId, final String symptom,
			final IBusinessCallback iCallback) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					String url = instance.getABEcgImageUrl(eventId, symptom);
					iCallback.getABEcgImageUrlCallback(0, "ok", url);
				} catch (BusinessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					iCallback.getABEcgImageUrlCallback(e.retCode, e.getMessage(),null);
				}
			}
		}).start();
	}
	/***************************************************
	 * 功能介绍：获取指定时间段异常数据数量
	 * 入参：       roleId 角色ID
	 *         type 测量类型 ECG/PPG/BP/BGM 
	 *         tBegin 开始时间
	 *         tEnd   结束时间
	 *         iCallback   回调
	 * 出参:    无
	 * 返回值:  无
	 **************************************************/
	public void getAbnormalMeasures(final String roleId, final String tBegin, final String tEnd,
			final String type,final IBusinessCallback iCallback) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					int count = instance.getAbnormalMeasures(roleId, tBegin,tEnd,type);
					iCallback.getAbnormalMeasuresCallback(0, "ok", count);
				} catch (BusinessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					iCallback.getAbnormalMeasuresCallback(e.retCode, e.getMessage(),0);
				}
			}
		}).start();
	}

	/***************************************************
	 * 功能介绍：获取角色的总体健康信息
	 * 入参：       bean 角色信息
	 * 出参:    无
	 * 返回值:  无
	 **************************************************/
	public void getAbnormalMeasures(RoleInfoBean bean) {
		String nowTime = TimeFormatUtil.getNowTime();
		String yestoday = TimeFormatUtil.getPreDaysTime(1);
		String preOneMonth = TimeFormatUtil.getPreMonthTime(1);
		String preThreeMonth = TimeFormatUtil.getPreMonthTime(3);
		try {
			bean.mYesAbNumber = instance.getAbnormalMeasures(bean.mId, yestoday, nowTime, BusinessConst.ALL_MESURE);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			bean.mMonthAbNumber = instance.getAbnormalMeasures(bean.mId, preOneMonth, nowTime, BusinessConst.ALL_MESURE);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			bean.m3MonthAbNumber = instance.getAbnormalMeasures(bean.mId, preThreeMonth, nowTime, BusinessConst.ALL_MESURE);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/***************************************************
	 * 功能介绍：获取各种测量信息列表
	 * 入参：       roleId 角色ID
	 *         pos 其实位置从0开始
	 *         count 数量
	 *         type   测量类型PPG/ECG/BP
	 *         iCallback   回调
	 * 出参:    无
	 * 返回值:  无
	 **************************************************/
	public void getMeasures(final String roleid, final int pos, final int count, final String error,
			final String type,final IBusinessCallback iCallback){
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					String tableName = DataCatchInterface.getTableName(
							type, error);
					if (pos == 0) {
						// 刷新数据
						List<JsonBase> beans = instance.getMeasures(roleid,
								pos, count, error, type);
						// 加入缓存
						DataCatchInterface.getInstance(mContext).insert(roleid,
								tableName, beans);
						
						iCallback.getMeasuresCallback(0, "ok", beans);
					} else {
						List<JsonBase> beans = DataCatchInterface.getInstance(mContext).getItems(roleid,
								tableName, pos,count);
						if (beans.size() < count) {
							// 缓存数据不足
							List<JsonBase> beannews = instance.getMeasures(
									roleid, pos + beans.size(),
									count - beans.size(), error, type);
							// 加入缓存
							DataCatchInterface.getInstance(mContext).insert(roleid,
									tableName, beannews);
							beans.addAll(beannews);
						}
						iCallback.getMeasuresCallback(0, "ok", beans);
					}
					
				} catch (BusinessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					iCallback.getMeasuresCallback(e.retCode, e.getMessage(),null);
				}
			}
		}).start();
	}

	/***************************************************
	 * 功能介绍：获取分析数据
	 * 入参：       roleId 角色ID
	 *         type   类型ppg/ecg/bp/bs/count
	 *         iCallback   回调
	 * 出参:    无
	 * 返回值:  无
	 **************************************************/
	public void getAnalyzeResult(final String roleid, final String type,
			final IBusinessCallback iCallback) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					JsonBase jbase = instance.getAnalyzeResult(roleid, type);
					iCallback.getAnalyzeResultCallback(0, "ok", jbase);
				} catch (BusinessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					iCallback.getAnalyzeResultCallback(e.retCode, e.getMessage(),null);
				}
			}
		}).start();
	}
	
	/***************************************************
	 * 功能介绍：忘记密码
	 * 入参：         name 用户名或者角色名
	 *          forget_type    忘记密码类型（用户：user；角色：role）
	 * 出参:    无
	 * 返回值:  无
	 **************************************************/
	public void fortgetPassword(final String name, final String forgettype,
			final IBusinessCallback iCallback) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					instance.fortgetPassword(name, forgettype);
					iCallback.callback(0, "ok");
				} catch (BusinessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					iCallback.callback(e.retCode, e.getMessage());
				}
			}
		}).start();
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
	public void updatePassword(final String name, final String forgettype,
			final String vcode, final String newPassword,
			final IBusinessCallback iCallback) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					instance.updatePassword(name, forgettype, vcode, newPassword);
					iCallback.callback(0, "ok");
				} catch (BusinessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					iCallback.callback(e.retCode, e.getMessage());
				}
			}
		}).start();
	}
	/***************************************************
	 * 功能介绍：根据日期获取各种测量信息列表
	 * 入参：       roleId 角色ID
	 *         pos 其实位置从0开始
	 *         count 数量
	 *         begin 开始日期
	 *         end   结束日期
	 *         type   测量类型PPG/ECG/BP
	 *         iCallback   回调
	 * 出参:    无
	 * 返回值:  无
	 **************************************************/
	public void getMeasuresFromDate(final String roleid, final int pos, final int count, final String begin,final String end,final String error,
			final String type,final IBusinessCallback iCallback){
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					String tableName = DataCatchInterface.getTableName(
							type, error);
					if (pos == 0) {
						// 刷新数据
						List<JsonBase> beans = instance.getMeasuresFromDate(roleid, pos, count,begin,end, error, type);
						// 加入缓存
						DataCatchInterface.getInstance(mContext).insert(roleid,
								tableName, beans);
						iCallback.getMeasuresCallback(0, "ok", beans);
					} else {
						List<JsonBase> beans = DataCatchInterface.getInstance(mContext).getItemsFromDate(roleid,
								tableName, pos,count,begin,end);
						if (beans.size() < count) {
							// 缓存数据不足
							List<JsonBase> beannews = instance.getMeasuresFromDate(
									roleid, pos + beans.size(),
									count - beans.size(), begin,end,error, type);
							// 加入缓存
							DataCatchInterface.getInstance(mContext).insert(roleid,
									tableName, beannews);
							beans.addAll(beannews);
						}
						iCallback.getMeasuresCallback(0, "ok", beans);
					}
				} catch (BusinessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					iCallback.getMeasuresCallback(e.retCode, e.getMessage(),null);
				}
			}
		}).start();
	}

	/***************************************************
	 * 功能介绍：获取详细测量数据信息
	 * 入参：       id 测量ID
	 *         type 类型PPG/ECG/BP
	 *         iCallback   回调
	 * 出参:    无
	 * 返回值:  无
	 **************************************************/
	public void getMeasureDetail(final String id, final String type,final IBusinessCallback iCallback){
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					JsonBase bean = instance.getMeasureDetail(id,type);
					iCallback.getMeasureDetailCallback(0, "ok",bean);
				} catch (BusinessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					iCallback.getMeasureDetailCallback(e.retCode, e.getMessage(),null);
				}
			}
		}).start();
	}
	/***************************************************
	 * 功能介绍：获取详细测量数据信息
	 * 入参：       roleid 角色ID
	 *         type 类型PPG/ECG/BP/BS
	 *         date  测量日期（格式为YYYY-MM-DD）
	 *         iCallback   回调
	 * 出参:    无
	 * 返回值:  无
	 **************************************************/
	public void getMeasureDetail(final String roleid,final String date,final String type,final IBusinessCallback iCallback){
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					JsonBase bean = instance.getMeasureDetail(roleid,date,type);
					iCallback.getMeasureDetailCallback(0, "ok",bean);
				} catch (BusinessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					iCallback.getMeasureDetailCallback(e.retCode, e.getMessage(),null);
				}
			}
		}).start();
	}

	/***************************************************
	 * 功能介绍：上传血压数据
	 * 入参：          roleid  角色ID
	 *          UploadBpBean 血压数据
	 * 出参:    无
	 * 返回值:  无
	 **************************************************/
	public void UploadBp(final UploadBpBean bean,final IBusinessCallback iCallback){
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					bean.mId = instance.UploadBp(bean);
					iCallback.callback(0, "ok");
				} catch (BusinessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					iCallback.callback(e.retCode, e.getMessage());
				}
			}
		}).start();
	}

	/***************************************************
	 * 功能介绍：上传血糖数据
	 * 入参：          roleid  角色ID
	 *          UploadBpBean 血糖数据
	 * 出参:    无
	 * 返回值:  无
	 **************************************************/
	public void UploadBs(final UploadBsBean bean,final IBusinessCallback iCallback){
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					bean.mId = instance.UploadBs(bean);
					iCallback.callback(0, "ok");
				} catch (BusinessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					iCallback.callback(e.retCode, e.getMessage());
				}
			}
		}).start();
	}
}
