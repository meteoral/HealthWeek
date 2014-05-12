package com.mobilehealth.healthehome;

import com.mobilehealth.core.ParentFragment;
import com.siat.healthweek.R;

import android.view.View;

public class FragmentSunsetCommunity extends ParentFragment{
	
	@Override
	protected void init(View layout) {
		// TODO Auto-generated method stub
		super.init(layout);
		
		ivContainerFrameBg.setImageResource(R.drawable.indicator_right_bg);
		
		this.initPageClassName=FragmentSunsetCommunityMainPage.class.getName();
		
		this.firstLevelIndex=2;
	}
}
