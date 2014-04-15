package com.mobilehealth.healthehome;

import com.mobilehealth.core.FragmentListAdapter;
import com.mobilehealth.core.MainFrameForMedicalKit;
import com.siat.healthweek.R;

public class HealthEHome extends MainFrameForMedicalKit{
	
	@Override
	protected void init() {
		// TODO Auto-generated method stub
		super.init();
		
		ivTab0.setImageResource(R.drawable.health_e_home);
		ivTab1.setImageResource(R.drawable.home_doctor);
		ivTab2.setImageResource(R.drawable.sunset_community);
		
		ivCurSubjectIcon.setImageResource(R.drawable.health_e_home);
		tvRightCaption.setText(R.string.health_e_home);
		
        vpAdapter = new FragmentListAdapter(this, getSupportFragmentManager());
        vpAdapter.addFragment(FragmentHealthEHome.class, null);
        vpAdapter.addFragment(FragmentHomeDoctor.class, null);
        vpAdapter.addFragment(FragmentSunsetCommunity.class, null);

		vpContent.setAdapter(vpAdapter);
	}
}
