package com.bit_health.android.ui.fragment;

import java.util.List;
import com.bit_health.android.controllers.IBusinessCallback;
import com.bit_health.android.controllers.InterfaceService;
import com.bit_health.android.controllers.beans.CheckVersionBean;
import com.bit_health.android.controllers.beans.JsonBase;
import com.bit_health.android.controllers.beans.RoleInfoBean;
import com.bit_health.android.util.SetTextSizeClass;
import com.bit_health.android.util.TimeConsumingDialog;

import android.app.Fragment;
import android.content.Intent;
import android.view.ViewGroup;
import android.widget.Toast;

/**********************************************************************
 * 类名：BaseFragment
 * 
 * @author 梁才学 主要功能：把具有某些共性的 Fragment 抽象出来，比如统一设置字体大小、标题风格等 创建日期：2013.12.17
 **********************************************************************/
public abstract class BaseFragment extends Fragment implements
		IBusinessCallback {

	private SetTextSizeClass mSetTextSizeClass;
	private TimeConsumingDialog mRequestWaittingDlg;// 耗时操作显示的进度对话框

	@Override
	public void callback(int retCode, String errorMsg) {
		// TODO Auto-generated method stub
		hideWaittingDialog();
		switch (retCode) {
		case 0:// 0 表示请求成功
			callbackOk();
			break;
		case -2:
			showAlert("网络错误");
			break;
		default:
			showAlert("其他错误:" + retCode);
			break;
		}
	}

	@Override
	public void updateHeadback(int retCode, String errorMsg) {
		// TODO Auto-generated method stub
		
	}
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		
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
	public void getAnalyzeResultCallback(int retCode, String errorMsg,
			JsonBase bean) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getMeasuresCallback(int retCode, String errorMsg,
			List<JsonBase> beans) {
		// TODO Auto-generated method stub

	}
	@Override
	public void checkVersionCallback(int retCode, String errorMsg,
			CheckVersionBean bean) {
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
	public void getPpgHrImageUrlCallback(int retCode, String errorMsg,
			String imageUrl) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getPpgImageUrlCallback(int retCode, String errorMsg,
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

	/****************************************************************
	 * 方法描述：抽象方法统一管理每个子 Fragment 的MainLayout
	 * 
	 ****************************************************************/
	public abstract ViewGroup getMainLayout();
		
	public void showWaittingDialog(final String msg) {
		if (getActivity() != null && !getActivity().isFinishing()) {
			getActivity().runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					if (mRequestWaittingDlg == null) {
						mRequestWaittingDlg = new TimeConsumingDialog(
								BaseFragment.this.getActivity());
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

//		if (getActivity() != null && !getActivity().isFinishing()) {
//			getActivity().runOnUiThread(new Runnable() {
//
//				@Override
//				public void run() {
//					// TODO Auto-generated method stub
//					Builder builder = new AlertDialog.Builder(BaseFragment.this
//							.getActivity());
//					builder.setTitle("警告！");
//					builder.setMessage(message);
//					builder.setPositiveButton("确定", null);
//					builder.create();
//					builder.show();
//				}
//			});
//		}
		if (getActivity() != null && !getActivity().isFinishing()) {
			getActivity().runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					Toast.makeText(getActivity(), message, 1000).show();
				}
			});
		}
	}

	/****************************************************************
	 * 方法描述：设置本应用的字体大小
	 * 
	 * @param font_size
	 *            需要设置的字体大小变化的幅度
	 * @return
	 ****************************************************************/
	public void setAllTextSizeOfApp(float font_size) {
		ViewGroup vg = getMainLayout();
		mSetTextSizeClass = new SetTextSizeClass(getActivity());
		mSetTextSizeClass.setFontSizeOfApp(vg, font_size);
	}

	public void callbackOk() {
	}

	public void updateAllRoleInfo() {
		showWaittingDialog("更新角色列表...");
		InterfaceService.getInstance(getActivity().getApplicationContext())
				.updateAllRoleInfoList(this);
	}
}
