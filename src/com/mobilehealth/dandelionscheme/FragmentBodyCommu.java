package com.mobilehealth.dandelionscheme;

import com.mobilehealth.core.FragmentChildPage;
import com.mobilehealth.core.ParentFragmentActivity;
import com.siat.healthweek.R;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

public class FragmentBodyCommu extends FragmentChildPage{
	
	private RelativeLayout rlIdeaSharer;
	
	public FragmentBodyCommu() {
		// TODO Auto-generated constructor stub
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
				((ParentFragmentActivity)getActivity()).changeToPage(FragmentIdeaSharer.class);
			}
		});
	}
}
