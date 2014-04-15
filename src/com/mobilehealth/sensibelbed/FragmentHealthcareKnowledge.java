package com.mobilehealth.sensibelbed;

import android.view.View;

import com.mobilehealth.core.ParentFragment;
import com.siat.healthweek.R;

public class FragmentHealthcareKnowledge extends ParentFragment{
	
	@Override
	protected void init(View layout) {
		// TODO Auto-generated method stub
		super.init(layout);
		
		ivContainerFrameBg.setImageResource(R.drawable.indicator_mid_bg);
		
		this.childFragmentArray=new String[]{
				FragmentHealthcareKnowledgeMainPage.class.getName()};
	}
}
