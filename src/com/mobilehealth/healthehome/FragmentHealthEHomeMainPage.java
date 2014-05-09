package com.mobilehealth.healthehome;

import com.mobilehealth.core.ChildPageMessageListener;
import com.mobilehealth.core.FragmentChildPage;
import com.siat.healthweek.R;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class FragmentHealthEHomeMainPage extends FragmentChildPage{
	
	private ImageView ivHealthExperience;

	@Override
	protected void setLayout() {
		// TODO Auto-generated method stub
		this.layoutId=R.layout.page_health_e_home;
	}

	@Override
	protected void init(View layout) {
		// TODO Auto-generated method stub
		ivHealthExperience = (ImageView) layout.findViewById(R.id.ivHealthExperience);

		ivHealthExperience.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((ChildPageMessageListener)getParentFragment()).changeToPage(1);
			}
		});
	}
}
