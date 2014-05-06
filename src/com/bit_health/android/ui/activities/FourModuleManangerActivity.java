package com.bit_health.android.ui.activities;

import java.util.List;

import com.siat.healthweek.R;
import com.bit_health.android.configuration.AndroidConfiguration;
import com.bit_health.android.configuration.RoleCatchInfo;
import com.bit_health.android.controllers.InterfaceService;
import com.bit_health.android.controllers.beans.CheckVersionBean;
import com.bit_health.android.controllers.beans.RoleInfoBean;
import com.bit_health.android.task.AndroidCustomTaskMgr;
import com.bit_health.android.task.CheckConnectTask;
import com.bit_health.android.task.CheckVersionTask;
import com.bit_health.android.task.GetOverAnalyzesTask;
import com.bit_health.android.task.UpateRoleInfosTask;
import com.bit_health.android.ui.fragment.BgSettingsFragment;
import com.bit_health.android.ui.fragment.ContentModuleFragment;
import com.bit_health.android.ui.fragment.FontSizeSetFragment;
import com.bit_health.android.ui.fragment.MenuFragment;
import com.bit_health.android.ui.fragment.MoreInfoFragment;
import com.bit_health.android.ui.fragment.ReportModuleFragment;
import com.bit_health.android.ui.fragment.TestModuleFragment;
import com.bit_health.android.ui.framelayout.HomePageContentOne;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.SlidingMenu.OnCloseListener;
import com.slidingmenu.lib.app.SlidingActivity;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

/**********************************************************************
 * 类名：FourModuleActivity
 * 
 * @author 梁才学 主要功能：内容界面的入口Activity 创建日期：2013.12.9
 **********************************************************************/
public class FourModuleManangerActivity extends SlidingActivity {

	public static FourModuleManangerActivity AppInstance = null;

