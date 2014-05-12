package com.mobilehealth.sensibelbed;

import android.view.View;

import com.mobilehealth.core.ParentFragment;
import com.siat.healthweek.R;

public class FragmentCommunityShare extends ParentFragment{
	
	public FragmentCommunityShare() {
		// TODO Auto-generated constructor stub
		this.initPageClassName=FragmentCommunityShareMainPage.class.getName();
		this.firstLevelIndex=2;
	}
	
	@Override
	protected void init(View layout) {
		// TODO Auto-generated method stub
		super.init(layout);
		
		ivContainerFrameBg.setImageResource(R.drawable.indicator_right_bg);
	}
}
