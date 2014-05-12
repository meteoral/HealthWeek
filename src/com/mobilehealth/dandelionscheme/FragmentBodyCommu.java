package com.mobilehealth.dandelionscheme;

import com.mobilehealth.core.ChildPageMessageListener;
import com.siat.healthweek.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

public class FragmentBodyCommu extends Fragment{
	
	private RelativeLayout rlIdeaSharer;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		return inflater.inflate(R.layout.page_body_commu, container, false);
		
		//return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		init(view);
		
		((ChildPageMessageListener)getActivity()).childPageChanged(-1, 1);
		
		super.onViewCreated(view, savedInstanceState);
	}
	
	private void init(View layout)
	{
		rlIdeaSharer=(RelativeLayout)layout.findViewById(R.id.rlBodyCommuBlock1);
		rlIdeaSharer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((ChildPageMessageListener)getActivity()).changeToPage(2);
			}
		});
	}

}
