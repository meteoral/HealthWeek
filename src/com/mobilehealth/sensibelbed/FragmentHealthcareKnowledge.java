package com.mobilehealth.sensibelbed;

import android.view.View;

import com.mobilehealth.core.ParentFragment;
import com.siat.healthweek.R;

public class FragmentHealthcareKnowledge extends ParentFragment{
	
	public FragmentHealthcareKnowledge() {
		// TODO Auto-generated constructor stub
		this.initPageClassName=FragmentHealthcareKnowledgeMainPage.class.getName();
		this.firstLevelIndex=1;
	}
	
	@Override
	protected void init(View layout) {
		// TODO Auto-generated method stub
		super.init(layout);
		
		ivContainerFrameBg.setImageResource(R.drawable.indicator_mid_bg);
	}
}
