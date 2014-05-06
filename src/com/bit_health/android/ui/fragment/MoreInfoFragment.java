package com.bit_health.android.ui.fragment;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.util.ByteArrayBuffer;

import com.siat.healthweek.R;
import com.bit_health.android.controllers.InterfaceService;
import com.bit_health.android.controllers.beans.CheckVersionBean;
import com.bit_health.android.ui.activities.DownLoadApkService;
import com.bit_health.android.ui.activities.FourModuleManangerActivity;
import com.bit_health.android.ui.activities.MoreInfoActivity;
import com.bit_health.android.util.CurrentVersion;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**********************************************************************
 * 类名：MyFamilyFragment
 * 
 * 主要功能：菜单中的 "更多"选项
 * 
 * @author 梁才学 创建日期：2014.1.7
 **********************************************************************/
public class MoreInfoFragment extends BaseFragment {

	public static final String DOWN_LOAD_APK_URL = "download_apk";
	public static final String VERSION_URL = "http://10.2.2.130:8081/api/app_update.php?type=check&version=VERSION&publish=PUBLISH&device_type=DEVICE_TYPE";
	private View view;
	private View layoutBgSet;// 背景设置
	// private View layoutRemindSet;// 提醒设置
	// private View layoutUnitSet;// 单位设置
	private View layoutFontSet;// 字体设置
	private View layoutAbout;// 关于
	private View layoutNewVersion;// 检查新版本
	private TextView currentVersionText;// 显示当前版本号
	private ImageView backIcon;
	private Handler mHandler;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.more_info_fragment, container, false);
		backIcon = (ImageView) view.findViewById(R.id.more_info_back_icon);

		layoutBgSet = view.findViewById(R.id.layout_bg_set);
		// layoutRemindSet =
		// view.findViewById(R.id.layout_more_info_remind_set);
		// layoutUnitSet = view.findViewById(R.id.layout_more_info_unit_set);
		layoutFontSet = view.findViewById(R.id.layout_more_info_font_set);
		layoutAbout = view.findViewById(R.id.layout_more_info_about);
		layoutNewVersion = view.findViewById(R.id.layout_version_num);
		currentVersionText = (TextView) view.findViewById(R.id.current_version_text);
		currentVersionText.setText(CurrentVersion.getVersionName(getActivity()));		

		MyViewListener listener = new MyViewListener();
		backIcon.setOnClickListener(listener);
		layoutBgSet.setOnClickListener(listener);
		// layoutRemindSet.setOnClickListener(listener);
		// layoutUnitSet.setOnClickListener(listener);
		layoutFontSet.setOnClickListener(listener);
		layoutAbout.setOnClickListener(listener);
		layoutNewVersion.setOnClickListener(listener);

		SharedPreferences preference = getActivity().getSharedPreferences(
				getActivity().getPackageName(), Context.MODE_PRIVATE);
		setAllTextSizeOfApp(preference.getFloat("font_size_range", 0));
		return view;
	}

	class MyViewListener implements OnClickListener {
		@Override
		public void onClick(View v) {

			switch (v.getId()) {
			case R.id.more_info_back_icon:
				getActivity().finish();
				getActivity().overridePendingTransition(R.anim.slide_left_in,
						R.anim.slide_right_out);
				break;

			case R.id.layout_bg_set:
				switchToMoreInfoActivity(MoreInfoActivity.BACKGROUND_SET);
				break;
			// case R.id.layout_more_info_remind_set:
			// switchToMoreInfoActivity(MoreInfoActivity.REMIND_SET);
			// break;
			// case R.id.layout_more_info_unit_set:
			// switchToMoreInfoActivity(MoreInfoActivity.UNIT_SET);
			// break;
			case R.id.layout_more_info_font_set:
				switchToMoreInfoActivity(MoreInfoActivity.FONT_SET);
				break;
			case R.id.layout_more_info_about:
				switchToMoreInfoActivity(MoreInfoActivity.INFO_ABOUT);
				break;
			case R.id.layout_version_num:
				// mHandler = new Handler();
				// checkVersionNum();

				showWaittingDialog("正在检查版本号...");
				InterfaceService.getInstance(
						getActivity().getApplicationContext()).checkAppVersion(
						MoreInfoFragment.this);

				break;
			default:

			}
		}
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

				getActivity().runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						Builder builder = new AlertDialog.Builder(getActivity());
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
										intent.putExtra(DOWN_LOAD_APK_URL,
												apkUrl);
										intent.setClass(MoreInfoFragment.this
												.getActivity(),
												DownLoadApkService.class);
										getActivity().startService(intent);

									}
								});
						builder.setNegativeButton("跳过",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										if (mIsForce == 1) {// 强制更新
											getActivity().finish();
											FourModuleManangerActivity.AppInstance
													.finish();
										}
									}
								});
						builder.create();
						builder.show();
					}
				});

			} else if (bean.mCheckCode == 1) {
				getActivity().runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						Toast.makeText(getActivity(), "已经是最新版本",
								Toast.LENGTH_SHORT).show();
					}
				});
			}

			break;
		default:
			break;
		}

	}

	private void switchToMoreInfoActivity(int fragment_index) {
		Intent intent = new Intent(getActivity().getApplicationContext(),
				MoreInfoActivity.class);
		Bundle bundle = new Bundle();
		bundle.putInt("fragment_index", fragment_index);
		intent.putExtras(bundle);
		getActivity().startActivity(intent);

		/*
		 * 注意：此方法只能在startActivity和finish方法之后调用。 第一个参数为所进入的Activity的动画效果,
		 * 第二参数为所离开的Activity的动画效果
		 */
		getActivity().overridePendingTransition(R.anim.slide_right_in,
				R.anim.slide_left_out);
	}

	@Override
	public ViewGroup getMainLayout() {
		// TODO Auto-generated method stub
		return (ViewGroup) view;
	}

	// 检查当前版本号
	public void checkVersionNum() {
		// TODO Auto-generated method stub
		try {
			URL updateURL = new URL(VERSION_URL);// 获取新版本号的URL
			URLConnection conn = updateURL.openConnection();
			InputStream is = conn.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			ByteArrayBuffer baf = new ByteArrayBuffer(50);

			int current = 0;
			while ((current = bis.read()) != -1) {
				baf.append((byte) current);
			}
			final String s = new String(baf.toByteArray());
			// 服务器中的版本号
			int newVersion = Integer.valueOf(s);

			// 获取用户当前APP的版本号
			int curVersion = getActivity().getPackageManager().getPackageInfo(
					"com.bit_health.android", 0).versionCode;

			// 如果服务器的版本号高于用户当前的版本号
			if (newVersion > curVersion) {
				mHandler.post(showUpdate);
			} else {
				Toast.makeText(getActivity(), "已经是最新版本", Toast.LENGTH_LONG)
						.show();
			}
		} catch (Exception e) {
		}
	}

	private Runnable showUpdate = new Runnable() {
		@Override
		public void run() {
			new AlertDialog.Builder(getActivity())
					.setTitle("提示")
					.setMessage("当前应用不是最新版本，是否更新？")
					.setPositiveButton("是",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int whichButton) {
									// String fileName = APPDATA_PATH
									// + "BitHealth1.0.apk";
									// Intent intent = new Intent(
									// Intent.ACTION_VIEW);
									// intent.setDataAndType(
									// Uri.parse("file://" + fileName),
									// "application/vnd.android.package-archive");
									// startActivityForResult(intent, 40);
								}
							})
					.setNegativeButton("否",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int whichButton) {

								}
							}).show();
		}
	};
}
