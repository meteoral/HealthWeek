package com.mobilehealth.sensibelbed;

import android.view.View;
import android.view.View.OnClickListener;

import com.mobilehealth.core.StartingDisplay;
import com.siat.healthweek.R;

public class StartingDisplaySensibleBed extends StartingDisplay{
	
	@Override
	protected void init() {
		// TODO Auto-generated method stub
		super.init();
		
		rlMain.setBackgroundResource(R.drawable.sensible_bed_starting_display);
		tvDescriptions.setText(R.string.sensible_bed_staring_display_descriptions);
		tvDescriptions.setTextColor(getResources().getColor(R.color.white));
		
		ivClickEnter.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startAnotherActivity(SensibleBed.class);
			}
		});
	}
}
