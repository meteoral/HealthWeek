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
		
		ivTab0.setImageResource(R.drawable.health_check);
		ivTab1.setImageResource(R.drawable.healthcare_knowledge);
		ivTab2.setImageResource(R.drawable.community_share);
		
		ivCurSubjectIcon.setImageResource(R.drawable.sensible_bed);
		tvRightCaption.setText(R.string.sensible_bed);
		
		{
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
	}
}
