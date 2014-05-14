package com.mobilehealth.sensibelbed;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.mobilehealth.core.ChildPageListener;
import com.mobilehealth.core.FragmentChildPage;
import com.siat.healthweek.R;

public class FragmentHealthCheckMainPage extends FragmentChildPage{

	private LinearLayout llHRV;
	private LinearLayout llBreathFreq;
	private LinearLayout llSleepStatus;
	private BluetoothAdapter btAdapt = null;

	public FragmentHealthCheckMainPage() {
		// TODO Auto-generated constructor stub
		this.layoutId=R.layout.page_health_check;
	}

	@Override
	protected void init(View layout) {
		// TODO Auto-generated method stub
		llHRV = (LinearLayout) layout.findViewById(R.id.llHRV);
		llBreathFreq = (LinearLayout) layout.findViewById(R.id.llBreathFreq);
		llSleepStatus = (LinearLayout) layout.findViewById(R.id.llSleepStatus);

		llHRV.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((ChildPageListener)getParentFragment()).changeToPage(FragmentBreathFreq.class);
			}
		});

		llBreathFreq.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((ChildPageListener)getParentFragment()).changeToPage(FragmentSleepStatus.class);
			}
		});

		llSleepStatus.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((ChildPageListener)getParentFragment()).changeToPage(FragmentHRV.class);
			}
		});
		btAdapt = BluetoothAdapter.getDefaultAdapter();// 初始化本机蓝牙功能
		if(btAdapt.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE)
		{
			Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
			discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 3600);
			startActivityForResult(discoverableIntent, BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE);
		}
	}
}
