package com.mobilehealth.medicalkit;

import com.mobilehealth.core.ChildPageListener;
import com.mobilehealth.core.FragmentChildPage;
import com.siat.healthweek.R;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class FragmentCloudDataMainPage extends FragmentChildPage{
	
	private ImageView ivPhysicalHealthResult;
	
	public FragmentCloudDataMainPage() {
		// TODO Auto-generated constructor stub
		this.layoutId=R.layout.page_cloud_data;
	}

	@Override
	protected void init(View layout) {
		// TODO Auto-generated method stub
		ivPhysicalHealthResult = (ImageView) layout.findViewById(R.id.ivPhysicalHealthResult);

		ivPhysicalHealthResult.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((ChildPageListener)getParentFragment()).changeToPage(FragmentPhysicalHealthResult.class);
			}
		});
	}
}
