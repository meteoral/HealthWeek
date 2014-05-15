package com.mobilehealth.medicalkit;

import com.mobilehealth.core.ParentFragment;
import com.siat.healthweek.R;

import android.view.View;

public class FragmentTimeSpaceConnecting extends ParentFragment{
	
	@Override
	protected void init(View layout) {
		// TODO Auto-generated method stub
		super.init(layout);
		
		ivContainerFrameBg.setImageResource(R.drawable.indicator_right_bg);
		
		this.curPageClassName=FragmentTimeSpaceConnectingMainPage.class.getName();
		
		this.firstLevelIndex=2;
	}
}
