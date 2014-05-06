package com.bit_health.android.ui.framelayout;

import java.util.ArrayList;
import java.util.List;

import com.siat.healthweek.R;
import com.bit_health.android.configuration.AndroidConfiguration;
import com.bit_health.android.configuration.RoleCatchInfo;
import com.bit_health.android.controllers.InterfaceService;
import com.bit_health.android.controllers.beans.RoleInfoBean;
import com.bit_health.android.ui.activities.FourModuleManangerActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

/**********************************************************************
 * 类名：HomePageContentTwo
 * 
 * 主要功能：主页模块中的第二页内容
 * 
 * @author 梁才学 创建日期：2013.12.31
 **********************************************************************/
public class HomePageContentTwo extends FrameLayout {

	private Activity mActivity;
	private LayoutInflater inflater;
	private ImageView addImage;
	private List<String> mUnCareList = new ArrayList<String>();

	public HomePageContentTwo(Activity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
		mActivity = activity;
	}

	public void homePageTwoCreate() {
		inflater = (LayoutInflater) mActivity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.content_module_page_2, this);
		addImage = (ImageView) findViewById(R.id.add_attention_person_image);
		MyViewListener listener = new MyViewListener();
		addImage.setOnClickListener(listener);
	}

	class MyViewListener implements OnClickListener {
		@Override
		public void onClick(View v) {

			switch (v.getId()) {

			case R.id.add_attention_person_image:
				RoleCatchInfo mRoleCatchInfo = RoleCatchInfo
						.getInstance(mActivity);
				List<RoleInfoBean> roles = mRoleCatchInfo.getRoles();
				String currentRoleId = AndroidConfiguration.getInstance(
						mActivity).getRoleId();// 当前角色的id
				List<String> roleFullNames = new ArrayList<String>();
				List<String> roleUserNames = new ArrayList<String>();
				// 循环遍历没有关注的角色
				for (int i = 0; i < roles.size(); i++) {
					if (!roles.get(i).mbCare
							&& !currentRoleId.equals(roles.get(i).mId)) {
						roleFullNames.add(roles.get(i).mFullName);
						roleUserNames.add(roles.get(i).mName);
						mUnCareList.add(roles.get(i).mId);
					}
				}

				String[] addRoleFullNames = new String[roleFullNames.size()];
				for (int i = 0; i < roleFullNames.size(); i++) {
					addRoleFullNames[i] = roleFullNames.get(i) + " " + roleUserNames.get(i);
				}

				if (addRoleFullNames.length == 0) {
					Toast.makeText(mActivity, "没有可以添加的角色", Toast.LENGTH_SHORT)
							.show();
				} else {
					AlertDialog mAlertDialog = new AlertDialog.Builder(mActivity)
							.setTitle("未关注的角色列表")
							.setItems(addRoleFullNames,
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											// TODO Auto-generated method stub
											((FourModuleManangerActivity) mActivity).bCaring = true;
											((FourModuleManangerActivity) mActivity)
													.showWaittingDialog("正在添加关注...");
											InterfaceService
													.getInstance(
															mActivity
																	.getApplicationContext())
													.careRole(
															mUnCareList
																	.get(which),
															true,
															(FourModuleManangerActivity) mActivity);
										}
									}).show();
					
					mAlertDialog.setCanceledOnTouchOutside(false);					
				}

				break;

			default:

			}

		}
	}
}
