/********************************************************
 * 类名：ReportActivity.java
 *
 * 作者：陈建平
 * 主要功能：
 * 创建日期：下午6:22:14
 ********************************************************/
package com.bit_health.android.ui.activities;
import java.util.List;
import android.util.Log;
import com.bit_health.android.configuration.AndroidConfiguration;
import com.bit_health.android.constants.BusinessConst;
import com.bit_health.android.controllers.DataCatchInterface;
import com.bit_health.android.controllers.InterfaceService;
import com.bit_health.android.controllers.beans.JsonBase;
import com.bit_health.android.ui.framelayout.HistoryRecords;
/**
 * @author Administrator
 *
 */
public abstract class ReportActivity extends BaseActivity {
	public static final int STATUS_INIT = 0;
	public static final int STATUS_REFRESH = 1;
	public static final int STATUS_LOADMORE = 2;
	private int mILoadStatus;
	protected List<JsonBase> mInfos;
	protected String mMesureType;
	protected HistoryRecords mHistoryRecords;
	protected abstract void initViewPager();
	
	/******************************************************************
	 * 方法描述：从服务器获取心电数据
	 * 
	 ******************************************************************/
	public void getDataFromService(int status) {
		// TODO Auto-generated method stub
		// 获取当前角色的心电信息
		mILoadStatus = status;
		String roleId = AndroidConfiguration.getInstance(
				getApplicationContext()).getRoleId();
		InterfaceService mBusiness = InterfaceService
				.getInstance(getApplicationContext());
		if (mILoadStatus == STATUS_INIT) {
			mInfos = getDataFromCath();
			if (mInfos != null && mInfos.size() > 0) {
				// 存在缓存数据
				initViewPager();
			}else{
				showWaittingDialog("正在获取数据...");
				mBusiness.getMeasures(roleId, 0, 10, BusinessConst.ALL_ANLASY, mMesureType,
						this);
			}
		}else{
			if(mILoadStatus != STATUS_LOADMORE){
				mBusiness.getMeasures(roleId, 0, 10, BusinessConst.ALL_ANLASY, mMesureType,
						this);
			} else {
				int pos = (mInfos == null || mInfos.size() == 0) ? 0 : mInfos
						.size();
				mBusiness.getMeasures(roleId, pos, 10, BusinessConst.ALL_ANLASY,
						mMesureType, this);
			}
		}
	}
	
	private List<JsonBase> getDataFromCath(){
		String roleId = AndroidConfiguration.getInstance(
				this).getRoleId();
		String table = DataCatchInterface.getTableName(mMesureType, BusinessConst.ALL_ANLASY);
		return DataCatchInterface.getInstance(this).getItems(roleId, table, 0, 10);
	}
	@Override
	public void getMeasuresCallback(int retCode, String errorMsg,
			List<JsonBase> beans) {
		// TODO Auto-generated method stub
		super.getMeasuresCallback(retCode, errorMsg, beans);
		hideWaittingDialog();
		switch (retCode) {
		case 0:
			if(mILoadStatus != STATUS_LOADMORE){
				if(mInfos != null){
					mInfos.clear();
					mInfos.addAll(beans);
				}else{
					mInfos = beans;
				}
				
			}else{
				mInfos.addAll(beans);
			}
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					if(mILoadStatus == STATUS_INIT){
						initViewPager();
					}else{
						if(mHistoryRecords != null){
							mHistoryRecords.notifydatachange();
						}
					}
					
				}
			});
			break;
		default:
			showAlert(errorMsg);
			break;
		}
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(mHistoryRecords != null){
					mHistoryRecords.onLoad();
				}
			}
		});
	}
}
