package com.mobilehealth.medicalkit;

import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;

import com.liuqingwei.healthweek.R;
import com.mobilehealth.core.MainFrame;

public class Medicalkit extends MainFrame {

	@Override
	protected void popularContent() {
		// TODO Auto-generated method stub
		super.popularContent();

		View content=View.inflate(this, R.layout.medicalkit, null);
		rlContent.addView(content,new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
	}

}
