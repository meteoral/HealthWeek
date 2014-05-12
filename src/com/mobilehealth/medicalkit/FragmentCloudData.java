package com.mobilehealth.medicalkit;

import android.view.View;

import com.mobilehealth.core.ParentFragment;
import com.siat.healthweek.R;

public class FragmentCloudData extends ParentFragment{
	
	@Override
	protected void init(View layout) {
		// TODO Auto-generated method stub
		super.init(layout);
		
		ivContainerFrameBg.setImageResource(R.drawable.indicator_left_bg);
		
		this.initPageClassName=FragmentCloudDataMainPage.class.getName();
		
		this.firstLevelIndex=0;
	}
}
