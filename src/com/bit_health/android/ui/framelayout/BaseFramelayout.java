/********************************************************
 * 类名：BaseFramelayout.java
 *
 * 作者：陈建平
 * 主要功能：
 * 创建日期：下午7:24:29
 ********************************************************/
package com.bit_health.android.ui.framelayout;

import java.util.List;

import com.bit_health.android.controllers.IBusinessCallback;
import com.bit_health.android.controllers.beans.CheckVersionBean;
import com.bit_health.android.controllers.beans.JsonBase;
import com.bit_health.android.controllers.beans.RoleInfoBean;
import com.bit_health.android.util.TimeConsumingDialog;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.Toast;

/**
 * @author Administrator
 *
 */
public class BaseFramelayout extends FrameLayout implements IBusinessCallback{

	private TimeConsumingDialog mRequestWaittingDlg;// 耗时操作显示的进度对话框
	protected Activity  mActivity;
	@Override
	public void callback(int retCode, String errorMsg) {
		// TODO Auto-generated method stub
		hideWaittingDialog();
		switch (retCode) {
		case 0:// 0 表示请求成功
			break;
		default:
			showAlert(errorMsg);
			break;
		}
	}
	public void showWaittingDialog(final String msg){
		if(mActivity != null && !mActivity.isFinishing()){
			mActivity.runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					if(mRequestWaittingDlg == null){
						mRequestWaittingDlg = new TimeConsumingDialog(mActivity);
					}
					mRequestWaittingDlg.show();
					mRequestWaittingDlg.setShowMessage(msg);
				}
			});
		}
		
	}
	
	public void hideWaittingDialog(){
		if(mRequestWaittingDlg != null){
			mRequestWaittingDlg.dismiss();
			mRequestWaittingDlg = null;
		}
	}
	
	public void showAlert(final String message){

//		if(mActivity != null ){
//			mActivity.runOnUiThread(new Runnable() {
//				
//				@Override
//				public void run() {
//					// TODO Auto-generated method stub
//					Builder builder = new AlertDialog.Builder(mActivity);
//					builder.setTitle("警告！");
//					builder.setMessage(message);
//					builder.setPositiveButton("确定", null);
//					builder.create();
//					builder.show();
//				}
//			});
//		}
		if(mActivity != null && !mActivity.isFinishing()){
			mActivity.runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Toast.makeText(mActivity, message, 1000).show();
				}
			});
		}
		
	}
	public BaseFramelayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mActivity = (Activity)context;
		// TODO Auto-generated constructor stub
	}

	public BaseFramelayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		mActivity = (Activity)context;
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param context
	 */
	public BaseFramelayout(Context context) {
		super(context);
		mActivity = (Activity)context;
		// TODO Auto-generated constructor stub
	}


	/* (non-Javadoc)
	 * @see com.bit_health.android.controllers.IBusinessCallback#getRoleInfoCallback(int, java.lang.String, com.bit_health.android.controllers.beans.RoleInfoBean)
	 */
	@Override
	public void getRoleInfoCallback(int retCode, String errorMsg,
			RoleInfoBean beans) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.bit_health.android.controllers.IBusinessCallback#getRoleInfoListCallback(int, java.lang.String, java.util.List)
	 */
	@Override
	public void getRoleInfoListCallback(int retCode, String errorMsg,
			List<RoleInfoBean> beans) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.bit_health.android.controllers.IBusinessCallback#getDoctorSuggestCallback(int, java.lang.String, java.lang.String)
	 */
	@Override
	public void getDoctorSuggestCallback(int retCode, String errorMsg,
			String doctorSuggest) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.bit_health.android.controllers.IBusinessCallback#getEcgImageUrlCallback(int, java.lang.String, java.lang.String)
	 */
	@Override
	public void getEcgImageUrlCallback(int retCode, String errorMsg,
			String imageUrl) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.bit_health.android.controllers.IBusinessCallback#getABEcgImageUrlCallback(int, java.lang.String, java.lang.String)
	 */
	@Override
	public void getABEcgImageUrlCallback(int retCode, String errorMsg,
			String imageUrl) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.bit_health.android.controllers.IBusinessCallback#getAbnormalMeasuresCallback(int, java.lang.String, int)
	 */
	@Override
	public void getAbnormalMeasuresCallback(int retCode, String errorMsg,
			int abCount) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.bit_health.android.controllers.IBusinessCallback#getMeasuresCallback(int, java.lang.String, java.util.List)
	 */
	@Override
	public void getMeasuresCallback(int retCode, String errorMsg,
			List<JsonBase> beans) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.bit_health.android.controllers.IBusinessCallback#getMeasureDetailCallback(int, java.lang.String, com.bit_health.android.controllers.beans.JsonBase)
	 */
	@Override
	public void getMeasureDetailCallback(int retCode, String errorMsg,
			JsonBase bean) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.bit_health.android.controllers.IBusinessCallback#registerCallback(int, java.lang.String, java.lang.String)
	 */
	@Override
	public void registerCallback(int retCode, String errorMsg, String retMsg) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.bit_health.android.controllers.IBusinessCallback#addroleCallback(int, java.lang.String, java.lang.String)
	 */
	@Override
	public void addroleCallback(int retCode, String errorMsg, String roleid) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see com.bit_health.android.controllers.IBusinessCallback#getEcgHrImageUrlCallback(int, java.lang.String, java.lang.String)
	 */
	@Override
	public void getEcgHrImageUrlCallback(int retCode, String errorMsg,
			String imageUrl) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see com.bit_health.android.controllers.IBusinessCallback#getEcgPRCallback(int, java.lang.String, int[])
	 */
	@Override
	public void getEcgPRCallback(int retCode, String errorMsg, int[] pr) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see com.bit_health.android.controllers.IBusinessCallback#getPpgHrImageUrlCallback(int, java.lang.String, java.lang.String)
	 */
	@Override
	public void getPpgHrImageUrlCallback(int retCode, String errorMsg,
			String imageUrl) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see com.bit_health.android.controllers.IBusinessCallback#getPpgImageUrlCallback(int, java.lang.String, java.lang.String)
	 */
	@Override
	public void getPpgImageUrlCallback(int retCode, String errorMsg,
			String imageUrl) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void checkVersionCallback(int retCode, String errorMsg,
			CheckVersionBean bean) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void updateHeadback(int retCode, String errorMsg) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void getAnalyzeResultCallback(int retCode, String errorMsg,
			JsonBase bean) {
		// TODO Auto-generated method stub
		
	}

}
