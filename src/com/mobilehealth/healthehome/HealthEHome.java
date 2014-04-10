package com.mobilehealth.healthehome;

import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;

import com.mobilehealth.core.MainFrame;
import com.siat.healthweek.R;

public class HealthEHome extends MainFrame{
	
	@Override
	protected void populateContent() {
		// TODO Auto-generated method stub
		super.populateContent();
		
		View content=View.inflate(this, R.layout.health_e_home, null);
		rlContent.addView(content,new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		
		init();
	}
	
	private void init()
	{
		this.ivCurSubjectIcon.setImageResource(R.drawable.icon_health_e_home);
		this.ivCloudData.setImageResource(R.drawable.health_e_home);
		this.ivHealthKnowledge.setImageResource(R.drawable.home_doctor);
		this.ivTimeSpaceConnecting.setImageResource(R.drawable.sunset_community);
		this.tvRightCaption.setText(R.string.health_e_home);
	}
}
