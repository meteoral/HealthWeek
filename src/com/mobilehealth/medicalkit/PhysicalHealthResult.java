package com.mobilehealth.medicalkit;

import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import com.mobilehealth.core.MainFrame;
import com.siat.healthweek.R;

public class PhysicalHealthResult extends MainFrame{

	@Override
	protected void popularContent() {
		// TODO Auto-generated method stub
		super.popularContent();
		
		View content=View.inflate(this, R.layout.physical_health_result, null);
		rlContent.addView(content,new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		
		init();
	}
	
	private void init()
	{
		setCenterCaption(getResources().getString(R.string.physicalHealthCapthion));
	}
}
