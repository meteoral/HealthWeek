package com.mobilehealth.medicalkit;

import android.view.View;
import android.view.View.OnClickListener;

import com.mobilehealth.core.StartingDisplay;

public class StartingDisplayMedicalkit extends StartingDisplay{
	
	@Override
	protected void init() {
		// TODO Auto-generated method stub
		super.init();
		
		ivClickEnter.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startAnotherActivity(MedicalKit.class);
			}
		});
	}
}
