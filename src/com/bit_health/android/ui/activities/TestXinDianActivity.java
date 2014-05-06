package com.bit_health.android.ui.activities;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.siat.healthweek.R;
import com.bit_health.android.constants.DeviceConst;
import com.bit_health.android.device.bluetooth.ConnectBluetooth;
import com.bit_health.android.device.bluetooth.ConnectionDevice;
import com.bit_health.android.device.bluetooth.GetBlueDataService;
import com.bit_health.android.device.bluetooth.ui.BluetoothData;
import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**********************************************************************
 * 类名：TestXinDianActivity
 * 
 * @author 梁才学 主要功能：心电测试 创建日期：2013.12.12
 **********************************************************************/
public class TestXinDianActivity extends BaseActivity {

	private ImageView backIcon;
	private Button ok_button;
	private LayoutInflater inflater;
	private View view;

	// private Button mBtnMenu;
	private ImageView mImgShowAlert;
	private TextView mTextViewMsg;
	// private TextView mTextViewTittle;
	private Timer mTimer;

	private static final int START_WAITCODE_FLAG = 1;
	public static final int RESULT_CODE_SWITCH_FLAG = 10; // 切换蓝牙需求
	public static final String FLAG_INTENT_DEVCE_NAME = "FLAG_INTENT_DEVCE_NAME";
	private ArrayList<String> mDeviceNames;
	private int mShowRemark = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.test_xin_dian);
		if (getIntent() != null) {
			mDeviceNames = getIntent().getStringArrayListExtra(
					FLAG_INTENT_DEVCE_NAME);
		}

		inflater = LayoutInflater.from(this);
		view = inflater.inflate(R.layout.test_xin_dian, null);
		setContentView(view);

		// openBlueTooth();

		MyViewListener listener = new MyViewListener();
		backIcon = (ImageView) view.findViewById(R.id.test_xindian_back_icon);
		ok_button = (Button) view.findViewById(R.id.test_xindian_ok_button);

		// mBtnMenu = (Button)view.findViewById(R.id.test_show_menu_id);
		mImgShowAlert = (ImageView) view
				.findViewById(R.id.test_alert_show_image_id);
		mTextViewMsg = (TextView) view.findViewById(R.id.test_alert_message_id);

		// mTextViewTittle =
		// (TextView)view.findViewById(R.id.test_show_tittle_id);
		backIcon.setOnClickListener(listener);
		ok_button.setOnClickListener(listener);

		IntentFilter filter = new IntentFilter();
		filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
		filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
		this.registerReceiver(mReceiver, filter);
		if (isMiniHolterDevice()) {
			mImgShowAlert.setBackgroundResource(R.drawable.alert_mini_power);
			mTextViewMsg.setText(R.string.how_to_do_test_mini_remarks1);
			mTimer = new Timer();
			mTimer.schedule(new TimerTask() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					showNextMiniRemark();
				}
			}, 3000, 15000);
		}
		SharedPreferences preference = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);		
		setAllTextSizeOfApp(preference.getFloat("font_size_range", 0));
	}

	private void showNextMiniRemark() {
		switch (mShowRemark) {
		case 0:
			switch2MiniHeartUi();
			mShowRemark++;
			break;
		case 1:
			switch2MiniBluetoothCnntUi();
			mShowRemark++;
			break;
		default:
			if (mTimer != null) {
				mTimer.cancel();
				mTimer = null;
			}
			mShowRemark = 0;
			break;
		}
	}

	private boolean isMiniHolterDevice() {
		if (mDeviceNames != null) {
			for (String name : mDeviceNames) {
				if (name.startsWith(DeviceConst.BT_MINIHOLTER_NAME)
						|| name.startsWith(DeviceConst.BT_MINIHOLTER_NAME_NEW)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		if (mReceiver != null) {
			unregisterReceiver(mReceiver);
			mReceiver = null;
		}
	}

	@Override
	public ViewGroup getMainLayout() {
		// TODO Auto-generated method stub
		return (ViewGroup) view;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (requestCode == START_WAITCODE_FLAG) {
			if (resultCode == RESULT_CODE_SWITCH_FLAG) {
				Intent intent = new Intent(TestXinDianActivity.this,
						ConnectBluetooth.class);
				startActivityForResult(intent, 0);
			}
		}
		
		if (requestCode == ConnectBluetooth.REQUEST_RESULT_CONNECTION
				&& resultCode == ConnectBluetooth.RESPONSE_RESULT_CONNECTION) {
			// 蓝牙连接上?
			Intent intent = new Intent(getApplicationContext(),
					GetBlueDataService.class);
			intent.setAction(GetBlueDataService.ACTION_START_RECEIVER_DATA);
			startService(intent);
			if (!isMiniHolterDevice()) {
				switch2HandleAlertUi();
				mTimer = new Timer();
				mTimer.schedule(new TimerTask() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						switch2StartUi();
						mTimer.cancel();
						mTimer = null;
					}
				}, 3000);
			}
//			else {
//				AndroidActivityMananger.getInstatnce().switchActivity(
//						TestXinDianActivity.class.getSimpleName(),
//						EcgPpgActivity.class);
//			}
//			
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public void switch2StartUi() {
		if (!this.isFinishing()) {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					ok_button.setVisibility(View.GONE);
					// mBtnMenu.setVisibility(View.VISIBLE);
					mImgShowAlert
							.setBackgroundResource(R.drawable.alert_3n1_start);
					mTextViewMsg.setText("第三步：请打开开始按钮");
				}
			});
		}
	}

	public void switch2HandleAlertUi() {
		if (!this.isFinishing()) {
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					ok_button.setVisibility(View.GONE);
					// mBtnMenu.setVisibility(View.VISIBLE);
					mImgShowAlert
							.setBackgroundResource(R.drawable.alert_3n1_handle);
					mTextViewMsg.setText("第二步：请将中指插入指套中,两手紧贴电极片");
				}
			});
		}

	}

	public void switch2MiniHeartUi() {
		if (!this.isFinishing()) {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					mImgShowAlert
							.setBackgroundResource(R.drawable.alert_mini_heart);
					mTextViewMsg.setText(R.string.how_to_do_test_mini_remarks2);
				}
			});
		}
	}

	public void switch2MiniBluetoothCnntUi() {
		if (!this.isFinishing()) {
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					mImgShowAlert
							.setBackgroundResource(R.drawable.alert_mini_bluetooth);
					mTextViewMsg.setText(R.string.how_to_do_test_mini_remarks3);
				}
			});
		}

	}
	
	class MyViewListener implements OnClickListener {
		@Override
		public void onClick(View v) {

			switch (v.getId()) {
			case R.id.test_xindian_back_icon:
				finish();
				overridePendingTransition(R.anim.slide_left_in,
						R.anim.slide_right_out);
				break;

			case R.id.test_xindian_ok_button:
				Intent intent = new Intent(TestXinDianActivity.this,
						ConnectBluetooth.class);
				intent.putStringArrayListExtra(FLAG_INTENT_DEVCE_NAME,
						mDeviceNames);
				intent.putExtra(ConnectBluetooth.TAGET_ORIENTATION,
						Configuration.ORIENTATION_PORTRAIT);
				startActivityForResult(intent, ConnectBluetooth.REQUEST_RESULT_CONNECTION);
			default:
				break;
			}

		}
	}

	BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) {
				// String deviceName =
				// ConnectionDevice.getInstance().getDeviceName();
				android.util.Log
						.i("TestXinDianActivity",
								"BluetoothDevice.ACTION_ACL_DISCONNECTED called...........");
//				BluetoothDevice device = intent
//						.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				// String devicename1 = device.getName();
				//
				// int i= 1;
				// i++;
				Activity activity = AndroidActivityMananger.getInstatnce()
						.getActivity(TestXinDianActivity.class.getSimpleName());
				if (activity != null) {
					stopService(new Intent(context.getApplicationContext(),
							GetBlueDataService.class));
					activity.setResult(-1);
					activity.finish();
				}
				ConnectionDevice.getInstance().shutDownConnection();
			}
			if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {
				android.util.Log
						.i("TestXinDianActivity",
								"BluetoothDevice.ACTION_ACL_CONNECTED called...........");
				BluetoothData.getInsetance().mDevice = intent
						.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//				Intent intent1 = new Intent(context.getApplicationContext(),
//						GetBlueDataService.class);
//				intent1.setAction(GetBlueDataService.ACTION_START_RECEIVER_DATA);
//				startService(intent1);
//				if (!isMiniHolterDevice()) {
//					switch2HandleAlertUi();
//					mTimer = new Timer();
//					mTimer.schedule(new TimerTask() {
//						@Override
//						public void run() {
//							// TODO Auto-generated method stub
//							switch2StartUi();
//							mTimer.cancel();
//							mTimer = null;
//						}
//					}, 3000);
//				} else {
//					AndroidActivityMananger.getInstatnce().switchActivity(
//							TestXinDianActivity.class.getSimpleName(),
//							EcgPpgActivity.class);
//				}

				// TestXinDianActivity.this.startActivityForResult(new Intent(
				// context.getApplicationContext(),
				// WaitforEcgSignActivity.class), START_WAITCODE_FLAG);
			}
		}
	};

}
