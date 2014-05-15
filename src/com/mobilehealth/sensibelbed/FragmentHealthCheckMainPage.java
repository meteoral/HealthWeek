package com.mobilehealth.sensibelbed;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobilehealth.core.FragmentActivityEx;
import com.mobilehealth.core.FragmentChildPage;
import com.mobilehealth.core.ParentFragment;
import com.mobilehealth.util.StatusCode;
import com.siat.healthweek.R;

public class FragmentHealthCheckMainPage extends FragmentChildPage{

	private LinearLayout llHRV;
	private LinearLayout llBreathFreq;
	private LinearLayout llSleepStatus;
	
	private ImageView ivStatus;
	private TextView tvGeneralHealthStatus;
	private TextView tvSleepQuality;
	private TextView tvTips;
	
	private String firstTime="firstTime";
	private final String KEY_FOR_FIRST_TIME="KEY_FOR_FIRST_TIME";

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
		
		ivStatus=(ImageView)layout.findViewById(R.id.ivStatus);
		tvGeneralHealthStatus=(TextView)layout.findViewById(R.id.tvGeneralHealthStatus);
		tvSleepQuality=(TextView)layout.findViewById(R.id.tvSleepQuality);
		tvTips=(TextView)layout.findViewById(R.id.tvTips);

		llHRV.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(checkReady()==false)
				{
					return;
				}
				((ParentFragment)getParentFragment()).changeToPage(FragmentHRV.class);
			}
		});

		llBreathFreq.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(checkReady()==false)
				{
					return;
				}
				((ParentFragment)getParentFragment()).changeToPage(FragmentBreathFreq.class);
			}
		});

		llSleepStatus.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(checkReady()==false)
				{
					return;
				}
				((ParentFragment)getParentFragment()).changeToPage(FragmentSleepStatus.class);
			}
		});
		
		if(BluetoothAdapter.getDefaultAdapter().getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE)
		{
			statusChanged(StatusCode.STATUS_NOT_READY);
			
			String temp=(String) ((FragmentActivityEx)getActivity()).getData(KEY_FOR_FIRST_TIME);
			if(temp.length()==0)
			{
				sendRequestForBlueTooth();
			}
		}else
		{
			statusChanged(StatusCode.STATUS_READY);
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if(3600 == resultCode)
		{
			statusChanged(StatusCode.STATUS_READY);
		}
		else
		{
			statusChanged(StatusCode.STATUS_NOT_READY);
		}
		//super.onActivityResult(requestCode, resultCode, data);
	}
	
	private void statusChanged(String statusCode)
	{
		if(statusCode.equals(StatusCode.STATUS_READY))
		{
			ivStatus.setImageResource(R.drawable.bluetooth_status_right);
			tvGeneralHealthStatus.setText("蓝牙开启成功！");
			tvSleepQuality.setText("试用健康床，请点击下面栏目");
			tvTips.setText("可以获取您的健康信息");
		}else if(statusCode.equals(StatusCode.STATUS_NOT_READY))
		{
			ivStatus.setImageResource(R.drawable.bluetooth_status_error);
			ivStatus.setScaleType(ImageView.ScaleType.FIT_CENTER);
			tvGeneralHealthStatus.setText("未开启蓝牙！");
			tvSleepQuality.setText("请在弹出的对话框选择“是”开启蓝牙功能");
			tvTips.setText("或点击上面异常的图标重新开启蓝牙");
			ivStatus.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					sendRequestForBlueTooth();
				}
			});
		}
	}
	
	@Override
	protected void saveData() {
		// TODO Auto-generated method stub
		super.saveData();
		
		((FragmentActivityEx)getActivity()).setData(KEY_FOR_FIRST_TIME, firstTime);
	}
	
	private void sendRequestForBlueTooth()
	{
		Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
		discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 3600);
		startActivityForResult(discoverableIntent, BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE);
	}
	
	private boolean checkReady()
	{
		if(BluetoothAdapter.getDefaultAdapter().getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE)
		{
			Toast.makeText(getActivity(), "蓝牙未开启，请先点击上部蓝牙图标开启蓝牙", Toast.LENGTH_SHORT).show();
			return false;
		}else
		{
			return true;
		}
	}
}
