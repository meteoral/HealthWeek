package com.mobilehealth.sensibelbed;

import java.util.ArrayList;

import com.mobilehealth.core.FragmentListAdapter;
import com.mobilehealth.core.MainFrameForMedicalKit;
import com.siat.healthweek.R;

public class SensibleBed extends MainFrameForMedicalKit{
	
	@Override
	protected void init() {
		// TODO Auto-generated method stub
		super.init();
		
		ivTabs[0].setImageResource(R.drawable.health_check_on_selector);
		ivTabs[1].setImageResource(R.drawable.healthcare_knowledge_off_selector);
		ivTabs[2].setImageResource(R.drawable.community_share_off_selector);
		
		ivCurSubjectIcon.setImageResource(R.drawable.sensible_bed);
		tvRightCaption.setText(R.string.sensible_bed);
		
		{
			centerCaptions=new ArrayList<ArrayList<String>>();
			
			ArrayList<String> temp=new ArrayList<String>();
			temp.add("");
			temp.add(getResources().getString(R.string.breath_freq));
			centerCaptions.add(temp);
			
			temp=new ArrayList<String>();
			temp.add("");
			centerCaptions.add(temp);
			
			temp=new ArrayList<String>();
			temp.add("");
			centerCaptions.add(temp);
		}
		
        vpAdapter = new FragmentListAdapter(this, getSupportFragmentManager());
        vpAdapter.addFragment(FragmentHealthCheck.class, null);
        vpAdapter.addFragment(FragmentHealthcareKnowledge.class, null);
        vpAdapter.addFragment(FragmentCommunityShare.class, null);

		vpContent.setAdapter(vpAdapter);
		
		bottom_tab_on_selectors=new int[]{R.drawable.health_check_on_selector, R.drawable.healthcare_knowledge_on_selector, R.drawable.community_share_on_selector};
		bottom_tab_off_selectors=new int[]{R.drawable.health_check_off_selector, R.drawable.healthcare_knowledge_off_selector, R.drawable.community_share_off_selector};
	}
}
