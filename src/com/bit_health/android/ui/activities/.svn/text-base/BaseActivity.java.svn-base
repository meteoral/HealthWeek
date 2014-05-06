package com.bit_health.android.ui.activities;

import java.util.List;

import com.siat.healthweek.R;
import com.bit_health.android.constants.AndroidDeviceConst;
import com.bit_health.android.controllers.IBusinessCallback;
import com.bit_health.android.controllers.beans.CheckVersionBean;
import com.bit_health.android.controllers.beans.JsonBase;
import com.bit_health.android.controllers.beans.RoleInfoBean;
import com.bit_health.android.ui.fragment.BaseFragment;
import com.bit_health.android.util.SetTextSizeClass;
import com.bit_health.android.util.TimeConsumingDialog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.widget.Toast;

/**********************************************************************
 * 类名：BaseActivity
 * 
 * @author 梁才学 主要功能：把具有某些共性的 Activity 抽象出来，比如统一设置字体大小、标题风格等 创建日期：2013.12.16
 **********************************************************************/
public abstract class BaseActivity extends Activity implements
		IBusinessCallback {
	private AndroidActivityMananger mMananger;
	private String subActivityName;
	private SetTextSizeClass mSetTextSizeClass;
	private TimeConsumingDialog mRequestWaittingDlg;// 耗时操作显示的进度对话框

	// 如果存在fragment,这个记录的是当前的fragment
	protected BaseFragment mCurrentFragment;

	// // 记录点击返回键的时间，若在2秒钟之内点击两次返回键，则退出应用
	// private long exitTime = 0;

	/****************************************************************
	 * 方法描述：抽象方法统一管理每个子 Activity 的MainLayout
	 * 
	 ****************************************************************/
	public abstract ViewGroup getMainLayout();

	/****************************************************************
	 * 方法描述：当子 Activity 被创建时，统一将类名添加到AndroidActivityMananger
	 * 
	 * @param
	 * @return
	 ****************************************************************/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mMananger = AndroidActivityMananger.getInstatnce();
		subActivityName = getClass().getSimpleName();// 这是获取子类的类名
		mMananger.putActivity(subActivityName, this);

		AndroidDeviceConst.Initialization(this);
	}

	/****************************************************************
	 * 方法描述：当子 Activity 被销毁时，统一将类名从 AndroidActivityMananger 中移除
	 * 
	 * @param
	 * @return
	 ****************************************************************/
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mMananger.removeActivity(subActivityName);
	}

	/****************************************************************
	 * 方法描述：设置本应用的字体大小
	 ****************************************************************/
	public void setAllTextSizeOfApp(float font_size) {
		ViewGroup vg = getMainLayout();
		mSetTextSizeClass = new SetTextSizeClass(this);
		mSetTextSizeClass.setFontSizeOfApp(vg, font_size);
	}

	/**********************************************************************
	 * 方法描述：在某些特定界面中点击返回键时，第一次点击提醒用户，再点击一次就退出应用
	 * 
	 * @param keyCode
	 * @param event
	 * @return
	 **********************************************************************/
//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//
//		if (keyCode == KeyEvent.KEYCODE_BACK
//				&& event.getAction() == KeyEvent.ACTION_DOWN
//				&& (subActivityName.equals("LoginActivity") || subActivityName
//						.equals("GuiderActivity"))) {
//			if ((System.currentTimeMillis() - exitTime) > 2000) {// 间隔两秒钟
//				Toast.makeText(getApplicationContext(), "再按一次退出程序",
//						Toast.LENGTH_SHORT).show();
//				exitTime = System.currentTimeMillis();
//			} else {
//				finish();
//			}
//			return true;
//		}
//		return super.onKeyDown(keyCode, event);
//	}

	public void showWaittingDialog(final String msg) {
		if (!this.isFinishing()) {
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					if (mRequestWaittingDlg == null) {
						mRequestWaittingDlg = new TimeConsumingDialog(
								BaseActivity.this);
					}
					mRequestWaittingDlg.show();
					mRequestWaittingDlg.setShowMessage(msg);
				}
			});
		}
	}

	public void hideWaittingDialog() {
		if (mRequestWaittingDlg != null) {
			mRequestWaittingDlg.dismiss();
			mRequestWaittingDlg = null;
		}
	}

	public void showAlert(final String message) {
		// if(!this.isFinishing()){
		// runOnUiThread(new Runnable() {
		//
		// @Override
		// public void run() {
		// // TODO Auto-generated method stub
		//
		// Builder builder = new AlertDialog.Builder(BaseActivity.this);
		// builder.setTitle("警告！");
		// builder.setMessage(message);
		// builder.setPositiveButton("确定", null);
		// builder.create();
		// builder.show();
		// }
		// });
		// }
		if (!this.isFinishing()) {
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					Toast.makeText(BaseActivity.this, message, 1000).show();
				}
			});
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (mCurrentFragment != null) {
			mCurrentFragment.onActivityResult(requestCode, resultCode, data);
		}
	}

	@Override
	public void checkVersionCallback(int retCode, String errorMsg,
			CheckVersionBean bean) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			finish();
			overridePendingTransition(R.anim.slide_left_in,
					R.anim.slide_right_out);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void callback(int retCode, String errorMsg) {
		// TODO Auto-generated method stub

	}
	@Override
	public void getAnalyzeResultCallback(int retCode, String errorMsg,
			JsonBase bean) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void updateHeadback(int retCode, String errorMsg) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void getRoleInfoCallback(int retCode, String errorMsg,
			RoleInfoBean beans) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getRoleInfoListCallback(int retCode, String errorMsg,
			List<RoleInfoBean> beans) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getDoctorSuggestCallback(int retCode, String errorMsg,
			String doctorSuggest) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getAbnormalMeasuresCallback(int retCode, String errorMsg,
			int abCount) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getMeasuresCallback(int retCode, String errorMsg,
			List<JsonBase> beans) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getMeasureDetailCallback(int retCode, String errorMsg,
			JsonBase bean) {
		// TODO Auto-generated method stub

	}

	@Override
	public void registerCallback(int retCode, String errorMsg, String retMsg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addroleCallback(int retCode, String errorMsg, String roleid) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getEcgImageUrlCallback(int retCode, String errorMsg,
			String imageUrl) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getABEcgImageUrlCallback(int retCode, String errorMsg,
			String imageUrl) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getEcgHrImageUrlCallback(int retCode, String errorMsg,
			String imageUrl) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getEcgPRCallback(int retCode, String errorMsg, int[] pr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getPpgHrImageUrlCallback(int retCode, String errorMsg,
			String imageUrl) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getPpgImageUrlCallback(int retCode, String errorMsg,
			String imageUrl) {
		// TODO Auto-generated method stub

	}
}