	private RadioGroup mRadioGroup;
	public ContentModuleFragment mContentModuleFragment;
	private TestModuleFragment mTestModuleFragment;
	private ReportModuleFragment mReportModuleFragment;
	private FragmentTransaction transaction;
	private AndroidActivityMananger mMananger;
	private RadioButton homePageRadio;
	private RadioButton testRadio;
	private RadioButton reportRadio;
	public boolean bCaring = false;
	private MenuFragment menuFragment;
	private AndroidCustomTaskMgr mTaskMgr;
	private CheckConnectTask mCheckConnectTask;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.four_module_mananger); // 设置 Sliding 内容界面的布局
		setBehindContentView(R.layout.frame_menu);// 设置 Sliding 菜单界面的布局

		AppInstance = this;
		mMananger = AndroidActivityMananger.getInstatnce();
		homePageRadio = (RadioButton) findViewById(R.id.content_module_rb);
		testRadio = (RadioButton) findViewById(R.id.test_module_rb);
		reportRadio = (RadioButton) findViewById(R.id.report_module_rb);
		setupWidgets();
		/*
		 * FragmentTransaction类主要用于管理Fragment,有添加，替换，删除等操作。
		 * 尤其是beginTransaction()与commit()方法与SQL中的事务有点类似
		 */
		FragmentTransaction fragmentTransaction = getFragmentManager()
				.beginTransaction();
		menuFragment = new MenuFragment();
		fragmentTransaction.replace(R.id.sliding_menu, menuFragment);
		fragmentTransaction.commit();

		// SlidingMenu 可以设置 Sliding 的一些属性
		SlidingMenu sm = getSlidingMenu();
		sm.setShadowWidth(50);// 设置阴影宽度，数值越大阴影宽度越大
		sm.setShadowDrawable(R.drawable.shadow);// 设置阴影的颜色
		sm.setBehindOffset(120);// 设置SlidingMenu打开后，右边留下的宽度
		sm.setFadeDegree(0.35f);// SlidingMenu收回时的黑色度，值越大，就越黑

		sm.setOnCloseListener(new OnCloseListener() {

			@Override
			public void onClose() {
				// TODO Auto-generated method stub
				// 当点击菜单页中的非"主页"选项时，滑过来的页面让底部的布局隐藏
				if (menuFragment.getmIndex() != 0) {
					mRadioGroup.setVisibility(View.GONE);
				} else {
					mRadioGroup.setVisibility(View.VISIBLE);
				}
			}
		});

		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		// setAllTextSizeOfApp(FontSizeSetFragment.FONT_SET_RANGE);
		setAllTextSize();

		mTaskMgr = AndroidCustomTaskMgr.getInstance(this
				.getApplicationContext());
		mTaskMgr.addSheduleTask(
				new CheckVersionTask(this.getApplicationContext(), this),
				CheckVersionTask.class.getSimpleName());
		mTaskMgr.addSheduleTask(
				new UpateRoleInfosTask(this.getApplicationContext(), this),
				UpateRoleInfosTask.class.getSimpleName());
		mTaskMgr.addSheduleTask(
				new GetOverAnalyzesTask(this.getApplicationContext(), this),
				GetOverAnalyzesTask.class.getSimpleName());

		// 注册监视网络连接的广播
		mCheckConnectTask = new CheckConnectTask(mContentModuleFragment);
		IntentFilter filter = new IntentFilter(
				ConnectivityManager.CONNECTIVITY_ACTION);
		registerReceiver(mCheckConnectTask, filter);
	}

	private void setupWidgets() {

		mRadioGroup = (RadioGroup) findViewById(R.id.four_radiogroup);
		mRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub

				switch (checkedId) {
				case R.id.content_module_rb:
					if (null == mContentModuleFragment) {
						mContentModuleFragment = new ContentModuleFragment();
					}
					transaction = getFragmentManager().beginTransaction();
					transaction.replace(R.id.fragment_container,
							mContentModuleFragment);
					transaction.commit();
					// homePageRadio.getBackground().setAlpha(0);
					homePageRadio.setTextColor(Color.parseColor("#00AEEF"));
					testRadio.setTextColor(Color.parseColor("#FFFFFF"));
					reportRadio.setTextColor(Color.parseColor("#FFFFFF"));
					mCurrentFragment = mContentModuleFragment;
					break;
				case R.id.test_module_rb:
					if (null == mTestModuleFragment) {
						mTestModuleFragment = new TestModuleFragment();
					}
					transaction = getFragmentManager().beginTransaction();
					transaction.replace(R.id.fragment_container,
							mTestModuleFragment);
					transaction.commit();
					// testRadio.getBackground().setAlpha(0);
					homePageRadio.setTextColor(Color.parseColor("#FFFFFF"));
					testRadio.setTextColor(Color.parseColor("#00AEEF"));
					reportRadio.setTextColor(Color.parseColor("#FFFFFF"));
					mCurrentFragment = mTestModuleFragment;
					break;
				case R.id.report_module_rb:
					if (null == mReportModuleFragment) {
						mReportModuleFragment = new ReportModuleFragment();
					}
					transaction = getFragmentManager().beginTransaction();
					transaction.replace(R.id.fragment_container,
							mReportModuleFragment);
					transaction.commit();
					// reportRadio.getBackground().setAlpha(0);
					homePageRadio.setTextColor(Color.parseColor("#FFFFFF"));
					testRadio.setTextColor(Color.parseColor("#FFFFFF"));
					reportRadio.setTextColor(Color.parseColor("#00AEEF"));
					mCurrentFragment = mReportModuleFragment;
					break;
				default:
					break;
				}

				// 处理：ViewPager 与 SlidingMenu 之间的滑动冲突
				getSlidingMenu().setTouchModeAbove(
						SlidingMenu.TOUCHMODE_FULLSCREEN);
			}
		});

		((RadioButton) mRadioGroup.getChildAt(0)).toggle();// 设置默认RadioButton为第一个
	}

	/**********************************************************************
	 * 方法描述：当从侧滑菜单“背景设置”返回内容界面时，对内容界面的背景进行更改
	 * 
	 **********************************************************************/
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		// 当设置了字体大小，返回主界面是，设置侧滑菜单中字体大小
		if (FontSizeSetFragment.isSetFontSize) {
			FontSizeSetFragment.isSetFontSize = false;
			setAllTextSize();// 主界面设置字体
			menuFragment.setAllTextSize();// 侧滑菜单界面设置字体
		}
		if (BgSettingsFragment.isSettingsBg) {
			toggle();// slidingmenu 中的 toggle()
		}
		mContentModuleFragment.refreshForHomePageContentOne();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mTaskMgr.removeSheduleTask(CheckVersionTask.class.getSimpleName());
		mTaskMgr.removeSheduleTask(UpateRoleInfosTask.class.getSimpleName());
		AppInstance = null;

		this.unregisterReceiver(mCheckConnectTask);// 手动取消广播
	}

	/****************************************************************
	 * 方法描述：设置本界面的字体大小
	 ****************************************************************/
	private void setAllTextSize() {

		SharedPreferences preference = getSharedPreferences(getPackageName(),
				Context.MODE_PRIVATE);
		int currentIndex = preference.getInt("currentIndex", 0);

		if (currentIndex == 2) {
			homePageRadio.setTextSize(
					TypedValue.COMPLEX_UNIT_PX,
					getResources().getDimensionPixelSize(
							R.dimen.set_font_size_31px));
			testRadio.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources()
					.getDimensionPixelSize(R.dimen.set_font_size_31px));
			reportRadio.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources()
					.getDimensionPixelSize(R.dimen.set_font_size_31px));
		} else if (currentIndex == 1) {
			homePageRadio.setTextSize(
					TypedValue.COMPLEX_UNIT_PX,
					getResources().getDimensionPixelSize(
							R.dimen.set_font_size_28px));
			testRadio.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources()
					.getDimensionPixelSize(R.dimen.set_font_size_28px));
			reportRadio.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources()
					.getDimensionPixelSize(R.dimen.set_font_size_28px));
		} else if (currentIndex == 0) {
			homePageRadio.setTextSize(
					TypedValue.COMPLEX_UNIT_PX,
					getResources().getDimensionPixelSize(
							R.dimen.set_font_size_25px));
			testRadio.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources()
					.getDimensionPixelSize(R.dimen.set_font_size_25px));
			reportRadio.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources()
					.getDimensionPixelSize(R.dimen.set_font_size_25px));
		}
	}

	/**********************************************************************
	 * 方法描述：点击返回键时，弹出对话框提示退出应用
	 * 
	 * @param keyCode
	 * @param event
	 * @return
	 **********************************************************************/
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {

			if (getSlidingMenu().isMenuShowing()) {// 如果显示菜单页，则弹出退出应用的对话框
				exitAppDialog();
			} else if (getSlidingMenu().isShown()
					&& mRadioGroup.getVisibility() == View.GONE) {
				// 如果显示菜单页中的某一项的内容界面，则切换到显示菜单页
				getSlidingMenu().toggle();
			} else {
				exitAppDialog();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/****************************************************************
	 * 方法描述：需要重写这个方法，因为在 SlidingActivityHelper.java中处理过
	 * 
	 * @param
	 * @return
	 ****************************************************************/
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return false;
		}

		return super.onKeyUp(keyCode, event);
	}

	/****************************************************************
	 * 方法描述：退出应用的对话框
	 * 
	 * @param
	 * @return
	 ****************************************************************/
	private void exitAppDialog() {
		Builder builder = new AlertDialog.Builder(
				FourModuleManangerActivity.this);

		builder.setTitle("提示");
		builder.setMessage("确定要退出程序吗？");
		builder.setPositiveButton("退出", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		builder.setNegativeButton("取消", null);
		builder.create();
		builder.show();
	}

	@Override
	public void checkVersionCallback(int retCode, String errorMsg,
			CheckVersionBean bean) {
		// TODO Auto-generated method stub
		super.checkVersionCallback(retCode, errorMsg, bean);
		hideWaittingDialog();
		switch (retCode) {
		case 0:

			if (bean.mCheckCode == 0) {// 0有新版本，1 已经是最新的版本
				final String apkUrl = bean.mUrl;
				final String updateContent = bean.mUpdateInfo;
				final int mIsForce = bean.mIsForce;

				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						Builder builder = new AlertDialog.Builder(
								FourModuleManangerActivity.this);
						builder.setTitle("提示");
						builder.setMessage("有新版本，更新内容如下：" + "\n"
								+ updateContent + "\n" + "是否下载安装？");
						builder.setPositiveButton("是",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										Intent intent = new Intent();
										intent.putExtra(
												MoreInfoFragment.DOWN_LOAD_APK_URL,
												apkUrl);
										intent.setClass(
												FourModuleManangerActivity.this,
												DownLoadApkService.class);
										FourModuleManangerActivity.this
												.startService(intent);

									}
								});
						builder.setNegativeButton("跳过",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										if (mIsForce == 1) {// 强制更新
											FourModuleManangerActivity.this
													.finish();
										}
									}
								});
						builder.create();
						builder.show();
					}
				});

			}

			break;
		default:
			break;
		}

	}

	@Override
	public void getRoleInfoListCallback(int retCode, String errorMsg,
			List<RoleInfoBean> beans) {
		// TODO Auto-generated method stub
		super.getRoleInfoListCallback(retCode, errorMsg, beans);
		hideWaittingDialog();
		switch (retCode) {
		case 0:// 0 表示请求成功
			RoleCatchInfo mRoleCatchInfo = RoleCatchInfo.getInstance(this
					.getApplicationContext());
			mRoleCatchInfo.setRolesAllData(beans);
			break;
		default:
			showAlert(errorMsg);
			break;
		}
	}

	@Override
	public void callback(int retCode, String errorMsg) {
		// TODO Auto-generated method stub
		super.callback(retCode, errorMsg);
		hideWaittingDialog();
		switch (retCode) {
		case 0:
			if (bCaring) {
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						List<String> carelist = RoleCatchInfo.getInstance(
								FourModuleManangerActivity.this
										.getApplicationContext()).getCareList();

						mContentModuleFragment.addAttentionRole(carelist
								.get(carelist.size() - 1));
					}
				});

			} else {
				AndroidConfiguration.getInstance(this.getApplicationContext())
						.clear();
				mMananger.switchActivity("FourModuleManangerActivity",
						LoginActivity.class);
			}

			break;
		default:
			showAlert(errorMsg);
			break;
		}
	}

	public void logOut() {
		/******************** 连接服务器 start *******************/
		showWaittingDialog("登出中...");
		InterfaceService.getInstance(getApplicationContext()).Logout(this);
		/******************** 连接服务器 end *******************/
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == HomePageContentOne.GO_TO_BasicInfoOfFamilyActivity
				&& resultCode == HomePageContentOne.GO_TO_BasicInfoOfFamilyActivity) {
			mContentModuleFragment.refreshForHomePageContentOne();
		}
	}
}
