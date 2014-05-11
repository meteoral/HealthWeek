package com.mobilehealth.sensibelbed;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.mobilehealth.core.ChildPageMessageListener;
import com.mobilehealth.core.FragmentChildPage;
import com.siat.healthweek.R;

public class FragmentHealthCheckMainPage extends FragmentChildPage{
	
	private LinearLayout llHRV;
	private LinearLayout llBreathFreq;
	private LinearLayout llSleepStatus;

	@Override
	protected void setLayout() {
		// TODO Auto-generated method stub
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
				((ChildPageMessageListener)getParentFragment()).changeToPage(FragmentHRV.class);
			}
		});
		
		llBreathFreq.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((ChildPageMessageListener)getParentFragment()).changeToPage(FragmentBreathFreq.class);
			}
		});
		
		llSleepStatus.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((ChildPageMessageListener)getParentFragment()).changeToPage(FragmentSleepStatus.class);
			}
		});
	}
}
