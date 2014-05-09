package com.mobilehealth.dandelionscheme;

import com.mobilehealth.core.ChildPageMessageListener;
import com.mobilehealth.core.FragmentChildPage;
import com.siat.healthweek.R;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class FragmentDandelionSchemeMainPage extends FragmentChildPage{
	
	private ImageView ivBodyCommu;

	@Override
	protected void setLayout() {
		// TODO Auto-generated method stub
		this.layoutId=R.layout.page_dandelion_scheme;
	}

	@Override
	protected void init(View layout) {
		// TODO Auto-generated method stub
		ivBodyCommu = (ImageView) layout.findViewById(R.id.ivBodyCommu);

		ivBodyCommu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((ChildPageMessageListener)getActivity()).changeToPage(pageIndex+1);
			}
		});
	}
}
