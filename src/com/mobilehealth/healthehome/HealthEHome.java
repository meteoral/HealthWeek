package com.mobilehealth.healthehome;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mobilehealth.core.MainFrame;
import com.siat.healthweek.R;

public class HealthEHome extends MainFrame{
	
	private ImageView ivHealthExperience;
	
	@Override
	protected void populateContent() {
		// TODO Auto-generated method stub
		super.populateContent();
		
		View content=View.inflate(this, R.layout.health_e_home, null);
		rlContent.addView(content,new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		
		init(content);
	}
	
	private void init(View content)
	{
		this.ivCurSubjectIcon.setImageResource(R.drawable.icon_health_e_home);
		this.ivCloudData.setImageResource(R.drawable.health_e_home);
		this.ivHealthKnowledge.setImageResource(R.drawable.home_doctor);
		this.ivTimeSpaceConnecting.setImageResource(R.drawable.sunset_community);
		this.tvRightCaption.setText(R.string.health_e_home);
		
		ivHealthExperience=(ImageView)content.findViewById(R.id.ivHealthExperience);
		
		ivHealthExperience.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(HealthEHome.this, HealthExperience.class);
				startActivity(i);
			}
		});
	}
}