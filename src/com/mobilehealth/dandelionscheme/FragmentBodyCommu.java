package com.mobilehealth.dandelionscheme;

import com.mobilehealth.core.ChildPageMessageListener;
import com.mobilehealth.core.FragmentChildPage;
import com.siat.healthweek.R;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

public class FragmentBodyCommu extends FragmentChildPage{
	
	private RelativeLayout rlIdeaSharer;
	
	@Override
	protected void setLayout() {
		// TODO Auto-generated method stub
		this.layoutId=R.layout.page_body_commu;
	}
	
	@Override
	protected void init(View layout) {
		// TODO Auto-generated method stub
		
		rlIdeaSharer=(RelativeLayout)layout.findViewById(R.id.rlBodyCommuBlock1);
		rlIdeaSharer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((ChildPageMessageListener)getActivity()).changeToPage(FragmentIdeaSharer.class);
			}
		});
	}
}
