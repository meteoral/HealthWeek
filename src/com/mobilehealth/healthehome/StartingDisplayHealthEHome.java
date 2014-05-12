package com.mobilehealth.healthehome;

import android.view.View;
import android.view.View.OnClickListener;

import com.bit_health.android.ui.activities.BitHealthMainActivity;
import com.mobilehealth.core.StartingDisplay;
import com.siat.healthweek.R;

public class StartingDisplayHealthEHome extends StartingDisplay{

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		super.init();

		rlMain.setBackgroundResource(R.drawable.health_ehome_starting_display);
		tvDescriptions.setText(R.string.health_ehome_staring_display_descriptions);

		ivClickEnter.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startAnotherActivity(BitHealthMainActivity.class);
				finish();
			}
		});
	}
}
