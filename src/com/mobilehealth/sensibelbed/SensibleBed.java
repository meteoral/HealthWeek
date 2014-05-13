package com.mobilehealth.sensibelbed;

import java.util.HashMap;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobilehealth.core.FragmentListAdapter;
import com.mobilehealth.core.MainFrameForMedicalKit;
import com.siat.healthweek.R;

public class SensibleBed extends MainFrameForMedicalKit{

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		super.init();

		ivTabs[0].setImageResource(R.drawable.health_check_on_selector);
		ivTabs[1].setImageResource(R.drawable.healthcare_knowledge_off_selector);
		ivTabs[2].setImageResource(R.drawable.community_share_off_selector);

		ivCurSubjectIcon.setImageResource(R.drawable.sensible_bed);
		tvRightCaption.setText(R.string.sensible_bed);

		{
			centerCaptions=new HashMap<String, String>();
			centerCaptions.put(FragmentBreathFreq.class.getName(), getResources().getString(R.string.breath_freq));
		}

        vpAdapter = new FragmentListAdapter(this, getSupportFragmentManager());
        vpAdapter.addFragment(FragmentHealthCheck.class, null);
        vpAdapter.addFragment(FragmentHealthcareKnowledge.class, null);
        vpAdapter.addFragment(FragmentCommunityShare.class, null);

		vpContent.setAdapter(vpAdapter);

		bottom_tab_on_selectors=new int[]{R.drawable.health_check_on_selector, R.drawable.healthcare_knowledge_on_selector, R.drawable.community_share_on_selector};
		bottom_tab_off_selectors=new int[]{R.drawable.health_check_off_selector, R.drawable.healthcare_knowledge_off_selector, R.drawable.community_share_off_selector};
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Activity a =vpAdapter.getItem(1).getActivity();
		ImageView status = (ImageView) a.findViewById(R.id.ivStatus);
		TextView firstLine = (TextView) a.findViewById(R.id.tvGeneralHealthStatus);
		TextView secondLine = (TextView) a.findViewById(R.id.tvSleepQuality);
		TextView thirdLine = (TextView) a.findViewById(R.id.tvTips);
		if(3600 == resultCode)
		{
			status.setImageResource(R.drawable.bluetooth_status_right);
			firstLine.setText("蓝牙开启成功！");
			secondLine.setText("请试用健康床，点击下面栏目");
			thirdLine.setText("获取您的健康信息");
			status.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
				}
			});
		}
		else
		{
			status.setImageResource(R.drawable.bluetooth_status_error);
			status.setScaleType(ImageView.ScaleType.FIT_CENTER);
			firstLine.setText("不能开启蓝牙！");
			secondLine.setText("请在弹出的对话框选择“是”开启蓝牙功能");
			thirdLine.setText("点击上面异常的图标重新开启蓝牙");
			status.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
					discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 3600);
					startActivityForResult(discoverableIntent, BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE);
				}
			});
		}
	 }
}
