package com.bit_health.android.util;

import com.siat.healthweek.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

/**********************************************************************
 * 类名：TimeConsumingDialog
 * 
 * 主要功能：登录操作时显示一个圈圈进度
 * 
 * @author 梁才学 创建日期：2014.1.6
 **********************************************************************/
public class TimeConsumingDialog extends Dialog {

	/**********************************************************************
	 * 方法描述：构造方法
	 * 
	 * @param Context
	 * @return
	 **********************************************************************/
	public TimeConsumingDialog(Context context) {
		super(context, R.style.transparentDialog);
	}

	/**********************************************************************
	 * 方法描述：创建圈圈进度条对话框
	 * 
	 * @param Bundle
	 * @return
	 **********************************************************************/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setCanceledOnTouchOutside(false);
		setContentView(R.layout.layout_drawing_progress);
	}

	/**********************************************************************
	 * 方法描述：在出现进度条对话框的过程中，使返回键失效
	 * 
	 * @param 无
	 * @return 无
	 * @see TimeConsumingDialog # onBackPressed
	 **********************************************************************/
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		// super.onBackPressed();
	}
	public void setShowMessage(String msg){
		TextView text = (TextView)findViewById(R.id.textViewPercent);
		text.setText(msg);
	}

//	public void callbackOk(BaseFragment baseFragment, List<RoleInfoBean> beans){
//		RoleCatchInfo mRoleCatchInfo = RoleCatchInfo.getInstance(baseFragment.getActivity().getApplicationContext());
//		mRoleCatchInfo.setRoles(beans);
//	}
}
