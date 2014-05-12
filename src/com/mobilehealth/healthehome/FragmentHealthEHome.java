package com.mobilehealth.healthehome;

import android.view.View;

import com.mobilehealth.core.ParentFragment;
import com.siat.healthweek.R;

public class FragmentHealthEHome extends ParentFragment{

	@Override
	protected void init(View layout) {
		// TODO Auto-generated method stub
		super.init(layout);
		
		ivContainerFrameBg.setImageResource(R.drawable.indicator_left_bg);
		
		this.childFragmentArray=new String[]{
				FragmentHealthEHomeMainPage.class.getName(),
				FragmentHealthExperience.class.getName()};
	}

}
