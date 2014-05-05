package com.mobilehealth.dandelionscheme;

import android.view.View;
import android.view.View.OnClickListener;

import com.mobilehealth.core.StartingDisplay;
import com.siat.healthweek.R;

public class StartingDisplayBodyCommu extends StartingDisplay{
	
	@Override
	protected void init() {
		// TODO Auto-generated method stub
		super.init();
		
		rlMain.setBackgroundResource(R.drawable.body_commu_starting_display);
		tvDescriptions.setText(R.string.body_commu_staring_display_descriptions);
		
		ivClickEnter.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startAnotherActivity(DandelionScheme.class);
			}
		});
	}
}
