package com.mobilehealth.healthehome;

import java.util.HashMap;

import com.mobilehealth.core.FragmentListAdapter;
import com.mobilehealth.core.ActivityViewPager;
import com.siat.healthweek.R;

public class HealthEHome extends ActivityViewPager{
	
	@Override
	protected void init() {
		// TODO Auto-generated method stub
		super.init();
		
		ivTabs[0].setImageResource(R.drawable.health_ehome_on_selector);
		ivTabs[1].setImageResource(R.drawable.home_doctor_off_selector);
		ivTabs[2].setImageResource(R.drawable.sunset_community_off_selector);
		
		ivCurSubjectIcon.setImageResource(R.drawable.health_ehome_icon);
		tvRightCaption.setText(R.string.health_e_home);
		
		{
			centerCaptions=new HashMap<String, String>();
			centerCaptions.put(FragmentHealthExperience.class.getName(), getResources().getString(R.string.health_experience));
		}
		
        vpAdapter = new FragmentListAdapter(this, getSupportFragmentManager());
        vpAdapter.addFragment(FragmentHealthEHome.class, null);
        vpAdapter.addFragment(FragmentHomeDoctor.class, null);
        vpAdapter.addFragment(FragmentSunsetCommunity.class, null);

		vpContent.setAdapter(vpAdapter);
		
		bottom_tab_on_selectors=new int[]{R.drawable.health_ehome_on_selector, R.drawable.home_doctor_on_selector, R.drawable.sunset_community_on_selector};
		bottom_tab_off_selectors=new int[]{R.drawable.health_ehome_off_selector, R.drawable.home_doctor_off_selector, R.drawable.sunset_community_off_selector};
	}
}
