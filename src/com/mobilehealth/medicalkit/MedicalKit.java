package com.mobilehealth.medicalkit;

import com.mobilehealth.core.FragmentListAdapter;
import com.mobilehealth.core.MainFrameForMedicalKit;

public class MedicalKit extends MainFrameForMedicalKit{
	
	@Override
	protected void init() {
		// TODO Auto-generated method stub
		super.init();
		
		vpAdapter = new FragmentListAdapter(this, getSupportFragmentManager());
		vpAdapter.addFragment(FragmentCloudData.class, null);
		vpAdapter.addFragment(FragmentHealthKnowledge.class, null);
		vpAdapter.addFragment(FragmentTimeSpaceConnecting.class, null);

		vpContent.setAdapter(vpAdapter);
	}
}
