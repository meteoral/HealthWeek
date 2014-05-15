package com.mobilehealth.sensibelbed;

import java.util.HashMap;

import android.content.Intent;
import com.mobilehealth.core.FragmentListAdapter;
import com.mobilehealth.core.ActivityViewPager;
import com.siat.healthweek.R;

public class SensibleBed extends ActivityViewPager{

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
			centerCaptions=new HashMap<String, String>();
			centerCaptions.put(FragmentHRV.class.getName(), "实时数据");
			centerCaptions.put(FragmentBreathFreq.class.getName(), "监控数据");
			centerCaptions.put(FragmentSleepStatus.class.getName(), "历史数据");
		}

        vpAdapter = new FragmentListAdapter(this, getSupportFragmentManager());
        vpAdapter.addFragment(FragmentHealthCheck.class, null);
        vpAdapter.addFragment(FragmentHealthcareKnowledge.class, null);
        vpAdapter.addFragment(FragmentCommunityShare.class, null);

		vpContent.setAdapter(vpAdapter);

		bottom_tab_on_selectors=new int[]{R.drawable.health_check_on_selector, R.drawable.healthcare_knowledge_on_selector, R.drawable.community_share_on_selector};
		bottom_tab_off_selectors=new int[]{R.drawable.health_check_off_selector, R.drawable.healthcare_knowledge_off_selector, R.drawable.community_share_off_selector};
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(3600 == resultCode)
		{
			vpAdapter.getItem(0).onActivityResult(requestCode, resultCode, data);
		}
	 }
}
