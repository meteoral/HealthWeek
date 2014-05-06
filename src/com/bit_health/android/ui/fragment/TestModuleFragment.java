package com.bit_health.android.ui.fragment;

import java.util.ArrayList;

import com.siat.healthweek.R;
import com.bit_health.android.constants.DeviceConst;
import com.bit_health.android.device.bluetooth.ConnectBluetooth;
import com.bit_health.android.device.usb.UsbUploadService;
import com.bit_health.android.ui.activities.AndroidActivityMananger;
import com.bit_health.android.ui.activities.BlueToothUploadActivity;
import com.bit_health.android.ui.activities.FourModuleManangerActivity;
import com.bit_health.android.ui.activities.HelpMenualActivity;
import com.bit_health.android.ui.activities.TestXinDianActivity;
import com.bit_health.android.ui.activities.TestXueTangActivity;
import com.bit_health.android.ui.activities.TestXueYaActivity;
import com.bit_health.android.ui.adapter.TestModuleGV_Adapter;
import com.slidingmenu.lib.SlidingMenu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

/**********************************************************************
 * 类名：TestModuleFragment
 * 
 * @author 梁才学 主要功能：测试模块 创建日期：2013.12.9
 **********************************************************************/
public class TestModuleFragment extends BaseFragment {

	private View view;

	private final int XIN_DIAN_TEST = 0;// 心电测试
	private final int XUE_YA_TEST = 1;// 血压测试
	private final int XUE_TANG_TEST = 2;// 血糖测试
	private final int HELP = 3;// 帮助手册
	private final int BLUETOOTH_UPLOAD = 4;// 蓝牙一键上传
	private final int HISTORY_RECORDS = 5;// 上传记录
	 private final int REQUEST_BL_CONNECT = 1;

	private GridView gridview;
	private String[] itemTitle = { "心电测试", "血压测试", "血糖测试", "帮助手册", "蓝牙上传",
			"上传记录" };

	private AndroidActivityMananger mMananger;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mMananger = AndroidActivityMananger.getInstatnce();

		// 在这个界面中不允许滑动到菜单界面
		((FourModuleManangerActivity) getActivity()).getSlidingMenu()
				.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		view = inflater.inflate(R.layout.test_module, container, false);
		gridview = (GridView) view.findViewById(R.id.test_module_gridview);

		TestModuleGV_Adapter adapter = new TestModuleGV_Adapter(getActivity(),
				itemTitle);
		gridview.setAdapter(adapter);
		gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				switch (position) {
				case XIN_DIAN_TEST:
					// 心电测试
					// mMananger.switchActivityNoClose(
					// "FourModuleManangerActivity",
					// TestXinDianActivity.class);
					mMananger
							.showSelectDeviceTestDialog(TestModuleFragment.this
									.getActivity());
					break;
				case XUE_YA_TEST:
					// 血压测试
					mMananger.switchActivityNoClose(
							"FourModuleManangerActivity",
							TestXueYaActivity.class);
					break;
				case XUE_TANG_TEST:
					// 血糖测试
					mMananger.switchActivityNoClose(
							"FourModuleManangerActivity",
							TestXueTangActivity.class);
					break;
				case HELP:
					// 帮助手册
					mMananger.switchActivityNoClose(
							"FourModuleManangerActivity",
							HelpMenualActivity.class);
					break;
				case BLUETOOTH_UPLOAD:
					// 蓝牙一键上传
					Intent intent = new Intent(TestModuleFragment.this
							.getActivity(), ConnectBluetooth.class);
					ArrayList<String> deviceNames = new ArrayList<String>();
					deviceNames.add(DeviceConst.BT_MINIHOLTER_NAME_NEW);
					intent.putStringArrayListExtra(
							TestXinDianActivity.FLAG_INTENT_DEVCE_NAME,
							deviceNames);
					TestModuleFragment.this.getActivity()
							.startActivityForResult(intent, REQUEST_BL_CONNECT);

					break;

				case HISTORY_RECORDS:
					Intent intent2 = new Intent(TestModuleFragment.this
							.getActivity(), BlueToothUploadActivity.class);
					TestModuleFragment.this.getActivity().startActivity(intent2);

					break;
				}
			}
		});

		SharedPreferences preference = getActivity().getSharedPreferences(
				getActivity().getPackageName(), Context.MODE_PRIVATE);
		setAllTextSizeOfApp(preference.getFloat("font_size_range", 0));
		return view;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_BL_CONNECT
				&& resultCode == ConnectBluetooth.RESPONSE_RESULT_CONNECTION) {
			// 连接成功
			Intent intent = new Intent(getActivity(), UsbUploadService.class);
			intent.setAction(UsbUploadService.ACTION_START_BLUETOOTH_UPLOAD);
			getActivity().startService(intent);

		}
	}
	
	@Override
	public ViewGroup getMainLayout() {
		// TODO Auto-generated method stub
		return (ViewGroup) view;
	}
}
