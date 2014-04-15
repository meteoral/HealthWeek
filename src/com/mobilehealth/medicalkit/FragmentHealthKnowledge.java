package com.mobilehealth.medicalkit;

import com.mobilehealth.core.ParentFragment;
import com.siat.healthweek.R;

import android.view.View;

public class FragmentHealthKnowledge extends ParentFragment{
	
	@Override
	protected void init(View layout) {
		// TODO Auto-generated method stub
		super.init(layout);
		
		ivContainerFrameBg.setImageResource(R.drawable.indicator_mid_bg);
		
		this.childFragmentArray=new String[]{
				FragmentHealthKnowledgeMainPage.class.getName()};
		
		firstLevelIndex=1;
	}
}
