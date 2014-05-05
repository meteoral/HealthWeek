package com.mobilehealth.medicalkit;

import com.mobilehealth.core.FragmentListAdapter;
import com.mobilehealth.core.MainFrameForMedicalKit;
import com.siat.healthweek.R;

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
		
		bottom_tab_on_selectors=new int[]{R.drawable.cloud_data_on_selector, R.drawable.health_knowledge_on_selector, R.drawable.time_space_connecting_on_selector};
		bottom_tab_off_selectors=new int[]{R.drawable.cloud_data_off_selector, R.drawable.health_knowledge_off_selector, R.drawable.time_space_connecting_off_selector};
	}
}
