package com.mobilehealth.healthehome;

import com.mobilehealth.core.ParentFragment;
import com.siat.healthweek.R;

import android.view.View;

public class FragmentHomeDoctor extends ParentFragment{
	
	@Override
	protected void init(View layout) {
		// TODO Auto-generated method stub
		super.init(layout);
		
		ivContainerFrameBg.setImageResource(R.drawable.indicator_mid_bg);
		
		this.childFragmentArray=new String[]{
				FragmentHomeDoctorMainPage.class.getName()};
	}
}
