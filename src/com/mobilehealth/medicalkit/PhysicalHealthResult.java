package com.mobilehealth.medicalkit;

import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import com.mobilehealth.core.MainFrame;
import com.siat.healthweek.R;

public class PhysicalHealthResult extends MainFrame{

	@Override
	protected void populateContent() {
		// TODO Auto-generated method stub
		super.populateContent();
		
		View content=View.inflate(this, R.layout.physical_health_result, null);
		rlContent.addView(content,new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		
		init();
	}
	
	private void init()
	{
		this.tvCenterCaption.setText(R.string.physicalHealthCapthion);
		this.tvCenterCaption.setVisibility(View.VISIBLE);
	}
}
